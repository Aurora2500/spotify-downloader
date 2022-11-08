package es.ulpgc.spotify.downloader;

import io.reactivex.rxjava3.core.Observable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PartialAlbumLinks {
    private final String id;
    // A set is used to only retain artists that only appear in the graph.
    private final Set<String> artistIds;
    private final Observable<String> trackIds;

    public PartialAlbumLinks(String id, Collection<String> artistIds, Observable<String> trackIds) {
        this.id = id;
        this.artistIds = new HashSet<>(artistIds);
        this.trackIds = trackIds;
    }

    public String id() {
        return id;
    }
    public AlbumLinks generate(Collection<String> artists) {
        artistIds.retainAll(artists);

        return new AlbumLinks(id, artistIds, trackIds);
    }
}
