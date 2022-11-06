package es.ulpgc.spotify.downloader;

import java.util.Collection;
import java.util.Objects;

public class AlbumLinks {
    private final String id;
    private final Collection<String> artistIds;
    private final Collection<String> trackIds;

    public AlbumLinks(String id, Collection<String> authorIds, Collection<String> trackIds) {
        this.id = id;
        this.artistIds = authorIds;
        this.trackIds = trackIds;
    }

    public String id() {
        return id;
    }

    public Collection<String> artistIds() {
        return artistIds;
    }

    public Collection<String> trackIds() {
        return trackIds;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AlbumLinks)) {
            return false;
        }
        AlbumLinks albumLinks = (AlbumLinks) o;
        return Objects.equals(this.id, albumLinks.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
