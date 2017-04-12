package vue;
import dao.*;
import metier.modele.*;
import metier.service.ServiceMetier;
import util.Saisie;

/**
 *
 * @author cguichon
 */

public class Main {
    
    
    public static void main(String[] args) throws Exception {
        
        JpaUtil.init();
        JpaUtil.creerEntityManager();
        ServiceMetier servMet = new ServiceMetier();
        boolean quit = false;
        String mail;
        
        while (!quit)
        {
            System.out.print("Bienvenue sur site du COLLECT'IF\n");
            String carac = "a";

            while(!((carac.equals("C")||(carac.equals("S"))||carac.equals("Q"))))
            {
                carac = Saisie.lireChaine("(C)onnexion\n(S)'inscrire\n(Q)uitter\n");
            }
            if (carac.equals("C"))
            {
                System.out.println("Connectez-vous\n(R)etour\n");

                mail = Saisie.lireChaine("Adresse e-mail :\n");
                
                if (mail.equals("rebarbatIF"))
                {
                    IHMAdmin IHMAd = new IHMAdmin();
                }
                
                else if(carac.equals("R")){}
                
                else
                {
                    IHMAdherent IHMadh = new IHMAdherent(mail);
                }
            }
            else if(carac.equals("Q"))
            {
                quit = true;
            }
            else
            {
                System.out.println("Inscription\n");

                String nom = Saisie.lireChaine("Nom\n");

                String prenom = Saisie.lireChaine("Prénom\n");

                mail = Saisie.lireChaine("Mail\n");

                System.out.println("Adresse\n");
                String rue = Saisie.lireChaine("Rue\n");
                String ville = Saisie.lireChaine("Ville\n");
                String adresse = rue +", "+ville;

                Adherent adh = new Adherent(nom, prenom, mail, adresse);

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
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
        
    }

}

