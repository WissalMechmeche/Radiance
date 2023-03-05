/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import javax.imageio.ImageIO;
import tn.esprit.ktebi.entities.Facture;
import tn.esprit.ktebi.entities.LignePanier;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.entities.Panier;
import tn.esprit.ktebi.entities.Promo;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.services.LivreServicee;
import tn.esprit.ktebi.services.PromoService;
import tn.esprit.ktebi.services.ServiceFacture;
import tn.esprit.ktebi.services.ServiceLignePanier;
import tn.esprit.ktebi.services.ServicePanier;
import tn.esprit.ktebi.services.SmsSender;
import tn.esprit.ktebi.services.UserService;



/**
 *
 * @author MSI
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        //Connection cnx = MaConnexion.getInstance().getCnx() ;
        
       /* LivreServicee ls = new LivreServicee();
        
        Calendar calendar = Calendar.getInstance();
        java.util.Date utilDate = calendar.getTime();

        // Convertir la date en java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        
        Livre livreA = new Livre();
        
        
        livreA.setLibelle("Test livre");
        livreA.setDescription("Description du test livre");
        livreA.setAuteur(new User(1,"Wissal", "Mechmeche","mechmechwissal@gmail.com")); // suppose que l'utilisateur d'id 1 existe déjà dans la base de données
        livreA.setEditeur("Test éditeur");
        livreA.setCategorie("Test catégorie");
        
        livreA.setDate_edition(sqlDate);
        livreA.setPromo(new Promo(1,"PROMO10",10.5,sqlDate,sqlDate)); // suppose que la promotion d'id 1 existe déjà dans la base de données
        livreA.setPrix(10.0f);
        livreA.setImage(null);
        livreA.setLangue("Français");
        
        //ls.create(livreA);
        
        Livre livreU = new Livre();
        livreU.setId(3); // L'ID du livre à mettre à jour
        livreU.setLibelle("Nouveau titre");
        livreU.setDescription("Nouvelle description");
        livreU.setEditeur("Nouvel éditeur");
        livreU.setCategorie("Nouvelle catégorie");
        livreU.setPrix(25.5f);
        livreU.setLangue("Nouvelle langue");
       
        
        
        User auteur = new User(1,"Wissal", "Mechmeche","mechmechwissal@gmail.com");
        livreU.setAuteur(auteur);

        // Récupération du code promo à partir de la base de données
        PromoService ps = new PromoService();
        Promo promo = ps.selectById(1);
        livreU.setPromo(promo);
        
        // Chargement de la nouvelle image à partir d'un fichier
        File imageFile = new File("src/tn/esprit/ktebi/ressources/images/nouvelle_image.png");
        try {
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            livreU.setImage(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        ls.update(livreU);
        
        //ls.delete(19);
        
        
        //Liste des Livres
        List<Livre> livres = ls.selectAll();
        for (Livre livre : livres) {
            System.out.println(livre);
        }
        
        UserService us = new UserService();
        
        //Liste des auteurs
        List<User> users =  us.getAll();
        for (User user: users) {
            System.out.println(user);
            
        }
        
        Promo pr = new Promo();
        pr.setId(3);
        pr.setCode("Wissal15");
        pr.setReduction(15.0);
        pr.setDate_debut(sqlDate);
        pr.setDate_fin(sqlDate);
        
        //ps.create(pr);
        
        //ps.update(pr);
        
        //dps.delete(3);
        
        
        System.out.println(ps.selectAll());*/
       
       //ServiceLignePanier sp = new ServiceLignePanier();
      /* User u = new User(1,"Wissal", "Mechmeche","mechmechwissal@gmail.com");
       
       Panier p = new Panier(1,25,600,u);
       
       ServicePanier sp = new ServicePanier();
       
       //sp.ajouterPanier(p);
       
        Livre livreU = new Livre();
        livreU.setId(3); // L'ID du livre à mettre à jour
        livreU.setLibelle("Nouveau titre");
        livreU.setDescription("Nouvelle description");
        livreU.setEditeur("Nouvel éditeur");
        livreU.setCategorie("Nouvelle catégorie");
        livreU.setPrix(25.5f);
        livreU.setLangue("Nouvelle langue");
        
       LignePanier lp = new LignePanier(livreU,p,45);
       
       ServiceLignePanier s = new ServiceLignePanier();
       //s.ajouterLignePanier(lp);
       
       
       ServiceFacture f = new ServiceFacture();
       
       Facture ft = new Facture(200f,"Especes",u,Timestamp.valueOf("2022-05-20 00:25:33"));
       
       //f.ajouterFacture(1, "Especes");*/
       
       
       
       
       
       
       
       
       
       //System.out.println(sp.ajouterLignePanier(p));
       /*
       UserService us = new UserService();
      List<User> clients = us.getAllClients();
       PromoService ps = new PromoService();
       List<Promo> list = ps.selectAll();
       
       LocalDate today = LocalDate.now();


        LocalDate datePlus5 = today.plusDays(5);


        Date datePlus5Sql = Date.valueOf(datePlus5);
      
       
      /* for(Promo p : list)
       {
           if(p.getDate_fin() == datePlus5Sql)
           {
               
           }
       }
      
      
       SmsSender.sendSms("+216 29 537 908","Le code Promo va s'expirer dans 5 jours");
      
      */
       
       
        
       
        
        
     
    }
}
