package pos.Playlist.Service;

import org.springframework.stereotype.Service;
import pos.Playlist.model.Playlist;
import pos.Playlist.repository.PlaylistRepository;

import java.util.List;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;


    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public Playlist findPlaylistById(String id){
        return playlistRepository.findPlaylistById(id);
    }

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist getPlaylistByName(String name) {
        return playlistRepository.findByName(name);
    }

    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public Playlist updatePlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(String id) {
        playlistRepository.deleteById(id);
    }
}
