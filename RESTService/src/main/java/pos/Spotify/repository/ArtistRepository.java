package pos.Spotify.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pos.Spotify.model.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {
    @Query(value = "SELECT * FROM artist WHERE name=:name",nativeQuery = true)
    Artist getByName(String name);

    @Query(value = "SELECT a.name as \"Artist name\", sa.name \"Album name\", album_parent, genre, type, year " +
            "FROM song_and_album sa, artist a, artist_song_album asa " +
            "WHERE  a.id = ?1 AND asa.artist_id = a.id AND sa.id = asa.songalbum_id"
            ,nativeQuery = true)
    Object[] findArtistSongs(Integer id);


    @Query(value = "INSERT INTO artist_song_album VALUES(?1, ?2)",nativeQuery = true)
    void addSongToArtist(Integer artist_id, Integer song_id);

    @Query(value = "SELECT sa.name FROM artist a, song_and_album sa, artist_song_album asa\n" +
            "WHERE a.id=?1 AND sa.id=?2 AND asa.artist_id=?1 AND asa.songalbum_id=?2",nativeQuery = true)
    String checkIfArtistHasSong(Integer artist_id, Integer song_id);
}
