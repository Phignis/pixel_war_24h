package fr.ensim.charme_quartier.pixel_war.service;

import fr.ensim.charme_quartier.pixel_war.model.Canvas;
import fr.ensim.charme_quartier.pixel_war.model.CanvasData;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Base64;

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

    public String getcanvasdataid(RestTemplate restTemplate, String token, String canvaname){
        String url = "http://149.202.79.34:8085/api/pixels/" + canvaname+ "/settings";
        HttpHeaders h = new HttpHeaders();
        h.setBearerAuth(token);

        ResponseEntity<CanvasData> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(h), CanvasData.class);
        if(response.getBody() == null) throw new IllegalStateException("response is null");
        return response.getBody().get_id();
    }

    public BufferedImage getcanvaspixels(RestTemplate restTemplate, String token, String canvadataid) throws IOException {
        String url = "http://149.202.79.34:8085/api/pixels/" + canvadataid+ "/data";
        HttpHeaders h = new HttpHeaders();
        h.setBearerAuth(token);

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(h), byte[].class);
        if(response.getBody() == null) throw new IllegalStateException("response is null");

        InputStream is = new ByteArrayInputStream(response.getBody());
        BufferedImage newBi = ImageIO.read(is);
        return newBi;
    }
}
