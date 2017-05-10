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
 * @author Julien
 */
public class ConnexionAction extends Action {
     @Override
    public void execute(HttpServletRequest request) {
        String mail = request.getParameter("Mail");
        
        Adherent adh = null;
        try {
            adh = service.connexionAdherent(mail); 
        } catch (Exception ex) {
            Logger.getLogger(ListeActiviteAction.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        request.setAttribute("adh", adh);
    }
}
    

