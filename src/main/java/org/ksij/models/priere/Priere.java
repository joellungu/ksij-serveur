package org.ksij.models.priere;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Priere extends PanacheEntity {
    public String dateTime;

    @ElementCollection
    public List<Heure> horaires;
}
