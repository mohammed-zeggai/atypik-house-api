package fr.atypikhouse.api.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Location location;

    private String commentaire;
    private Date date_ajout;
    private Date date_modification;

    public Commentaire() {
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Location getLocation() { return location; }

    public void setLocation(Location location) { this.location = location; }

    public String getCommentaire() { return commentaire; }

    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }

    public Date getDate_ajout() { return date_ajout; }

    public void setDate_ajout(Date date_ajout) { this.date_ajout = date_ajout; }

    public Date getDate_modification() { return date_modification; }

    public void setDate_modification(Date date_modification) { this.date_modification = date_modification; }

    @Override
    public String toString(){
        return "Commentaire {" +
                "id =" + id +
                ",user =" + user +
                ",location ='" + location +
                ",date_ajout ='" + date_ajout + '\'' +
                ",date_modification ='" + date_modification + '\'' +
                '}';
    }
}