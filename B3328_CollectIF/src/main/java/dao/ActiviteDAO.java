package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Activite;
import metier.modele.Lieu;

public class ActiviteDAO extends JpaUtil {
    
    public Activite findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Activite activite = null;
        try {
            activite = em.find(Activite.class, id);
        }
        catch(Exception e) {
            System.out.println(activite);
            throw e;
        }
        return activite;
    }
    
    public Activite findByName(String name) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Activite activite = null;
        try {
            Query q = em.createQuery("SELECT a FROM Activite a WHERE a.denomination=:name");
            q.setParameter("name", name);
            List<Activite> listAct = q.getResultList();
            activite = listAct.get(0);
        }
        catch(Exception e) {
            System.out.println(activite);
            throw e;
        }
        return activite;
    }
    
    public List<Activite> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Activite> activites = null;
        try {
            Query q = em.createQuery("SELECT a FROM Activite a");
            activites = (List<Activite>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return activites;
    }
    
     public void ajouterAct(Activite act)
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(act);
    }
    
    public void consulterListeAct() throws Exception
    {
        List<Activite> liste = findAll();
        for (Activite a : liste) {
            System.out.println(a.toString());
        }
    }
}