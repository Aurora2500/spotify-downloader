package es.ulpgc.spotify.downloader;

import java.util.List;
import java.util.Map;

public abstract class ListPaginator<T> extends Paginator<T> {
	private final List<String> ids;

	protected ListPaginator(SpotifyAccessor spotify, String endpoint, int limit, List<String> ids) {
		super(spotify, endpoint, limit, 0, ids.size());
		this.ids = ids;
	}

	@Override
	protected String getNextResponse(int offset, int limit) throws Exception {
		return spotify.get(endpoint, Map.of("ids", String.join(",", ids.subList(offset, offset + limit))));
	}
}
