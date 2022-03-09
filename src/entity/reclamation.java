/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;
import javafx.scene.image.ImageView;

/**
 *
 * @author YedesHamda
 */
public class reclamation {

    private int idRec, idUser;
    private String SujetRec, DescriptionRec, StatusRec;
    private String NomPrenomUser, EmailUser, imgRec;
    private Date DateRec, DateTraitement;
    private String libelleProduit;
    ImageView imgReclamation;
    private String Reponse;

    public reclamation() {
    }

    public reclamation(int idRec, String StatusRec,String Reponse) {
        this.idRec = idRec;
        this.StatusRec = StatusRec;
        this.Reponse=Reponse;
    }

    public reclamation(int idRec, int idUser, String SujetRec, String DescriptionRec, String StatusRec, String NomPrenomUser, String EmailUser, String imgRec, Date DateRec, Date DateTraitement, String libelleProduit) {
        this.idRec = idRec;
        this.idUser = idUser;
        this.SujetRec = SujetRec;
        this.DescriptionRec = DescriptionRec;
        this.StatusRec = StatusRec;
        this.NomPrenomUser = NomPrenomUser;
        this.EmailUser = EmailUser;
        this.imgRec = imgRec;
        this.DateRec = DateRec;
        this.DateTraitement = DateTraitement;
        this.libelleProduit = libelleProduit;
    }
    
        public reclamation(int idRec, int idUser, String SujetRec, String DescriptionRec, String StatusRec, String NomPrenomUser, String EmailUser, ImageView imgReclamation, Date DateRec, Date DateTraitement, String libelleProduit) {
        this.idRec = idRec;
        this.idUser = idUser;
        this.SujetRec = SujetRec;
        this.DescriptionRec = DescriptionRec;
        this.StatusRec = StatusRec;
        this.NomPrenomUser = NomPrenomUser;
        this.EmailUser = EmailUser;
        this.imgReclamation = imgReclamation;
        this.DateRec = DateRec;
        this.DateTraitement = DateTraitement;
        this.libelleProduit = libelleProduit;
    }

    public int getIdRec() {
        return idRec;
    }

    public void setIdRec(int idRec) {
        this.idRec = idRec;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getSujetRec() {
        return SujetRec;
    }

    public void setSujetRec(String SujetRec) {
        this.SujetRec = SujetRec;
    }

    public String getDescriptionRec() {
        return DescriptionRec;
    }

    public void setDescriptionRec(String DescriptionRec) {
        this.DescriptionRec = DescriptionRec;
    }

    public String getStatusRec() {
        return StatusRec;
    }

    public void setStatusRec(String StatusRec) {
        this.StatusRec = StatusRec;
    }

    public String getNomPrenomUser() {
        return NomPrenomUser;
    }

    public void setNomPrenomUser(String NomPrenomUser) {
        this.NomPrenomUser = NomPrenomUser;
    }

    public String getEmailUser() {
        return EmailUser;
    }

    public void setEmailUser(String EmailUser) {
        this.EmailUser = EmailUser;
    }

    public String getImgRec() {
        return imgRec;
    }

    public void setImgRec(String imgRec) {
        this.imgRec = imgRec;
    }

    public Date getDateRec() {
        return DateRec;
    }

    public void setDateRec(Date DateRec) {
        this.DateRec = DateRec;
    }

    public Date getDateTraitement() {
        return DateTraitement;
    }

    public void setDateTraitement(Date DateTraitement) {
        this.DateTraitement = DateTraitement;
    }

    public String getLibelleProduit() {
        return libelleProduit;
    }

    public void setLibelleProduit(String libelleProduit) {
        this.libelleProduit = libelleProduit;
    }

    public ImageView getImgReclamation() {
        return imgReclamation;
    }

    public void setImgReclamation(ImageView imgReclamation) {
        this.imgReclamation = imgReclamation;
    }

    public String getReponse() {
        return Reponse;
    }

    public void setReponse(String Reponse) {
        this.Reponse = Reponse;
    }  

    @Override
    public String toString() {
        return "reclamation{" + "idRec=" + idRec + ", idUser=" + idUser + ", SujetRec=" + SujetRec + ", DescriptionRec=" + DescriptionRec + ", StatusRec=" + StatusRec + ", NomPrenomUser=" + NomPrenomUser + ", EmailUser=" + EmailUser + ", imgRec=" + imgRec + ", DateRec=" + DateRec + ", DateTraitement=" + DateTraitement + ", libelleProduit=" + libelleProduit + ", imgReclamation=" + imgReclamation + '}';
    }

    

}
