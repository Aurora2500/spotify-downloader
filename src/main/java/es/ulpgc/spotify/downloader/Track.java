package es.ulpgc.spotify.downloader;

import java.util.Set;

public class Track {
    private final String id;
    private final String title;
    private final int duration;
    private final boolean explicit;

    private final Set<String> artists;
    private final String album;

    public Track(String id, String title, int duration, boolean explicit, Set<String> artists, String album) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.explicit = explicit;

        this.artists = artists;
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

    public Set<String> artists() {
        return artists;
    }

    public String album() {
        return album;
    }
}
