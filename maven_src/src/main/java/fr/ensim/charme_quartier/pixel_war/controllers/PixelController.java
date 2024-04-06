package fr.ensim.charme_quartier.pixel_war.controllers;

import fr.ensim.charme_quartier.pixel_war.model.ApiEquipesResponse;
import fr.ensim.charme_quartier.pixel_war.model.equipe;
import fr.ensim.charme_quartier.pixel_war.service.AuthentifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
public class PixelController {
    @Autowired
    AuthentifierService as;



    @GetMapping("/")
    public String putPixel(RestTemplate restTemplate) {
        String url = "http://149.202.79.34:8085/api/equipes";
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ6MktTejRUcWdvMHkzQzZ3czBoRmQ2cXBjV241WEdueWRpUThRUWQtWWNzIn0";

        HttpHeaders h = new org.springframework.http.HttpHeaders();
        h.setBearerAuth(token);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(h), String.class);

        return "test";
    }


    @GetMapping("/equipes")
    public String equipes (RestTemplate rt) {
        String querry = "http://149.202.79.34:8085/api/equipes";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(as.getToken(rt));
        ResponseEntity<String> aer =rt.exchange(querry, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        return aer.getBody();
    }

    @GetMapping("/token")
    public String getToken(RestTemplate restTemplate){
        return as.getToken(restTemplate);
    }
}
