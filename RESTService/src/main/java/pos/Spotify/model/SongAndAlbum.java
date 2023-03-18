package pos.Spotify.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class SongAndAlbum {
//    public enum MusicGenre {Pop, Rap, Kpop, Rock, Jazz};
//    public enum ElementType {Song, Album, Single};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String genre;
    private int year;
    private String type;
    private int albumParent;

    //Many to many
    @ManyToMany(mappedBy = "artistSet")
    Set<Artist> songandalbumSet;

    //Constructor
    public SongAndAlbum(){}

    public SongAndAlbum(int id, String name, String genre, int year, String type, int albumParent) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.year = year;
        this.type = type;
        this.albumParent = albumParent;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAlbumParent() {
        return albumParent;
    }

    public void setAlbumParent(int albumParent) {
        this.albumParent = albumParent;
    }
}
