package fr.ensim.charme_quartier.pixel_war.Model;

public class Chunk {
    private int id;
    private String nom;
    private int pos_x;
    private int pos_y;
    private String type;
    private int equipeProprietaire;

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

    public int getPos_x() {
        return pos_x;
    }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getEquipeProprietaire() {
        return equipeProprietaire;
    }

    public void setEquipeProprietaire(int equipeProprietaire) {
        this.equipeProprietaire = equipeProprietaire;
    }
}
