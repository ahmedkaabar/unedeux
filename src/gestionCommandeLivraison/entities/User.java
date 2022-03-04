/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionCommandeLivraison.entities;



import java.sql.Date;
import java.util.Objects;
import javax.management.relation.Role;

/**
 * @author Fedi
 */
public class User {

    private Integer id;
    private String nom;
    private String prenom;
    private Date date_naiss;
    private String email;
    private String mdp;
    private Integer num_tel;
    private Date createdAt;

    public User() {
    }

    public User(Integer id, String nom, String prenom, Date date_naiss, String email, String mdp, Integer num_tel,
                 Date createdAt) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naiss = date_naiss;
        this.email = email;
        this.mdp = mdp;
        this.num_tel = num_tel;
        this.createdAt = createdAt;
    }

    public User(Integer id, String nom, String prenom, Date date_naiss, String email, String mdp, Integer num_tel) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naiss = date_naiss;
        this.email = email;
        this.mdp = mdp;
        this.num_tel = num_tel;
        this.createdAt = new Date(System.currentTimeMillis());
    }

    public User(String nom, String prenom, Date date_naiss, String email, String mdp, Integer num_tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.date_naiss = date_naiss;
        this.email = email;
        this.mdp = mdp;
        this.num_tel = num_tel;
        this.createdAt = new Date(System.currentTimeMillis());
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDate_naiss() {
        return this.date_naiss;
    }

    public void setDate_naiss(Date date_naiss) {
        this.date_naiss = date_naiss;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return this.mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Integer getNum_tel() {
        return this.num_tel;
    }

    public void setNum_tel(Integer num_tel) {
        this.num_tel = num_tel;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(nom, user.nom) && Objects.equals(prenom, user.prenom)
                && Objects.equals(date_naiss, user.date_naiss) && Objects.equals(email, user.email)
                && Objects.equals(mdp, user.mdp) && Objects.equals(num_tel, user.num_tel)
                && Objects.equals(createdAt, user.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, date_naiss, email, mdp, num_tel, createdAt);
    }

    @Override
    public String toString() {
        return "{"
                + " id='" + getId() + "'"
                + ", nom='" + getNom() + "'"
                + ", prenom='" + getPrenom() + "'"
                + ", date_naiss='" + getDate_naiss() + "'"
                + ", email='" + getEmail() + "'"
                + ", mdp='" + getMdp() + "'"
                + ", num_tel='" + getNum_tel() + "'"
                + ", createdAt='" + getCreatedAt() + "'"
                + "}";
    }
}