package pos.Spotify.repository;

import pos.Spotify.model.SongAndAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SongAndAlbumRepository extends JpaRepository<SongAndAlbum, Integer> {
    @Query(value = "SELECT * FROM song_and_album WHERE name=?1",nativeQuery = true)
    SongAndAlbum getByName(String name);

}
