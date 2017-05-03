package metier.modele;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Activite;
import metier.modele.Adherent;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-03T16:09:59")
@StaticMetamodel(Demande.class)
public class Demande_ { 

    public static volatile SingularAttribute<Demande, Long> id;
    public static volatile SingularAttribute<Demande, Boolean> confirme;
    public static volatile SingularAttribute<Demande, Activite> activite;
    public static volatile SingularAttribute<Demande, String> moment;
    public static volatile SingularAttribute<Demande, Adherent> adherent;
    public static volatile SingularAttribute<Demande, Date> date;

}