/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MSI
 */
public class Participation {
    private int id ;
    private Evenement evenemnet ;
    List<User> list_participant ;
    
    public Participation()
    {
        list_participant = new ArrayList();
    }

    public Participation(int id,Evenement evenemnet, List<User> list_participant) {
        this.id = id ;
        this.evenemnet = evenemnet;
        this.list_participant = list_participant;
    }
    
    public Participation(Evenement evenemnet, List<User> list_participant) {
        this.evenemnet = evenemnet;
        this.list_participant = list_participant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public Evenement getEvenemnet() {
        return evenemnet;
    }

    public void setEvenemnet(Evenement evenemnet) {
        this.evenemnet = evenemnet;
    }

    public List<User> getList_participant() {
        return list_participant;
    }

    public void setList_participant(List<User> list_participant) {
        this.list_participant = list_participant;
    }

    @Override
    public String toString() {
        return "Participation{" + "id=" + id + ", evenemnet=" + evenemnet + ", list_participant=" + list_participant + '}';
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
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
