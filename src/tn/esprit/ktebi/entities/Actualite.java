/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.entities;

/**
 *
 * @author MSI
 */
public class Actualite {
    
    private int id ;
    private String titre ;
    private String description ;
    private int nb_like ;
    private int nb_commentaire ;
    
    public Actualite()
    {
        
    }
    
    public Actualite(int id, String titre, String description, int nb_like, int nb_commentaire) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.nb_like = nb_like;
        this.nb_commentaire = nb_commentaire;
    }
    
    public Actualite(String titre, String description, int nb_like, int nb_commentaire) {
        this.titre = titre;
        this.description = description;
        this.nb_like = nb_like;
        this.nb_commentaire = nb_commentaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNb_like() {
        return nb_like;
    }

    public void setNb_like(int nb_like) {
        this.nb_like = nb_like;
    }

    public int getNb_commentaire() {
        return nb_commentaire;
    }

    public void setNb_commentaire(int nb_commentaire) {
        this.nb_commentaire = nb_commentaire;
    }

    @Override
    public String toString() {
        return "Actualite{" + "id=" + id + ", titre=" + titre 
                + ", description=" + description + ", nb_like=" + nb_like 
                + ", nb_commentaire=" + nb_commentaire + '}';
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
        final Actualite other = (Actualite) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
}
