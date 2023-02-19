/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.tests;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.services.LivreService;


/**
 *
 * @author MSI
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        //Connection cnx = MaConnexion.getInstance().getCnx() ;
        
        
       //CRUD
       LivreService ls = new LivreService();
       
       //Ajouter un livre
       
       Date date = Date.valueOf("2022-10-10");
       Livre l = new Livre("AA","Test","Test",date,"Test",17.5f,"Test","Test",1,1);
       //ls.create(l);
        
        //Modifier un livre
        
        //Récupérer la liste des livres
         List list = ls.selectAll() ;
         
        //Trier la liste les livres par Libelle
        Collections.sort(list);
     
        
        //Afficher la liste des livres
        System.out.println(list);
        
        
        
       
        
        
    }
    
}
