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

/**
 *
 * @author jcharlesni
 */
public class ActiviteAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        String Sid = request.getParameter("id");
        Long id = Long.parseLong(Sid);
        List<Activite> activites = new ArrayList();
        System.out.println(id);
        try {
            activites = service.catalogueActivites();
            
        } catch (Exception ex) {
            Logger.getLogger(ListeActiviteAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        Activite activite= null;
        for (Activite a : activites) {
            if(a.getId()==id){
                activite = a;
                break;
            }
        }
      System.out.println(activite.toString());
        request.setAttribute("activite", activite);
    }
    
}
