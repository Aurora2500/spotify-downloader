package es.ulpgc.spotify.downloader;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;

public class SpotifyGraphGenerator {

	public static @NonNull Observable<SpotifyGraphFragment> generateGraph(List<String> id) {
		Observable<Artist> artist = Observable.just(new Artist(id, "name", 0, 0));

		return null;
	}

	private static Observable<Artist> artists(List<String> ids) {
		return ;
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
