package es.ulpgc.spotify.downloader;

import java.util.Set;

public class Track implements SpotifyGraphFragment {
    private final String id;
    private final String title;
    private final int duration;
    private final boolean explicit;
    private final int popularity;

    private final Set<String> artists;
    private final String album;

    public Track(String id, String title, int duration, boolean explicit, int popularity, Set<String> artists, String album) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.explicit = explicit;
        this.popularity = popularity;

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

    public int popularity() {
        return popularity;
    }

    @Override
    public void accept(StoreVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
