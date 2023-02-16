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
public class SousTheme {
    private int id ;
    private String nom ;
    
    public SousTheme()
    {
        
    }

    public SousTheme(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    
    public SousTheme(String nom) {
        this.nom = nom;
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

    @Override
    public String toString() {
        return "SousTheme{" + "id=" + id + ", nom=" + nom + '}';
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
        final SousTheme other = (SousTheme) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
}
