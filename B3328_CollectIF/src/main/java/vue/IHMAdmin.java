package vue;

import com.google.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.*;
import metier.service.ServiceMetier;
import util.Saisie;

/**
 *
 * @author Tom
 */
public class IHMAdmin {
    
    public IHMAdmin()
    {
        ServiceMetier servMet = new ServiceMetier();
        System.out.println("Bienvenue admnistrateur");
        String carac = "A";
        while(!carac.equals("Q"))
        {
            carac = Saisie.lireChaine("(L)iste des événements\n Liste des événements à (p)lanifier\n");
            if (carac.equals("L"))
            {
                try {
                    List<Evenement> listEvt = servMet.consulterTDB();
                    if (listEvt.isEmpty())
                    {
                        System.out.println("Pas d'événements");
                    }
                } catch (Exception ex) {}
            }
            else if(carac.equals("p"))
            {
                
                String carac2 = "A";
                while(!carac2.equals("Q"))
                {
                    boolean empty = false;
                    List<Evenement> listEvtPlan = null;
                    try {
                        listEvtPlan = servMet.consulterTDBaPlanifier();
                        if (listEvtPlan.isEmpty())
                        {
                            System.out.println("Pas d'événements à planifier");
                            empty = true;
                            carac2 = "Q";
                        }
                    } catch (Exception ex) {}
                    if (!empty)
                    {
                        carac2 = Saisie.lireChaine("(P)lanifier un événement\n(Q)uitter\n");
                        if (carac2.equals("P"))
                        {
                            List<Integer> valeursPossibles = new ArrayList<Integer>();
                            for (Integer i=0;i<=listEvtPlan.size();i++)
                            {
                                valeursPossibles.add(i);
                            }
                            Integer selecEvt = Saisie.lireInteger("Indiquez un événement que vous souhaitez planifier\n", valeursPossibles);

                            System.out.println("Latitude et longitude des adhérents participant à l'événement");
                            List<LatLng> listeLatLngAdh = servMet.latLngAdhEvent(listEvtPlan.get(selecEvt));
                            for (LatLng latlng : listeLatLngAdh)
                            {
                                System.out.println(latlng.lat+" "+latlng.lng);
                            }

                            List<Lieu> catalogueLieux = null;
                            try {
                                catalogueLieux = servMet.catalogueLieux();
                            } catch (Exception ex) {
                                Logger.getLogger(IHMAdmin.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            List<Integer> valeursPossibles2 = new ArrayList<Integer>();
                            for (Integer i=0;i<catalogueLieux.size();i++)
                            {
                                valeursPossibles2.add(i);
                            }
                            Integer selectLieu = Saisie.lireInteger("Indiquez l'ID du lieu\n", valeursPossibles2);

                            Float PAF = Float.parseFloat(Saisie.lireChaine("Participation aux frais : \n"));
                            try {
                                servMet.assignerLieuPAF(listEvtPlan.get(selecEvt), catalogueLieux.get(selectLieu), PAF);
                            } catch (Exception ex) {
                                System.out.println("Problème assignation Lieu/PAF");
                                Logger.getLogger(IHMAdmin.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                }
            }
        }
        
        
        
        
        
        
        
        
    }
    
    
}
