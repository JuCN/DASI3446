package metier.service;

import com.google.maps.OkHttpRequestHandler;
import metier.modele.*;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.GeoTest;



/**
 *
 * @author cguichon
 */
public class ServiceTechnique{
    
    public static void envoiMailEvenementPlanifie(Evenement evt, Adherent adh)
    {
        LatLng origine = new LatLng(adh.getLatitude(), adh.getLongitude());
        LatLng destination = new LatLng(evt.getLieu().getLatitude(), evt.getLieu().getLongitude());
        
        System.out.println("Expediteur : collectif@collectif.org");
        System.out.println("Pour : "+ adh.getMail());
        System.out.println("Sujet : Nouvel Evenement Collect'IF");
        System.out.println("Corps : \n");
        System.out.println("Bonjour " + adh.getPrenom()+"\n"
                + "Comme vous l'aviez souhaité, COLLECT'IF organise un événement de "+evt.getActivite().getDenomination()+" le "+evt.getDate()
                +". Vous trouverez ci-dessous les détails de cet événement.\n\nAssociativement votre,\nLe Responsable de l'Association\n");
        System.out.println("Événement : "+evt.getActivite().getDenomination());
        System.out.println("Date : "+evt.getDate());
        Logger.getLogger(OkHttpRequestHandler.class.getName()).setLevel(Level.WARNING);
        Double dist = GeoTest.getTripDurationOrDistance(TravelMode.WALKING, false, origine, destination);
        System.out.println("Lieu : "+evt.getLieu().getDenomination()+", "+evt.getLieu().getAdresse()+" (à "+dist+" km de chez vous)");
        System.out.println("Vous jouerez avec :");
        for (Adherent adherent : evt.getAdherents())
        {
            if (!adh.getMail().equals(adherent.getMail()))
            {
                System.out.println(adherent.getPrenom()+" "+adherent.getNom());
            }
        }
        
    }
    
    public static void envoiMailInscription(boolean valide, Adherent adh)
    {
        System.out.println("Expediteur : collectif@collectif.org");
        System.out.println("Pour : "+ adh.getMail());
        System.out.println("Sujet : Nouvel Evenement Collect'IF");
        System.out.println("Corps :");
        System.out.println("Bonjour " + adh.getPrenom()+",");
        
        if (valide)
        {
            System.out.println("Nous vous confirmons votre adhésion à l'association COLLECT'IF. Votre numéro d'adhérent est : "+adh.getId()+".");
        }
        else
        {
            System.out.println("Votre adhésion à l'association COLLECT'IF a malencontreusement échoué... Merci de recommencer ultérieurement.");
        }
    }
}
