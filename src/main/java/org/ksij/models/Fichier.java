package org.ksij.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Fichier extends PanacheEntity {
    public long idFichier;
    public byte[] fichier;
}
