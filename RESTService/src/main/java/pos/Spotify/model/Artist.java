package pos.Spotify.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Boolean activity;


    //Many to many
    @ManyToMany
    @JoinTable(
            name = "Artist_SongAlbum",
            joinColumns = @JoinColumn(name = "artistId"),
            inverseJoinColumns = @JoinColumn(name = "songalbumId"))
    Set<SongAndAlbum> artistSet;


    //Constructor
    public Artist(){}
    public Artist(int id, String name, Boolean activity) {
        this.id = id;
        this.name = name;
        this.activity = activity;
    }

    //Getter and setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActivity() {
        return activity;
    }

    public void setActivity(Boolean activity) {
        this.activity = activity;
    }
}
