package fr.ensim.charme_quartier.pixel_war.model;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;

public class Worker {
    private int id;
    private String dateDernierPixelPose;

    private int equipeProprietaire;


    private WorkerType type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateDernierPixelPose() {
        return dateDernierPixelPose;
    }

    public void setDateDernierPixelPose(String dateDernierPixelPose) {
        this.dateDernierPixelPose = dateDernierPixelPose;
    }

    public int getEquipeProprietaire() {
        return equipeProprietaire;
    }

    public void setEquipeProprietaire(int equipeProprietaire) {
        this.equipeProprietaire = equipeProprietaire;
    }

    public WorkerType getType() {
        return type;
    }

    public void setType(WorkerType type) {
        this.type = type;
    }

    public boolean IsOnCooldown(){
        Instant now = Instant.now();
        if(getDateDernierPixelPose() == null){
            return false;
        }

        if(Instant.parse(getDateDernierPixelPose()).plus(Duration.ofSeconds(type.getCooldown())).isAfter(now)){
            return true;
        }
        return true;
    }
}
