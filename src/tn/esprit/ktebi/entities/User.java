/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.entities;

import java.security.Key;
import java.time.LocalDate;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author MSI
 */
public class User {
    
    private int id ;
    private String nom ;
    private String prenom ;
    private String email ;
    private String adresse ;
    private LocalDate dateNaissance ;
    private int tel ;
    private int motPasse ;
    Role role ;
    

    public User() {
    
    }

    public User(int id, String nom, String prenom, String email, String adresse, LocalDate dateNaissance, int tel, int motPasse, Role role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        this.tel = tel;
        this.motPasse = motPasse;
        this.role = role;
    }
   

    public User(String nom, String prenom, String email, String adresse, LocalDate dateNaissance, int tel, int motPasse, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        this.tel = tel;
        this.motPasse = motPasse;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public int getMotPasse() {
        return motPasse;
    }

    public void setMotPasse(int motPasse) {
        this.motPasse = motPasse;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom 
                + ", email=" + email + ", adresse=" + adresse 
                + ", dateNaissance=" + dateNaissance + ", tel=" + tel 
                + ", motPasse=" + motPasse + ", role=" + role + '}';
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
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
}
