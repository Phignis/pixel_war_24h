package fr.ensim.charme_quartier.pixel_war.controllers;

import fr.ensim.charme_quartier.pixel_war.service.AuthentifierService;
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

    @GetMapping("/")
    public String putPixel(RestTemplate restTemplate) {
        String url = "http://149.202.79.34:8085/api/equipes";
        String token = as.getToken(restTemplate);

        HttpHeaders h = new org.springframework.http.HttpHeaders();
        h.setBearerAuth(token);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(h), String.class);

        return response.getBody() + " TEAM ID : " + as.getTeamId(restTemplate, "Le charme du quartier", token);
    }

    @GetMapping("/token")
    public String getToken(RestTemplate restTemplate){
        return as.getToken(restTemplate);
    }
}
