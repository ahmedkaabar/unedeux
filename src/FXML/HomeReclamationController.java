/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXML;

import static FXML.AdminReclamationController.encode;
import Main.MainApp;
import Service.CrudReclamation;
import animations.Animations;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import entity.reclamation;
import entity.user;
import notifications.NotificationType;
import notifications.NotificationsBuilder;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
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
public class HomeReclamationController implements Initializable {

    @FXML
    private Pane ContainerUsersAdmin;
    @FXML
    private AnchorPane ContainerDeleteUser;
    @FXML
    private Circle imgOnline;
    @FXML
    private Label dateTime;
    @FXML
    private TabPane TabPaneRec;
    @FXML
    private TableView<reclamation> TableViewReclamation;
    @FXML
    private TableColumn<reclamation, String> col_SujetRec;
    @FXML
    private TableColumn<reclamation, String> col_DescriptionRec;
    @FXML
    private TableColumn<reclamation, ImageView> col_StatusRec;
    @FXML
    private TableColumn<reclamation, Date> col_DateRec;
    @FXML
    private TableColumn<reclamation, Date> col_DateTraitement;
    @FXML
    private TableColumn<reclamation, ImageView> col_imgRec;
    @FXML
    private TableColumn<reclamation, String> col_Reponse;
    @FXML
    private AnchorPane containerAjouterReclam;
    @FXML
    private JFXTextField tfNomRec;
    @FXML
    private JFXTextField tfPrenomRec;
    @FXML
    private JFXTextField tfEmailRec;
    @FXML
    private JFXTextField tfTlRec;
    @FXML
    private JFXComboBox<String> ComboSujetRec;
    @FXML
    private JFXTextArea tfDescriptionRec;
    @FXML
    private ImageView DragimgRec;
    @FXML
    private Text textRec;
    @FXML
    private JFXButton btnSaveReclam;
    @FXML
    private JFXButton btnCancelAddRec;
    @FXML
    private JFXButton btnUpdateReclam;
    @FXML
    private JFXComboBox<String> ComboProduitRec;
    /////
    private JFXDialogTool dialogDeleteReclamation;
    private JFXDialogTool dialogAjouterReclamation;
    private JFXDialogTool dialogShowReponse;

    private static final Stage stage = new Stage();
    @FXML
    private StackPane stckReclamation;
    @FXML
    private AnchorPane rootReclamation;
    @FXML
    private TableColumn<reclamation, String> col_Action;
    @FXML
    private TableColumn<reclamation, String> col_libelle;
    String path = "";
    String ImagePath = "";
    private Image genQRCodeImg; // Generated QR Code image
    reclamation reclamation = new reclamation();
    CrudReclamation CrudReclamation = new CrudReclamation();
    private ObservableList<reclamation> ListReclam;
    private ObservableList<reclamation> FiltreReclam;
    @FXML
    private TextField txtSearch;
    Desktop desktop = Desktop.getDesktop();
    @FXML
    private Label txtStatTotal;
    @FXML
    private AnchorPane ContainerReponse;
    @FXML
    private JFXTextArea txtAreaReponse;
    ///////////////////////////////
    ///////////////////////////////
    ///////////////////////////////
    public static int idUserConnected = 1;  // ======>>>>>>>>>>>>>> idUserConnected
    ///////////////////////////////
    ///////////////////////////////

    /**
     * Initializes the controller class.
     */
    /////////////////////////////
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LoadTableReclam();
        LoadChamps();
        FiltreReclam = FXCollections.observableArrayList();
        LoadStat();
    }

    @FXML
    private void iconAddReclamClicked(MouseEvent event) {
        DragimgRec.setImage(new Image(getClass().getResource("/image/drag-drop.gif").toExternalForm()));

        rootReclamation.setEffect(Constants.BOX_BLUR_EFFECT);
        textRec.setText("Ajouter une Reclamation");
        containerAjouterReclam.setVisible(true);
        btnSaveReclam.setDisable(false);
        btnUpdateReclam.setVisible(true);
        btnSaveReclam.toFront();
        dialogAjouterReclamation = new JFXDialogTool(containerAjouterReclam, stckReclamation);
        dialogAjouterReclamation.show();
        dialogAjouterReclamation.setOnDialogOpened(ev -> {
            // txtNom.requestFocus();
        });

        dialogAjouterReclamation.setOnDialogClosed(ev -> {
            closeStage();
            TableViewReclamation.setDisable(false);
            rootReclamation.setEffect(null);
            containerAjouterReclam.setVisible(false);
            LoadTableReclam();
        });
    }

    @FXML
    private void SearchAnything(KeyEvent event) {
        String WordTyped = txtSearch.getText().trim();
        if (WordTyped.isEmpty()) {
            TableViewReclamation.setItems(ListReclam);
            LoadTableReclam();
        } else {
            FiltreReclam.clear();
            for (reclamation p : ListReclam) {
                if ((p.getSujetRec().toLowerCase().contains(WordTyped.toLowerCase())) || (p.getDescriptionRec().toLowerCase().contains(WordTyped.toLowerCase()))
                        || (p.getDateRec().toString().contains(WordTyped.toLowerCase()))) {
                    FiltreReclam.add(p);
                }
            }
            TableViewReclamation.setItems(FiltreReclam);
        }
    }

    private void LoadStat() {

        // Changing random data after every 1 second.
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int Total = CrudReclamation.countTotalReclamation(idUserConnected);
                txtStatTotal.setText(String.valueOf(Total));
            }
        }));
        ///Repeat indefinitely until stop() method is called.
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();

    }

    @FXML
    private void GeneratePDF(MouseEvent event) throws IOException {

        long millis = System.currentTimeMillis();
        java.sql.Date DateRapport = new java.sql.Date(millis);

        String DateLyoum = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(DateRapport);//yyyyMMddHHmmss
        System.out.println("DateLyoummmmmmmmmmmmmmmmmmmmm   " + DateLyoum);

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {

            PdfWriter.getInstance(document, new FileOutputStream(String.valueOf(DateLyoum + ".pdf")));//yyyy-MM-dd
            document.open();
            Paragraph ph1 = new Paragraph("Rapport Pour :" + DateRapport);
            Paragraph ph2 = new Paragraph(".");
            PdfPTable table = new PdfPTable(6);

            //On créer l'objet cellule.
            PdfPCell cell;

            //contenu du tableau.
            table.addCell("idRec");
            table.addCell("SujetRec");
            table.addCell("DescriptionRec");
            table.addCell("StatusRec");
            table.addCell("getDateRec");
            table.addCell("DateTraitement");
            //     table.addCell("Image : ");
            reclamation.setIdUser(idUserConnected);
            CrudReclamation.AfficherReclamForUser(reclamation).forEach(e
                    -> {
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(String.valueOf(e.getIdRec()));
                table.addCell(e.getSujetRec());
                table.addCell(e.getDescriptionRec());
                table.addCell(e.getStatusRec());
                table.addCell(String.valueOf(e.getDateRec()));
                table.addCell(String.valueOf(e.getDateTraitement()));
            }
            );
            document.add(ph1);
            document.add(ph2);
            document.add(table);
            //  document.addAuthor("Bike");
            // AlertDialog.showNotification("Creation PDF ", "Votre fichier PDF a ete cree avec success", AlertDialog.image_checked);
        } catch (Exception e) {
            System.out.println(e);
        }
        document.close();

        ///Open FilePdf
        File file = new File(DateLyoum + ".pdf");
        if (file.exists()) //checks file exists or not  
        {
            desktop.open(file); //opens the specified file   
        }
    }

    @FXML
    private void GoHomeReclam(MouseEvent event) throws IOException {
        Parent menu = FXMLLoader.load(getClass().getResource("/FXML/HomeReclamation.fxml"));
        stckReclamation.getChildren().removeAll();
        stckReclamation.getChildren().setAll(menu);
    }

    @FXML
    private void GoAdminReclam(MouseEvent event) throws IOException {
        Parent menu = FXMLLoader.load(getClass().getResource("/FXML/AdminReclamation.fxml"));
        stckReclamation.getChildren().removeAll();
        stckReclamation.getChildren().setAll(menu);
    }

    @FXML
    private void GoHomeRating(MouseEvent event) throws IOException {
        Parent menu = FXMLLoader.load(getClass().getResource("/FXML/HomeRating.fxml"));
        stckReclamation.getChildren().removeAll();
        stckReclamation.getChildren().setAll(menu);
    }

    @FXML
    private void GoAdminRating(MouseEvent event) throws IOException {
        Parent menu = FXMLLoader.load(getClass().getResource("/FXML/AdminRating.fxml"));
        stckReclamation.getChildren().removeAll();
        stckReclamation.getChildren().setAll(menu);
    }

    @FXML
    private void CloseReponse(MouseEvent event) {
        if (dialogShowReponse != null) {
            dialogShowReponse.close();
        }

    }

    private class StatusReclamCellValueFactory implements Callback<TableColumn.CellDataFeatures<reclamation, ImageView>, ObservableValue<ImageView>> {

        @Override
        public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<reclamation, ImageView> param) {
            reclamation item = param.getValue();

            ImageView Etat = null;

            if (item.getStatusRec().equals("non traite")) {
                Etat = new ImageView(new Image("/image/enAttente.png"));
            }
            if (item.getStatusRec().equals("traite")) {
                Etat = new ImageView(new Image("/image/traite.png"));
            }
            return new SimpleObjectProperty<>(Etat);
        }
    }

    private class ImageReclamCellValueFactory implements Callback<TableColumn.CellDataFeatures<reclamation, ImageView>, ObservableValue<ImageView>> {

        @Override
        public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<reclamation, ImageView> param) {
            reclamation item = param.getValue();

            ImageView Etat = null;

            Etat = item.getImgReclamation();

            return new SimpleObjectProperty<>(Etat);
        }
    }

    private void LoadTableReclam() {
        List<reclamation> listeReclamm = new ArrayList<>();
        reclamation.setIdUser(idUserConnected);
        listeReclamm = CrudReclamation.AfficherReclamForUser(reclamation);
        ObservableList<reclamation> Listeeee = FXCollections.observableArrayList(listeReclamm);

        col_SujetRec.setCellValueFactory(new PropertyValueFactory<>("SujetRec"));
        col_DescriptionRec.setCellValueFactory(new PropertyValueFactory<>("DescriptionRec"));
        //col_StatusRec.setCellValueFactory(new PropertyValueFactory<>("StatusRec"));
        col_StatusRec.setCellValueFactory(new StatusReclamCellValueFactory());
        col_DateRec.setCellValueFactory(new PropertyValueFactory<>("DateRec"));
        col_DateTraitement.setCellValueFactory(new PropertyValueFactory<>("DateTraitement"));
        col_imgRec.setCellValueFactory(new ImageReclamCellValueFactory());
        col_libelle.setCellValueFactory(new PropertyValueFactory<>("libelleProduit"));
        col_Reponse.setCellValueFactory(new PropertyValueFactory<>("reponse"));

        //
        ListReclam = FXCollections.observableArrayList(listeReclamm);
        TableViewReclamation.setItems(ListReclam);
        ///

        //add cell of button edit 
        Callback<TableColumn<reclamation, String>, TableCell<reclamation, String>> cellFoctory = (TableColumn<reclamation, String> param) -> {
            //make cell containing buttons

            final TableCell<reclamation, String> cell = new TableCell<reclamation, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    // that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                    } else {
                        ImageView Deleteicon, Editicon;
                        Editicon = new ImageView(new Image("/image/editicon.png"));
                        Editicon.setFitHeight(30);
                        Editicon.setFitWidth(30);
                        setGraphic(Editicon);

                        Deleteicon = new ImageView(new Image("/image/deleteicon.png"));
                        Deleteicon.setFitHeight(30);
                        Deleteicon.setFitWidth(30);
                        setGraphic(Deleteicon);

                        Editicon.setOnMouseClicked((MouseEvent event) -> {
                            System.out.println("icon edit is pressed !");

                            int idRec = Integer.valueOf((TableViewReclamation.getSelectionModel().getSelectedItem().getIdRec()));
                            String reponse = TableViewReclamation.getSelectionModel().getSelectedItem().getReponse();

                            if (CrudReclamation.contraintModifier24h(idRec) < 1 && (reponse.length() == 0)) {
                                btnUpdateReclam.toFront();
                                ComboSujetRec.setValue(TableViewReclamation.getSelectionModel().getSelectedItem().getSujetRec());
                                ComboProduitRec.setValue(TableViewReclamation.getSelectionModel().getSelectedItem().getLibelleProduit());

                                tfDescriptionRec.setText(String.valueOf(TableViewReclamation.getSelectionModel().getSelectedItem().getDescriptionRec()));

                                textRec.setText("Modifier La reclamation");
                                ///////////////////////
                                try {
                                    String requeteee = "SELECT imgRec FROM reclamation WHERE idRec = '" + idRec + "'";
                                    Statement psttt = Myconnexion.getInstance().getCnx().createStatement();
                                    ResultSet rss = psttt.executeQuery(requeteee);
                                    while (rss.next()) {
                                        ImagePath = rss.getString(1);//bech najmt njyb mnha nom image 
                                        path = rss.getString(1);//bech najmt njyb mnha nom image
                                        Image img = new Image(new FileInputStream(ImagePath));
                                        DragimgRec.setImage(img);
                                    }

                                } catch (SQLException ex) {
                                    System.out.println(ex.getMessage());
                                } catch (FileNotFoundException ex) {
                                    DragimgRec.setImage(new Image(getClass().getResource("/image/drag-drop.gif").toExternalForm()));
                                }

                                //////////////////////
                                showDialogModifierReclam();
                            } else {
                                AlertsBuilder.create(AlertType.ERROR, stckReclamation, rootReclamation, TableViewReclamation, "reclam dépasse 24H Or Reclam Traité");
                            }
                        });

                        Deleteicon.setOnMouseClicked((MouseEvent event) -> {
                            System.out.println("icon delete is pressed !");
                            int idRec = Integer.valueOf((TableViewReclamation.getSelectionModel().getSelectedItem().getIdRec()));
                            String reponse = TableViewReclamation.getSelectionModel().getSelectedItem().getReponse();

                            if (CrudReclamation.contraintModifier24h(idRec) < 1 && (reponse.length() == 0)) {
                                showDialogDeleteReclam();
                            } else {
                                AlertsBuilder.create(AlertType.ERROR, stckReclamation, rootReclamation, TableViewReclamation, "reclam dépasse 24H Or Reclam Traité");
                            }

                        });
                        //managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(Deleteicon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(Editicon, new Insets(2, 3, 0, 2));
                        HBox managebtn = new HBox(Editicon, Deleteicon);
                        setGraphic(managebtn);
                    }
                }
            };
            return cell;
        };
        col_Action.setCellFactory(cellFoctory);

        TableViewReclamation.setOnMouseClicked(ev -> {
            if (ev.getButton().equals(MouseButton.PRIMARY) && ev.getClickCount() == 2) {
                String Reponse = TableViewReclamation.getSelectionModel().getSelectedItem().getReponse();
                if (Reponse.length() > 0) {
                    txtAreaReponse.setText(Reponse);
                    showRep();
                }
            }
        });

    }

    private void showDialogDeleteReclam() {
        rootReclamation.setEffect(Constants.BOX_BLUR_EFFECT);
        ContainerDeleteUser.setVisible(true);

        dialogDeleteReclamation = new JFXDialogTool(ContainerDeleteUser, stckReclamation);
        dialogDeleteReclamation.show();

        dialogDeleteReclamation.setOnDialogClosed(ev -> {
            TableViewReclamation.setDisable(false);
            rootReclamation.setEffect(null);
            ContainerDeleteUser.setVisible(false);
            LoadTableReclam();

        });
    }

    private void showDialogModifierReclam() {

        rootReclamation.setEffect(Constants.BOX_BLUR_EFFECT);
        //imageContainer.toFront();
        containerAjouterReclam.setVisible(true);
        // btnSaveReclam.setDisable(false);

        dialogAjouterReclamation = new JFXDialogTool(containerAjouterReclam, stckReclamation);
        dialogAjouterReclamation.show();
        dialogAjouterReclamation.setOnDialogOpened(ev -> {
            tfNomRec.requestFocus();
        });

        dialogAjouterReclamation.setOnDialogClosed(ev -> {
            closeStage();
            TableViewReclamation.setDisable(false);
            rootReclamation.setEffect(null);
            containerAjouterReclam.setVisible(false);
            LoadTableReclam();
        });
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
    private void hideDialogDeleteReclam() {
        if (dialogDeleteReclamation != null) {
            dialogDeleteReclamation.close();
        }
    }

    @FXML
    private void deleteReclamClicked(MouseEvent event) {
        if (TableViewReclamation.getSelectionModel().getSelectedItem() != null) {
            reclamation = TableViewReclamation.getSelectionModel().getSelectedItem();
            Boolean result = CrudReclamation.SupprimerReclam(reclamation.getIdRec());
            if (result) {
                hideDialogDeleteReclam();
                AlertsBuilder.create(AlertType.SUCCES, stckReclamation, rootReclamation, TableViewReclamation, "id: " + reclamation.getIdRec() + " " + reclamation.getSujetRec() + " \n" + "a été supprimé");
            } else {
                AlertsBuilder.create(AlertType.ERROR, stckReclamation, rootReclamation, TableViewReclamation, Constants.MESSAGE_ERROR_CONNECTION_MYSQL);
            }
        }

    }

    private void LoadChamps() {
        ComboSujetRec.getItems().addAll("Produit", "Application", "Autre");
        try {
            user t = new user();
            String requetee = "SELECT * FROM user WHERE id_user = '" + idUserConnected + "'";
            System.out.println("id ===>>>>>> " + idUserConnected);
            Statement pstt = Myconnexion.getInstance().getCnx().createStatement();
            ResultSet rs = pstt.executeQuery(requetee);
            while (rs.next()) {
                tfNomRec.setText(rs.getString("nom"));
                tfPrenomRec.setText(rs.getString("prenom"));
                tfEmailRec.setText(rs.getString("email"));
                tfTlRec.setText(rs.getString("numTel_user"));
                break;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void SelectSujetRec(ActionEvent event) {
        String choix = ComboSujetRec.getSelectionModel().getSelectedItem();
        System.out.println(choix);
        if (!(null == choix)) {
            if (choix.equals("Produit")) {
                ComboProduitRec.setVisible(true);
                try {
                    String requeteee = "SELECT * FROM produit ";
                    Statement psttt = Myconnexion.getInstance().getCnx().createStatement();
                    ResultSet rss = psttt.executeQuery(requeteee);
                    while (rss.next()) {
                        ComboProduitRec.getItems().addAll(rss.getString("libelle"));
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                ComboProduitRec.setVisible(false);
                ComboProduitRec.getItems().clear();
            }
        }
    }

    @FXML
    private void handleDragOver_reclamation(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    private void handleDrop_reclamation(DragEvent event) throws FileNotFoundException {
        List<File> files = event.getDragboard().getFiles();
        Image img = new Image(new FileInputStream(files.get(0)));
        DragimgRec.setImage(img);
        path = files.get(0).getAbsolutePath();
        System.out.println(path);
    }

    @FXML
    private void newReclam(MouseEvent event) {

        String DescriptionRec = tfDescriptionRec.getText().trim(); // Supprimer supprimer les espace vide
        String SujetRec = ComboSujetRec.getSelectionModel().getSelectedItem();
        String ProduitRec = ComboProduitRec.getSelectionModel().getSelectedItem();

        if (DescriptionRec.isEmpty()) {
            //  tfDescriptionRec.requestFocus();
            Animations.shake(tfDescriptionRec);
            return;
        }
        if ((SujetRec == "Produit") && (ProduitRec == null)) {
            Animations.shake(ComboProduitRec);
            return;
        }
        if (SujetRec == null) {
            Animations.shake(ComboSujetRec);
            return;
        }

        reclamation rec = new reclamation();
        rec.setIdUser(Integer.parseInt(String.valueOf(idUserConnected)));
        rec.setSujetRec(ComboSujetRec.getSelectionModel().getSelectedItem());
        String Value = tfNomRec.getText() + " " + tfPrenomRec.getText();
        rec.setNomPrenomUser(Value);
        rec.setDescriptionRec(tfDescriptionRec.getText());
        rec.setEmailUser(tfEmailRec.getText());
        rec.setImgRec(path);
        if (SujetRec == "Produit") {
            rec.setLibelleProduit(ProduitRec);
        }
        System.out.println("aaaaaaaaaaaaaaaaa " + path);
        CrudReclamation work = new CrudReclamation();
        //work.ajouterReclamation(rec);

        DragimgRec.setImage(new Image(getClass().getResource("/image/drag-drop.gif").toExternalForm()));
        boolean result = CrudReclamation.AjouterReclam(rec);
        System.out.println(result);
        closeDialogAddRec();
        path = "";
        ImagePath = "";
        if (result) {
            closeDialogAddRec();
            AlertsBuilder.create(AlertType.SUCCES, stckReclamation, rootReclamation, TableViewReclamation, Constants.MESSAGE_ADDED);
 	    NotificationsBuilder.create(NotificationType.SUCCESS, Constants.MESSAGE_ADDED);
            LoadTableReclam();
        } else {
            AlertsBuilder.create(AlertType.ERROR, stckReclamation, rootReclamation, TableViewReclamation, Constants.MESSAGE_ADDED);
        }
        tfDescriptionRec.clear();

    }

    @FXML
    private void closeDialogAddRec() {
        if (dialogAjouterReclamation != null) {
            dialogAjouterReclamation.close();
            btnUpdateReclam.setVisible(true);
            btnCancelAddRec.setVisible(true);
            tfDescriptionRec.setText("");
            ImagePath = "";
            ComboProduitRec.getSelectionModel().clearSelection();
            ComboSujetRec.getSelectionModel().clearSelection();

        }
        ComboProduitRec.getSelectionModel().clearSelection();
        ComboSujetRec.getSelectionModel().clearSelection();
        LoadTableReclam();
    }

    @FXML
    private void closeDialogAddrec(MouseEvent event) {
        if (dialogAjouterReclamation != null) {
            dialogAjouterReclamation.close();
            tfDescriptionRec.setText("");
            ComboProduitRec.getSelectionModel().clearSelection();
            ComboSujetRec.getSelectionModel().clearSelection();
            ImagePath = "";
            btnUpdateReclam.setVisible(true);
            btnCancelAddRec.setVisible(true);
        }
        ComboProduitRec.getSelectionModel().clearSelection();
        ComboSujetRec.getSelectionModel().clearSelection();
        LoadTableReclam();
    }

    @FXML
    private void updateReclam(MouseEvent event) {

        int idRec = 0;
        if (TableViewReclamation.getSelectionModel().getSelectedItem() != null) {
            idRec = Integer.valueOf((TableViewReclamation.getSelectionModel().getSelectedItem().getIdRec()));
        }
        String Sujet = ComboSujetRec.getSelectionModel().getSelectedItem();
        String Produit = ComboProduitRec.getSelectionModel().getSelectedItem();

        reclamation cat = new reclamation();
        closeDialogAddRec();

        cat.setSujetRec(Sujet);
        cat.setDescriptionRec(tfDescriptionRec.getText());
        cat.setLibelleProduit(Produit);
        cat.setImgRec(path);
        cat.setIdRec(idRec);
        /////////////////////////
        DragimgRec.setImage(new Image(getClass().getResource("/image/drag-drop.gif").toExternalForm()));
        path = "";
        ImagePath = "";
        ///////////////////////
        Boolean result = CrudReclamation.ModifierReclam(cat);

        if (result) {
            closeDialogAddRec();
            AlertsBuilder.create(AlertType.SUCCES, stckReclamation, rootReclamation, TableViewReclamation, Constants.MESSAGE_UPDATED);
        } else {
            AlertsBuilder.create(AlertType.ERROR, stckReclamation, rootReclamation, TableViewReclamation, Constants.MESSAGE_ERROR_CONNECTION_MYSQL);

        }
        LoadTableReclam();

    }

    public static void closeStage() {
        if (stage != null) {
            stage.hide();
        }
    }

    private void showRep() {

        rootReclamation.setEffect(Constants.BOX_BLUR_EFFECT);
        //imageContainer.toFront();
        ContainerReponse.setVisible(true);
        // btnSaveReclam.setDisable(false);

        dialogShowReponse = new JFXDialogTool(ContainerReponse, stckReclamation);
        dialogShowReponse.show();
        dialogShowReponse.setOnDialogOpened(ev -> {
            txtAreaReponse.requestFocus();
        });

        dialogShowReponse.setOnDialogClosed(ev -> {
            closeStage();
            TableViewReclamation.setDisable(false);
            rootReclamation.setEffect(null);
            ContainerReponse.setVisible(false);
            LoadTableReclam();
        });
    }

}
