/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.entities;

import java.io.InputStream;
import java.sql.Blob;
import java.time.LocalDate;

/**
 *
 * @author MSI
 */
public class Reclamation {
    private int id ;
    private String contenu ;
    private LocalDate date_reclamation ;
    private String etat ;
    private User user ;
    private String img;
    private Blob img1;
    private TypeReclamation type;

    public Reclamation()
    {

    }
   public Reclamation(int id)
    {
        this.id=id;
    }   
       public Reclamation(int id, String contenu, LocalDate date_reclamation, String etat, User user,Blob img1) {
        this.id = id;
        this.contenu = contenu;
        this.date_reclamation = date_reclamation;
        this.etat = etat;
        this.user = user;
        this.img1 = img1;
       
    }
    public Reclamation(int id, String contenu, LocalDate date_reclamation, String etat, User user) {
        this.id = id;
        this.contenu = contenu;
        this.date_reclamation = date_reclamation;
        this.etat = etat;
        this.user = user;
        
    }

    public Reclamation(String contenu, LocalDate date_reclamation, String etat, User user,String img,TypeReclamation type) {
        this.contenu = contenu;
        this.date_reclamation = date_reclamation;
        this.etat = etat;
        this.user = user;
        this.img= img;
        this.type= type;

    }
    
        public Reclamation(String contenu, LocalDate date_reclamation, String etat, User user) {
        this.contenu = contenu;
        this.date_reclamation = date_reclamation;
        this.etat = etat;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDate getDate_reclamation() {
        return date_reclamation;
    }

    public void setDate_reclamation(LocalDate date_reclamation) {
        this.date_reclamation = date_reclamation;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Blob getImg1() {
        return img1;
    }

    public void setImg1(Blob img1) {
        this.img1 = img1;
    }

    public TypeReclamation getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id=" + id + ", contenu=" + contenu + ", date_reclamation=" + date_reclamation + ", etat=" + etat + ", user=" + user + ", img=" + img + ", img1=" + img1 + ", type=" + type + '}';
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
        final Reclamation other = (Reclamation) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }







}