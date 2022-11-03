package es.ulpgc.spotify.downloader;

import java.util.Map;

public abstract class NumberPaginator<T> extends Paginator<T>{
	protected NumberPaginator(SpotifyAccessor spotify, String endpoint, int limit, int offset, Integer total) {
				super(spotify, endpoint, limit, offset, total);
		}

		protected NumberPaginator(SpotifyAccessor spotify, String endpoint, Integer total) {
			super(spotify, endpoint, 50, 0, total);
		}

		@Override
		protected String getNextResponse(int offset, int limit) throws Exception {
			return spotify.get(endpoint, Map.of("limit", String.valueOf(limit), "offset", String.valueOf(offset)));
		}
}
