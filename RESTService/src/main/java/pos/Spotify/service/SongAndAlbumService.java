package pos.Spotify.service;

import pos.Spotify.model.SongAndAlbum;
import pos.Spotify.repository.SongAndAlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SongAndAlbumService {

    @Autowired
    private SongAndAlbumRepository repository;

    public SongAndAlbum save(SongAndAlbum songAndAlbum){
        return repository.save(songAndAlbum);
    }

    public List<SongAndAlbum> getAllSongsAndAlbums(){
        return (List<SongAndAlbum>) repository.findAll();
    }

    public void delete(SongAndAlbum songAndAlbum){
        repository.delete(songAndAlbum);
    }

    public SongAndAlbum findById(Integer id){
        return repository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }


    public SongAndAlbum findOneByName(String name) {
        SongAndAlbum songAndAlbum = repository.getByName(name);
        if(songAndAlbum!=null){
            return songAndAlbum;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }



}
