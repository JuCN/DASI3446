package metier.service;
import com.google.maps.OkHttpRequestHandler;
import metier.modele.*;
import dao.*;
import javax.persistence.RollbackException;
import com.google.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.GeoTest;


/**
 *
 * @author cguichon
 */
public class ServiceMetier extends ServiceTechnique{
      
    // Méthode pour avoir des messages de Log dans le bon ordre (pause)
    private static void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            ex.hashCode();
        }
    }

    private static void log(String message) {
            System.out.flush();
            pause(5);
            System.err.println("[JpaUtil:Log] " + message);
            System.err.flush();
            pause(5);
        }
    
    
    
    public boolean inscrireAdherent(Adherent adh)
    {
        boolean valide = true;
        if(adh.getPrenom().equals("")||adh.getNom().equals("")||adh.getMail().equals("")||
                !(adh.getMail().contains("@"))||!((adh.getMail().endsWith(".fr")||adh.getMail().endsWith(".com"))))
        {
            System.out.println("Informations invalides !");
            valide = false;
        }
        AdherentDAO adhDAO = new AdherentDAO();
        try
        {
            if(valide)
            {
                JpaUtil.ouvrirTransaction();
                valide = adhDAO.ajouterAdh(adh);
                JpaUtil.validerTransaction();
            }
            ServiceTechnique.envoiMailInscription(valide, adh);
            return valide;
        }
        catch (RollbackException re)
        {
            JpaUtil.annulerTransaction();
            return false;
        }
    }
    
    public void posterDemande(Demande demande)
    {
        DemandeDAO demDAO = new DemandeDAO();
        boolean creerEvent = false;
        try
        {
            if (demDAO.verifDemande(demande))
            {
                System.out.println("Nouvelle demande");
                JpaUtil.ouvrirTransaction();
                demDAO.ajouterDemande(demande);
                JpaUtil.validerTransaction();
                creerEvent=true;
                
            } 
        }
        catch (RollbackException re)
        {
            JpaUtil.annulerTransaction();
        }
        
        if (creerEvent)
        {
            Evenement event = demDAO.demandeSuffisante(demande);

            if (!(event.getMoment()==null))
            {
                System.out.println("Nouvel event");
                EvenementDAO evtDAO = new EvenementDAO();
                try{
                    JpaUtil.ouvrirTransaction();
                    evtDAO.ajouterEvt(event);
                    JpaUtil.validerTransaction();
                    
                }
                catch (RollbackException re)
                {
                    JpaUtil.annulerTransaction();
                } 
            }
        }
    }
    
    public List<Activite> catalogueActivites() throws Exception
    {
        ActiviteDAO actDAO = new ActiviteDAO();
        List<Activite> catalogueAct = actDAO.findAll();
        
        /*for (Activite act : catalogueAct)
        {
            System.out.println("[" + act.getId() + "] " + act.getDenomination());
            if(act.getPayant())
            {
                System.out.println("Payant : oui");
            }
            else
            {
                System.out.println("Payant : non");
            }
            System.out.println("Nombre de participants : " + act.getNbParticipants() + "\n");
        }
        */
        return catalogueAct;
    }
    
    public List<Lieu> catalogueLieux() throws Exception
    {
        LieuDAO lieuDAO = new LieuDAO();
        List<Lieu> catalogueLieu = lieuDAO.findAll();
        int compteur = 0;
        for (Lieu lieu : catalogueLieu)
        {
            System.out.println("[" + compteur + "] " + lieu.getDenomination());
            System.out.println("Type : " + lieu.getType());
            System.out.println(lieu.getAdresse()+ "\n");
            compteur++;
        }
        
        return catalogueLieu;
    }
    
    
    // Retourne une liste de LatLng qui contient d'abord les n adhérents
    // participant à l'événement
    
    public List<LatLng> latLngAdhEvent(Evenement event) 
    {
        List<LatLng> latLngAdh = new ArrayList<>() ; 
        for (int i=0; i<event.getActivite().getNbParticipants(); i++)
        {
            Logger.getLogger(OkHttpRequestHandler.class.getName()).setLevel(Level.WARNING);
            latLngAdh.add(GeoTest.getLatLng(event.getAdherents().get(i).getAdresse()));
        }
        return latLngAdh;
    }
    
    public List<LatLng> latLngLieux() throws Exception
    {
        List<LatLng> latLngLieux = new ArrayList<>() ;
        LieuDAO lieuDAO = new LieuDAO();
        List<Lieu> catalogueLieux = lieuDAO.findAll();
        for (Lieu lieu : catalogueLieux)
        {
            Logger.getLogger(OkHttpRequestHandler.class.getName()).setLevel(Level.WARNING);
            latLngLieux.add(GeoTest.getLatLng(lieu.getAdresse()));
        }
        return latLngLieux;
    }
    
    public List<Evenement> consulterTDB() throws Exception
    {
        EvenementDAO evtDAO = new EvenementDAO();
        List<Evenement> listEvt = evtDAO.findAll();
        
        for (Evenement evt : listEvt)
        {
            System.out.println("["+ evt.getId() +"] "+evt.getActivite().getDenomination());
            System.out.println("Date : "+ evt.getDate());
            System.out.println("Moment : "+ evt.getMoment());
            if (evt.getPAF()==0F)
            {
                System.out.println("Activité : "+ evt.getActivite().getDenomination() +"    Gratuit");
            }
            else
            {
                System.out.println("Activité : "+evt.getActivite().getDenomination()+"     "+ evt.getPAF()+"€.");
            }
            if (evt.getLieu() == null)
            {
                System.out.println("Statut : à planifier");
            }
            else
            {
                System.out.println("Statut : planifié");
            }
            System.out.println("");
        }
        
        return listEvt;
    }
    
    public List<Evenement> consulterTDBaPlanifier() throws Exception
    {
        EvenementDAO evtDAO = new EvenementDAO();
        List<Evenement> listEvt = evtDAO.findEvenementNonPlanifie();
        int compteur = 0;
        for (Evenement evt : listEvt)
        {
            System.out.println("["+compteur+"] "+evt.getActivite().getDenomination());
            System.out.println("Date : "+evt.getDate()+" "+evt.getMoment());
            System.out.println("Moment : "+ evt.getMoment());
            if (evt.getActivite().getPayant())
            {
                System.out.println("Payant");
            }
            else
            {
                System.out.println("Gratuit");
            }
            
            System.out.println("");
            compteur++;
        }
        
        return listEvt;
    }
    
    public List<Demande> historiqueDemande(Adherent adh)
    {
        AdherentDAO adhDAO = new AdherentDAO();
        List<Demande> listeDemandes = adhDAO.consulterListeDemande(adh);
        
        for (Demande dem : listeDemandes)
        {
            System.out.println("["+ dem.getId() +"] "+dem.getActivite().getDenomination());
            System.out.println("Date : " + dem.getDate());
            System.out.println("Moment : "+ dem.getMoment());
            if (dem.isConfirme())
            {
                System.out.println("Statut : validée");
            }
            else
            {
                System.out.println("Statut : non validée");
            }
            System.out.println("");
        }
        
        
        return listeDemandes;
    }
    
    public void assignerLieuPAF(Evenement event, Lieu lieu, Float PAF) throws Exception
    {
        try{
            JpaUtil.ouvrirTransaction();
            EvenementDAO evtDAO = new EvenementDAO();
            Evenement evt1 = evtDAO.findById(event.getId());
            evt1.setLieu(lieu);            
            evt1.setPAF(PAF);
            JpaUtil.validerTransaction();
            for (Adherent adherent : event.getAdherents()) {
                ServiceTechnique.envoiMailEvenementPlanifie(event, adherent);
            }
            
        }
        catch (RollbackException re)
        {
            JpaUtil.annulerTransaction();
        }
        
    }
    
    public Adherent connexionAdherent(String mail)
    {
        AdherentDAO adhDAO = new AdherentDAO();
        Adherent adh = adhDAO.verifMailConnexion(mail);
        if (adh == null)
        {
            System.out.println("Connexion échouée");
            return adh;
        }
        else
        {
            System.out.println("Connexion réussie : Bienvenue " + adh.getPrenom() + " " + adh.getNom());
            return adh;
        }
    }
    
}