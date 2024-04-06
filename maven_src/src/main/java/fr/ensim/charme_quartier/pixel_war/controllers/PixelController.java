package fr.ensim.charme_quartier.pixel_war.controllers;

import fr.ensim.charme_quartier.pixel_war.model.*;
import fr.ensim.charme_quartier.pixel_war.model.Canvas;
import fr.ensim.charme_quartier.pixel_war.service.AuthentifierService;
import fr.ensim.charme_quartier.pixel_war.service.CanvasService;
import fr.ensim.charme_quartier.pixel_war.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Objects;

@RestController
public class PixelController {
    @Autowired
    AuthentifierService as;

    @Autowired
    WorkerService ws;

    @Autowired
    CanvasService cs;
    private int indexWorker = 0;

    @GetMapping("/")
    public String putPixel(RestTemplate restTemplate, int X, int Y, String PixelColor) {

        String token = as.getToken(restTemplate);
        int teamId = as.getTeamId(restTemplate, "Le charme du quartier", token);
        Worker[] workers = ws.getWorkersOf(restTemplate, token, teamId);

        HttpHeaders h = new org.springframework.http.HttpHeaders();
        h.setBearerAuth(token);
        h.add("Content-Type", MediaType.APPLICATION_JSON.toString());

        System.out.println("workers selection");
        System.out.println("worker : " + workers[indexWorker].getId());


        Instant now = Instant.now();
        System.out.println("worker time :" + workers[indexWorker].getDateDernierPixelPose());
        while (workers[indexWorker].IsOnCooldown()) {
            indexWorker++;
            System.out.println("worker : " + workers[indexWorker].getId());
            System.out.println(workers[indexWorker].getDateDernierPixelPose());
            if (indexWorker == workers.length - 1) {
                System.out.println("refresh");
                //workers = ws.getWorkersOf(restTemplate, token, teamId);
                indexWorker = 0;
            }
        }

        System.out.println("worker selected : " + indexWorker);
        String url = "http://149.202.79.34:8085/api/equipes/" + teamId + "/workers/" + workers[indexWorker].getId() + "/pixel";

        int chunkId = -1;

        Canvas canva = cs.getCanvaOf(restTemplate, token);
        for (Chunk c : canva.getChunks()) {
            if (c.getType().equals("privé") && c.getEquipeProprietaire() == teamId) {
                chunkId = c.getId();
            }
        }
        if (chunkId == -1) {
            throw new IllegalStateException("chunk not found for you");
        }

        System.out.println(chunkId);

        var body = new HashMap<String, Object>();

        body.put("canvas", canva.getNom());
        body.put("chunk", chunkId);
        body.put("color", PixelColor);
        body.put("pos_x", X);
        body.put("pos_y", Y);

        System.out.println("x: " + X + " y: " + Y);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(body, h), String.class);
        System.out.println("pixel put");
        System.out.println("");
        return response.getBody();


        //return response.getBody() + " TEAM ID : " + as.getTeamId(restTemplate, "Le charme du quartier", token);


    }

    @GetMapping("/jinbe")
    public String insertImage(RestTemplate restTemplate) throws IOException {
        InputStream image = this.getClass().getClassLoader().getResourceAsStream("jinbe.jpg");
        BufferedImage bi = ImageIO.read(image);
        BufferedImage rezized = ImageModifier.resizeImage(bi, 50, 50);
        BufferedImage recolor = ImageModifier.recolor(rezized);
        String laststate = "";
        for (int y = 0; y < recolor.getHeight(); y++) {
            for (int x = 0; x < recolor.getWidth(); x++) {
                int rgb = recolor.getRGB(x, y);
                Color pixelColor = new Color(rgb);
                String colorName = EUseableColors.findColorName(pixelColor);
                System.out.println("Pixel at (" + x + ", " + y + ") has color: " + colorName);
                laststate = putPixel(restTemplate, x, y, colorName);
            }
        }

        return laststate;
    }

    @GetMapping("/token")
    public String getToken(RestTemplate restTemplate) {
        return as.getToken(restTemplate);
    }
}
