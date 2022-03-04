/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;
import org.controlsfx.control.Rating;

/**
 *
 * @author YedesHamda
 */
public class Rate {
    int idRate;
    String Libelle;
    Date DateRate;
    int Rate;
    Rating RateView;

    public Rate() {
    }

    public Rate(int idRate, String Libelle, Date DateRate, int Rate) {
        this.idRate = idRate;
        this.Libelle = Libelle;
        this.DateRate = DateRate;
        this.Rate = Rate;
    }

    public int getIdRate() {
        return idRate;
    }

    public void setIdRate(int idRate) {
        this.idRate = idRate;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String Libelle) {
        this.Libelle = Libelle;
    }

    public Date getDateRate() {
        return DateRate;
    }

    public void setDateRate(Date DateRate) {
        this.DateRate = DateRate;
    }

    public int getRate() {
        return Rate;
    }

    public void setRate(int Rate) {
        this.Rate = Rate;
    }

    public Rating getRateView() {
        return RateView;
    }

    public void setRateView(Rating RateView) {
        this.RateView = RateView;
    }

    @Override
    public String toString() {
        return "Rate{" + "idRate=" + idRate + ", Libelle=" + Libelle + ", DateRate=" + DateRate + ", Rate=" + Rate + '}';
    }
    
    
}
