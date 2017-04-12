package dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Adherent;
import metier.modele.Demande;
import metier.modele.Evenement;
/**
 *
 * @author cguichon
 */
public class DemandeDAO extends JpaUtil{
    
    public Demande findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Demande demande = null;
        try {
            demande = em.find(Demande.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return demande;
    }
    
    public List<Demande> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Demande> demandes = null;
        try {
            Query q = em.createQuery("SELECT l FROM Demande l");
            demandes = (List<Demande>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return demandes;
    }
    
     public void ajouterDemande(Demande demande)
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(demande);
    }
    
    public void consulterListeDemande() throws Exception
    {
        List<Demande> liste = findAll();
        for (Demande a : liste) {
            System.out.println(a.toString());
        }
    }
    
    
    public boolean verifDemande(Demande demande)
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Date instantT = new Date();
        if (instantT.compareTo(demande.getDate())==-1)
        {
            Query q = em.createQuery("SELECT d.adherent FROM Demande d WHERE d.activite.id=:actID AND d.date=:date AND d.moment=:moment AND d.adherent.id=:adherentID");
            q.setParameter("date", demande.getDate());
            q.setParameter("actID", demande.getActivite().getId());
            q.setParameter("moment", demande.getMoment());
            q.setParameter("adherentID", demande.getAdherent().getId());
            List<Adherent> adherents = q.getResultList();
            System.out.println("Nb de demandes identiques :"+ adherents.size());
            return adherents.isEmpty();
        }
        else
        {
            System.out.println("Date antérieure à la date actuelle");
            return false;
        }
    }
    
    
    public Evenement demandeSuffisante(Demande demande)
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query q = em.createQuery("SELECT d FROM Demande d WHERE d.activite.id=:actID AND d.date=:date AND d.moment=:moment AND d.confirme=false");
        q.setParameter("date", demande.getDate());
        q.setParameter("actID", demande.getActivite().getId());
        q.setParameter("moment", demande.getMoment());
        List<Demande> nbDemandes = (List<Demande>) q.getResultList();
        System.out.println("Il y a "+nbDemandes.size()+" personnes inscrites à cette activité en attente d'un événement");
        if (nbDemandes.size()>=demande.getActivite().getNbParticipants())
        { 
            for (int i=0;i<nbDemandes.size();i++)
            {
                nbDemandes.get(i).setConfirme(true);
            }
            q = em.createQuery("SELECT d.adherent FROM Demande d WHERE d.activite.id=:actID AND d.date=:date AND d.moment=:moment AND d.confirme=false");
            q.setParameter("date", demande.getDate());
            q.setParameter("actID", demande.getActivite().getId());
            q.setParameter("moment", demande.getMoment());
            List<Adherent> adherents = q.getResultList();

            System.out.println("liste" + adherents);
            Evenement event = new Evenement(demande.getDate(), demande.getMoment(), demande.getActivite(), adherents);
            System.out.println("event "+event);
            return event;
        }
        Evenement event = new Evenement();
        return(event);
    }
}

