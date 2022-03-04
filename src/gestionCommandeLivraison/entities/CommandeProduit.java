/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionCommandeLivraison.entities;

import java.util.Objects;

/**
 *
 * @author Fedi
 */
public class CommandeProduit {
    
private int id;
    private Produit produit;
    private Commande commande;

    public CommandeProduit() {

    }

    public CommandeProduit(int id, Produit produit, Commande commande) {
        this.id = id;
        this.produit = produit;
        this.commande = commande;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.produit);
        hash = 97 * hash + Objects.hashCode(this.commande);
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
        final CommandeProduit other = (CommandeProduit) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.produit, other.produit)) {
            return false;
        }
        return Objects.equals(this.commande, other.commande);
    }

}
