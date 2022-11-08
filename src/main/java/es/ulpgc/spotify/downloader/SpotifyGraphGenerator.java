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


	/*public SpotifyGraph generateGraph(List<String> artists) throws Exception {
		SpotifyGraph graph = new SpotifyGraph();
		Paginator<Artist> artistPaginator = new ArtistPaginator(artists);
		while (artistPaginator.hasNext()) {
			List<Artist> artistList = artistPaginator.next();
			for (Artist artist : artistList) {
				graph.artists().put(artist.id(), artist);
			}
			graph.artists();
		}
		for (String artistId : artists) {
			ArtistsAlbumPaginator albumPaginator = new ArtistsAlbumPaginator(accessor, artistId);
			while (albumPaginator.hasNext()) {
				List<Album> albumList = albumPaginator.next();
				for (Album album : albumList) {
					graph.albums().put(album.id(), album);
				}
			}
		}
		List<String> albumIds = new ArrayList<>(graph.albums().keySet());
		AlbumPaginator albumPaginator = new AlbumPaginator(accessor, albumIds);
		Set<AlbumLinks> albumLinks = new HashSet<>();
		while (albumPaginator.hasNext()) {
			List<PartialAlbumLinks> partialAlbumLinks = albumPaginator.next();
			for (PartialAlbumLinks partialAlbumLink : partialAlbumLinks) {
				albumLinks.add(partialAlbumLink.generate(accessor, artists));
			}
		}
		Set<String> trackIds = new HashSet<>();
		for (AlbumLinks albumLink : albumLinks) {
			graph.albums().get(albumLink.id()).artists().addAll(albumLink.artistIds());
			trackIds.addAll(albumLink.trackIds());
		}
		List<String> trackIdList = new ArrayList<>(trackIds);
		Paginator<Track> trackPaginator = new TrackPaginator(accessor, trackIdList);
		while (trackPaginator.hasNext()) {
			List<Track> trackList = trackPaginator.next();
			for (Track track : trackList) {

				graph.tracks().put(track.id(), track);
				track.artists().retainAll(artists);
				for (String artistId : track.artists()) {
					graph.artists().get(artistId).tracks().add(track.id());
				}
			}
		}
		return graph;
	}*/
}
