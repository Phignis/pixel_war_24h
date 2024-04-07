package fr.ensim.charme_quartier.pixel_war.service;

import fr.ensim.charme_quartier.pixel_war.model.Canvas;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class CanvasService {
    Canvas c;

    public Canvas getCanvaOfAPI(RestTemplate restTemplate, String token) throws IllegalStateException {
        String url = "http://149.202.79.34:8085/api/canvas/" + 1;
        HttpHeaders h = new HttpHeaders();
        h.setBearerAuth(token);

        ResponseEntity<Canvas> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(h), Canvas.class);
        if(response.getBody() == null) throw new IllegalStateException("response is null");
        return response.getBody();
    }

    public Canvas getCanvaOf(RestTemplate restTemplate, String token){
        if (c != null) {
            return c;
        } else {
            c = getCanvaOfAPI(restTemplate, token);
            return c;
        }
    }
}
