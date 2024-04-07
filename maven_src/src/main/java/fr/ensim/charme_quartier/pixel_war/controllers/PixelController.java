package fr.ensim.charme_quartier.pixel_war.controllers;

import fr.ensim.charme_quartier.pixel_war.model.*;
import fr.ensim.charme_quartier.pixel_war.model.Canvas;
import fr.ensim.charme_quartier.pixel_war.service.AuthentifierService;
import fr.ensim.charme_quartier.pixel_war.service.CanvasService;
import fr.ensim.charme_quartier.pixel_war.utils.ImageUtils;
import fr.ensim.charme_quartier.pixel_war.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.HashMap;
import java.util.Random;

@RestController
public class PixelController {
    @Autowired
    AuthentifierService as;

    @Autowired
    WorkerService ws;

    @Autowired
    CanvasService cs;

    @GetMapping("/")
    public String putPixelChunk(RestTemplate restTemplate, int X, int Y, String PixelColor, int chunkid) {
        String token = as.getToken(restTemplate);
        int teamId = as.getTeamId(restTemplate, "Le charme du quartier", token);
        Worker[] workers = ws.getWorkersOf(restTemplate, token, teamId);

        HttpHeaders h = new org.springframework.http.HttpHeaders();
        h.setBearerAuth(token);
        h.add("Content-Type", MediaType.APPLICATION_JSON.toString());

        Worker selectedWorker = ws.getAvailableWorker(workers);

        String url = "http://149.202.79.34:8085/api/equipes/" + teamId + "/workers/" + selectedWorker.getId() + "/pixel";

        // TODO: move this algorithm so service
        //int chunkId = -1;

        Canvas canva = cs.getCanvaOf(restTemplate, token);
        /*for (Chunk c : canva.getChunks()) {
            if (c.getType().equals("privé") && c.getEquipeProprietaire() == teamId) {
                chunkId = c.getId();
            }
        }
        if (chunkId == -1) {
            throw new IllegalStateException("chunk not found for you");
        }*/

        System.out.println(chunkid);

        var body = new HashMap<String, Object>();

        body.put("canvas", canva.getNom());
        body.put("chunk", chunkid);
        body.put("color", PixelColor);
        body.put("pos_x", X);
        body.put("pos_y", Y);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(body, h), String.class);
        return response.getBody();


        //return response.getBody() + " TEAM ID : " + as.getTeamId(restTemplate, "Le charme du quartier", token);


    }

    public String putPixelabsolu(RestTemplate restTemplate, int X, int Y, String PixelColor, Point offset) {
        String token = as.getToken(restTemplate);
        int teamId = as.getTeamId(restTemplate, "Le charme du quartier", token);
        Worker[] workers = ws.getWorkersOf(restTemplate, token, teamId);

        HttpHeaders h = new org.springframework.http.HttpHeaders();
        h.setBearerAuth(token);
        h.add("Content-Type", MediaType.APPLICATION_JSON.toString());

        Worker selectedWorker = ws.getAvailableWorker(workers);

        String url = "http://149.202.79.34:8085/api/equipes/" + teamId + "/workers/" + selectedWorker.getId() + "/pixel";

        // TODO: move this algorithm so service
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
        body.put("color", PixelColor);
        body.put("pos_x", X+ offset.x);
        body.put("pos_y", Y+ offset.y);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(body, h), String.class);
        return response.getBody();


        //return response.getBody() + " TEAM ID : " + as.getTeamId(restTemplate, "Le charme du quartier", token);
    }

    @GetMapping("/attack")
    public void attack(RestTemplate restTemplate) {
        Random r = new Random();
        while(true) {
            int x = r.nextInt(50);
            int y = r.nextInt(50);
            System.out.println("x: " + x + " y : " + y);
            putPixelChunk(restTemplate, x, y, EUseableColors.FOREST_GREEN.getKey(), 18);
        }
    }
    @GetMapping("/desine")
    public String insertImage(RestTemplate restTemplate) throws IOException {
        InputStream image = this.getClass().getClassLoader().getResourceAsStream("draco.jpg");
        BufferedImage bi = ImageIO.read(image);
        BufferedImage rezized = ImageUtils.resizeImage(bi, 50, 50);
        BufferedImage recoloredToPrint = ImageUtils.recolor(rezized);
        String lastState = "";
        for (int y = 0; y < recoloredToPrint.getHeight(); y++) {
            for (int x = 0; x < recoloredToPrint.getWidth(); x++) {
                int rgb = recoloredToPrint.getRGB(x, y);
                Color pixelColor = new Color(rgb);
                String colorName = EUseableColors.findColorName(pixelColor);
                System.out.println("Pixel at (" + x + ", " + y + ") has color: " + colorName);
                //lastState = putPixelabsolu(restTemplate, x, y, colorName, new Point(50,50));
                lastState = putPixelChunk(restTemplate, x, y, colorName, 13);
            }
        }

        return lastState;
    }

    @GetMapping("/token")
    public String getToken(RestTemplate restTemplate) {
        return as.getToken(restTemplate);
    }
}
