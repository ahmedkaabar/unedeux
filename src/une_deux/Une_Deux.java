/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package une_deux;

import gestionCommandeLivraison.entities.Commande;
import gestionCommandeLivraison.entities.Livraison;
import gestionCommandeLivraison.entities.User;
import gestionCommandeLivraison.services.CommandeProduitService;
import gestionCommandeLivraison.services.CommandeService;
import gestionCommandeLivraison.services.LivraisonService;
import gestionCommandeLivraison.services.UserService;
import gestionProduitCompetition.entities.Categorie;
import gestionProduitCompetition.entities.Competition;
import gestionCommandeLivraison.entities.Produit;
import gestionProduitCompetition.entities.Statuts;
import java.sql.SQLException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmed
 */
public class Une_Deux {

    /**
     * @param args the command line arguments
     * @throws java.text.ParseException
     */
    public static void main(String[] args) throws ParseException, SQLException {
        // TODO code application logic here

        //Date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateDebut = format.parse("2022-02-18");
        Date dateFin = format.parse("2022-02-19");
        Date dateDebut1 = format.parse("2022-04-28");
        Date dateFin1 = format.parse("2022-05-10");
        Date dateDebut2 = format.parse("2022-06-28");
        Date dateFin2 = format.parse("2022-08-10");

        //Categorie
        //Compettion
        //System.out.println("LISTE DES COMPETITIONS");
        //System.out.println("\n");
        Competition cmp = new Competition("day1", "image1", dateDebut, dateFin, "voilà la description", 0);
        Competition cmp1 = new Competition("day2", "image2", dateDebut1, dateFin1, "voilà la description1", 0);
        //Competition cmp2 = new Competition("day3", "image3", dateDebut2, dateFin2, "voilà la description2", 0);
        //Competition cmp3 = new Competition("day3", "image3", dateDebut2, dateFin2, "voilà la description2", 0);
        //CompetitionService competitionService = new CompetitionService();
        //competitionService.ajouter(cmp);
        //competitionService.ajouter(cmp1);
        //competitionService.ajouter(cmp2);
        //competitionService.ajouter(cmp3);
        //System.out.println(competitionService.afficher());
        //competitionService.modifier(cmp, 4);
        //competitionService.supprimer(cmp3);

        //System.out.println(competitionService.afficher());
        //System.out.println(competitionService.tri());
        //competitionService.rechercher("day1");
        //System.out.println("\n");
        Categorie c1 = null;
        Categorie c = null;

       // UserService su = new UserService();
        //User user = su.get(18);
        CommandeService cs = new CommandeService();
        //LivraisonService ls = new LivraisonService();
        CommandeProduitService cps = new CommandeProduitService();
       // Commande commande = new Commande("aaaa", "bb", 54, "aaa", user);
       // cs.update(7, commande);
        for (Commande c7 : cs.getall()) {
            System.out.println(c7);
        }

        //Commande c0 = cs.get(7);
       // Livraison l = new Livraison(c0, "fedi", "aaa", user);
        //ls.update(4, l);
       // cps.addProduitToCommande(2, 7);
        //for (Produit p : cps.getProduitsParCommande(7)) {
            //System.out.println(p);
        }
        

    }


