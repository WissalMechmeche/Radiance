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
public class Facture {
    private int id ;
    private float montant_totale ;
    private String mode_paiement ;
    private User user ;
    private Livre livre ;
    
    public Facture()
    {
        
    }

    public Facture(int id, float montant_totale, String mode_paiement, User user, Livre livre) {
        this.id = id;
        this.montant_totale = montant_totale;
        this.mode_paiement = mode_paiement;
        this.user = user;
        this.livre = livre;
    }
    
    public Facture(float montant_totale, String mode_paiement, User user, Livre livre) {
        this.montant_totale = montant_totale;
        this.mode_paiement = mode_paiement;
        this.user = user;
        this.livre = livre;
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

    public String getMode_paiement() {
        return mode_paiement;
    }

    public void setMode_paiement(String mode_paiement) {
        this.mode_paiement = mode_paiement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    @Override
    public String toString() {
        return "Facture{" + "id=" + id + ", montant_totale=" 
                + montant_totale + ", mode_paiement=" 
                + mode_paiement + ", user=" 
                + user + ", livre=" + livre + '}';
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
        final Facture other = (Facture) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
    
}
