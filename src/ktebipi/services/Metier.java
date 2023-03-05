/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ktebipi.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ktebipi.entities.Evenement;
import ktebipi.utils.Maconnexion;
import java.sql.Date;
/**
 *
 * @author ASUS
 */
public class Metier implements ModifierInterface{
Statement ste;
Connection  conn = Maconnexion.getInstance().getCnx();
    @Override
    public List<Evenement> SearchByName(String name) {
            List<Evenement> pers = new ArrayList<>();
        try {
            PreparedStatement pre = conn.prepareStatement("select * from `event` WHERE `nom_event` LIKE ?");
            pre.setString(1, "%" + name + "%");
            ResultSet result = pre.executeQuery();
            ste = conn.createStatement();
        System.out.println(result);
        while (result.next()) {
               Evenement   resultPerson = new Evenement(result.getInt("id_event"), result.getString("nom_event"),result.getString("lieu_event"),result.getFloat("prix_event"), result.getString("desc_event"),result.getString("image"));
            pers.add(resultPerson);
        }
        System.out.println(pers);
     
    } catch (SQLException ex) {
         System.out.println(ex);  
    }
   return pers;

    }

    @Override
    public List<Evenement> sortByDate() {
         List<Evenement> pers = new ArrayList<>();
        try {
           PreparedStatement pre = conn.prepareStatement("SELECT * FROM `event` ORDER BY `date_event`");
           // pre.setString(1, "%" + name + "%");
            ResultSet result = pre.executeQuery();
            ste = conn.createStatement();
        System.out.println(result);
            while (result.next()) {
                Evenement resultPerson = new Evenement(result.getInt("id_event"), result.getString("nom_event"), result.getString("lieu_event"), result.getFloat("prix_event"), result.getString("desc_event"),result.getDate("date_event").toLocalDate(), result.getString("image"));
                pers.add(resultPerson);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return pers;
    }
    }
    

