package fr.ensim.charme_quartier.pixel_war.model;

import java.util.List;

public class Canvas {
    private int id;
    private String nom;
    private int taille_x;
    private int taille_y;
    private int taille_chunk_x;
    private int taille_chunk_y;
  
    private List<Chunk> chunks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getTaille_x() {
        return taille_x;
    }

    public void setTaille_x(int taille_x) {
        this.taille_x = taille_x;
    }

    public int getTaille_y() {
        return taille_y;
    }

    public void setTaille_y(int taille_y) {
        this.taille_y = taille_y;
    }

    public int getTaille_chunk_x() {
        return taille_chunk_x;
    }

    public void setTaille_chunk_x(int taille_chunk_x) {
        this.taille_chunk_x = taille_chunk_x;
    }

    public int getTaille_chunk_y() {
        return taille_chunk_y;
    }

    public void setTaille_chunk_y(int taille_chunk_y) {
        this.taille_chunk_y = taille_chunk_y;
    }

    public List<Chunk> getChunks() {
        return chunks;
    }

    public void setChunks(List<Chunk> chunks) {
        this.chunks = chunks;
    }
}
