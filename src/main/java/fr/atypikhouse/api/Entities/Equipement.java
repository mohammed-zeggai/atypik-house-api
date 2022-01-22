package fr.atypikhouse.api.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private Location location;

    @NotNull
    private String titre;

    @NotNull
    private String description;

    public Equipement() {
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    @JsonIgnore
    public Location getLocation() { return location; }

    public void setLocation(Location location) { this.location = location; }

    public String getTitre() { return titre; }

    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString(){
        return "Equipement {" +
                "id =" + id +
                ",location =" + location +
                ",titre ='" + titre + '\'' +
                ",description ='" + description + '\'' +
                '}';
    }
}