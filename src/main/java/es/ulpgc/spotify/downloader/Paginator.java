package es.ulpgc.spotify.downloader;

import io.reactivex.rxjava3.core.Observable;

import java.util.List;

public abstract class Paginator<T> {
	protected final String endpoint;
	protected final int limit;
	protected int offset;
	// This isn't final because sometimes we don't know the total number of items until we start fetching them.
	// A null value represents that it is unknown.
	protected Integer total;

	protected Paginator(String endpoint, int limit, int offset, Integer total) {
		this.endpoint = endpoint;
		this.limit = limit;
	}


	rotected abstract Observable<String> getNextResponse(int offset, int limit) throws Exception;
	protected abstract Observable<T> interpretResponse(Observable<String> response);
}
