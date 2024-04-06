package fr.ensim.charme_quartier.pixel_war.service;

import fr.ensim.charme_quartier.pixel_war.model.AuthentifierToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthentifierService {
    public String getToken(RestTemplate restTemplate){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("password", "%dpcDp-9ZY%(b49");
        map.add("username", "charme_quartier");
        map.add("grant_type", "password");
        map.add("client_id", "pixel-war");


        String query = "http://149.202.79.34:8081/realms/codelemans/protocol/openid-connect/token";
        ResponseEntity<AuthentifierToken> response = restTemplate.postForEntity(query, map, AuthentifierToken.class);
        return response.getBody().getAccess_token();
    }
}
