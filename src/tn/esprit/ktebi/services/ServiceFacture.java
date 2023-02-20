/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.ktebi.entities.Facture;
import tn.esprit.ktebi.interfaces.IFacture;
import tn.esprit.ktebi.utils.MaConnexion;

/**
 *
 * @author Pc Anis
 */
public class ServiceFacture implements IFacture {

    private Connection cnx;

    public ServiceFacture() {
        cnx = MaConnexion.getInstance().getCnx();

    }

    @Override
    public void ajouterFacture(Facture f) throws SQLException {
        try {

            String requete = "insert into facture(mode_paiement,mnt_totale,id_user,id_livre) values(" + f.getMode_paiement() + ",' " + f.getMontant_totale() + "','" + f.getUser() + "','"
                    + f.getLivre() + "');";

            PreparedStatement st = cnx.prepareStatement(requete);
            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                ServicePanier sp = new ServicePanier();
                sp.ViderPanier(f.getUser());

                System.out.println("La facture est ajouté avec succès!");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public List<Facture> afficherFactures() throws SQLException {
        List<Facture> listFact = new ArrayList<>();

        String requete = "SELECT * FROM `facture`";
        PreparedStatement ps = cnx.prepareStatement(requete);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Facture facture = new Facture();
            facture.setId(rs.getInt(1));
            facture.setMode_paiement(rs.getString("mode_paiement"));
            facture.setMontant_totale(rs.getFloat("mnt_totale"));
            facture.setUser(rs.getInt("id_user"));
            facture.setLivre(rs.getInt("id_livre"));

            listFact.add(facture);

        }

        return listFact;
    }

    @Override
    public void supprimerFacture(Facture f) throws SQLException {
        String requete = "delete from facture where id_facture=" + f.getId() + "";
        try {
            PreparedStatement st = cnx.prepareStatement(requete);
            st.executeUpdate();
            System.out.println("La facture est supprimée!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
