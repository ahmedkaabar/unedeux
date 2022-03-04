/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionCommandeLivraison.entities;

/**
 *
 * @author Fedi
 */

import java.util.Objects;

public class Livraison {

    private int id;
    private Commande commande;
    private String adresse;
    private String etat;
    private User vendeur;

    public Livraison() {
    }

    public Livraison(int id, Commande commande, String adresse, String etat, User vendeur) {
        this.id = id;
        this.commande = commande;
        this.adresse = adresse;
        this.etat = etat;
        this.vendeur = vendeur;
    }

    public Livraison(Commande commande, String adresse, String etat, User vendeur) {
        this.commande = commande;
        this.adresse = adresse;
        this.etat = etat;
        this.vendeur = vendeur;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Commande getCommande() {
        return this.commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEtat() {
        return this.etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public User getVendeur() {
        return this.vendeur;
    }

    public void setVendeur(User vendeur) {
        this.vendeur = vendeur;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Livraison)) {
            return false;
        }
        Livraison livraison = (Livraison) o;
        return id == livraison.id && Objects.equals(commande, livraison.commande)
                && Objects.equals(adresse, livraison.adresse) && Objects.equals(etat, livraison.etat)
                && Objects.equals(vendeur, livraison.vendeur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commande, adresse, etat, vendeur);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", commande='" + getCommande() + "'" +
                ", adresse='" + getAdresse() + "'" +
                ", etat='" + getEtat() + "'" +
                ", livreur_id='" + getVendeur() + "'" +
                "}";
    }

}
