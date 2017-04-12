package dao;

import com.google.maps.OkHttpRequestHandler;
import com.google.maps.model.LatLng;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Adherent;
import metier.modele.Demande;
import util.GeoTest;

public class AdherentDAO extends JpaUtil {
    
    public Adherent findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Adherent adherent = null;
        try {
            adherent = em.find(Adherent.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return adherent;
    }
    
    public List<Adherent> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Adherent> adherents = null;
        try {
            Query q = em.createQuery("SELECT a FROM Adherent a");
            adherents = (List<Adherent>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return adherents;
    }
    
    public boolean ajouterAdh(Adherent adh)
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<String> mails = null;
        Query q = em.createQuery("SELECT a.mail FROM Adherent a");
        mails = q.getResultList();
        Logger.getLogger(OkHttpRequestHandler.class.getName()).setLevel(Level.WARNING);
        LatLng latlng = GeoTest.getLatLng(adh.getAdresse());
        if(mails.contains(adh.getMail())) {
            em.remove(adh);
            System.out.println("Adresse mail déjà existante dans la base");
            return false;
        } 
        else if(latlng==null)
        {
            em.remove(adh);
            System.out.println("Adresse inexistante");
            return false;
        }
        else
        {
            adh.setLatitudeLongitude(latlng.lat, latlng.lng);
            em.persist(adh);
            return true;
        }
    }
    
    public void consulterListeAdh() throws Exception
    {
        List<Adherent> liste = findAll();
        for (Adherent a : liste) {
            System.out.println(a.toString());
        }
    }
    
    public List<Demande> consulterListeDemande(Adherent adh)
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query q = em.createQuery("SELECT d FROM Demande d WHERE d.adherent.id=:adhID");
        q.setParameter("adhID", adh.getId());
        List<Demande> demandes = q.getResultList();
        return demandes;
    }
    
    public Adherent verifMailConnexion(String mail)
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Adherent adherent = null;
        Query q = em.createQuery("SELECT a FROM Adherent a WHERE a.mail=:mail");
        q.setParameter("mail", mail);
        List<Adherent> adherents = q.getResultList();
        if (adherents.isEmpty())
        {
            System.out.println("L'adresse e-mail n'a pas été trouvée dans la base");
            return adherent;
        }
        else
        {
            adherent = adherents.get(0);
        }
        return adherent;
    }
}
