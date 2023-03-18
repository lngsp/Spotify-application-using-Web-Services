package pos.Spotify.service;

import pos.Spotify.model.Artist;
import pos.Spotify.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository repository;

    public Artist save(Artist artist){
        return repository.save(artist);
    }

    public List<Artist> getAllArtists(){
        return (List<Artist>) repository.findAll();
    }

    public void delete(Artist artist){
        repository.delete(artist);
    }


    public Artist findById(Integer id){
        return repository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    public Artist findOneByName(String name) {
        Artist artist = repository.getByName(name);
        if(artist!=null){
            return artist;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    public Object[] findArtistSongs(Integer id){
        return repository.findArtistSongs(id);
    }


    public void addSongToArtist(Integer artist_id, Integer song_id){
        repository.addSongToArtist(artist_id, song_id);
    }

    public boolean checkIfArtistHasSong(Integer artist_id, Integer song_id){
        String result = repository.checkIfArtistHasSong(artist_id, song_id);
        if(result != null){
            return true;
        }
        else{
            return false;
        }
    }
}
