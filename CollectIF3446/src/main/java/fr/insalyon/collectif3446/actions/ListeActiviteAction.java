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

/**
 *
 * @author jcharlesni
 */
public class ListeActiviteAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        String activite = "";
        request.getParameter(activite);
        List<Activite> activites = new ArrayList();

        try {
            activites = service.catalogueActivites();
        } catch (Exception ex) {
            Logger.getLogger(ListeActiviteAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("activites", activites);
    }

}
