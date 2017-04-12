package vue;

import dao.ActiviteDAO;
import java.util.List;
import metier.modele.*;
import metier.service.ServiceMetier;
import util.Saisie;

/**
 *
 * @author Tom
 */
public class IHMAdherent {
    
    public IHMAdherent(String mail) throws Exception
    {
        ServiceMetier servMet = new ServiceMetier();
        boolean inscript = false;
        Adherent adh = servMet.connexionAdherent(mail);
        String carac = "S";
        if(adh == null)
        {
            while (!((carac.equals("R")||carac.equals("I"))))
            {
                System.out.println("Échec de connexion\nVeuillez vérifier votre adresse e-mail ou inscrivez-vous");
                carac = Saisie.lireChaine("(I)nscription\n(R)etour\n");
                if (carac.equals("I"))
                {
                    inscript=true;
                }
            }
        }
        else 
        {
            System.out.println("Bienvenue, "+adh.getPrenom()+" "+adh.getNom());
            
            boolean deco = false;
            while (!deco)
            {
                carac = Saisie.lireChaine("(D)emande d'activité\n(C)atalogue d'activités\n(H)istorique des demandes\n(Q)uit\n");
                if(carac.equals("D"))
                {
                    String actStr = Saisie.lireChaine("Nom activité :\n");
                    String dateStr = Saisie.lireChaine("Date (JJ/MM/AAAA) :\n");
                    String moment = Saisie.lireChaine("Moment (matin, midi, soir) :\n");
                    ActiviteDAO actDAO = new ActiviteDAO();
                    Activite act = actDAO.findByName(actStr);
                    Demande demande = new Demande(adh, act, dateStr, moment);
                    servMet.posterDemande(demande);
                }
                else if (carac.equals("C"))
                {
                    List<Activite> catalogueAct = servMet.catalogueActivites();
                }
                else if (carac.equals("H"))
                {
                    List<Demande> histoDem = servMet.historiqueDemande(adh);
                }
                else if(carac.equals("Q"))
                {
                    deco = true;
                }
            }
                    
        }
        
        if (inscript)
        {
            System.out.println("Inscription\n");
                    
            String nom = Saisie.lireChaine("Nom\n");

            String prenom = Saisie.lireChaine("Prénom\n");

            mail = Saisie.lireChaine("Mail\n");

            System.out.println("Adresse\n");
            String rue = Saisie.lireChaine("Rue\n");
            String ville = Saisie.lireChaine("Ville\n");
            String adresse = rue +", "+ville;

            adh = new Adherent(nom, prenom, mail, adresse);

            if (servMet.inscrireAdherent(adh))
            {
                System.out.println("E-mail de confirmation envoyé\n");
                Saisie.lireChaine("Retour\n");
            }
            else
            {
                System.out.println("Echec à l'inscription");
                Saisie.lireChaine("Retour\n");
            }
        }
    }
}
