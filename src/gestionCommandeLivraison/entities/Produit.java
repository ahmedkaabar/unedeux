/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionCommandeLivraison.entities;

import java.util.Objects;

/**
 *
 * @author user
 */
public class Produit {



    private int idProduit;
    private String libelle;
    private int prix;
    private int quantite;
    private String image;

    public Produit() {

    }

    public Produit(int idProduit, String libelle, int prix, int quantite, String image) {
        this.idProduit = idProduit;
        this.libelle = libelle;
        this.prix = prix;
        this.quantite = quantite;
        this.image = image;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.idProduit;
        hash = 19 * hash + Objects.hashCode(this.libelle);
        hash = 19 * hash + this.prix;
        hash = 19 * hash + this.quantite;
        hash = 19 * hash + Objects.hashCode(this.image);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produit other = (Produit) obj;
        if (this.idProduit != other.idProduit) {
            return false;
        }
        if (this.prix != other.prix) {
            return false;
        }
        if (this.quantite != other.quantite) {
            return false;
        }
        if (!Objects.equals(this.libelle, other.libelle)) {
            return false;
        }
        return Objects.equals(this.image, other.image);
    }

    @Override
    public String toString() {
        return "{" +
                " idProduit='" + getIdProduit() + "'" +
                ", libelle='" + getLibelle() + "'" +
                ", prix='" + getPrix() + "'" +
                ", quantite='" + getQuantite() + "'" +
                ", image='" + getImage() + "'" +
                "}";
    }

}   

