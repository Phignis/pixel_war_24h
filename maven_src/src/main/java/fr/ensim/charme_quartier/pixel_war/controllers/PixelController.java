package fr.ensim.charme_quartier.pixel_war.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ensim.charme_quartier.pixel_war.model.*;
import fr.ensim.charme_quartier.pixel_war.model.Canvas;
import fr.ensim.charme_quartier.pixel_war.service.AuthentifierService;
import fr.ensim.charme_quartier.pixel_war.service.CanvasService;
import fr.ensim.charme_quartier.pixel_war.service.ProtectImageService;
import fr.ensim.charme_quartier.pixel_war.service.WorkerService;
import fr.ensim.charme_quartier.pixel_war.utils.ImageUtils;
import io.socket.client.IO;
import io.socket.emitter.Emitter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;

import io.socket.client.Socket;

import static java.util.Collections.singletonMap;

@Controller
public class PixelController {
    @Autowired
    private AuthentifierService as;

    @Autowired
    private WorkerService ws;

    @Autowired
    private CanvasService cs;

    @Autowired
    private ProtectImageService pis;

    private static Socket socket;

    private static final String TEAM_NAME = "Le charme du quartier";

    @GetMapping("/")
    public String displayHome(Model model) {

        model.addAttribute("colors", EUseableColors.getAllColors());
        return "index";
    }

    @PostMapping("/pixel")
    public String putPixel(@RequestParam(name="x_coord", required = true) int xCoord,
                           @RequestParam(name="y_coord", required = true) int yCoord,
                           @RequestParam(name="color", required = true) EUseableColors color,
                           RestTemplate restTemplate) {


        String token = as.getToken(restTemplate);
        int teamId = as.getTeamId(restTemplate, TEAM_NAME, token);
        Worker[] workers = ws.getWorkersOf(restTemplate, token, teamId);

        HttpHeaders h = new HttpHeaders();
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
        body.put("chunk", 13);
        body.put("color", color.getKey());
        body.put("pos_x", xCoord);
        body.put("pos_y", yCoord);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(body, h), String.class);
        return response.getBody();


        //return response.getBody() + " TEAM ID : " + as.getTeamId(restTemplate, TEAM_NAME, token);


    }
    public String putPixelabs(@RequestParam(name="x_coord", required = true) int xCoord,
                           @RequestParam(name="y_coord", required = true) int yCoord,
                           @RequestParam(name="color", required = true) EUseableColors color,
                           RestTemplate restTemplate, Point offset) {


        String token = as.getToken(restTemplate);
        int teamId = as.getTeamId(restTemplate, TEAM_NAME, token);
        Worker[] workers = ws.getWorkersOf(restTemplate, token, teamId);

        HttpHeaders h = new HttpHeaders();
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
        body.put("color", color.getKey());
        body.put("pos_x", xCoord + offset.x);
        body.put("pos_y", yCoord + offset.y);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(body, h), String.class);
        return response.getBody();


        //return response.getBody() + " TEAM ID : " + as.getTeamId(restTemplate, TEAM_NAME, token);


    }

    @GetMapping("/jinbe")
    public String insertImage(RestTemplate restTemplate) throws IOException {
        InputStream image = this.getClass().getClassLoader().getResourceAsStream("jinbe.jpg");
        BufferedImage bi = ImageIO.read(image);
        BufferedImage resized = ImageUtils.resizeImage(bi, 100, 100);
        BufferedImage recoloredToPrint = ImageUtils.recolor(resized);

        pis.registerProtectionFor(new CanvasCoords(0, 0, 7), recoloredToPrint);
        String lastState = "";
        protect(as.getToken(restTemplate), restTemplate);

        for (int y = 0; y < recoloredToPrint.getHeight(); y++) {
            for (int x = 0; x < recoloredToPrint.getWidth(); x++) {
                int rgb = recoloredToPrint.getRGB(x, y);
                Color pixelColor = new Color(rgb);
                EUseableColors colorName = EUseableColors.getEUseableColors(pixelColor);
                System.out.println("Pixel at (" + x + ", " + y + ") has color: " + colorName);
                lastState = putPixelabs(x, y, colorName, restTemplate, new Point(50,50));
            }
        }
        return lastState;

    }

    @GetMapping("/token")
    public String getToken(RestTemplate restTemplate) {
        return as.getToken(restTemplate);
    }

    @GetMapping("/protect")
    public String protect(String token, RestTemplate rt) {
        Canvas canva = cs.getCanvaOf(rt, token);
        int teamId = as.getTeamId(rt, TEAM_NAME, token);
        Worker[] workers = ws.getWorkersOf(rt, token, 4);

        URI uri = URI.create("http://149.202.79.34:8085/api/socket");
        IO.Options options = IO.Options.builder()
                .setAuth(singletonMap("token", token))
                .build();

        socket = IO.socket(uri, options);
        socket.on("pixelUpdated", (Object... responses) -> {
            System.out.println(Arrays.toString(responses));
            PixelUpdatedResponse p = null;
            try {
                p = new ObjectMapper().readValue(((JSONObject) responses[0]).toString(), PixelUpdatedResponse.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            CanvasCoords trigerringCoord = new CanvasCoords(p.getX(), p.getY());
            Color c = new Color(p.getRgb()[0], p.getRgb()[1], p.getRgb()[2]);
            EUseableColors color = EUseableColors.getEUseableColors(c);
            Worker wToUse = ws.getAvailableWorker(workers);

            assert color != null;
            pis.triggerProtection(trigerringCoord, color, token, wToUse.getId(), canva.getNom());
        });
        socket.connect();
        return "index";
    }

}
