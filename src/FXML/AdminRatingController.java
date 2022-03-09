/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXML;

import Main.MainApp;
import Service.CrudRating;
import animations.Animations;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTextField;
import entity.Rate;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.controlsfx.control.Rating;
import utils.Myconnexion;

/**
 * FXML Controller class
 *
 * @author YedesHamda
 */
public class AdminRatingController implements Initializable {

    @FXML
    private StackPane stckRating;
    @FXML
    private AnchorPane rootRating;
    @FXML
    private Pane ContainerUsersAdmin;
    @FXML
    private Circle imgOnline;
    @FXML
    private Label dateTime;

    private final JFXMasonryPane mansoryPane = new JFXMasonryPane();
    private final JFXMasonryPane mansoryPaneStatRate = new JFXMasonryPane();

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ScrollPane scrollPaneRate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initMansoryStat();
        loadRating();
        initMansoryCard();
        statRating();
    }

    private void initMansoryStat() {
        mansoryPaneStatRate.setPadding(new Insets(15, 15, 15, 15));
        mansoryPaneStatRate.setVSpacing(5);
        mansoryPaneStatRate.setHSpacing(5);
        scrollPaneRate.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPaneRate.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPaneRate.setFitToWidth(true);
        scrollPaneRate.setContent(mansoryPaneStatRate);
    }

    private void initMansoryCard() {
        mansoryPane.setPadding(new Insets(15, 15, 15, 15));
        mansoryPane.setVSpacing(5);
        mansoryPane.setHSpacing(5);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(mansoryPane);

    }

    private void statRating() { // bech na3meel  sous lensemble sur lensemble 
        mansoryPaneStatRate.getChildren().clear();// ye3ni css 3endeha 14 star ne9semhom 3ela total star (css+est)
        String Libelle = null;
        String enfin = null;
        Integer SumRateParEquipe = 0;
        Integer SumRateEquipetLkol = 0;
        Double resultRate;
        Integer Sum = 0;

        try {

            String requeteee = "SELECT DISTINCT Libelle FROM rate";
            Statement psttt = Myconnexion.getInstance().getCnx().createStatement();
            ResultSet rss = psttt.executeQuery(requeteee);
            while (rss.next()) {
                Libelle = rss.getString(1);//bech najmt njyb mnha nom de 
                System.out.println("NomEquipee ==> " + Libelle);

                String requeteee2 = "SELECT SUM(Rate) FROM rate where Libelle = '" + Libelle + "' ";
                Statement psttt2 = Myconnexion.getInstance().getCnx().createStatement();
                ResultSet rss2 = psttt2.executeQuery(requeteee2);
                //
                while (rss2.next()) {
                    Sum = rss2.getInt(1);
                }
                String requeteee3 = "SELECT Count(idRate) FROM rate where Libelle = '" + Libelle + "' ";
                Statement psttt3 = Myconnexion.getInstance().getCnx().createStatement();
                ResultSet rss3 = psttt3.executeQuery(requeteee3);
                while (rss3.next()) {
                    SumRateParEquipe = rss3.getInt(1);//bech najmt njyb mnha nom de 
                    resultRate = (((double) Sum) /( SumRateParEquipe*5)) * 100;
                    System.out.println("=========>>>>SumRateParEquipe " + SumRateParEquipe);
                    System.out.println("=========>>>>Sum " + Sum);

                    System.out.println("ResulFinal  ==> " + resultRate);

                    DecimalFormat df = new DecimalFormat("########.00");
                    enfin = df.format(resultRate);
                    System.out.println("Heeddha L7achttyyy byyh ===> " + enfin);
                }

                //lehna chnekhdem lcode te3 affichage 
                System.out.println("hahahahahha  " + Libelle);
                VBox root = new VBox();

                root.setStyle("-fx-background-color:  linear-gradient(to right top, #56ab2f , #a8e063);    -fx-background-radius: 15px;-fx-effect:dropshadow(three-pass-box, gray, 10, 0, 0, 0);");

                root.setPadding(new Insets(12, 17, 17, 17));

                root.setSpacing(13);
                // root.getChildren().add(ook);
                root.getChildren().addAll(new Label("Nom: " + Libelle), new Label("Rating: " + enfin + " %"));
                root.setAlignment(Pos.CENTER);
                mansoryPaneStatRate.getChildren().add(root);
                Animations.fadeInUp(mansoryPaneStatRate);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void loadRating() {
        mansoryPane.getChildren().clear();
        CrudRating pcd = new CrudRating();
        Rate ratee = new Rate();
        List<Rate> listeRate = new ArrayList<>();
        listeRate = pcd.displayRateForAdmin(ratee);

        if (!listeRate.isEmpty()) {
            int h = 0;
            int nbStarts = 0;
            for (int i = 0; i < listeRate.size(); i++) {
                VBox root = new VBox();
                Rating ook = new Rating();

                root.setStyle("-fx-background-color: #fff; -fx-background-radius: 15px;-fx-effect:dropshadow(three-pass-box, gray, 10, 0, 0, 0);");
                nbStarts = listeRate.get(i).getRate();
                Date DateRate = listeRate.get(i).getDateRate();
                String nom = listeRate.get(i).getLibelle();
                h++;
                ook.setRating(nbStarts);
                ook.setDisable(true);
                // System.out.println(listeRate);
                // System.out.println(h);

                root.setPadding(new Insets(12, 17, 17, 17));

                root.setSpacing(13);
                // root.getChildren().add(ook);
                root.getChildren().addAll(new Label("Nom : " + nom), ook, new Label("DateRate : " + DateRate));
                root.setAlignment(Pos.CENTER);
                mansoryPane.getChildren().add(root);
                Animations.fadeInUp(mansoryPane);
            }

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

}
