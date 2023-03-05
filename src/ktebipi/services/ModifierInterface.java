/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ktebipi.services;

import java.util.List;
import ktebipi.entities.Evenement;

/**
 *
 * @author ASUS
 */
public interface ModifierInterface {
    public List<Evenement> SearchByName(String name);
    public List<Evenement> sortByDate() ;

}
