package es.ulpgc.spotify.downloader;

import java.util.HashMap;
import java.util.Map;

public class SpotifyGraph {
    private final Map<String, Artist> artists;
    private final Map<String, Album> albums;
    private final Map<String, Track> tracks;

    public SpotifyGraph() {
        artists = new HashMap<>();
        albums = new HashMap<>();
        tracks = new HashMap<>();
    }

    public Map<String, Artist> artists() {
        return artists;
    }

    public Map<String, Album> albums() {
        return albums;
    }

    public Map<String, Track> tracks() {
        return tracks;
    }
}
