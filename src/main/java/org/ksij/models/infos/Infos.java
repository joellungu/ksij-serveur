package org.ksij.models.infos;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Infos extends PanacheEntity {
    public String titre;
    public String contenu;
    public String auteur;
    public String dateTime;
}
