package es.ulpgc.spotify.downloader;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import java.util.List;

public class SpotifyGraphGenerator {

	public static @NonNull Observable<SpotifyGraphFragment> generateGraph(List<String> ids) {
		// Artists
		Observable<Artist> artist = new ArtistPaginator(Observable.fromIterable(ids)).get();
		// Albums
		// ArtistsAlbumPaginator does not generate the links from the album to the artists.
		Observable<Album> albums = Observable.fromIterable(ids).flatMap(id -> new ArtistsAlbumPaginator(id).get()).distinct();
		Observable<AlbumLinks> albumLinks = new AlbumPaginator(albums.map(Album::id)).get().map(a -> a.generate(ids));
		Observable<Album> generatedAlbums = albums.zipWith(albumLinks, (a, l) -> {a.artists().addAll(l.artistIds()); assert a.id().equals(l.id());return a;});

		// Tracks
		Observable<String> trackIds = albumLinks.flatMap(AlbumLinks::trackIds);
		Observable<Track> tracks = new TrackPaginator(trackIds).get();

		return artist.map(a -> (SpotifyGraphFragment) a)
						.concatWith(generatedAlbums.map(a -> (SpotifyGraphFragment) a))
						.concatWith(tracks.map(t -> (SpotifyGraphFragment) t));
	}
}
