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
    private User user ;
    
    public ReponseReclamation()
    {
        
    }

    public ReponseReclamation(int id, String contenu, User user) {
        this.id = id;
        this.contenu = contenu;
        this.user = user;
    }
    
    public ReponseReclamation(String contenu, User user) {
        this.contenu = contenu;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ReponseReclamation{" + "id=" + id + ", contenu=" + contenu + ", user=" + user + '}';
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
