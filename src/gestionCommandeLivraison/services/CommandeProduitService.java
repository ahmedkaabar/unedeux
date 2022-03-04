/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionCommandeLivraison.services;

import gestionCommandeLivraison.entities.CommandeProduit;
import gestionCommandeLivraison.entities.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import une_deux.utils.DataSource;

/**
 *
 * @author Fedi
 */
public class CommandeProduitService {
    
 private Connection connection;
 public CommandeProduitService() {
        this.connection = DataSource.getInstance().getCnx();
    }


    public CommandeProduit get(int id) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    public ArrayList<Produit> getProduitsParCommande(int commandeId) {
        String select = "select DISTINCT p.idProduit,p.libelle,p.prix,p.quantite,p.image from commande c, produits p, commandes_produits cp where cp.idCommande = ? and cp.idProduit = p.idProduit";
        ArrayList<Produit> listeProduits = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(select)) {
            pst.setInt(1, commandeId);
           

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int idProduit = rs.getInt("idProduit");
                String libelle = rs.getString("libelle");
                int prix = rs.getInt("prix");
                int quantite = rs.getInt("quantite");
                String image = rs.getString("image");

                Produit p = new Produit(idProduit, libelle, prix, quantite, image);

                listeProduits.add(p);
            }
            return listeProduits;
        } catch (SQLException e) {
        }
        return listeProduits;
    }

    public void addProduitToCommande(int idProduit, int idCommande) {
        String requete = "INSERT INTO commandes_produits ( idProduit, idCommande ) VALUES (?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(requete)) {
            pst.setInt(1, idProduit);
            pst.setInt(2, idCommande);
            int rowsUpdated = pst.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("A product was added to the order successfully!");
            }
        } catch (SQLException e) {
        }
    }

}
