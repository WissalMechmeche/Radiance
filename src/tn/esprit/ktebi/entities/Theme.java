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
public class Theme {
    private int id ;
    private String nom ;
    private SousTheme sousTheme ;
    
    public Theme()
    {
    }

    public Theme(int id, String nom, SousTheme sousTheme) {
        this.id = id;
        this.nom = nom;
        this.sousTheme = sousTheme;
    }
    
    public Theme(String nom, SousTheme sousTheme) {
        this.nom = nom;
        this.sousTheme = sousTheme;
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

    public SousTheme getSousTheme() {
        return sousTheme;
    }

    public void setSousTheme(SousTheme sousTheme) {
        this.sousTheme = sousTheme;
    }

    @Override
    public String toString() {
        return "Theme{" + "id=" + id + ", nom=" + nom + ", sousTheme=" + sousTheme + '}';
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
        final Theme other = (Theme) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
