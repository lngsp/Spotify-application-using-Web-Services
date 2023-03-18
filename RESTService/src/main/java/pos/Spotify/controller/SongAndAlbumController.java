package pos.Spotify.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pos.Spotify.model.SongAndAlbum;
import pos.Spotify.service.SongAndAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/songs")
public class SongAndAlbumController {

    @Autowired
    private SongAndAlbumService songAndAlbumService;


    //-----------------GET-----------------
    @GetMapping("/")
    public List<SongAndAlbum> getAllSongsAndAlbums(){
        return songAndAlbumService.getAllSongsAndAlbums();
    }

    //http://localhost:8083/api/songs/1
    @GetMapping("/{id}")
    public ResponseEntity<SongAndAlbum> getById(@PathVariable("id") Integer id){
        SongAndAlbum songAndAlbum = songAndAlbumService.findById(id);
        if(songAndAlbum == null){
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(songAndAlbum, new HttpHeaders(), HttpStatus.OK);
        }
    }

    //http://localhost:8083/api/songs/name/?name=This Is It
    @GetMapping("/name/")
    public ResponseEntity<SongAndAlbum> getSongAndAlbumByName(@Param("name") String name) {
        SongAndAlbum songAndAlbum =  songAndAlbumService.findOneByName(name);
        if(songAndAlbum == null){
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(songAndAlbum, new HttpHeaders(), HttpStatus.OK);
        }
    }


    //-----------------POST-----------------
    @PostMapping("/") //add new songs
    public ResponseEntity<SongAndAlbum> newSongOrAlbum(@RequestBody SongAndAlbum newSongOrAlbum) {
        return new ResponseEntity<>(songAndAlbumService.save(newSongOrAlbum), new HttpHeaders(), HttpStatus.OK);
    }

    //http://localhost:8083/api/songs/albumParent/9?albumParent=10
    @PostMapping("/albumParent/{id}")
    public ResponseEntity<SongAndAlbum> updateAlbumParent(@PathVariable("id") Integer id,
                                   @RequestParam(required = true, name = "albumParent") Integer albumParent) {

        SongAndAlbum songAndAlbum =  songAndAlbumService.findById(id);
        if(songAndAlbum == null){
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        else{
            songAndAlbum.setAlbumParent(albumParent);
            songAndAlbumService.save(songAndAlbum);
            return new ResponseEntity<>(songAndAlbum, new HttpHeaders(), HttpStatus.OK);
        }

    }


    //-----------------PUT-----------------
    @PutMapping("/{id}") //inlocuire completa
    public ResponseEntity<String> replaceSongOrAlbum(@RequestBody SongAndAlbum newSongOrAlbum, @PathVariable Integer id) {

        SongAndAlbum songAndAlbum =  songAndAlbumService.findById(id);
        if(songAndAlbum!=null){
            songAndAlbum.setName(newSongOrAlbum.getName());
            songAndAlbum.setGenre(newSongOrAlbum.getGenre());
            songAndAlbum.setType(newSongOrAlbum.getType());
            songAndAlbum.setYear(newSongOrAlbum.getYear());
            songAndAlbum.setAlbumParent(newSongOrAlbum.getAlbumParent());
            songAndAlbumService.save(songAndAlbum);
            return new ResponseEntity<>("The id is already in use! Replaced the old song!", new HttpHeaders(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("The id doesn't exist!", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }



    //-----------------DELETE-----------------

    @DeleteMapping("/{id}")   //nu se decrementeaza id-ul
    public ResponseEntity<String> deleteSongorAlbum(@PathVariable Integer id) {
        SongAndAlbum songAndAlbum = songAndAlbumService.findById(id);
        if(songAndAlbum == null){
            return new ResponseEntity<>("The song/album doesn't exist!", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        else{
            songAndAlbumService.delete(songAndAlbum);
            return new ResponseEntity<>("The song has been deleted!", new HttpHeaders(), HttpStatus.OK);
        }
    }
}
