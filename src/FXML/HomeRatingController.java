/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXML;

import static FXML.HomeReclamationController.closeStage;
import Main.MainApp;
import Service.CrudRating;
import animations.Animations;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import entity.Rate;
import entity.reclamation;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.Rating;
import utils.AlertType;
import utils.AlertsBuilder;
import utils.Constants;
import utils.JFXDialogTool;
import utils.Myconnexion;

/**
 * FXML Controller class
 *
 * @author YedesHamda
 */
public class HomeRatingController implements Initializable {

    @FXML
    private Pane ContainerUsersAdmin;
    @FXML
    private Circle imgOnline;
    @FXML
    private Label dateTime;
    @FXML
    private TabPane TabPaneRec;
    @FXML
    private TableColumn<Rate, String> col_Action;
    @FXML
    private AnchorPane containerAddRate;
    @FXML
    private Text textRate;
    @FXML
    private JFXButton btnSaveRate;
    @FXML
    private JFXButton btnCancelAddRate;
    @FXML
    private JFXTextField tfDateRate;
    @FXML
    private StackPane stckRating;
    @FXML
    private AnchorPane rootRating;
    private JFXDialogTool dialogAddRating;
    private JFXDialogTool dialogDeleteRating;
    private static final Stage stage = new Stage();
    CrudRating CrudRating = new CrudRating();
    Rate rate = new Rate();
    @FXML
    private JFXComboBox<String> ComboLibelle;
    @FXML
    private TableColumn<Rate, Integer> col_idRate;
    @FXML
    private TableColumn<Rate, String> col_Libelle;
    @FXML
    private TableColumn<Rate, Date> col_DateRate;
    @FXML
    private TableColumn<Rate, Rating> col_Rate;
    @FXML
    private TableView<Rate> TableViewRate;
    @FXML
    private Rating RatingProduit;
    @FXML
    private JFXButton btnUpdateRate;
    @FXML
    private AnchorPane ContainerDeleteRate;
    private ComboBox<String> CombofiltreSearch;
    private ObservableList<Rate> ListRatee;
    private ObservableList<Rate> FiltreRatee;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        FiltreRatee = FXCollections.observableArrayList();
        LoadTableRate();
    }

    private void LoadTableRate() {

        List<Rate> listeRate = new ArrayList<>();
        listeRate = CrudRating.displayRate(rate);

        ObservableList<Rate> Liste = FXCollections.observableArrayList(listeRate);

        col_idRate.setCellValueFactory(new PropertyValueFactory<Rate, Integer>("idRate"));
        col_Libelle.setCellValueFactory(new PropertyValueFactory<Rate, String>("Libelle"));
        col_DateRate.setCellValueFactory(new PropertyValueFactory<Rate, Date>("DateRate"));
        col_Rate.setCellValueFactory(new PropertyValueFactory<Rate, Rating>("Rate"));
        TableViewRate.setItems(Liste);


        //add cell of button edit 
        Callback<TableColumn<Rate, String>, TableCell<Rate, String>> cellFoctory = (TableColumn<Rate, String> param) -> {
            //make cell containing buttons

            final TableCell<Rate, String> cell = new TableCell<Rate, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    // that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                    } else {

                        ImageView DeleteJoueur, EditJoueur;
                        EditJoueur = new ImageView(new Image("/image/editicon.png"));
                        EditJoueur.setFitHeight(30);
                        EditJoueur.setFitWidth(30);
                        setGraphic(EditJoueur);

                        DeleteJoueur = new ImageView(new Image("/image/deleteicon.png"));
                        DeleteJoueur.setFitHeight(30);
                        DeleteJoueur.setFitWidth(30);
                        setGraphic(DeleteJoueur);

                        EditJoueur.setOnMouseClicked((MouseEvent event) -> {
                            System.out.println("icon edit is pressed !");
                            ComboLibelle.setValue(TableViewRate.getSelectionModel().getSelectedItem().getLibelle());
                            RatingProduit.setRating(TableViewRate.getSelectionModel().getSelectedItem().getRate());
                            tfDateRate.setText(String.valueOf(TableViewRate.getSelectionModel().getSelectedItem().getDateRate()));
                            showDialogModifierReclam();
                        });

                        DeleteJoueur.setOnMouseClicked((MouseEvent event) -> {
                            System.out.println("icon delete is pressed !");
                            ShowDeleteDialoge();
                        });
                        //managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(DeleteJoueur, new Insets(2, 2, 0, 3));
                        HBox.setMargin(EditJoueur, new Insets(2, 3, 0, 2));
                        HBox managebtn = new HBox(EditJoueur, DeleteJoueur);
                        setGraphic(managebtn);
                    }
                }
            };
            return cell;
        };
        col_Action.setCellFactory(cellFoctory);
    }

    private void ShowDeleteDialoge() {
        ContainerDeleteRate.setVisible(true);

        dialogDeleteRating = new JFXDialogTool(ContainerDeleteRate, stckRating);
        dialogDeleteRating.show();

        dialogDeleteRating.setOnDialogClosed(ev -> {
            closeStage();
            // TableViewReclamation.setDisable(false);
            rootRating.setEffect(null);
            ContainerDeleteRate.setVisible(false);
            //  LoadTableReclamation();
        });
    }

    private void showDialogModifierReclam() {
        RemplireCombo();
        ComboLibelle.setValue(TableViewRate.getSelectionModel().getSelectedItem().getLibelle());

        rootRating.setEffect(Constants.BOX_BLUR_EFFECT);
        //imageContainer.toFront();
        containerAddRate.setVisible(true);
        //btnSaveRate.setDisable(false);
        btnUpdateRate.toFront();

        dialogAddRating = new JFXDialogTool(containerAddRate, stckRating);
        dialogAddRating.show();
        dialogAddRating.setOnDialogOpened(ev -> {
            tfDateRate.requestFocus();
        });

        dialogAddRating.setOnDialogClosed(ev -> {
            closeStage();
            TableViewRate.setDisable(false);
            rootRating.setEffect(null);
            containerAddRate.setVisible(false);
            LoadTableRate();
        });
    }

    @FXML
    private void iconAddReclamClicked(MouseEvent event) {
        long millis = System.currentTimeMillis();
        java.sql.Date DateRate = new java.sql.Date(millis);
        tfDateRate.setText(String.valueOf(DateRate));
        btnSaveRate.toFront();
        rootRating.setEffect(Constants.BOX_BLUR_EFFECT);
        RemplireCombo();
        textRate.setText("Ajouter une Evaluation");
        containerAddRate.setVisible(true);
        btnSaveRate.setDisable(false);
        btnSaveRate.toFront();

        dialogAddRating = new JFXDialogTool(containerAddRate, stckRating);
        dialogAddRating.show();

        dialogAddRating.setOnDialogOpened(ev -> {
            ComboLibelle.requestFocus();
        });

        dialogAddRating.setOnDialogClosed(ev -> {
            closeStage();
            // TableViewReclamation.setDisable(false);
            rootRating.setEffect(null);
            containerAddRate.setVisible(false);
            //  LoadTableReclamation();
        });
    }

    @FXML
    private void newRate(MouseEvent event) {

        rate.setLibelle(ComboLibelle.getSelectionModel().getSelectedItem());
        int stars = (int) RatingProduit.getRating();
        rate.setRate(stars);
        System.out.println("9adech men star ===  " + stars);

        String ValueComboLibelle = ComboLibelle.getSelectionModel().getSelectedItem();
        if (ValueComboLibelle == null) {
            Animations.shake(ComboLibelle);
            return;
        }

        CrudRating.ajouterRate(rate);
        LoadTableRate();
        closeDialogAddRate();

    }

    @FXML
    private void closeDialogAddRate() {
        if (dialogAddRating != null) {
            dialogAddRating.close();
        }
    }

    @FXML
    private void closeDialogAddrate(MouseEvent event) {
        if (dialogAddRating != null) {
            dialogAddRating.close();
        }
    }

    @FXML
    private void close_app(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You sure do you want Exit !");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    private void minimize_app(ActionEvent event) {
        MainApp.stage.setIconified(true);
    }

    @FXML
    private void GoHomeReclam(MouseEvent event) throws IOException {
        Parent menu = FXMLLoader.load(getClass().getResource("/FXML/HomeReclamation.fxml"));
        stckRating.getChildren().removeAll();
        stckRating.getChildren().setAll(menu);
    }

    @FXML
    private void GoAdminReclam(MouseEvent event) throws IOException {
        Parent menu = FXMLLoader.load(getClass().getResource("/FXML/AdminReclamation.fxml"));
        stckRating.getChildren().removeAll();
        stckRating.getChildren().setAll(menu);
    }

    @FXML
    private void GoHomeRating(MouseEvent event) throws IOException {
        Parent menu = FXMLLoader.load(getClass().getResource("/FXML/HomeRating.fxml"));
        stckRating.getChildren().removeAll();
        stckRating.getChildren().setAll(menu);
    }

    @FXML
    private void GoAdminRating(MouseEvent event) throws IOException {
        Parent menu = FXMLLoader.load(getClass().getResource("/FXML/AdminRating.fxml"));
        stckRating.getChildren().removeAll();
        stckRating.getChildren().setAll(menu);
    }

    public static void closeStage() {
        if (stage != null) {
            stage.hide();
        }
    }

    private void RemplireCombo() {
        ComboLibelle.getItems().clear();
        String libelle = null;
        try {
            String requeteee = "SELECT * FROM produit ";
            Statement psttt = Myconnexion.getInstance().getCnx().createStatement();
            ResultSet rss = psttt.executeQuery(requeteee);
            while (rss.next()) {
                libelle = rss.getString(1);//bech najmt njyb mnha nom de 
                ComboLibelle.getItems().addAll(rss.getString("libelle"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void UpdateRate(MouseEvent event) {

        int idRate = 0;
        if (TableViewRate.getSelectionModel().getSelectedItem() != null) {
            idRate = Integer.valueOf((TableViewRate.getSelectionModel().getSelectedItem().getIdRate()));
        }

        rate.setLibelle(ComboLibelle.getSelectionModel().getSelectedItem());
        int stars = (int) RatingProduit.getRating();
        rate.setRate(stars);
        rate.setIdRate(idRate);

        Boolean result = CrudRating.ModifierRate(rate);

        if (result) {
            closeDialogAddRate();
            AlertsBuilder.create(AlertType.SUCCES, stckRating, rootRating, TableViewRate, Constants.MESSAGE_UPDATED);
        } else {
            AlertsBuilder.create(AlertType.ERROR, stckRating, rootRating, TableViewRate, Constants.MESSAGE_ERROR_CONNECTION_MYSQL);
        }

        LoadTableRate();

    }

    @FXML
    private void hideDialogDeleteRate() {
        if (dialogDeleteRating != null) {
            dialogDeleteRating.close();
        }

    }

    @FXML
    private void deleteRateClicked(MouseEvent event) {

        if (TableViewRate.getSelectionModel().getSelectedItem() != null) {
            rate = TableViewRate.getSelectionModel().getSelectedItem();
            Boolean result = CrudRating.SupprimerRating(rate.getIdRate());
            if (result) {
                hideDialogDeleteRate();
                LoadTableRate();
                AlertsBuilder.create(AlertType.ERROR, stckRating, rootRating, TableViewRate, "This Rating Has Been Deleted!");
            } else {
                AlertsBuilder.create(AlertType.ERROR, stckRating, rootRating, TableViewRate, Constants.MESSAGE_ERROR_CONNECTION_MYSQL);
            }
        }
    }



}
