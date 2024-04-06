package fr.ensim.charme_quartier.pixel_war.controller;

import fr.ensim.charme_quartier.pixel_war.model.CanvasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CanvasController {
    @Autowired
    CanvasRepository canvasRepository;

    @GetMapping("http://149.202.79.34:8085/api/canvas/{canvasId}")
    public String getCanvas(int canvasId, Model model) {
        model.addAttribute("canvasId", canvasRepository.findAll());
        return "canvas";
    }
}
