package pos.Playlist.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pos.Playlist.model.Playlist;

@Repository
public interface PlaylistRepository extends MongoRepository<Playlist, String> {
    Playlist findByName(String name);

    @Query("{id:'?0'}")
    Playlist findPlaylistById(String id);

}
