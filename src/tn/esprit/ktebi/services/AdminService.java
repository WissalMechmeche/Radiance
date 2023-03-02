/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import tn.esprit.ktebi.utils.MaConnexion;

/**
 *
 * @author Pc Anis
 */
public class AdminService {

    private Connection cnx;

    public AdminService() {
        cnx = MaConnexion.getInstance().getCnx();

    }

    public int getTotalUsers() {
        int count = 0;
        try {
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM utilisateur");
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getTotalLivres() {
        int count = 0;
        try {
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM livre");
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getTotalFactures() {
        int count = 0;
        try {
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM facture");
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getTotalAuteurs() {
        int count = 0;
        try {
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery(" SELECT COUNT(*) FROM utilisateur u \n"
                    + "JOIN role r ON u.id_role = r.id_role \n"
                    + "WHERE r.role = 'auteur'");
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getTotalLib() {
        int count = 0;
        try {
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery(" SELECT COUNT(*) FROM utilisateur u \n"
                    + "JOIN role r ON u.id_role = r.id_role \n"
                    + "WHERE r.role = 'librairie'");
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getTotalClient() {
        int count = 0;
        try {
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery(" SELECT COUNT(*) FROM utilisateur u \n"
                    + "JOIN role r ON u.id_role = r.id_role \n"
                    + "WHERE r.role = 'client'");
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

}
