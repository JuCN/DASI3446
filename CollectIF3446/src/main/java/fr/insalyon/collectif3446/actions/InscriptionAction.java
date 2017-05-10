/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.collectif3446.actions;

import fr.insalyon.collectif3446.actions.Action;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Activite;
import metier.modele.Adherent;

/**
 *
 * @author jcharlesni
 */
public class InscriptionAction extends Action {
     @Override
    public void execute(HttpServletRequest request) {
        String prenom = request.getParameter("Prenom");
        String nom = request.getParameter("Nom");
        String mail = request.getParameter("Mail");
        String rue = request.getParameter("Rue");
        String ville = request.getParameter("Ville");
        String adresse = rue + " " + ville;
        
        Adherent adh = new Adherent(nom, prenom, mail, adresse);
        boolean res = false;
        try {
            res = service.inscrireAdherent(adh); 
        } catch (Exception ex) {
            Logger.getLogger(ListeActiviteAction.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        request.setAttribute("res", res);
        request.setAttribute("adh", adh);
    }
}
