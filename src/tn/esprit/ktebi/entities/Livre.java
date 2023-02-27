/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.entities;

import javafx.scene.control.TableColumn;

/**
 *
 * @author MSI
 */
public class Livre {

    private int id;
    private String libelle;
    private String description;
    private String editeur;
    private String date_edition;
    private String categorie;
    private float prix;
    private String langue;
    private int promo;
    private int user;
    private String image;

    public Livre() {
    }

    public Livre(int id, String libelle, String description, String editeur, String date_edition, String categorie, float prix, String langue, int promo, int user, String image) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.editeur = editeur;
        this.date_edition = date_edition;
        this.categorie = categorie;
        this.prix = prix;
        this.langue = langue;
        this.promo = promo;
        this.user = user;
        this.image = image;
    }
    
    public Livre(int user,float prix){
        this.user=user;
        this.prix=prix;
        
    }
    public Livre(String libelle,String image, String categorie,float prix,int user){
        this.libelle = libelle;
        this.categorie = categorie;
        this.prix = prix;
        this.image = image;
        this.user=user;
        
    }

    public Livre(String libelle, String description, String editeur, String date_edition, String categorie, float prix, String langue, int promo, int user, String image) {
        this.libelle = libelle;
        this.description = description;
        this.editeur = editeur;
        this.date_edition = date_edition;
        this.categorie = categorie;
        this.prix = prix;
        this.langue = langue;
        this.promo = promo;
        this.user = user;
        this.image = image;

    }

    public Livre(String libelle, String image, String categorie, float prix) {
        this.libelle = libelle;

        this.categorie = categorie;
        this.prix = prix;

        this.image = image;

    }

    public Livre(int user, String libelle, String image, float prix) {
        this.user=user;
        this.libelle=libelle;
        this.image=image;
        this.prix=prix;
        
    }

    public Livre(int qte, String string, String image, float aFloat, float sousTot) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getDate_edition() {
        return date_edition;
    }

    public void setDate_edition(String date_edition) {
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

    public int getPromo() {
        return promo;
    }

    public void setPromo(int promo) {
        this.promo = promo;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Livre{" + "id=" + id + ", libelle=" + libelle
                + ", description=" + description
                + ", editeur=" + editeur + ", date_edition=" + date_edition
                + ", categorie=" + categorie + ", prix=" + prix
                + ", langue=" + langue + ", promo=" + promo + ", user=" + user + '}';
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

}
