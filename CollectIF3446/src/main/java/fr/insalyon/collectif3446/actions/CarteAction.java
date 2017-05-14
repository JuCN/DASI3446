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
import metier.modele.Adherent;
import metier.modele.Evenement;
import metier.modele.Lieu;

/**
 *
 * @author Julien
 */
public class CarteAction extends Action {
    @Override
    public void execute(HttpServletRequest request) {
       
        List<Lieu> lieux = new ArrayList();
        
        try {
            lieux= service.catalogueLieux();
        } catch (Exception ex) {
            Logger.getLogger(ListeActiviteAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("lieux", lieux);
        
        String Sid = request.getParameter("id");
        Long id = Long.parseLong(Sid);
        List<Evenement> evenements = new ArrayList();
        try {
            evenements = service.consulterTDBaPlanifier();
            
        } catch (Exception ex) {
            Logger.getLogger(ListeActiviteAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        Evenement evenement= null;
        for (Evenement a : evenements) {
            if(a.getId().compareTo(id) == 0){
                evenement = a;
                break;
            }
        }
        
        List<Adherent> adherents = evenement.getAdherents();
        request.setAttribute("adherents", adherents);
    }
}
