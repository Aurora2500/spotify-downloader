package es.ulpgc.spotify.downloader;

import java.util.ArrayList;
import java.util.List;

public class Artist {
    private final String id;
    private final String name;
    private final int followers;
    private final int popularity;

    private final List<String> albums;
    private final List<String> tracks;

    public Artist(String id, String name, int followers, int popularity) {
        this.id = id;
        this.name = name;
        this.followers = followers;
        this.popularity = popularity;
        this.albums = new ArrayList<>();
        this.tracks = new ArrayList<>();
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

    public List<String> albums() {
        return albums;
    }

    public List<String> tracks() {
        return tracks;
    }
}
