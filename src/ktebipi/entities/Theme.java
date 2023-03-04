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
    private int idtheme ;
    private String nom ;
    private SousTheme sousTheme ;

    public Theme()
    {
    }

    public Theme(int idtheme, String nom, SousTheme sousTheme) {
        this.idtheme = idtheme;
        this.nom = nom;
        this.sousTheme = sousTheme;
    }
        public Theme(int idtheme, String nom) {
        this.idtheme = idtheme;
        this.nom = nom;
    }

    public Theme(String nom, SousTheme sousTheme) {
        this.nom = nom;
        this.sousTheme = sousTheme;
    }

    public int getIdtheme() {
        return idtheme;
    }

    public void setIdtheme(int idtheme) {
        this.idtheme = idtheme;
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
        return "Theme{" + "idtheme=" + idtheme + ", nom=" + nom + ", sousTheme=" + sousTheme + '}';
    }

    







}