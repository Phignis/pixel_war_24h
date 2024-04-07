package fr.ensim.charme_quartier.pixel_war.service;

import fr.ensim.charme_quartier.pixel_war.model.AuthentifierToken;
import fr.ensim.charme_quartier.pixel_war.model.Team;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class AuthentifierService {
    private AuthentifierToken token;
    public AuthentifierToken getTokenFromAPI(RestTemplate restTemplate){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("password", "%dpcDp-9ZY%(b49");
        map.add("username", "charme_quartier");
        map.add("grant_type", "password");
        map.add("client_id", "pixel-war");


        String query = "http://149.202.79.34:8081/realms/codelemans/protocol/openid-connect/token";
        ResponseEntity<AuthentifierToken> response = restTemplate.postForEntity(query, map, AuthentifierToken.class);
        return response.getBody();
    }

    public String getToken(RestTemplate restTemplate){

        if (token == null) {
            token = getTokenFromAPI(restTemplate);
            return token.getAccess_token();
        }
        if(token.getExpireTime().isAfter(LocalDateTime.now())){
            return token.getAccess_token();
        }
        else{
            token = getTokenFromAPI(restTemplate);
            return token.getAccess_token();
        }
    }

    public int getTeamId(RestTemplate restTemplate, String teamName, String token) throws InvalidParameterException {
        String url = "http://149.202.79.34:8085/api/equipes";

        HttpHeaders h = new org.springframework.http.HttpHeaders();
        h.setBearerAuth(token);

        ResponseEntity<Team[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(h), Team[].class);

        try {
            for (Team t: Objects.requireNonNull(response.getBody())) {
                if(t.getNom().equals(teamName)) return t.getId();
            }
        } catch (Exception e) {
            // TODO: change exception type
            throw new InvalidParameterException("API is not available");
        }
        throw new InvalidParameterException("team name is not found");
    }
}
