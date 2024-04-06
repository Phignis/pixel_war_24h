package fr.ensim.charme_quartier.pixel_war.controllers;

import fr.ensim.charme_quartier.pixel_war.model.Canvas;
import fr.ensim.charme_quartier.pixel_war.model.Chunk;
import fr.ensim.charme_quartier.pixel_war.model.EUseableColors;
import fr.ensim.charme_quartier.pixel_war.model.Worker;
import fr.ensim.charme_quartier.pixel_war.service.AuthentifierService;
import fr.ensim.charme_quartier.pixel_war.service.CanvasService;
import fr.ensim.charme_quartier.pixel_war.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Objects;

@Controller
public class PixelController {
    @Autowired
    AuthentifierService as;

    @Autowired
    WorkerService ws;

    @Autowired
    CanvasService cs;

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
        int teamId = as.getTeamId(restTemplate, "Le charme du quartier", token);
        Worker[] workers = ws.getWorkersOf(restTemplate, token, teamId);

        HttpHeaders h = new org.springframework.http.HttpHeaders();
        h.setBearerAuth(token);
        h.add("Content-Type", MediaType.APPLICATION_JSON.toString());


        String url = "http://149.202.79.34:8085/api/equipes/"+ teamId +"/workers/"+ workers[0].getId() + "/pixel";

        int chunkId = -1;

        Canvas canva = cs.getCanvaOf(restTemplate, token);
        for (Chunk c: canva.getChunks()) {
            if(c.getType().equals("privé") && c.getEquipeProprietaire() == teamId) {
                chunkId = c.getId();
            }
        }
        if(chunkId == -1) {
            throw new IllegalStateException("chunk not found for you");
        }

        System.out.println(chunkId);

        var body = new HashMap<String, Object>();

        body.put("canvas", canva.getNom());
        body.put("chunk", chunkId);
        body.put("color",  color.getKey());
        body.put("pos_x", xCoord);
        body.put("pos_y", yCoord);
        System.out.println(body.toString());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(body, h), String.class);
        return response.getBody();
    }

    @GetMapping("/token")
    public String getToken(RestTemplate restTemplate){
        return as.getToken(restTemplate);
    }


}
