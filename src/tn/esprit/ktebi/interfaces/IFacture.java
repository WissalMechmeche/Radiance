/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.interfaces;

import java.sql.SQLException;
import java.util.List;
import tn.esprit.ktebi.entities.Facture;
import tn.esprit.ktebi.entities.LigneFacture;
import tn.esprit.ktebi.entities.User;

/**
 *
 * @author Pc Anis
 */
public interface IFacture {
        public void ajouterFacture(int id_user,String mode_paiement) throws SQLException;
        public List<Facture> afficherFactures() throws SQLException;
        public void supprimerFacture(Facture f) throws SQLException;
        public User getUserById(int id) throws SQLException;
        public void ajouterLigneFacture(LigneFacture ligneFac) throws SQLException;



    
}
