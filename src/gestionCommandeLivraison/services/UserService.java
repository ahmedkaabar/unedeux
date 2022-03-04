/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionCommandeLivraison.services;

import gestionCommandeLivraison.entities.User;
import gestionCommandeLivraison.entities.Iservice.IService;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Fedi
 */

import java.sql.*;
import java.util.ArrayList;
import javax.management.relation.Role;
import une_deux.utils.DataSource;

public class UserService implements IService<User> {

    private Connection connection;

    public UserService() {
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
    public ArrayList<User> getall(){
        String select = "SELECT * FROM users";
        try (PreparedStatement st = connection.prepareStatement(select);
                ResultSet rs = st.executeQuery()) {
            ArrayList<User> userList = new ArrayList<User>();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                Date date_naiss = rs.getDate("date_naiss");
                String email = rs.getString("email");
                String mdp = rs.getString("mdp");
                Integer num_tel = rs.getInt("num_tel");
                Integer role_id = rs.getInt("role_id");
                Date createdAt = rs.getDate("createdAt");
                User u = new User(id, nom, prenom, date_naiss, email, mdp, num_tel,createdAt);
                userList.add(u);
            }
            return userList;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    @Override
    public User get(int id) throws SQLException {
        String select = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(select)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            User u = new User();
            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                Date date_naiss = rs.getDate("date_naiss");
                String email = rs.getString("email");
                String mdp = rs.getString("mdp");
                Integer num_tel = rs.getInt("num_tel");
                Date createdAt = rs.getDate("createdAt");
                
                u = new User(id, nom, prenom, date_naiss, email, mdp, num_tel,createdAt);
            }
            return u;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    @Override
    public User insert(User u) {
        String insert = "INSERT INTO Users (nom, prenom, date_naiss, email, mdp, num_tel, role_id, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, u.getNom());
            st.setString(2, u.getPrenom());
            st.setDate(3, u.getDate_naiss());
            st.setString(4, u.getEmail());
            st.setString(5, u.getMdp());
            st.setInt(6, u.getNum_tel());
            
            st.setDate(8, new Date(System.currentTimeMillis()));

            int rowsInserted = st.executeUpdate();
            ResultSet generatedKeys = st.getGeneratedKeys();

            if (generatedKeys.next()) {
                u.setId(generatedKeys.getInt(1));
            }

            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
                return u;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    @Override
    public void update(int id, User u) {
        String update = "UPDATE Users SET nom=?, prenom=?, date_naiss=?, email=?, mdp=?, num_tel=?, role_id=? WHERE id=?";
        try (PreparedStatement st = connection.prepareStatement(update)) {
            st.setString(1, u.getNom());
            st.setString(2, u.getPrenom());
            st.setDate(3, u.getDate_naiss());
            st.setString(4, u.getEmail());
            st.setString(5, u.getMdp());
            st.setInt(7, u.getNum_tel());
            
            st.setInt(9, id);

            int rowsUpdated = st.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public void delete(int id) {
        String delete = "DELETE FROM users WHERE id=?";
        try (PreparedStatement st = connection.prepareStatement(delete)) {

            st.setInt(1, id);

            int rowsDeleted = st.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A user was deleted successfully!");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Boolean login(String email, String mdp) {
        String select = "SELECT * FROM users WHERE email=? and mdp=?";
        try (PreparedStatement st = connection.prepareStatement(select)) {

            st.setString(1, email);
            st.setString(2, mdp);
            ResultSet rs = st.executeQuery();
            if (rs.next())
                return true;
            return false;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    public Boolean getByEmail(String email) {
        String select = "SELECT * FROM users WHERE email=?";
        try (PreparedStatement st = connection.prepareStatement(select)) {

            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    public ArrayList<User> getUsersThatHaveRole(Role role) {
        String select = "SELECT * FROM users where role_id = ?";
        try (PreparedStatement st = connection.prepareStatement(select)) {
            
            ResultSet rs = st.executeQuery();
            ArrayList<User> userList = new ArrayList<User>();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                Date date_naiss = rs.getDate("date_naiss");
                String email = rs.getString("email");
                String mdp = rs.getString("mdp");
                Integer num_tel = rs.getInt("num_tel");
                Date createdAt = rs.getDate("createdAt");
                User u = new User(id, nom, prenom, date_naiss, email, mdp, num_tel, createdAt);
                userList.add(u);
            }
            return userList;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

   

}
