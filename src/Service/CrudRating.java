/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import entity.Rate;
import interfaces.IRate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.Myconnexion;
import org.controlsfx.control.Rating;

/**
 *
 * @author YedesHamda
 */
public class CrudRating implements IRate<Rate> {

    @Override
    public void ajouterRate(Rate t) {

        try {

            String requete = "INSERT INTO rate(Libelle,DateRate,Rate,idUser)"
                    + "VALUES (?,?,?,?)";
            PreparedStatement pst = Myconnexion.getInstance().getCnx()
                    .prepareStatement(requete);
            ////////////////////////////
            pst.setString(1, t.getLibelle());

            long millis = System.currentTimeMillis();
            java.sql.Date DateRate = new java.sql.Date(millis);
            pst.setDate(2, DateRate);

            pst.setInt(3, t.getRate());
            
            pst.setInt(4, t.getIdUser());

            pst.executeUpdate();

            System.out.println("Rate ajouté!");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
    }

    @Override
    public List<Rate> displayRateForAdmin(Rate t) {
        List<Rate> RateList = new ArrayList<>();
        try {
            String requete = "SELECT idRate,Libelle,DateRate,Rate FROM rate ORDER BY DateRate DESC";
            Statement pst = Myconnexion.getInstance().getCnx()
                    .createStatement();
            ResultSet rs = pst.executeQuery(requete);
            while (rs.next()) {
                Rate r = new Rate();

                Rating rr = new Rating();
                rr.setRating(rs.getInt(4));
                rr.setDisable(true);
                
                r.setIdRate(rs.getInt(1));
                r.setRateView(rr); //lel tableau

                //System.out.println(rs.getInt(1));
                r.setLibelle(rs.getString(2));
                r.setDateRate(rs.getDate(3));

                r.setRate(rs.getInt(4));//lel card
                RateList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return RateList;
    }
    
        public List<Rate> displayRateForUser(Rate t) {
        List<Rate> RateList = new ArrayList<>();
        try {
            String requete = "SELECT idRate,Libelle,DateRate,Rate FROM rate WHERE idUser = '" + t.getIdUser()+ "'";
            Statement pst = Myconnexion.getInstance().getCnx()
                    .createStatement();
            ResultSet rs = pst.executeQuery(requete);
            while (rs.next()) {
                Rate r = new Rate();

                Rating rr = new Rating();
                rr.setRating(rs.getInt(4));
                rr.setDisable(true);
                
                r.setIdRate(rs.getInt(1));
                r.setRateView(rr); //lel tableau

                //System.out.println(rs.getInt(1));
                r.setLibelle(rs.getString(2));
                r.setDateRate(rs.getDate(3));

                r.setRate(rs.getInt(4));//lel card
             //   r.setIdUser(rs.getInt(5));
                RateList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return RateList;
    }

    public boolean ModifierRate(Rate j) {
        try {

            String requete = "UPDATE rate SET Libelle=?,Rate=? WHERE idRate=?";
            PreparedStatement pst = Myconnexion.getInstance().getCnx().prepareStatement(requete);

            pst.setString(1, j.getLibelle());
            pst.setInt(2, j.getRate());
            pst.setInt(3, j.getIdRate());
            pst.executeUpdate();

            System.out.println("Rate été modifiée");

            return true;

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return false;
    }

    public boolean SupprimerRating(int idRating) {
        try {
            String requete = "DELETE FROM rate where idRate=" + String.valueOf(idRating) + "";
            PreparedStatement pst = Myconnexion.getInstance().getCnx().prepareStatement(requete);
            pst.execute(requete);
            System.out.println("rate supprimée");

            return true;

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return false;
    }

}
