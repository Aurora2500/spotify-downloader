package es.ulpgc.spotify.downloader;

import io.reactivex.rxjava3.core.Observable;

import java.util.Map;

public abstract class ListPaginator<T> extends Paginator<T> {
	private final Observable<String> ids;

	protected ListPaginator(String endpoint, int limit, Observable<String> ids) {
		super(endpoint, limit);
		this.ids = ids;
	}

	@Override
	protected Observable<String> getResponses() {
		return ids.buffer(this.limit).flatMap(bufferedIds -> SpotifyAccessor.getInstance().get(this.endpoint, Map.of("ids", String.join(",", bufferedIds))));
	}
}
