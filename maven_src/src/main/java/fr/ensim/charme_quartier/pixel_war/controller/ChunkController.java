package fr.ensim.charme_quartier.pixel_war.controller;

import fr.ensim.charme_quartier.pixel_war.model.Canvas;
import fr.ensim.charme_quartier.pixel_war.model.ChunkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChunkController {
    @Autowired
    ChunkRepository chunkRepository;

    @GetMapping("/")
    public String getChunk(@PathVariable("canvasId") int canvasId, @PathVariable("chunkId") int chunkId, Model model) {
        String apiAddress = "http://149.202.79.34:8085/api";
        String canvasIdQuery = String.valueOf(canvasId);
        String chunkIdQuery = String.valueOf(chunkId);
        String query = apiAddress + "/canvas/" + canvasIdQuery + "/chunks/" + chunkIdQuery;

        Canvas canvas = null;

        int taille_chunk_x;
        int taille_chunk_y;

        model.addAttribute("canvas", canvas);
        return "canvas";
    }
}
