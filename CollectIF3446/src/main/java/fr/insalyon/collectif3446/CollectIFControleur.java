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
import fr.insalyon.collectif3446.actions.CarteAction;
import fr.insalyon.collectif3446.actions.ConnexionAction;
import fr.insalyon.collectif3446.actions.DemandeAction;
import fr.insalyon.collectif3446.actions.EvenementAction;
import fr.insalyon.collectif3446.actions.HistoriqueAction;
import fr.insalyon.collectif3446.actions.InscriptionAction;
import fr.insalyon.collectif3446.actions.ListeActiviteAction;
import fr.insalyon.collectif3446.actions.ListeEvenementAction;
import fr.insalyon.collectif3446.actions.ListeLieuxAction;
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
import metier.modele.Demande;
import metier.modele.Evenement;
import metier.modele.Lieu;

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
            case "carte" : {
                CarteAction cA = new CarteAction();
                cA.execute(request);
                List<Lieu> lieux = (List<Lieu>) request.getAttribute("lieux");
                List<Adherent> adherents = (List<Adherent>) request.getAttribute("adherents");
                PrintWriter out = response.getWriter();
                printCarteLieux(out, lieux);
                printCarteAdh(out, adherents);
                break;
            }
            case "demande" : {
                Adherent adh = (Adherent)session.getAttribute("adh");
                request.setAttribute("adh", adh);
                Action dA = new DemandeAction();
                dA.execute(request);
                boolean res = (boolean) request.getAttribute("res");
                if(res){
                    response.sendRedirect("menu.html");
                } else {
                    System.out.println("Demande non acceptée !");
                    response.sendRedirect("menu.html");
                }
                break;
            }
            case "historique" : {
                Adherent adh = (Adherent)session.getAttribute("adh");
                request.setAttribute("adh", adh);
                Action hA = new HistoriqueAction();
                hA.execute(request);
                List<Demande> historique = (List<Demande>) request.getAttribute("historique");
                PrintWriter out = response.getWriter();
                printHistorique(out, historique);
                break;
            }
            case "connexion" : {
                String mail = request.getParameter("Mail");
                if(!mail.equals("rebarbatIF")){
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
                } else {
                    response.sendRedirect("menuAdmin.html");
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
            case "PAF" : {
                Action eA = new EvenementAction();
                eA.execute(request);
                Evenement evenement = (Evenement) request.getAttribute("evenement");
                PrintWriter out = response.getWriter();
                printEvenement(out, evenement);
                break;
            }
            case "listeLieux": {
                Action la = new ListeLieuxAction();
                la.execute(request);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                List<Lieu> LesLieux = (List<Lieu>) request.getAttribute("Lieux");
                PrintWriter out = response.getWriter();
                printListeLieux(out, LesLieux);
                break;
            }
            case "listeEvenementsATraiter": {
                Action lEA = new ListeEvenementAction();
                lEA.execute(request);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                List<Evenement> lesEvenements = (List<Evenement>) request.getAttribute("evenements");
                PrintWriter out = response.getWriter();
                printListeEvenements(out, lesEvenements);
                break;
            }
            case "listeEvenements": {
                Action lEA = new ListeEvenementAction();
                lEA.execute(request);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                List<Evenement> lesEvenements = (List<Evenement>) request.getAttribute("evenements");
                PrintWriter out = response.getWriter();
                printListeEvenements(out, lesEvenements);
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
    
    private void printListeLieux(PrintWriter out, List<Lieu> lesLieux ) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonArray jsonListe = new JsonArray();
        for (Lieu a : lesLieux) {
            JsonObject jsonActivites = new JsonObject();
            jsonActivites.addProperty("id", a.getId());
            jsonActivites.addProperty("denomination", a.getDenomination());
            jsonActivites.addProperty("type", a.getType());
            jsonActivites.addProperty("adresse", a.getAdresse());
            jsonListe.add(jsonActivites);
        }

        JsonObject container = new JsonObject();
        container.add("Lieux", jsonListe);
        out.println(gson.toJson(container));
    }

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
    
    private void printCarteLieux(PrintWriter out, List<Lieu> lieux) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonListe = new JsonArray();
        for (Lieu a : lieux) {
            JsonObject jsonActivites = new JsonObject();
            jsonActivites.addProperty("id", a.getId());
            jsonActivites.addProperty("denomination", a.getDenomination());
            jsonActivites.addProperty("latitude", a.getLatitude());
            jsonActivites.addProperty("longitude", a.getLongitude());
            jsonActivites.addProperty("adresse", a.getAdresse());
            jsonListe.add(jsonActivites);
        }

        JsonObject container = new JsonObject();
        container.add("lieux", jsonListe);
        out.println(gson.toJson(container));
    }
    
    private void printCarteAdh(PrintWriter out, List<Adherent> adherents) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonArray jsonListe = new JsonArray();
        for (Adherent a : adherents) {
            JsonObject jsonActivites = new JsonObject();
            jsonActivites.addProperty("id", a.getId());
            jsonActivites.addProperty("nom", a.getNom()+ " " + a.getPrenom());
            jsonActivites.addProperty("latitude", a.getLatitude());
            jsonActivites.addProperty("longitude", a.getLongitude());
            jsonActivites.addProperty("adresse", a.getAdresse());
            jsonListe.add(jsonActivites);
        }

        JsonObject container = new JsonObject();
        container.add("adherents", jsonListe);
        out.println(gson.toJson(container));
    }
    
    private void printListeEvenements(PrintWriter out, List<Evenement> lesEvenements) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonArray jsonListe = new JsonArray();
        for (Evenement a : lesEvenements) {
            JsonObject jsonActivites = new JsonObject();
            jsonActivites.addProperty("id", a.getId());
            jsonActivites.addProperty("denomination", a.getActivite().getDenomination());
            String pay = "Payant";
            if(!a.getActivite().getPayant()){
                pay = "Gratuit";
            }
            jsonActivites.addProperty("payant", pay);
            jsonActivites.addProperty("date", a.getDate().getDay());
            jsonActivites.addProperty("moment", a.getMoment());
            String res = "planifié";
            if(a.getLieu()==null){
                res = "à planifier";
            }
            jsonActivites.addProperty("statut", res);
            jsonListe.add(jsonActivites);
        }

        JsonObject container = new JsonObject();
        container.add("evenements", jsonListe);
        out.println(gson.toJson(container));
    }
    
     private void printHistorique(PrintWriter out, List<Demande> historique) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonArray jsonListe = new JsonArray();
        for (Demande a : historique) {
            JsonObject jsonActivites = new JsonObject();
            jsonActivites.addProperty("id", a.getId());
            jsonActivites.addProperty("denomination", a.getActivite().getDenomination());
            jsonActivites.addProperty("date", a.getDate().toString());
            jsonActivites.addProperty("moment", a.getMoment());
            String res = "validée";
            if(!a.isConfirme()){
                res = "non "+res;
            }
            jsonActivites.addProperty("statut", res);
            jsonListe.add(jsonActivites);
        }

        JsonObject container = new JsonObject();
        container.add("historique", jsonListe);
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
    
    private void printEvenement(PrintWriter out, Evenement a) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject jsonActivites = new JsonObject();
            jsonActivites.addProperty("id", a.getId());
            jsonActivites.addProperty("denomination", a.getActivite().getDenomination());
            String pay = "Payant";
            if(!a.getActivite().getPayant()){
                pay = "Gratuit";
            }
            jsonActivites.addProperty("payant", pay);
            jsonActivites.addProperty("date", a.getDate().getDay());
            jsonActivites.addProperty("moment", a.getMoment());
            String res = "planifié";
            if(a.getLieu()==null){
                res = "à planifier";
            }
            jsonActivites.addProperty("statut", res);
        out.println(gson.toJson(jsonActivites));
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
