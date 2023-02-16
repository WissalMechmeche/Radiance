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
public class Espace {
    private int id ;
    private String type ;
    private LocalDate date_creation ;
    private User user ;

    public Espace()
    {
        
    }
    public Espace(int id, String type, LocalDate date_creation, User user) {
        this.id = id;
        this.type = type;
        this.date_creation = date_creation;
        this.user = user;
    }
    
    public Espace(String type, LocalDate date_creation, User user) {
        this.type = type;
        this.date_creation = date_creation;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(LocalDate date_creation) {
        this.date_creation = date_creation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Espace{" + "id=" + id + ", type=" + type 
                + ", date_creation=" + date_creation 
                + ", user=" + user + '}';
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
        final Espace other = (Espace) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
}
