package es.ulpgc.spotify.downloader;

import io.reactivex.rxjava3.core.Observable;

public abstract class Paginator<T> {
	protected final String endpoint;
	protected final int limit;

	protected Paginator(String endpoint, int limit) {
		this.endpoint = endpoint;
		this.limit = limit;
	}

	public Observable<T> get() {
		return getResponses().flatMap(this::interpretResponse);
	}

	protected abstract Observable<String> getResponses();
	protected abstract Observable<T> interpretResponse(String response);
}
