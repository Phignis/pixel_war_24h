package fr.ensim.charme_quartier.pixel_war.service;

import fr.ensim.charme_quartier.pixel_war.model.Team;
import fr.ensim.charme_quartier.pixel_war.model.Worker;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WorkerService {
    public Worker[] getWorkersOf(RestTemplate restTemplate, String token, int teamId) throws IllegalStateException {
        String url = "http://149.202.79.34:8085/api/equipes/" + teamId;
        HttpHeaders h = new org.springframework.http.HttpHeaders();
        h.setBearerAuth(token);

        ResponseEntity<Team> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(h), Team.class);
        if(response.getBody() == null) throw new IllegalStateException("response is null");
        return response.getBody().getWorkers();
    }
}
