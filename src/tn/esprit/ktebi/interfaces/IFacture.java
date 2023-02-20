/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.interfaces;

import java.sql.SQLException;
import java.util.List;
import tn.esprit.ktebi.entities.Facture;

/**
 *
 * @author Pc Anis
 */
public interface IFacture {
        public void ajouterFacture(Facture f) throws SQLException;
        public List<Facture> afficherFactures() throws SQLException;
        public void supprimerFacture(Facture f) throws SQLException;



    
}
