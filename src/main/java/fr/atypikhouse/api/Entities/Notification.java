package fr.atypikhouse.api.Entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private User user;

    @NotNull
    private String message;

    @NotNull
    private Date date;

    public Notification() {
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    @Override
    public String toString(){
        return "Noticiation {" +
                "id =" + id +
                ",user =" + user +
                ",message ='" + message + '\'' +
                ",date ='" + date + '\'' +
                '}';
    }
}