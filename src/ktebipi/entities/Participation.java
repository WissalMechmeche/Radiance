/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ktebipi.entities;

/**
 *
 * @author ASUS
 */
public class Participation {

    private int id_partipation;
    private Evenement event;
    private User user;

    public Participation() {
    }

    public Participation(int id_partipation, Evenement event, User user) {
        this.id_partipation = id_partipation;
        this.event = event;
        this.user = user;
    }

    public Participation(Evenement event, User user) {
        this.event = event;
        this.user = user;
    }
    

    public int getId_partipation() {
        return id_partipation;
    }

    public void setId_partipation(int id_partipation) {
        this.id_partipation = id_partipation;
    }

    public Evenement getEvent() {
        return event;
    }

    public void setEvent(Evenement event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Participation{" + "id_partipation=" + id_partipation + ", event=" + event + ", user=" + user + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id_partipation;
        return hash;
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
        final Participation other = (Participation) obj;
        if (this.id_partipation != other.id_partipation) {
            return false;
        }
        return true;
    }

}
