/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.entities;

import java.util.Date;



/**
 *
 * @author MSI
 */
public class Livre implements Comparable<Livre> {
    private int id ;
    private String libelle ;
    private String description ;
    private String editeur ;
    private Date date_edition ;
    private String categorie ;
    private float prix ;
    private String langue ;
    private String image ;
    private int promo ;
    private int id_user ;
    
    public Livre(){
    }

    public Livre(int id, String libelle, String description, String editeur, Date date_edition, String categorie, float prix, String langue, String image , int promo, int id_user) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.editeur = editeur;
        this.date_edition = date_edition;
        this.categorie = categorie;
        this.prix = prix;
        this.langue = langue;
        this.image = image ;
        this.promo = promo;
        this.id_user = id_user;
    }
    
    public Livre(String libelle, String description, String editeur, Date date_edition, String categorie, float prix, String langue, String image , int promo, int id_user) {
        this.libelle = libelle;
        this.description = description;
        this.editeur = editeur;
        this.date_edition = date_edition;
        this.categorie = categorie;
        this.prix = prix;
        this.langue = langue;
        this.image = image ;
        this.promo = promo;
        this.id_user = id_user;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public Date getDate_edition() {
        return date_edition;
    }

    public void setDate_edition(Date date_edition) {
        this.date_edition = date_edition;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPromo() {
        return promo;
    }

    public void setPromo(int promo) {
        this.promo = promo;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Livre{" + "id=" + id + ", libelle=" + libelle 
                + ", description=" + description + ", editeur=" + editeur 
                + ", date_edition=" + date_edition + ", categorie=" + categorie 
                + ", prix=" + prix + ", langue=" + langue + ", image=" + image 
                + ", promo=" + promo + ", id_user=" + id_user + "}\n";
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
        final Livre other = (Livre) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Livre o) {
        return this.libelle.compareTo(o.libelle);
    } 
}
