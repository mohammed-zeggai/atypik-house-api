package fr.atypikhouse.api.Entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Location location;

    @NotNull
    private Date date;

    public Reservation() {
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Location getLocation() { return location; }

    public void setLocation(Location location) { this.location = location; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    @Override
    public String toString(){
        return "Reservation {" +
                "id =" + id +
                ",user =" + user +
                ",location ='" + location +
                ",date ='" + date + '\'' +
                '}';
    }
}