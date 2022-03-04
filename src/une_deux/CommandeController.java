/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package une_deux;

import gestionCommandeLivraison.entities.Commande;
import gestionCommandeLivraison.entities.Livraison;
import gestionCommandeLivraison.entities.Produit;
import gestionCommandeLivraison.entities.User;
import gestionCommandeLivraison.services.CommandeService;
import gestionCommandeLivraison.services.UserService;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import une_deux.utils.DataSource;

/**
 * FXML Controller class
 *
 * @author user
 */
public class CommandeController implements Initializable {

    @FXML
    private TextField tfidClient;
    @FXML
    private TextField tfPrix;
    @FXML
    private TextField tfAdresse;
    @FXML
    private TextField tfDesignation;
    @FXML
    private TextField tfEtat;
    @FXML
    private Button btninsert;
    @FXML
    private Button btnupdate;
    @FXML
    private Button delete;
    @FXML
    private TableView<Commande> tCommande;
    @FXML
    private TableColumn<Commande, Integer> CallIdClient;
    @FXML
    private TableColumn<Commande, Integer> CallPrix;
    @FXML
    private TableColumn<Commande, String> CallAdresse;
    @FXML
    private TableColumn<Commande, String> CallDesignation;
    @FXML
    private TableColumn<Commande, String> CallEtat;
    private Connection connection = DataSource.getInstance().getCnx();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showCommande();
        } catch (SQLException ex) {
            Logger.getLogger(LivraisonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void InsertOnClick(ActionEvent event) throws SQLException {
        insertRecord();
    }

    @FXML
    private void UpdateOnClick(ActionEvent event) throws SQLException {
        updateRecord();
    }

    @FXML
    private void DeleteOnClick(ActionEvent event) throws SQLException {
        deleteRecord();
    }

    public ObservableList<Commande> getCommandeList() throws SQLException {
        ObservableList<Commande> CommandeList = FXCollections.observableArrayList();
        String select = "SELECT * FROM commande";
        PreparedStatement st = connection.prepareStatement(select);
        ResultSet rs = st.executeQuery();

        CommandeService sr = new CommandeService();
        UserService su = new UserService();
        while (rs.next()) {
            Integer id = rs.getInt("idCommande");
            User client = su.get(rs.getInt("idClient"));

            Integer prix = rs.getInt("prixCommande");
            String adresse = rs.getString("adresseCommande");
            String designation = rs.getString("designation");
            String etat = rs.getString("etatCommande");
            Commande c = new Commande(id, designation, adresse, prix, etat, client);
            CommandeList.add(c);
        }
        return CommandeList;
    }

    public void showCommande() throws SQLException {
        ObservableList<Commande> list = getCommandeList();

        CallIdClient.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("idClient"));
        CallPrix.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("prixCommande"));
        CallAdresse.setCellValueFactory(new PropertyValueFactory<Commande, String>("adresseCommande"));
        CallDesignation.setCellValueFactory(new PropertyValueFactory<Commande, String>("designation"));
        CallEtat.setCellValueFactory(new PropertyValueFactory<Commande, String>("etatCommande"));

        tCommande.setItems(list);
    }

    private void insertRecord() throws SQLException {
        String insert = "INSERT INTO commande (idClient, prixCommande, adresseCommande, designation,etatCommande) VALUES (?, ?, ?, ?, ?)";
        Connection connection = DataSource.getInstance().getCnx();
        PreparedStatement st = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
        st.setInt(1, Integer.parseInt(tfidClient.getText()));
        st.setInt(2, Integer.parseInt(tfPrix.getText()));
        st.setString(3, tfAdresse.getText());
        st.setString(4, tfDesignation.getText());
        st.setString(5, tfEtat.getText());

        int rowsInserted = st.executeUpdate();
        ResultSet generatedKeys = st.getGeneratedKeys();

        if (rowsInserted > 0) {
            System.out.println("A new Commande was inserted successfully!");
        }

        showCommande();
    }

    private void updateRecord() throws SQLException {
        String update = "UPDATE commande SET idCommande=?, idClient=?, prixCommande=?, adresseCommande=?, designation=?, etatCommande=? WHERE idCommande=?";
        PreparedStatement st = connection.prepareStatement(update);
        st.setInt(1, Integer.parseInt(tfidClient.getText()));
        st.setString(2, tfPrix.getText());
        st.setString(3, tfAdresse.getText());
        st.setString(4, tfDesignation.getText());
        st.setString(5, tfEtat.getText());

        int rowsUpdated = st.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("An existing Commande was updated successfully!");
        }
        showCommande();
    }

    private void deleteRecord() throws SQLException {
        String delete = "DELETE FROM commande WHERE idCommande=?";
        PreparedStatement st = connection.prepareStatement(delete);

        st.setInt(1, tCommande.getSelectionModel().getSelectedItem().getIdCommande());

        int rowsDeleted = st.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A Livraison was deleted successfully!");
        }
        showCommande();
    }
}
