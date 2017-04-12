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
import fr.insalyon.collectif3446.actions.ListeActiviteAction;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Activite;

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
        JpaUtil.creerEntityManager();

        switch (todo) {
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
