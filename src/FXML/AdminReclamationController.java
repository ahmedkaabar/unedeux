/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXML;

import static FXML.HomeReclamationController.idUserConnected;
import Main.MainApp;
import Service.CrudReclamation;
import Service.mail;
import animations.Animations;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import entity.reclamation;
import entity.user;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
public class AdminReclamationController implements Initializable {

    @FXML
    private StackPane stckReclamation;
    @FXML
    private AnchorPane rootReclamation;
    @FXML
    private Pane ContainerUsersAdmin;
    @FXML
    private TabPane TabPaneRec;
    @FXML
    private TableView<reclamation> TableViewReclamation;
    @FXML
    private TableColumn<reclamation, Integer> col_idRec;
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
    private TableColumn<reclamation, String> col_libelle;
    @FXML
    private TableColumn<reclamation, String> col_Action;
    @FXML
    private AnchorPane ContainerDeleteUser;
    @FXML
    private TextField txtSearch;
    @FXML
    private JFXTextField tfNomRec;
    @FXML
    private JFXTextField tfEmailRec;
    @FXML
    private JFXTextArea tfDescriptionRec;
    @FXML
    private ImageView DragimgRec;
    @FXML
    private AnchorPane containerInfoReclam;
    @FXML
    private JFXButton btnCloseinfoRec;
    String path = "";
    String ImagePath = "";
    private Image genQRCodeImg; // Generated QR Code image
    reclamation reclamation = new reclamation();
    CrudReclamation CrudReclamation = new CrudReclamation();
    private ObservableList<reclamation> ListReclam;
    private ObservableList<reclamation> FiltreReclam;
    private JFXDialogTool dialogDeleteReclamation;
    private JFXDialogTool dialogInfoReclamation;
    private JFXDialogTool dialogChangeStatus;
    private JFXDialogTool dialogCodeQR;
    private static final Stage stage = new Stage();
    @FXML
    private AnchorPane ContainerStauts;
    @FXML
    private JFXTextField txtSujetRec;
    @FXML
    private JFXTextField txtProduit;
    @FXML
    private ImageView imgQRCodeGen;
    mail mail = new mail();
    @FXML
    private ComboBox<String> CombofiltreSearch;
    @FXML
    private JFXTextArea txtAreaReponse;
    @FXML
    private TableColumn<reclamation, String> col_Reponse;
    @FXML
    private PieChart ReclamPie;
    @FXML
    private Label txtStatReclam;
    @FXML
    private AnchorPane ContainerCodeQR;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LoadTableReclam();
        CombofiltreSearch.getItems().addAll("Application", "Produit", "Autre", "non traite", "traite", "ViewAll");
        FiltreReclam = FXCollections.observableArrayList();

        ShowStat();
    }

    private void ShowStat() {

        // Changing random data after every 1 second.
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ////-------->>>>>Statistique Pie Chart
                int nbTotalTraite = CrudReclamation.countTotalTraite();
                int nbTotalNonTraite = CrudReclamation.countTotalnonTraite();
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                        new PieChart.Data("Traite", nbTotalTraite),
                        new PieChart.Data("Non Traite", nbTotalNonTraite)
                );
                ReclamPie.setData(pieChartData);
                //  sexChart.setTitle("Sexe");
                ReclamPie.setClockwise(true);
                ReclamPie.setLabelLineLength(70);
                ReclamPie.setLabelsVisible(true);
                ReclamPie.setStartAngle(180);

                ///Afficher En Pourcentage
                int Total = nbTotalNonTraite + nbTotalTraite;
                Double pourcentageTraite = (((double) nbTotalTraite) / Total) * 100;
                Double pourcentageNonTraite = (((double) nbTotalNonTraite) / Total) * 100;
                DecimalFormat df = new DecimalFormat("########.00");
                String enfinTraite = df.format(pourcentageTraite);
                String enfinNontraite = df.format(pourcentageNonTraite);
                txtStatReclam.setVisible(true);
                txtStatReclam.setText("Traite: " + String.valueOf(enfinTraite) + "%" + "\n" + "NonTraite: " + String.valueOf(enfinNontraite) + "%");

            }
        }));
        ///Repeat indefinitely until stop() method is called.
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();

    }

    @FXML
    private void hideDialogCodeQR(MouseEvent event) {
        if (dialogCodeQR != null) {
            dialogCodeQR.close();
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
        listeReclamm = CrudReclamation.AfficherReclamForAdmin(reclamation);
        ObservableList<reclamation> Listeeee = FXCollections.observableArrayList(listeReclamm);

        col_idRec.setCellValueFactory(new PropertyValueFactory<>("idRec"));
        col_SujetRec.setCellValueFactory(new PropertyValueFactory<>("SujetRec"));
        col_DescriptionRec.setCellValueFactory(new PropertyValueFactory<>("DescriptionRec"));
        //col_StatusRec.setCellValueFactory(new PropertyValueFactory<>("StatusRec"));
        col_StatusRec.setCellValueFactory(new StatusReclamCellValueFactory());
        col_DateRec.setCellValueFactory(new PropertyValueFactory<>("DateRec"));
        col_DateTraitement.setCellValueFactory(new PropertyValueFactory<>("DateTraitement"));
        col_imgRec.setCellValueFactory(new ImageReclamCellValueFactory());
        //col_User.setCellValueFactory(new PropertyValueFactory<>("NomPrenomUser"));
        //col_email.setCellValueFactory(new PropertyValueFactory<>("EmailUser"));
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
                        ImageView Deleteicon, Editicon, informationicon;
                        Editicon = new ImageView(new Image("/image/ChatUser.png"));
                        Editicon.setFitHeight(30);
                        Editicon.setFitWidth(30);
                        setGraphic(Editicon);

                        Deleteicon = new ImageView(new Image("/image/deleteicon.png"));
                        Deleteicon.setFitHeight(30);
                        Deleteicon.setFitWidth(30);
                        setGraphic(Deleteicon);

                        informationicon = new ImageView(new Image("/image/information.png"));
                        informationicon.setFitHeight(30);
                        informationicon.setFitWidth(30);
                        setGraphic(informationicon);

                        Editicon.setOnMouseClicked((MouseEvent event) -> {
                            System.out.println("icon edit is pressed !");

                            int idRec = Integer.valueOf((TableViewReclamation.getSelectionModel().getSelectedItem().getIdRec()));
                            //////////////////////
                            txtAreaReponse.setText(TableViewReclamation.getSelectionModel().getSelectedItem().getReponse());
                            //txtAreaReponse.setText(TableViewReclamation.getSelectionModel().getSelectedItem().getreponse());
                            showDialogChangeStauts();
                        });

                        Deleteicon.setOnMouseClicked((MouseEvent event) -> {
                            System.out.println("icon delete is pressed !");
                            int idRec = Integer.valueOf((TableViewReclamation.getSelectionModel().getSelectedItem().getIdRec()));

                            showDialogDeleteReclam();
                        });
                        informationicon.setOnMouseClicked((MouseEvent event) -> {
                            System.out.println("infomation is pressed !");
                            txtSujetRec.setText("");
                            txtProduit.setText("");
                            int idRec = Integer.valueOf((TableViewReclamation.getSelectionModel().getSelectedItem().getIdRec()));

                            tfDescriptionRec.setText(String.valueOf(TableViewReclamation.getSelectionModel().getSelectedItem().getDescriptionRec()));
                            txtSujetRec.setText(TableViewReclamation.getSelectionModel().getSelectedItem().getSujetRec());
                            txtProduit.setText(TableViewReclamation.getSelectionModel().getSelectedItem().getLibelleProduit());
                            LoadInfor(idRec);
                            showDialoginfoReclam();
                        });
                        //managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(Deleteicon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(Editicon, new Insets(2, 3, 0, 2));
                        HBox.setMargin(informationicon, new Insets(2, 4, 0, 4));
                        HBox managebtn = new HBox(Editicon, Deleteicon, informationicon);
                        setGraphic(managebtn);
                    }
                }
            };
            return cell;
        };
        col_Action.setCellFactory(cellFoctory);

        /////// CodeQR
        TableViewReclamation.setOnMouseClicked(ev -> {
            if (ev.getButton().equals(MouseButton.PRIMARY) && ev.getClickCount() == 2) {
                String Sujet = TableViewReclamation.getSelectionModel().getSelectedItem().getSujetRec();
                String description = TableViewReclamation.getSelectionModel().getSelectedItem().getDescriptionRec();
                String status = TableViewReclamation.getSelectionModel().getSelectedItem().getStatusRec();
                String dateRec = TableViewReclamation.getSelectionModel().getSelectedItem().getDateRec().toString();
                String Libel = TableViewReclamation.getSelectionModel().getSelectedItem().getLibelleProduit();
                String AllInfo = " Sujet " + Sujet + "\n description " + description + "\n status " + status + "\n dateRec " + dateRec + "";
                ////////////////////////:
                if (!AllInfo.isEmpty()) {
                    String foregroundColor = "#2E3437";
                    String backgroundColor = "#FFFFFF";
                    imgQRCodeGen.setVisible(true);
                    genQRCodeImg = encode(AllInfo, Integer.parseInt("300"), Integer.parseInt("300"), foregroundColor, backgroundColor);
                    if (genQRCodeImg != null) {
                        imgQRCodeGen.setImage(genQRCodeImg);
                    }
                }
                showDialogCodeQR();
            }
        });
    }

    private void LoadInfor(int idRec) {
        try {
            String requeteee = "SELECT imgRec,NomPrenomUser,EmailUser FROM reclamation WHERE idRec = '" + idRec + "'";
            Statement psttt = Myconnexion.getInstance().getCnx().createStatement();
            ResultSet rss = psttt.executeQuery(requeteee);
            while (rss.next()) {
                ImagePath = rss.getString(1);//bech najmt njyb mnha nom image 
                path = rss.getString(1);//bech najmt njyb mnha nom image
                Image img = new Image(new FileInputStream(ImagePath));
                DragimgRec.setImage(img);
                ///////
                tfNomRec.setText(rss.getString(2));
                tfEmailRec.setText(rss.getString(3));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (FileNotFoundException ex) {
            DragimgRec.setImage(new Image(getClass().getResource("/image/drag-drop.gif").toExternalForm()));
        }
    }

    private void showDialoginfoReclam() {
        rootReclamation.setEffect(Constants.BOX_BLUR_EFFECT);
        containerInfoReclam.setVisible(true);

        dialogInfoReclamation = new JFXDialogTool(containerInfoReclam, stckReclamation);
        dialogInfoReclamation.show();

        dialogInfoReclamation.setOnDialogClosed(ev -> {
            TableViewReclamation.setDisable(false);
            rootReclamation.setEffect(null);
            containerInfoReclam.setVisible(false);
            LoadTableReclam();

        });
    }

    private void showDialogChangeStauts() {
        rootReclamation.setEffect(Constants.BOX_BLUR_EFFECT);
        ContainerStauts.setVisible(true);

        dialogChangeStatus = new JFXDialogTool(ContainerStauts, stckReclamation);
        dialogChangeStatus.show();

        dialogChangeStatus.setOnDialogClosed(ev -> {
            TableViewReclamation.setDisable(false);
            rootReclamation.setEffect(null);
            ContainerStauts.setVisible(false);
            LoadTableReclam();

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

    @FXML
    private void SaveStatutsClicked(MouseEvent event) {
        int idReccc = 0;
        if (TableViewReclamation.getSelectionModel().getSelectedItem() != null) {//Modifier mel tableau
            idReccc = Integer.valueOf((TableViewReclamation.getSelectionModel().getSelectedItem().getIdRec()));
        }
        String Reponse = txtAreaReponse.getText();
        if (Reponse.trim().isEmpty()) {
            //  tfDescriptionRec.requestFocus();
            Animations.shake(txtAreaReponse);
            return;
        }
        reclamation rec = new reclamation(idReccc, "traite", Reponse);

        Boolean result = CrudReclamation.TraiteRclam(rec);
        if (result) {
            hideDialogStatusReclam();
            AlertsBuilder.create(AlertType.SUCCES, stckReclamation, rootReclamation, TableViewReclamation, Constants.MESSAGE_UPDATED);
        } else {
            AlertsBuilder.create(AlertType.ERROR, stckReclamation, rootReclamation, TableViewReclamation, Constants.MESSAGE_ERROR_CONNECTION_MYSQL);
        }
        LoadTableReclam();

        if (!Reponse.isEmpty()) {
            String emailUser = tfEmailRec.getText();
            long millis = System.currentTimeMillis();
            java.sql.Date DateLyoum = new java.sql.Date(millis);
            mail.sendMail("Salut Votre réclamation a été traité Aujourd'hui " + DateLyoum + "", "dabyain@gmail.com");
        }
    }

    @FXML
    private void hideDialogStatusReclam() {
        if (dialogChangeStatus != null) {
            dialogChangeStatus.close();
            ImagePath = "";
        }
        LoadTableReclam();
    }

    @FXML
    private void SearchParFiltre(MouseEvent event) {

        if (CombofiltreSearch.getSelectionModel().getSelectedItem() == null) {
            AlertsBuilder.create(AlertType.ERROR, stckReclamation, rootReclamation, TableViewReclamation, "You Need to Select Something to filtre it !");
        }
        if (CombofiltreSearch.getSelectionModel().getSelectedItem() != null) {
            String WordTyped = CombofiltreSearch.getSelectionModel().getSelectedItem();
            if (WordTyped.equals("ViewAll")) {
                TableViewReclamation.setItems(ListReclam);
                LoadTableReclam();
            } else {
                FiltreReclam.clear();
                for (reclamation p : ListReclam) {
                    if ((p.getStatusRec().toLowerCase().equals(WordTyped.toLowerCase()))
                            || (p.getSujetRec().toLowerCase().equals(WordTyped.toLowerCase()))) {
                        FiltreReclam.add(p);
                    }
                }
                TableViewReclamation.setItems(FiltreReclam);
            }
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

    @FXML
    private void hideDialogDeleteReclam() {
        if (dialogDeleteReclamation != null) {
            dialogDeleteReclamation.close();
            ImagePath = "";
        }
        LoadTableReclam();
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
    private void closeDialogAddRec(MouseEvent event) {
        if (dialogInfoReclamation != null) {
            dialogInfoReclamation.close();
            ImagePath = "";
        }
        txtSujetRec.setText("");
        txtProduit.setText("");
        LoadTableReclam();
    }

    @FXML
    private void closeDialogInfoRec(MouseEvent event) {
        if (dialogInfoReclamation != null) {
            dialogInfoReclamation.close();
            ImagePath = "";
        }
        txtSujetRec.setText("");
        txtProduit.setText("");
        LoadTableReclam();
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

    public static void closeStage() {
        if (stage != null) {
            stage.hide();
        }
    }

    public static Image encode(String data, int width, int height, String foregroundColor, String backgroundColor) {
        try {
            BitMatrix byteMatrix = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, width, height);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();
            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            graphics.setColor(Color.decode(backgroundColor));
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.decode(foregroundColor));
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            return SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (WriterException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void showDialogCodeQR() {

        rootReclamation.setEffect(Constants.BOX_BLUR_EFFECT);
        ContainerCodeQR.setVisible(true);

        dialogCodeQR = new JFXDialogTool(ContainerCodeQR, stckReclamation);
        dialogCodeQR.show();

        dialogCodeQR.setOnDialogClosed(ev -> {
            closeStage();
            TableViewReclamation.setDisable(false);
            rootReclamation.setEffect(null);
            ContainerCodeQR.setVisible(false);
            LoadTableReclam();
        });
    }

}
