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

    private int indexWorker = 0;

    public Worker[] getWorkersOf(RestTemplate restTemplate, String token, int teamId) throws IllegalStateException {
        String url = "http://149.202.79.34:8085/api/equipes/" + teamId;
        HttpHeaders h = new org.springframework.http.HttpHeaders();
        h.setBearerAuth(token);

        ResponseEntity<Team> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(h), Team.class);
        if(response.getBody() == null) throw new IllegalStateException("response is null");
        return response.getBody().getWorkers();
    }
    public Worker getWorkerStat(RestTemplate restTemplate, String token, int teamId, int workerid) throws IllegalStateException {
        String url = "http://149.202.79.34:8085/api/equipes/" + teamId+"/workers/" + workerid;
        HttpHeaders h = new org.springframework.http.HttpHeaders();
        h.setBearerAuth(token);

        ResponseEntity<Worker> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(h), Worker.class);
        if(response.getBody() == null) throw new IllegalStateException("response is null");
        return response.getBody();
    }

    public Worker getAvailableWorker(Worker[] workers) {
        while (workers[indexWorker].IsOnCooldown()) {
            indexWorker = (indexWorker + 1) % (workers.length - 1);
            System.out.println("worker : " + workers[indexWorker].getId());
            System.out.println(workers[indexWorker].getDateDernierPixelPose());
        }
        return workers[indexWorker];
    }

}
