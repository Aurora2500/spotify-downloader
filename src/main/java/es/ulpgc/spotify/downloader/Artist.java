package es.ulpgc.spotify.downloader;

import java.util.HashSet;
import java.util.Set;

public class Artist implements SpotifyGraphFragment {
    private final String id;
    private final String name;
    private final int followers;
    private final int popularity;

    private final Set<String> albums;
    private final Set<String> tracks;

    public Artist(String id, String name, int followers, int popularity) {
        this.id = id;
        this.name = name;
        this.followers = followers;
        this.popularity = popularity;
        this.albums = new HashSet<>();
        this.tracks = new HashSet<>();
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public int followers() {
        return followers;
    }

    public int popularity() {
        return popularity;
    }

    public Set<String> albums() {
        return albums;
    }

    public Set<String> tracks() {
        return tracks;
    }

    @Override
    public void accept(StoreVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
