/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import entity.reclamation;
import utils.Myconnexion;
import interfaces.Ireclamation;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 *
 */
public class CrudReclamation implements Ireclamation<reclamation> {

    private ResultSet rs;
    private String output;

    @Override
    public boolean AjouterReclam(reclamation j) {

        try {
            String requete = "INSERT INTO reclamation(idUser,SujetRec,DescriptionRec,StatusRec,DateRec,DateTraitement,NomPrenomUser,EmailUser,imgRec,libelleProduit,reponse)" + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = Myconnexion.getInstance().getCnx().prepareStatement(requete);

            pst.setInt(1, j.getIdUser());
            pst.setString(2, j.getSujetRec());
            //////////////////////////////////////////////////////////////////////
            output = BadWordFilter.getCensoredText(j.getDescriptionRec());
            //pst.setString(3, j.getDescriptionRec());
            pst.setString(3, output);
            //////////////////////////////////////////////////////////////////////
            pst.setString(4, "non traite");
            //////
            long millis = System.currentTimeMillis();
            java.sql.Date DateRec = new java.sql.Date(millis);
            //////
            pst.setDate(5, DateRec);
            pst.setDate(6, j.getDateTraitement());
            pst.setString(7, j.getNomPrenomUser());
            pst.setString(8, j.getEmailUser());
            pst.setString(9, j.getImgRec());
            pst.setString(10, j.getLibelleProduit());
            
            
            
            
            /// 
            pst.setString(11, "");
            pst.executeUpdate();

            System.out.println("Reclam ajouté!");
            return true;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return false;
    }

    @Override
    public boolean ModifierReclam(reclamation j) {
        try {

            String requete = "UPDATE reclamation SET SujetRec=?,DescriptionRec=?,libelleProduit=?,imgRec=? WHERE idRec=?";
            PreparedStatement pst = Myconnexion.getInstance().getCnx().prepareStatement(requete);

            pst.setString(1, j.getSujetRec());
            pst.setString(2, j.getDescriptionRec());
            pst.setString(3, j.getLibelleProduit());
            pst.setString(4, j.getImgRec());
            pst.setInt(5, j.getIdRec());
            pst.executeUpdate();

            System.out.println("reclamation été modifiée");

            return true;

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return false;
    }

    @Override
    public boolean SupprimerReclam(int idReclam) {
        try {
            String requete = "DELETE FROM reclamation where idRec=" + String.valueOf(idReclam) + "";
            PreparedStatement pst = Myconnexion.getInstance().getCnx().prepareStatement(requete);
            pst.execute(requete);
            System.out.println("reclamation supprimée");

            return true;

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return false;
    }

    @Override
    public List<reclamation> AfficherReclamForAdmin(reclamation t) {
        List<reclamation> ReclamList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM reclamation";
            Statement pst = Myconnexion.getInstance().getCnx().createStatement();
            ResultSet rs = pst.executeQuery(requete);
            while (rs.next()) {
                reclamation r = new reclamation();

                ImageView img = new ImageView();
                Image image;
                try {
                    if (rs.getString("imgRec") == null) {
                    } else if (rs.getString("imgRec") != null) {
                        image = new Image(new FileInputStream((rs.getString("imgRec"))));
                        img.setImage(image);
                        img.setPreserveRatio(true);
                        img.setFitWidth(50);
                        img.setFitHeight(50);

                    }
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getMessage());
//                    img.setPreserveRatio(true);
//                    img.setFitWidth(50);
//                    img.setFitHeight(50);
                    //img.setImage(new Image(getClass().getResource("/image/nophoto.png").toString()));
                }

                r.setIdRec(rs.getInt("idRec"));
                r.setIdUser(rs.getInt("idUser"));
                r.setSujetRec(rs.getString("SujetRec"));
                r.setDescriptionRec(rs.getString("DescriptionRec"));
                r.setStatusRec(rs.getString("StatusRec"));
                r.setDateRec(rs.getDate("DateRec"));
                r.setDateTraitement(rs.getDate("DateTraitement"));
                r.setImgReclamation(img);
                r.setEmailUser(rs.getString("EmailUser"));
                r.setLibelleProduit(rs.getString("libelleProduit"));
                r.setNomPrenomUser(rs.getString("NomPrenomUser"));
                r.setReponse(rs.getString("Reponse"));
                ReclamList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return ReclamList;
    }

    
        public List<reclamation> AfficherReclamForUser(reclamation t) {
        List<reclamation> ReclamList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM reclamation WHERE idUser = '" + t.getIdUser()+ "'";
            Statement pst = Myconnexion.getInstance().getCnx().createStatement();
            ResultSet rs = pst.executeQuery(requete);
            while (rs.next()) {
                reclamation r = new reclamation();

                ImageView img = new ImageView();
                Image image;
                try {
                    if (rs.getString("imgRec") == null) {
                    } else if (rs.getString("imgRec") != null) {
                        image = new Image(new FileInputStream((rs.getString("imgRec"))));
                        img.setImage(image);
                        img.setPreserveRatio(true);
                        img.setFitWidth(50);
                        img.setFitHeight(50);

                    }
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getMessage());
//                    img.setPreserveRatio(true);
//                    img.setFitWidth(50);
//                    img.setFitHeight(50);
                    //img.setImage(new Image(getClass().getResource("/image/nophoto.png").toString()));
                }

                r.setIdRec(rs.getInt("idRec"));
                r.setIdUser(rs.getInt("idUser"));
                r.setSujetRec(rs.getString("SujetRec"));
                r.setDescriptionRec(rs.getString("DescriptionRec"));
                r.setStatusRec(rs.getString("StatusRec"));
                r.setDateRec(rs.getDate("DateRec"));
                r.setDateTraitement(rs.getDate("DateTraitement"));
                r.setImgReclamation(img);
                r.setEmailUser(rs.getString("EmailUser"));
                r.setLibelleProduit(rs.getString("libelleProduit"));
                r.setNomPrenomUser(rs.getString("NomPrenomUser"));
                r.setReponse(rs.getString("Reponse"));
                ReclamList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return ReclamList;
    }
    
    public int contraintModifier24h(int idRec) {
        int heure;
        int difference = 0;
        long millis = System.currentTimeMillis();
        java.sql.Date Datelyoum = new java.sql.Date(millis);

        int nherleyoum = Integer.valueOf(String.format("%1$td", Datelyoum));
        // System.out.println("day lyou me system "+nherleyoum);

        try {
            String requete = "SELECT DateRec FROM reclamation WHERE idRec = " + String.valueOf(idRec) + "";
            Statement pst = Myconnexion.getInstance().getCnx().createStatement();
            ResultSet rs = pst.executeQuery(requete);
            while (rs.next()) {
                //heure = rs.getDate(1);
                heure = Integer.valueOf(String.format("%1$td", rs.getDate(1)));
                //System.out.println("day mel base de données "+heure);
                difference = nherleyoum - heure;
                System.out.println("Id " + idRec + " Day = " + difference + " Jour");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        //heure=nherleyoum-heure;
        return difference;
    }

    public int countTotalReclamation(int idUser) {
        String req = "SELECT COUNT(*) as cu FROM reclamation where idUser=" + String.valueOf(idUser) + "  ";
        ResultSet rs = null;
        try {
            Statement ste = Myconnexion.getInstance().getCnx().createStatement();
            rs = ste.executeQuery(req);
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        int cu = 0;
        try {
            while (rs.next()) {
                cu = rs.getInt("cu");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return cu;
    }

    public boolean TraiteRclam(reclamation t) {
        try {
            String requete = "UPDATE reclamation SET StatusRec=?,DateTraitement=?,Reponse=? WHERE idRec=? ";
            PreparedStatement pst = Myconnexion.getInstance().getCnx().prepareStatement(requete);

            pst.setString(1, t.getStatusRec());
            if (t.getStatusRec() == "traite") {
                long millis = System.currentTimeMillis();
                java.sql.Date Datetaw = new java.sql.Date(millis);
                pst.setDate(2, Datetaw);
            } else {
                pst.setNull(2, Types.DATE);
            }
            pst.setString(3, t.getReponse());

            pst.setInt(4, t.getIdRec());
            pst.executeUpdate();

            System.out.println("Reclam Traité !");
            return true;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return false;
    }
    
    //Statistique
    
     public int countTotalTraite() {
       String req = "SELECT COUNT(*) as cu FROM reclamation u WHERE StatusRec like '%traite%'";
        ResultSet rs = null;
        try {
            Statement ste = Myconnexion.getInstance().getCnx().createStatement();
            rs = ste.executeQuery(req);
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        int cu = 0;
        try {
            while (rs.next()) {
                cu = rs.getInt("cu");
            }
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        return cu;
    }

    public int countTotalnonTraite() {
        String req = "SELECT COUNT(*) as cu FROM reclamation u WHERE StatusRec like '%non traite%'";
        ResultSet rs = null;
        try {
            Statement ste = Myconnexion.getInstance().getCnx().createStatement();
            rs = ste.executeQuery(req);
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        int cu = 0;
        try {
            while (rs.next()) {
                cu = rs.getInt("cu");
            }
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        return cu;
    }

}
