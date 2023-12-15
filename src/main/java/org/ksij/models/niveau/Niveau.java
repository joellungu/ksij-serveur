package org.ksij.models.niveau;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Niveau extends PanacheEntity {

    @Column(name="niveau", columnDefinition = "integer default 1")
    public int niveau;
    public byte[] cours;
}
