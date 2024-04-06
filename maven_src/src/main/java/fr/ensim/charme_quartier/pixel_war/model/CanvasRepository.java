package fr.ensim.charme_quartier.pixel_war.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanvasRepository extends CrudRepository<Canvas, Integer> {
}
