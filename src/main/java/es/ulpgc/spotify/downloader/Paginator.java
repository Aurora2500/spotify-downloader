package es.ulpgc.spotify.downloader;

import java.util.List;

public abstract class Paginator<T> {
	protected final SpotifyAccessor spotify;
	protected final String endpoint;
	protected final int limit;
	protected int offset;
	// This isn't final because sometimes we don't know the total number of items until we start fetching them.
	// A null value represents that it is unknown.
	protected Integer total;

	protected Paginator(SpotifyAccessor spotify, String endpoint, int limit, int offset, Integer total) {
		this.spotify = spotify;
		this.endpoint = endpoint;
		this.limit = limit;
		this.offset = offset;
		this.total = total;
	}

	public List<T> next() throws Exception {
		if(total != null && offset >= total) {
			return List.of();
		}
		int currentLimit = total == null ? limit : Math.min(limit, total - offset);
		String response = getNextResponse(offset, currentLimit);
		offset += currentLimit;
		return interpretResponse(response);
	};

	public boolean hasNext() {
		return total == null || offset < total;
	}

	protected abstract String getNextResponse(int offset, int limit) throws Exception;
	protected abstract List<T> interpretResponse(String response);
}
