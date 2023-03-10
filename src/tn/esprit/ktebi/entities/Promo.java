/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.entities;

import java.time.LocalDate;

/**
 *
 * @author MSI
 */
public class Promo {
    private int codePromo  ;
    private LocalDate date_expiration ;

    public Promo() {
    }

    public Promo(int codePromo, LocalDate date_expiration) {
        this.codePromo = codePromo;
        this.date_expiration = date_expiration;
    }

    public Promo(LocalDate date_expiration) {
        this.date_expiration = date_expiration;
    }
    

    public int getCodePromo() {
        return codePromo;
    }

    public void setCodePromo(int codePromo) {
        this.codePromo = codePromo;
    }

    public LocalDate getDate_expiration() {
        return date_expiration;
    }

    public void setDate_expiration(LocalDate date_expiration) {
        this.date_expiration = date_expiration;
    }

    @Override
    public String toString() {
        return "Promo{" + "codePromo=" + codePromo + ", date_expiration=" + date_expiration + '}';
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
        final Promo other = (Promo) obj;
        if (this.codePromo != other.codePromo) {
            return false;
        }
        return true;
    }
    
    
    
}
