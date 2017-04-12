package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Evenement;
/**
 *
 * @author cguichon
 */
public class EvenementDAO extends JpaUtil {
    
    public Evenement findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Evenement evenement = null;
        try {
            evenement = em.find(Evenement.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return evenement;
    }
    
    public List<Evenement> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Evenement> evenements = null;
        try {
            Query q = em.createQuery("SELECT ev FROM Evenement ev");
            evenements = (List<Evenement>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return evenements;
    }
    
    public void ajouterEvt(Evenement evt)
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(evt);
    }
    
    public void consulterListeEvt() throws Exception
    {
        List<Evenement> liste = findAll();
        for (Evenement a : liste) {
            System.out.println(a.toString());
        }
    }
    
    public List<Evenement> findEvenementNonPlanifie() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Evenement> evenements = null;
        try {
            Query q = em.createQuery("SELECT ev FROM Evenement ev WHERE ev.lieu is null");
            evenements = (List<Evenement>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return evenements;
    }
    
}
