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
import metier.modele.Demande;
import metier.modele.Lieu;

/**
 *
 * @author ahamroun
 */
public class ListeLieuxAction extends Action {
    @Override
    public void execute(HttpServletRequest request) {
       
        List<Lieu> lieux = new ArrayList();

        try {
            lieux= service.catalogueLieux();
        } catch (Exception ex) {
            Logger.getLogger(ListeActiviteAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("Lieux", lieux);
    }
}