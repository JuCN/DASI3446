package metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
/**
 *
 * @author cguichon
 */
@Entity
public class Evenement implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    private String moment;
    @ManyToOne
    private Lieu lieu; 
    private Float PAF;
    @ManyToOne
    private Activite activite;
    @OneToMany
    private List<Adherent> adherents;

    public Evenement() {
        this.moment=null;
    }

    public Evenement(Date date, String moment, Activite activite, List<Adherent> adherents) {
        this.date = date;
        this.moment = moment;
        this.activite = activite;
        this.adherents = adherents;
        this.PAF=0F;
        this.lieu=null;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getMoment() {
        return moment;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public Float getPAF() {
        return PAF;
    }

    public Activite getActivite() {
        return activite;
    }

    public List<Adherent> getAdherents() {
        return adherents;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMoment(String moment) {
        this.moment = moment;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public void setPAF(Float PAF) {
        this.PAF = PAF;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public void setAdherents(List<Adherent> adherents) {
        this.adherents = adherents;
    }
    public void testCommit(){
    
    }
    
    @Override
    public String toString() {
        return "Evenement{" + "id=" + id + ", Date=" + date + ", Moment=" + moment + ", lieu=" +lieu+", PAF=" + PAF + ", adherents :"+adherents+'}';
    }
    
}
