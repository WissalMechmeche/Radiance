/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.entities;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author MSI
 */
public class Facture {

    private int id;
    private float montant_totale;
    private String mode_paiement;
    private User user;
    private Timestamp date_fac;

    public Facture() {

    }

    public Facture(int id, float montant_totale, String mode_paiement, User user, Timestamp date_fac) {
        this.id = id;
        this.montant_totale = montant_totale;
        this.mode_paiement = mode_paiement;
        this.user = user;
        this.date_fac = date_fac;
    }

    public Facture(int id, float montant_totale, String mode_paiement, User user) {
        this.id = id;
        this.montant_totale = montant_totale;
        this.mode_paiement = mode_paiement;
        this.user = user;
    }

    public Facture(float montant_totale, String mode_paiement, User user) {
        this.montant_totale = montant_totale;
        this.mode_paiement = mode_paiement;
        this.user = user;
    }

    private String nom;
    private String prenom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Facture(int id, float montant_totale, String mode_paiement, User user, Timestamp date_fac, String nom, String prenom) {
        this.id = id;
        this.montant_totale = montant_totale;
        this.mode_paiement = mode_paiement;
        this.user = user;
        this.date_fac = date_fac;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Facture(float montant_totale, String mode_paiement, Timestamp date_fac, String nom, String prenom) {
        this.montant_totale = montant_totale;
        this.mode_paiement = mode_paiement;
        this.date_fac = date_fac;
        this.nom = nom;
        this.prenom = prenom;
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

    public Timestamp getDate_fac() {
        return date_fac;
    }

    public void setDate_fac(Timestamp date_fac) {
        this.date_fac = date_fac;
    }

    @Override
    public String toString() {
        return "Facture{" + "id=" + id + ", montant_totale=" + montant_totale + ", mode_paiement=" + mode_paiement + ", user=" + user + ", date_fac=" + date_fac + '}';
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
