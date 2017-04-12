package metier.modele;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
/**
 *
 * @author cguichon
 */
@Entity
public class Demande implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Adherent adherent;
    @ManyToOne
    private Activite activite;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    private String moment;
    private boolean confirme;

    

    protected Demande() {
    }

    public Demande(Adherent adherent, Activite activite, String dateStr, String moment) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
        Date date=null;
        
        try {
            date = df.parse(dateStr);
        } catch (ParseException ex) {
            Logger.getLogger(Demande.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.adherent = adherent;
        this.activite = activite;
        this.date = date;
        this.moment = moment;
        this.confirme = false;
    }

    public Long getId() {
        return id;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public Activite getActivite() {
        return activite;
    }


    public Date getDate() {
        return date;
    }

    public String getMoment() {
        return moment;
    }

    public boolean isConfirme() {
        return confirme;
    }
    
    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMoment(String moment) {
        this.moment = moment;
    }

    public void setConfirme(boolean confirme) {
        this.confirme = confirme;
    }
    
    @Override
    public String toString() {
        return "Evenement{" + "id=" + id + ", Date=" + date + ", Moment=" + moment + ", activite=" + activite.getDenomination() + ", adherent=" + adherent.getNom() + '}';
    }
    
    
}
