/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author MSI
 */
public class MaConnexion {
    private final String url="jdbc:mysql://localhost/ktebi";
    private final String login="root";
    private final String password="";
    
    private Connection cnx ;
    
    
    private static MaConnexion instance ;

    private MaConnexion() {
        
        try {
            cnx = DriverManager.getConnection(url,login, password);
            System.out.println("Connexion etablie !");
        } catch (SQLException ex) {
            System.err.println("Erreur de connexion ");
        }
        
    }
    
    public static MaConnexion getInstance()
    {
        if(instance == null)
        {
            return new MaConnexion();
        }
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
     
    
}
