package es.ulpgc.spotify.downloader;

import java.util.List;

public class AlbumPaginator extends ListPaginator<String> {

	public AlbumPaginator(SpotifyAccessor spotify, List<String> ids) {
		super(spotify, "/albums", 20, ids);
	}

	@Override
	protected List<String> interpretResponse(String response) {
		// TODO
		return null;
	}
}
