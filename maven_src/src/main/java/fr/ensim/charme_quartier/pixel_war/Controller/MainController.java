package fr.ensim.charme_quartier.pixel_war.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MainController {
    @Autowired
    private RestTemplate rt;
    @GetMapping("/equipes")
    public String equipes () {
        /*
        String querry = "http://149.202.79.34:8085/api/equipes";
        rt.getInterceptors().add((outReq, bytes, clientHttpReqExec) -> {
            outReq.getHeaders().set(
                    HttpHeaders.AUTHORIZATION, "Bearer " + token
            );
            return clientHttpReqExec.execute(outReq, bytes);
        });
        return rt.getForObject(querry, equipes.class);
        */
        return null;
    }

    @RequestMapping("/token")
    public String getToken(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("password", "%dpcDp-9ZY%(b49");
        map.add("username", "charme_quartier");
        map.add("grant_type", "password");
        map.add("client_id", "pixel-war");

        HttpEntity<MultiValueMap<String,String>> entity =
                new HttpEntity<MultiValueMap<String, String>>(map, headers);


        String queery = "http://149.202.79.34:8081/realms/codelemans/protocol/openid-connect/token";
        ResponseEntity<String> response = rt.postForEntity(queery, map, String.class);
        return response.getBody();
    }
}
