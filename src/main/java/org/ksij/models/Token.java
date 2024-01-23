package org.ksij.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Token extends PanacheEntity {
    public String token;
}
