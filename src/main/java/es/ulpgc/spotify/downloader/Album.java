package es.ulpgc.spotify.downloader;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private final String id;
    private final String title;
    private final String releaseDate;
    private final String releaseDatePrecision;
    private final String type;

    private final List<String> artists;
    private final List<String> tracks;

    public Album(String id, String title, String releaseDate, String releaseDatePrecision, String type) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.releaseDatePrecision = releaseDatePrecision;
        this.type = type;

        artists = new ArrayList<>();
        tracks = new ArrayList<>();
    }

    public String id() {
        return id;
    }

    public String title() {
        return title;
    }

    public String releaseDate() {
        return releaseDate;
    }

    public String releaseDatePrecision() {
        return releaseDatePrecision;
    }

    public String type() {
        return type;
    }

    public List<String> artists() {
        return artists;
    }

    public List<String> tracks() {
        return tracks;
    }
}
