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
import tn.esprit.ktebi.entities.Promo;
import tn.esprit.ktebi.services.LivreService;
import tn.esprit.ktebi.services.PromoService;


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
        
        
        //CRUD Promo
        PromoService ps = new PromoService();
        
        //Ajouter un code Promo
        Date date_expirationC = Date.valueOf("2022-10-10");
        Promo tC = new Promo(date_expirationC);
        //ps.create(tC);
        
        //Modifier un code Promo
        Date date_expirationU = Date.valueOf("2022-10-25");
        Promo tU = new Promo(1,date_expirationU);
        //ps.update(tU);
        
        //Lister les promos
        System.out.println(ps.selectAll());
        
        
        
       //CRUD Livre
       LivreService ls = new LivreService();
       
       //Ajouter un livre
       
       Date dateC = Date.valueOf("2022-10-10");
       Livre lC = new Livre("Libelle","Description","Editeur",dateC,"Categorie",20.5f,"Langue","",1,1,"Auteur");
       //ls.create(lC);
        
       //Modifier un livre
       Date dateU = Date.valueOf("2022-10-25");
       Livre lU = new Livre(2,"Libelle1","Description1","Editeur1",dateU,"Categorie1",20.5f,"Langue1","",1,1,"Auteur1");
       //ls.update(lU);
        
        //Récupérer la liste des livres
         List list = ls.selectAll() ;
         
         
        //Trier la liste les livres par Libelle
        Collections.sort(list);
     
        
        //Afficher la liste des livres
        System.out.println(list);
        
        
        //Rechercher un livre par Libelle
        System.out.println(ls.searchByLibelle("Libelle"));
        
        //Rechercher un livre par Categorie
        System.out.println(ls.searchByCategorie("Enfant"));
        
        
        
        
        
        
        
       
        
        
    }
    
}
