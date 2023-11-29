package org.ksij.models.utilisateurs;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Utilisateur extends PanacheEntity {
    public String nom;
    public String telephone;
    public String maman;
    public int niveau;
    public String mdp;
    //
}
