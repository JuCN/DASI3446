/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.collectif3446.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.Demande;

/**
 *
 * @author Julien
 */
public class DemandeAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        List<Activite> activites = new ArrayList();
        Adherent adh = (Adherent) request.getAttribute("adh");
        String activite = request.getParameter("activite");
        String date = request.getParameter("date");
        System.out.println(date);
        String moment = request.getParameter("moment");
        
        try {
            activites = service.catalogueActivites();
        } catch (Exception ex) {
            Logger.getLogger(ListeActiviteAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(date);
        Activite a = null;
        for(int i=0; i<activites.size(); i++){
            if(activites.get(i).getDenomination().equals(activite)){
                a = activites.get(i);
                break;
            }
        }
        
        Demande demande = new Demande(adh, a, date, moment);
        
        boolean res = service.posterDemande(demande);
       
        request.setAttribute("res", res);
    }

}
