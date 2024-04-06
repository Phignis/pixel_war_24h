package fr.ensim.charme_quartier.pixel_war.controllers;

import fr.ensim.charme_quartier.pixel_war.model.Canvas;
import fr.ensim.charme_quartier.pixel_war.model.Worker;
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

@RestController
public class PixelController {
    @Autowired
    AuthentifierService as;

    @Autowired
    WorkerService ws;

    @Autowired
    CanvasService cs;

    @GetMapping("/")
    public String putPixel(RestTemplate restTemplate) {

        String token = as.getToken(restTemplate);
        int teamId = as.getTeamId(restTemplate, "Le charme du quartier", token);
        Worker[] workers = ws.getWorkersOf(restTemplate, token, teamId);

        HttpHeaders h = new org.springframework.http.HttpHeaders();
        h.setBearerAuth(token);

        String url = "http://149.202.79.34:8085/api/equipes/"+ teamId +"/workers/"+ workers[0].getId() + "/pixel";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(h), String.class);

        Canvas canva = cs.getCanvaOf(restTemplate, token);

        return canva.getNom();



        //return response.getBody() + " TEAM ID : " + as.getTeamId(restTemplate, "Le charme du quartier", token);




    }

    @GetMapping("/token")
    public String getToken(RestTemplate restTemplate){
        return as.getToken(restTemplate);
    }
}
