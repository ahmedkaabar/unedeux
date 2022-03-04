


    
package gestionCommandeLivraison.services;

import gestionCommandeLivraison.entities.Commande;
import gestionCommandeLivraison.entities.Iservice.IService;
import gestionCommandeLivraison.entities.Livraison;
import gestionCommandeLivraison.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import une_deux.utils.DataSource;


/**
 *
 * @author Fedi
 */

public class LivraisonService implements IService<Livraison> {
    private Connection connection;

    public LivraisonService() {
        this.connection = DataSource.getInstance().getCnx();
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    @Override
    public ArrayList<Livraison> getall() {
        String select = "SELECT * FROM Livraisons";
        try (PreparedStatement st = connection.prepareStatement(select);
                ResultSet rs = st.executeQuery()) {
            ArrayList<Livraison> LivraisonList = new ArrayList<Livraison>();
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
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    @Override
    public Livraison get(int id) throws SQLException {
        String select = "SELECT * FROM Livraisons WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(select)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            Livraison l = new Livraison();
            while (rs.next()) {
                UserService su = new UserService();
                CommandeService sc = new CommandeService();
                Commande commande = sc.get(rs.getInt("commandeId"));
                String adresse = rs.getString("adresse");
                String etat = rs.getString("etat");
                User vendeur_id = su.get(rs.getInt("vendeurId"));
                l = new Livraison(id, commande, adresse, etat, vendeur_id);
            }
            return l;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    @Override
    public Livraison insert(Livraison l) {
        String insert = "INSERT INTO Livraisons (commandeId, adresse, etat, vendeurId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, l.getCommande().getIdCommande());
            st.setString(2, l.getAdresse());
            st.setString(3, l.getEtat());
            st.setInt(4, l.getVendeur().getId());

            int rowsInserted = st.executeUpdate();
            ResultSet generatedKeys = st.getGeneratedKeys();

            if (generatedKeys.next()) {
                l.setId(generatedKeys.getInt(1));
            }

            if (rowsInserted > 0) {
                System.out.println("A new Livraison was inserted successfully!");
                return l;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    @Override
    public void update(int id, Livraison l) {
        String update = "UPDATE Livraisons SET commandeId=?, adresse=?, etat=?, vendeurId=? WHERE id=?";
        try (PreparedStatement st = connection.prepareStatement(update)) {
            st.setInt(1, l.getCommande().getIdCommande());
            st.setString(2, l.getAdresse());
            st.setString(3, l.getEtat());
            st.setInt(4, l.getVendeur().getId());
            st.setInt(5, id);

            int rowsUpdated = st.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing Livraison was updated successfully!");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public void delete(int id) {
        String delete = "DELETE FROM Livraisons WHERE id=?";
        try (PreparedStatement st = connection.prepareStatement(delete)) {

            st.setInt(1, id);

            int rowsDeleted = st.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A Livraison was deleted successfully!");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

   
}
