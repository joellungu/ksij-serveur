package org.ksij.models.niveau;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Niveau extends PanacheEntity {

    public int niveau;
    public byte[] cours;
}
