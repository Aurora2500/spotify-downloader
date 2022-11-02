package es.ulpgc.spotify.downloader;

import java.util.ArrayList;
import java.util.List;

public class Track {
    private final String id;
    private final String title;
    private final int duration;
    private final boolean explicit;

    private final List<String> artists;
    private final String album;

    public Track(String id, String title, int duration, boolean explicit, String album) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.explicit = explicit;

        artists = new ArrayList<>();
        this.album = album;
    }

    public String id() {
        return id;
    }

    public String title() {
        return title;
    }

    public int duration() {
        return duration;
    }

    public boolean explicit() {
        return explicit;
    }

    public List<String> artists() {
        return artists;
    }

    public String album() {
        return album;
    }
}
