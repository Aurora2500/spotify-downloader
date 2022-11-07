package es.ulpgc.spotify.downloader;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.List;
import java.util.Map;

public abstract class ListPaginator<T> extends Paginator<T> {
	private final Observable<String> ids;

	protected ListPaginator(String endpoint, int limit) {
		super(endpoint, limit, 0, ids.size());
		this.ids = ids;
	}

	@Override
	protected Observable<String> getNextResponse(int offset, int limit) throws Exception {
		return SpotifyAccessor.getInstance().get(endpoint, Map.of("ids", String.join(",", ids.subList(offset, offset + limit))));
	}
}
