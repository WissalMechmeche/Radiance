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
public class Panier {
    private int id ;
    private float montant_totale ;
    private int nb_articles ;
    private User user ;
    private Facture facture ;
    
    public Panier()
    {
        
    }

    public Panier(int id, float montant_totale, int nb_articles, User user, Facture facture) {
        this.id = id;
        this.montant_totale = montant_totale;
        this.nb_articles = nb_articles;
        this.user = user;
        this.facture = facture;
    }
    
    
    public Panier(float montant_totale, int nb_articles, User user, Facture facture) {
        this.montant_totale = montant_totale;
        this.nb_articles = nb_articles;
        this.user = user;
        this.facture = facture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMontant_totale() {
        return montant_totale;
    }

    public void setMontant_totale(float montant_totale) {
        this.montant_totale = montant_totale;
    }

    public int getNb_articles() {
        return nb_articles;
    }

    public void setNb_articles(int nb_articles) {
        this.nb_articles = nb_articles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    @Override
    public String toString() {
        return "Panier{" + "id=" + id + ", montant_totale=" 
                + montant_totale + ", nb_articles=" 
                + nb_articles + ", user=" + user 
                + ", facture=" + facture + '}';
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
        final Panier other = (Panier) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
}
