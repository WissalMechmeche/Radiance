/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.entities;

import java.time.LocalDate;

/**
 *
 * @author MSI
 */
public class Evenement {
    private int id ;
    private String description ;
    private String lieu ;
    private float prix ;
    private LocalDate date_evenement ;
    private Theme theme ;
    private User user ;
    
    public Evenement()
    {
        
    }

    public Evenement(int id, String description, String lieu, float prix, LocalDate date_evenement, Theme theme, User user) {
        this.id = id;
        this.description = description;
        this.lieu = lieu;
        this.prix = prix;
        this.date_evenement = date_evenement;
        this.theme = theme;
        this.user = user;
    }
    
    public Evenement(String description, String lieu, float prix, LocalDate date_evenement, Theme theme, User user) {
        this.description = description;
        this.lieu = lieu;
        this.prix = prix;
        this.date_evenement = date_evenement;
        this.theme = theme;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public LocalDate getDate_evenement() {
        return date_evenement;
    }

    public void setDate_evenement(LocalDate date_evenement) {
        this.date_evenement = date_evenement;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Evenement{" + "id=" + id + ", description=" + description 
                + ", lieu=" + lieu + ", prix=" + prix 
                + ", date_evenement=" + date_evenement 
                + ", theme=" + theme + ", user=" + user + '}';
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Evenement other = (Evenement) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
    
}
