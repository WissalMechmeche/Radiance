/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.interfaces;

import java.sql.SQLException;
import java.util.List;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.entities.Panier;

/**
 *
 * @author Pc Anis
 */
public interface IPanier {
    public void ajouterPanier(Panier p) throws SQLException;
    public Panier getPanierByUser(int userId) throws SQLException;
    public void ViderPanier(int id_user)throws SQLException;
    public Livre getLivreByLibelle(String libelle) throws SQLException;
    public List<Panier> afficherPanier()throws SQLException;
    
}
