/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.tests;

import java.sql.SQLException;
import tn.esprit.ktebi.entities.Facture;
import tn.esprit.ktebi.entities.LignePanier;
import tn.esprit.ktebi.entities.Panier;
import tn.esprit.ktebi.services.ServiceFacture;
import tn.esprit.ktebi.services.ServiceLignePanier;
import tn.esprit.ktebi.services.ServicePanier;

/**
 *
 * @author Pc Anis
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here

        Panier p = new Panier(2, 1, 2);
        ServicePanier se = new ServicePanier();

        Facture f = new Facture(0, "", 3, 19);
        ServiceFacture sf = new ServiceFacture();
        sf.ajouterFacture(3); 

//               LignePanier ligne=new LignePanier(4, 7, 2);
//                ServiceLignePanier sl=new ServiceLignePanier();
//                sl.ajouterLignePanier(ligne);
        //System.out.println(sl.calculerPrixTotal(5));
        //Facture f=new Facture(200,"dqsd",2,2);
        //ServiceFacture fac=new ServiceFacture();
        //System.out.println(fac.afficherFactures());
        //System.out.println(sl.listelivres());
        //System.out.println(se.getPanierByUser(2)); 
    }

}
