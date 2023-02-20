/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.interfaces;

import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import tn.esprit.ktebi.entities.LignePanier;
import tn.esprit.ktebi.entities.Livre;

/**
 *
 * @author Pc Anis
 */
public interface ILignePanier {

    public void ajouterLignePanier(LignePanier p) throws SQLException;
    public void modifierLignePanier(LignePanier p);
    public Float calculerPrixTotal(int id_panier) throws SQLException;
    public void supprimerLignePanier(LignePanier p) throws SQLException;
    public LignePanier getLignePanierByLivreAndPanier(int idLivre, int idPanier) throws SQLException;
    public List<LignePanier> getLigneByUser(int id_user) throws SQLException;
    public ObservableList<Livre> listelivres() throws SQLException;
    public List<LignePanier> afficherLignePanier() throws SQLException;

}
