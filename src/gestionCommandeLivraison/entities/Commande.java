/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionCommandeLivraison.entities;

import java.util.List;
import java.util.Objects;

public class Commande {

    private int idCommande;
    private List<Produit> listeProduits;
    private String designation;
    private String adresseCommande;
    private int prixCommande;
    private String etatCommande;
    private User client;

    public Commande() {
    }

    public Commande(int idCommande, List<Produit> listeProduits, String designation, String adresseCommande,
            int prixCommande, String etatCommande, User client) {
        this.idCommande = idCommande;
        this.listeProduits = listeProduits;
        this.designation = designation;
        this.adresseCommande = adresseCommande;
        this.prixCommande = prixCommande;
        this.etatCommande = etatCommande;
        this.client = client;
    }

    public Commande(List<Produit> listeProduits, String designation, String adresseCommande,
            int prixCommande, String etatCommande, User client) {
        this.listeProduits = listeProduits;
        this.designation = designation;
        this.adresseCommande = adresseCommande;
        this.prixCommande = prixCommande;
        this.etatCommande = etatCommande;
        this.client = client;
    }

    public Commande(String designation, String adresseCommande,
            int prixCommande, String etatCommande, User client) {
        this.designation = designation;
        this.adresseCommande = adresseCommande;
        this.prixCommande = prixCommande;
        this.etatCommande = etatCommande;
        this.client = client;
    }

    public int getIdCommande() {
        return this.idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public List<Produit> getListeProduits() {
        return this.listeProduits;
    }

    public void setListeProduits(List<Produit> listeProduits) {
        this.listeProduits = listeProduits;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAdresseCommande() {
        return this.adresseCommande;
    }

    public void setAdresseCommande(String adresseCommande) {
        this.adresseCommande = adresseCommande;
    }

    public int getPrixCommande() {
        return this.prixCommande;
    }

    public void setPrixCommande(int prixCommande) {
        this.prixCommande = prixCommande;
    }

    public String getEtatCommande() {
        return this.etatCommande;
    }

    public void setEtatCommande(String etatCommande) {
        this.etatCommande = etatCommande;
    }

    public User getClient() {
        return this.client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Commande)) {
            return false;
        }
        Commande commande = (Commande) o;
        return idCommande == commande.idCommande && Objects.equals(listeProduits, commande.listeProduits)
                && Objects.equals(designation, commande.designation)
                && Objects.equals(adresseCommande, commande.adresseCommande) && prixCommande == commande.prixCommande
                && Objects.equals(etatCommande, commande.etatCommande) && Objects.equals(client, commande.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCommande, listeProduits, designation, adresseCommande, prixCommande, etatCommande,
                client);
    }

    @Override
    public String toString() {
        return "{"
                + " idCommande='" + getIdCommande() + "'"
                + ", listeProduits='" + getListeProduits() + "'"
                + ", designation='" + getDesignation() + "'"
                + ", adresseCommande='" + getAdresseCommande() + "'"
                + ", prixCommande='" + getPrixCommande() + "'"
                + ", etatCommande='" + getEtatCommande() + "'"
                + ", client='" + getClient() + "'"
                + "}";
    }

}
