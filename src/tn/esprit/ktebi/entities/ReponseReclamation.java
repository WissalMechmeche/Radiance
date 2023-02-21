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

public class ReponseReclamation {
    private int id ;
    private String contenu ;
    private Reclamation reclamation ;

    public ReponseReclamation()
    {

    }
    public ReponseReclamation(int id)
    {
this.id=id;
    }

    public ReponseReclamation(int id, String contenu, Reclamation reclamation) {
        this.id = id;
        this.contenu = contenu;
        this.reclamation = reclamation;
    }

    public ReponseReclamation(String contenu, Reclamation reclamation) {
        this.contenu = contenu;
        this.reclamation = reclamation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Reclamation getUser() {
        return reclamation;
    }

    public void setUser(Reclamation reclamation) {
        this.reclamation = reclamation;
    }

    @Override
    public String toString() {
        return "ReponseReclamation{" + "id=" + id + ", contenu=" + contenu + ", user=" + reclamation + '}';
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
        final ReponseReclamation other = (ReponseReclamation) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}