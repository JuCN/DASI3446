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
import metier.modele.Evenement;

/**
 *
 * @author Julien
 */
public class EvenementAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
         String Sid = request.getParameter("id");
        Long id = Long.parseLong(Sid);
        List<Evenement> evenements = new ArrayList();
        System.out.println(id);
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
        request.setAttribute("evenement", evenement);
    }
    
}
