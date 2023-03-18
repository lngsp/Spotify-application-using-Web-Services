package pos.Spotify.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pos.Spotify.model.Artist;
import pos.Spotify.model.SongAndAlbum;
import pos.Spotify.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import pos.Spotify.service.SongAndAlbumService;

import java.util.List;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {
    @Autowired
    private ArtistService artistService;

    @Autowired
    private SongAndAlbumService songAndAlbumService;

    //-----------------GET-----------------
    //http://localhost:8083/api/artist/
    @GetMapping("/")
    public List<Artist> getAllArtists(){
        return artistService.getAllArtists();
    }

    //http://localhost:8083/api/artist/2
    @GetMapping("/{id}")
    public ResponseEntity<Artist> getById(@PathVariable("id") Integer id){
        Artist artistList = artistService.findById(id);
        if(artistList == null){
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(artistList, new HttpHeaders(), HttpStatus.OK);
        }
    }

    //http://localhost:8083/api/artist/name/?name=The%20Weeknd
    @GetMapping("/name/")
    public ResponseEntity<Artist> getByName(@Param("name") String name){
        Artist artist = artistService.findOneByName(name);;
        if(artist == null){
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(artist, new HttpHeaders(), HttpStatus.OK);
        }
    }

    //http://localhost:8083/api/artist/2/songs/
    @GetMapping("/{id}/songs/")
    public ResponseEntity<Object[]> getArtistSongs(@PathVariable("id") Integer id) {
        Object[] songOfArtist = artistService.findArtistSongs(id);
        if(songOfArtist == null){
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(songOfArtist, new HttpHeaders(), HttpStatus.OK);
        }

    }

    //http://localhost:8083/api/artist/2/hasSong/3
    @GetMapping("/{id}/hasSong/{id_song}")
    public ResponseEntity<String> checkIfArtistHasSong(@PathVariable("id") Integer id, @PathVariable("id_song") Integer id_song) {
        boolean result = artistService.checkIfArtistHasSong(id, id_song);
        if(result == true){
            return new ResponseEntity<>("The artist sings the song!", new HttpHeaders(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("The artist does not sing the song", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }

    //-----------------POST----------------- POST is used to create a resource
    @PostMapping("/")
    public ResponseEntity<Artist> newArtist(@RequestBody Artist newArtist) {
        return new ResponseEntity<>(artistService.save(newArtist), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/{id}/addSong/{id_song}")
    public ResponseEntity<String> newSongToArtist(@PathVariable("id") Integer id, @PathVariable("id_song") Integer song_id){
        if(songAndAlbumService.findById(song_id) == null){
            return new ResponseEntity<>("The song doesn't exist in DB!", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        boolean check = artistService.checkIfArtistHasSong(id, song_id);
        if(check == true){
            return new ResponseEntity<>("The artist already has the song!", new HttpHeaders(), HttpStatus.CONFLICT);
        }
        else{
            artistService.addSongToArtist(id, song_id);
            return new ResponseEntity<>("The song has been added to the artist!", new HttpHeaders(), HttpStatus.OK);
        }
    }

    //-----------------PUT----------------- PUT is used to create or replace a resource
    @PutMapping("/{id}")
    public ResponseEntity<String> replaceArtist(@RequestBody Artist newArtist, @PathVariable Integer id) {

        Artist artist =  artistService.findById(id);
        if(artist!=null){
            artist.setName(newArtist.getName());
            artist.setActivity(newArtist.getActivity());
            artistService.save(artist);
            return new ResponseEntity<>("The id is already in use! Replaced the old artist!", new HttpHeaders(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("The id doesn't exist!", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }




    //-----------------DELETE-----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer id) {
        Artist artist = artistService.findById(id);
        if(artist == null){
            return new ResponseEntity<>("The artist doesn't exist!", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        else{
            artistService.delete(artist);
            return new ResponseEntity<>("The artist has been deleted!", new HttpHeaders(), HttpStatus.OK);
        }
    }
}
