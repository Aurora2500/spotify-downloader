package es.ulpgc.spotify.downloader;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PartialAlbumLinks {
    private final String id;
    // A set is used to only retain artists that only appear in the graph.
    private final Set<String> artistIds;
    private final Collection<String> trackIds;
    private final int trackSize;

    public PartialAlbumLinks(String id, Collection<String> artistIds, Collection<String> trackIds, int trackSize) {
        this.id = id;
        this.artistIds = new HashSet<>(artistIds);
        this.trackIds = trackIds;
        this.trackSize = trackSize;
    }

    public String id() {
        return id;
    }

    public AlbumLinks generate(SpotifyAccessor spotify, Collection<String> artists) throws Exception {
        artistIds.retainAll(artists);
        if (trackSize > 50) {
            AlbumsTracksPaginator paginator = new AlbumsTracksPaginator(spotify, id, trackIds.size(), trackSize);
            while(paginator.hasNext()) {
                List<String> tracks = paginator.next();
                trackIds.addAll(tracks);
            }
        }
        return new AlbumLinks(id, artistIds, trackIds);
    }
}
