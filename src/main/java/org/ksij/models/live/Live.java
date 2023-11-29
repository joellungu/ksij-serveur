package org.ksij.models.live;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Live extends PanacheEntity {
    public String titre;
    public String lien;
    public String dateTime;
}
