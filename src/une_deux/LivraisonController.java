package une_deux;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import gestionCommandeLivraison.entities.Commande;
import gestionCommandeLivraison.entities.Livraison;
import gestionCommandeLivraison.entities.User;
import gestionCommandeLivraison.services.CommandeService;
import gestionCommandeLivraison.services.LivraisonService;
import static gestionCommandeLivraison.services.LivraisonService.printSQLException;
import gestionCommandeLivraison.services.UserService;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
 * @author Fedi
 */
public class LivraisonController implements Initializable {

    @FXML
    private TextField tfidcommande;
    @FXML
    private TextField tfadresse;
    @FXML
    private TextField tfetat;
    @FXML
    private TextField tfvendeur;
    @FXML
    private TableView<Livraison> tLivraison;
    @FXML
    private TableColumn<Livraison, Integer> callId;
    @FXML
    private TableColumn<Livraison, Integer> CallIdCommande;
    @FXML
    private TableColumn<Livraison, String> CallAdresse;
    @FXML
    private TableColumn<Livraison, String> CallEtat;
    @FXML
    private TableColumn<Livraison, Integer> CallVendeur;
    @FXML
    private Button btninsert;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btndelete;

    private Connection connection = DataSource.getInstance().getCnx();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showLivraisons();
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

    public ObservableList<Livraison> getLivraisonList() throws SQLException {
        ObservableList<Livraison> LivraisonList = FXCollections.observableArrayList();
        String select = "SELECT * FROM Livraisons";
        PreparedStatement st = connection.prepareStatement(select);
        ResultSet rs = st.executeQuery();

        CommandeService sr = new CommandeService();
        UserService su = new UserService();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            Commande commande = sr.get(rs.getInt("commandeId"));
            String adresse = rs.getString("adresse");
            String etat = rs.getString("etat");
            User vendeur_id = su.get(rs.getInt("vendeurId"));
            Livraison l = new Livraison(id, commande, adresse, etat, vendeur_id);
            LivraisonList.add(l);
        }
        return LivraisonList;
    }

    public void showLivraisons() throws SQLException {
        ObservableList<Livraison> list = getLivraisonList();

        CallIdCommande.setCellValueFactory(new PropertyValueFactory<Livraison, Integer>("idCommande"));
        CallAdresse.setCellValueFactory(new PropertyValueFactory<Livraison, String>("adresse"));
        CallEtat.setCellValueFactory(new PropertyValueFactory<Livraison, String>("etat"));
        CallVendeur.setCellValueFactory(new PropertyValueFactory<Livraison, Integer>("vendeur"));

        tLivraison.setItems(list);
    }

    private void insertRecord() throws SQLException {
        String insert = "INSERT INTO Livraisons (commandeId, adresse, etat, vendeurId) VALUES (?, ?, ?, ?)";
        Connection connection = DataSource.getInstance().getCnx();
        PreparedStatement st = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
        st.setInt(1, Integer.parseInt(tfidcommande.getText()));
        st.setString(2, tfadresse.getText());
        st.setString(3, tfetat.getText());
        st.setInt(4, Integer.parseInt(tfvendeur.getText()));

        int rowsInserted = st.executeUpdate();
        ResultSet generatedKeys = st.getGeneratedKeys();

        if (rowsInserted > 0) {
            System.out.println("A new Livraison was inserted successfully!");
        }

        showLivraisons();
    }

    private void updateRecord() throws SQLException {
        String update = "UPDATE Livraisons SET commandeId=?, adresse=?, etat=?, vendeurId=? WHERE id=?";
        PreparedStatement st = connection.prepareStatement(update);
        st.setInt(1, Integer.parseInt(tfidcommande.getText()));
        st.setString(2, tfadresse.getText());
        st.setString(3, tfetat.getText());
        st.setInt(4, Integer.parseInt(tfvendeur.getText()));
        st.setInt(5, tLivraison.getSelectionModel().getSelectedItem().getId());

        int rowsUpdated = st.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("An existing Livraison was updated successfully!");
        }
        showLivraisons();
    }

    private void deleteRecord() throws SQLException {
        String delete = "DELETE FROM Livraisons WHERE id=?";
        PreparedStatement st = connection.prepareStatement(delete);
        
        st.setInt(1, tLivraison.getSelectionModel().getSelectedItem().getId());

        int rowsDeleted = st.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A Livraison was deleted successfully!");
        }
        showLivraisons();
    }
}
