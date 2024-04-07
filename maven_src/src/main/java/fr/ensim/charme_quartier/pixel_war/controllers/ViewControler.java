package fr.ensim.charme_quartier.pixel_war.controllers;

import fr.ensim.charme_quartier.pixel_war.model.Canvas;
import fr.ensim.charme_quartier.pixel_war.model.CanvasData;
import fr.ensim.charme_quartier.pixel_war.service.AuthentifierService;
import fr.ensim.charme_quartier.pixel_war.service.CanvasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Controller

public class ViewControler {
    @Autowired
    private CanvasService cs;
    @Autowired
    private AuthentifierService ts;
    @GetMapping("/index")
    public String index(Model model, RestTemplate rt) throws IOException {
        Canvas c = cs.getCanvaOf(rt, ts.getToken(rt));
        System.out.println("récupération du canvadataid : canvaname = " + c.getNom());
        String cdId = cs.getcanvasdataid(rt, ts.getToken(rt),c.getNom());
        System.out.println("récupération du canva : dataid = " + cdId);
        BufferedImage bi = cs.getcanvaspixels(rt, ts.getToken(rt), cdId);
        System.out.println("canva : "+ bi);

        File outputfile = new File("image.jpg");
        ImageIO.write(bi, "jpg", outputfile);


        model.addAttribute("canva", bi);



        return "index";
    }

}
