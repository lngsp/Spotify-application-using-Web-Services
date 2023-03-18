package pos.Playlist.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "playlists")
public class Playlist {
    @Id
    private String id;
    private String name;
    private List<String> songAndAlbums;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getSongAndAlbums() {
        return songAndAlbums;
    }

    public void setSongAndAlbums(List<String> songAndAlbums) {
        this.songAndAlbums = songAndAlbums;
    }
}

