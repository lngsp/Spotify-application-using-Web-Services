package pos.Playlist.Controller;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.Playlist.Service.PlaylistService;
import pos.Playlist.model.Playlist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }


    @GetMapping
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Playlist> getPlaylistByName(@PathVariable String name) {
        Playlist playlist = playlistService.getPlaylistByName(name);
        if(playlist == null){
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(playlist, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}/songs")   //afisarea tuturor melodiilor din playlist
    public List<String> getSongs(@PathVariable String id){
        Playlist playlist = playlistService.findPlaylistById(id);
        return playlist.getSongAndAlbums();
    }

    @PostMapping("/{id}/song/{name}")   //adaugarea unei melodii in playlist
    public ResponseEntity<Playlist> addSong(@PathVariable String id, @PathVariable String song_name){
        Playlist playlist = playlistService.findPlaylistById(id);
        if(playlist == null){
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8083/api/songs/name/?name=" + song_name);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            // handle response
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            List<String> listSongs = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                listSongs.add(line);
            }
            playlist.setSongAndAlbums(listSongs);
            return new ResponseEntity<>(playlist, new HttpHeaders(), HttpStatus.OK);
        } catch (IOException e) {
            // handle exception
            e.printStackTrace();
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(@RequestBody Playlist p) {
        Playlist playlist = playlistService.findPlaylistById(p.getId());
        if(playlist != null){
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.CONFLICT);
        }
        else{
            Playlist newPlaylist = playlistService.createPlaylist(p);
            return new ResponseEntity<>(newPlaylist, new HttpHeaders(), HttpStatus.OK);
        }

    }

    @PutMapping
    public ResponseEntity<Playlist> updatePlaylist(@RequestBody Playlist p) {
        Playlist playlist = playlistService.findPlaylistById(p.getId());
        if(playlist == null){
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.CONFLICT);
        }
        else {
            Playlist updatePlaylist = playlistService.updatePlaylist(playlist);
            return new ResponseEntity<>(updatePlaylist, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlaylist(@PathVariable String id) {
        Playlist playlist = playlistService.findPlaylistById(id);
        if(playlist == null){
            return new ResponseEntity<>("The playlist does't exist!", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        else {
            playlistService.deletePlaylist(id);
            return new ResponseEntity<>("Deleted the playlist!", new HttpHeaders(), HttpStatus.OK);
        }
    }
}

