/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.tests;

import java.sql.Connection;
import tn.esprit.ktebi.utils.MaConnexion;

/**
 *
 * @author MSI
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Connection cnx = MaConnexion.getInstance().getCnx() ;
    }
    
}
