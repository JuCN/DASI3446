package metier.modele;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-12T17:29:45")
@StaticMetamodel(Lieu.class)
public class Lieu_ { 

    public static volatile SingularAttribute<Lieu, Long> id;
    public static volatile SingularAttribute<Lieu, String> adresse;
    public static volatile SingularAttribute<Lieu, Double> longitude;
    public static volatile SingularAttribute<Lieu, Double> latitude;
    public static volatile SingularAttribute<Lieu, String> type;
    public static volatile SingularAttribute<Lieu, String> denomination;

}