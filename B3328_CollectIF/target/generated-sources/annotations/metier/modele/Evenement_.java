package metier.modele;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.Lieu;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-12T17:29:45")
@StaticMetamodel(Evenement.class)
public class Evenement_ { 

    public static volatile SingularAttribute<Evenement, Long> id;
    public static volatile SingularAttribute<Evenement, Lieu> lieu;
    public static volatile ListAttribute<Evenement, Adherent> adherents;
    public static volatile SingularAttribute<Evenement, String> moment;
    public static volatile SingularAttribute<Evenement, Activite> activite;
    public static volatile SingularAttribute<Evenement, Float> PAF;
    public static volatile SingularAttribute<Evenement, Date> date;

}