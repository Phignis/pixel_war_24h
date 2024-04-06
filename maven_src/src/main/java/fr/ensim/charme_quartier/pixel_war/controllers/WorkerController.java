package fr.ensim.charme_quartier.pixel_war.controllers;

import fr.ensim.charme_quartier.pixel_war.model.Worker;
import fr.ensim.charme_quartier.pixel_war.service.AuthentifierService;
import fr.ensim.charme_quartier.pixel_war.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
public class WorkerController {
    @Autowired
    public AuthentifierService as;

    @Autowired
    public WorkerService ws;

    @GetMapping("workers")
    public String getTeamWorkers(RestTemplate restTemplate) {
        String token = as.getToken(restTemplate);
        int id = as.getTeamId(restTemplate, "Le charme du quartier", token);
        StringBuilder returned = new StringBuilder();
        for(Worker w : ws.getWorkersOf(restTemplate, token, id)) {
            returned.append(w.getId()).append(" ").append(w.getEquipeProprietaire()).append("\n");
        }
        return returned.toString();
    }
}
