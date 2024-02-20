package org.ksij.models.niveau;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Cours extends PanacheEntity {

    public int niveau;
    public String titre;
    public byte[] cours;
}
