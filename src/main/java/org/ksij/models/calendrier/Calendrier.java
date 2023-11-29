package org.ksij.models.calendrier;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Calendrier extends PanacheEntity {
    public String dateTime;
    public String dateTimeLunaire;
    public String contenu;
    public int couleur;
    //
}
