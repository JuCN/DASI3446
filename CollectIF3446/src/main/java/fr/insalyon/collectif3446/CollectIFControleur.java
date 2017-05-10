/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.collectif3446;

import fr.insalyon.collectif3446.actions.ActiviteAction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dao.JpaUtil;
import fr.insalyon.collectif3446.actions.Action;
import fr.insalyon.collectif3446.actions.ConnexionAction;
import fr.insalyon.collectif3446.actions.InscriptionAction;
import fr.insalyon.collectif3446.actions.ListeActiviteAction;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.modele.Activite;
import metier.modele.Adherent;

/**
 *
 * @author jcharlesni
 */


@WebServlet(name = "CollectIFControleur", urlPatterns = {"/CollectIFControleur"})
public class CollectIFControleur extends HttpServlet {

    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String todo = request.getParameter("todo");
        HttpSession session = request.getSession(true);
        JpaUtil.creerEntityManager();
        
        switch (todo) {
            case "menu" : {
                Adherent adh = (Adherent)session.getAttribute("adh");
                if(adh!=null){
                    PrintWriter out = response.getWriter();
                    printUserName(out, adh);
                } else {
                    System.out.println("L'utilisateur n'existe pas !");
                }
                break;
            }
            case "demande" : {
                Adherent adh = (Adherent)session.getAttribute("adh");
                request.setAttribute("adh", adh);
                Action dA = new DemandeAction();
                break;
            }
            case "connexion" : {
                Action cA = new ConnexionAction();
                cA.execute(request);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                Adherent adh = (Adherent) request.getAttribute("adh");
                session.setAttribute("adh",adh );
                if(adh != null){
                    response.sendRedirect("reussiteConnexion.html");
                } else {
                    response.sendRedirect("echecConnexion.html");
                }
                break;
            }
            case "inscription" : {
                Action Ia = new InscriptionAction();
                Ia.execute(request);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                boolean res = (boolean) request.getAttribute("res");
                Adherent adh = (Adherent) request.getAttribute("adh");
                PrintWriter out = response.getWriter();
                if(res){
                    response.sendRedirect("reussiteConnexion.html");
                    session.setAttribute("adh", adh);
                } else {
                    response.sendRedirect("echecConnexion.html");
                }
                break;
            }
            case "listeActivites": {
                Action la = new ListeActiviteAction();
                la.execute(request);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                List<Activite> lesActivites = (List<Activite>) request.getAttribute("activites");
                PrintWriter out = response.getWriter();
                printListeActivites(out, lesActivites);
                break;
            }
            case "DetailActivite": {
                Action aa = new ActiviteAction();
                aa.execute(request);
                System.out.println("************** DETAIL");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                Activite activite = (Activite) request.getAttribute("activite");
                System.out.println(activite.toString());
                PrintWriter out = response.getWriter();
                printActivite(out, activite);
                break;
            }
            default: {
                break;
            }
        }
        JpaUtil.fermerEntityManager();
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void printListeActivites(PrintWriter out, List<Activite> lesActivites) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonArray jsonListe = new JsonArray();
        for (Activite a : lesActivites) {
            JsonObject jsonActivites = new JsonObject();
            jsonActivites.addProperty("id", a.getId());
            jsonActivites.addProperty("denomination", a.getDenomination());
            jsonActivites.addProperty("payant", a.getPayant());
            jsonActivites.addProperty("nbParticipants", a.getNbParticipants());
            jsonListe.add(jsonActivites);
        }

        JsonObject container = new JsonObject();
        container.add("activites", jsonListe);
        out.println(gson.toJson(container));
    }

    private void printActivite(PrintWriter out, Activite a) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject jsonActivite = new JsonObject();
        jsonActivite.addProperty("id", a.getId());
        jsonActivite.addProperty("denomination", a.getDenomination());
        String pay = ""; 
        if(a.getPayant()){
            pay = "oui";
        } else {
            pay = "non";
        }
        jsonActivite.addProperty("payant", pay);
        jsonActivite.addProperty("nbParticipants", a.getNbParticipants());
        out.println(gson.toJson(jsonActivite));
    }
    
    private void printUserName(PrintWriter out, Adherent adh) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject jsonAdherent = new JsonObject();
        jsonAdherent.addProperty("nom", adh.getNom());
        jsonAdherent.addProperty("prenom", adh.getPrenom());
        out.println(gson.toJson(jsonAdherent));
    }

    @Override
    public void init() throws ServletException {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: return
         *  */
        // </editor-fold>
        JpaUtil.init();
    }

    @Override
    public void destroy() {
        JpaUtil.destroy();
    }
}
