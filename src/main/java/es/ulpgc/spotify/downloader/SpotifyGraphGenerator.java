package es.ulpgc.spotify.downloader;

import java.util.List;

public class SpotifyGraphGenerator {
	private final SpotifyAccessor accessor;

	public SpotifyGraphGenerator(SpotifyAccessor accessor) {
		this.accessor = accessor;
	}

	public SpotifyGraph generateGraph(List<String> artists) throws Exception {
		SpotifyGraph graph = new SpotifyGraph();
		Paginator<Artist> artistPaginator = new ArtistPaginator(accessor, artists);
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
		//Paginator<Track> trackPaginator = new TrackPaginator(accessor, trackIds);
		return graph;
	}
}
