package gestionCommandeLivraison.services;

import gestionCommandeLivraison.entities.Commande;
import gestionCommandeLivraison.entities.User;
import gestionCommandeLivraison.entities.Iservice.IService;
import gestionCommandeLivraison.entities.Produit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import une_deux.utils.DataSource;



public class CommandeService implements IService<Commande> {

    private Connection connection;

    public CommandeService() {
        this.connection = DataSource.getInstance().getCnx();
    }

    @Override
    public Commande get(int id) throws SQLException {
        String requete = "SELECT * FROM commande WHERE idCommande=?";
        try (PreparedStatement pst = connection.prepareStatement(requete)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            CommandeProduitService scp = new CommandeProduitService();
            ArrayList<Produit> listeProduits = new ArrayList<>();

            Commande c = new Commande();
            UserService su = new UserService();
            while (rs.next()) {
                int idCommande = rs.getInt("idCommande");
                listeProduits = scp.getProduitsParCommande(idCommande);
                String designation = rs.getString("designation");
                String adresseCommande = rs.getString("adresseCommande");
                int prixCommande = rs.getInt("prixCommande");
                String etatCommande = rs.getString("etatCommande");
                User vendeur_id = su.get(rs.getInt("idClient"));

                c = new Commande(idCommande, listeProduits, designation, adresseCommande, prixCommande, etatCommande,
                        vendeur_id);
            }
            return c;
        } catch (SQLException e) {
        }
        return null;
    }

    @Override
    public Commande insert(Commande c) {
        String requete = "INSERT INTO commande ( designation, adresseCommande, prixCommande, etatCommande, idClient) VALUES (?, ?, ?, ?,?)";
        try (PreparedStatement pst = connection.prepareStatement(requete)) {

            pst.setString(1, c.getDesignation());
            pst.setString(2, c.getAdresseCommande());
            pst.setInt(3, c.getPrixCommande());
            pst.setString(4, c.getEtatCommande());
            pst.setInt(5, c.getClient().getId());

            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new order was inserted successfully!");
                return c;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void update(int id, Commande c) {
        String update = "UPDATE commande SET designation=?, adresseCommande=?, prixCommande=?, etatCommande=?, idClient=?  WHERE idCommande=?";
        try (PreparedStatement pst = connection.prepareStatement(update)) {

            pst.setString(1, c.getDesignation());
            pst.setString(2, c.getAdresseCommande());
            pst.setInt(3, c.getPrixCommande());
            pst.setString(4, c.getEtatCommande());
            pst.setInt(5, c.getClient().getId());
            pst.setInt(6, id);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing order was updated successfully!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        String delete = "DELETE FROM commande WHERE idCommande=?";
        try (PreparedStatement pst = connection.prepareStatement(delete)) {
            pst.setInt(1, id);

            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A order was deleted successfully!");
            }
        } catch (SQLException e) {
        }
    }

    @Override
    public ArrayList<Commande> getall() {
        String requete = "SELECT * FROM commande";

        try (PreparedStatement pst = connection.prepareStatement(requete);
                ResultSet rs = pst.executeQuery()) {

            ArrayList<Commande> commandeList = new ArrayList<>();
            ArrayList<Produit> listeProduits = new ArrayList<>();
            CommandeProduitService scp = new CommandeProduitService();
            UserService su = new UserService();
            while (rs.next()) {
                int idCommande = rs.getInt("idCommande");
                listeProduits = scp.getProduitsParCommande(idCommande);
                String designation = rs.getString("designation");
                String adresseCommande = rs.getString("adresseCommande");
                int prixCommande = rs.getInt("prixCommande");
                String etatCommande = rs.getString("etatCommande");
                User idClient = su.get(rs.getInt("idClient"));

                Commande c = new Commande(idCommande, listeProduits, designation, adresseCommande, prixCommande,
                        etatCommande,
                        idClient);

                commandeList.add(c);
            }
            return commandeList;
        } catch (SQLException e) {
        }
        return null;
    }

    public ArrayList<Commande> getCommandeByUtilisateur(int idClient) throws SQLException {
        UserService us = new UserService();
        String requete = "SELECT * FROM commande WHERE idClient=? ";
        try (PreparedStatement pst = connection.prepareStatement(requete)) {
            pst.setInt(1, idClient);
            ResultSet rs = pst.executeQuery();

            CommandeProduitService scp = new CommandeProduitService();
            ArrayList<Commande> commandeList = new ArrayList<>();
            ArrayList<Produit> listeProduits = new ArrayList<>();

            Commande c = new Commande();
            while (rs.next()) {
                int idCommande = rs.getInt("idCommande");
                listeProduits = scp.getProduitsParCommande(idCommande);
                String designation = rs.getString("designation");
                String adresseCommande = rs.getString("adresseCommande");
                int prixCommande = rs.getInt("prixCommande");
                String etatCommande = rs.getString("etatCommande");

                User Client = us.get(rs.getInt("idClient"));
                c = new Commande(idCommande, listeProduits, designation, adresseCommande, prixCommande,
                        etatCommande,
                        Client);
            }
            return commandeList;
        } catch (SQLException e) {
        }
        return null;
    }
}

   
