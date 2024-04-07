package fr.ensim.charme_quartier.pixel_war.controllers;

import fr.ensim.charme_quartier.pixel_war.model.*;
import fr.ensim.charme_quartier.pixel_war.model.Canvas;
import fr.ensim.charme_quartier.pixel_war.model.Chunk;
import fr.ensim.charme_quartier.pixel_war.model.EUseableColors;
import fr.ensim.charme_quartier.pixel_war.model.Worker;
import fr.ensim.charme_quartier.pixel_war.service.AuthentifierService;
import fr.ensim.charme_quartier.pixel_war.service.CanvasService;
import fr.ensim.charme_quartier.pixel_war.utils.ImageUtils;
import fr.ensim.charme_quartier.pixel_war.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.HashMap;

@Controller
public class PixelController {
    @Autowired
    AuthentifierService as;

    @Autowired
    WorkerService ws;

    @Autowired
    CanvasService cs;

    @GetMapping("/")
    public String displayHome(Model model) {

        model.addAttribute("colors", EUseableColors.getAllColors());
        return "index";
    }

    @PostMapping("/pixel")
    public String putPixel(@RequestParam(name = "x_coord", required = true) int xCoord,
                           @RequestParam(name = "y_coord", required = true) int yCoord,
                           @RequestParam(name = "color", required = true) EUseableColors color,
                           RestTemplate restTemplate) {


        String token = as.getToken(restTemplate);
        int teamId = as.getTeamId(restTemplate, "Le charme du quartier", token);
        Worker[] workers = ws.getWorkersOf(restTemplate, token, teamId);

        HttpHeaders h = new HttpHeaders();
        h.setBearerAuth(token);
        h.add("Content-Type", MediaType.APPLICATION_JSON.toString());

        Worker selectedWorker = ws.getAvailableWorker(workers);

        String url = "http://149.202.79.34:8085/api/equipes/" + teamId + "/workers/" + selectedWorker.getId() + "/pixel";

        // TODO: move this algorithm so service
        int chunkId = -1;

        Canvas canva = cs.getCanvaOf(restTemplate, token);
        for (Chunk c : canva.getChunks()) {
            if (c.getType().equals("privé") && c.getEquipeProprietaire() == teamId) {
                chunkId = c.getId();
            }
        }
        if (chunkId == -1) {
            throw new IllegalStateException("chunk not found for you");
        }

        System.out.println(chunkId);

        var body = new HashMap<String, Object>();

        body.put("canvas", canva.getNom());
        body.put("chunk", chunkId);
        body.put("color", color);
        body.put("pos_x", xCoord);
        body.put("pos_y", yCoord);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(body, h), String.class);
        return response.getBody();


        //return response.getBody() + " TEAM ID : " + as.getTeamId(restTemplate, "Le charme du quartier", token);


    }

    @GetMapping("/jinbe")
    public String insertImage(RestTemplate restTemplate) throws IOException {
        InputStream image = this.getClass().getClassLoader().getResourceAsStream("Gol_D._Roger_Portrait.png");
        BufferedImage bi = ImageIO.read(image);
        BufferedImage rezized = ImageUtils.resizeImage(bi, 50, 50);
        BufferedImage recoloredToPrint = ImageUtils.recolor(rezized);
        String lastState = "";
        for (int y = 0; y < recoloredToPrint.getHeight(); y++) {
            for (int x = 0; x < recoloredToPrint.getWidth(); x++) {
                int rgb = recoloredToPrint.getRGB(x, y);
                Color pixelColor = new Color(rgb);
                EUseableColors colorName = EUseableColors.getEUseableColors(pixelColor);
                System.out.println("Pixel at (" + x + ", " + y + ") has color: " + colorName);
                lastState = putPixel(x, y, colorName, restTemplate);
            }
        }

        return lastState;

    }

    @GetMapping("/token")
    public String getToken(RestTemplate restTemplate) {
        return as.getToken(restTemplate);
    }


    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    @GetMapping("/uploadimage")
    public String displayUploadForm() {
        return "imageupload/index";
    }

    @RequestMapping("/upload")
    public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
        return "index";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @PostMapping("/index")
    public String uploadImage(@ModelAttribute("image") BufferedImage image) {

        return "redirect:someOtherPage";
    }

}
