package es.ulpgc.spotify.downloader;

import com.google.gson.JsonObject;
import io.reactivex.rxjava3.core.Observable;

import java.util.Map;

public abstract class NumberPaginator<T> extends Paginator<T>{
	private final int offset;
	private final Integer total;

	protected NumberPaginator(String endpoint, int limit, int offset, Integer total) {
				super(endpoint, limit);
				this.offset = offset;
				this.total = total;
		}

		private Observable<String> singleResponse(int offset, int limit) {
				return SpotifyAccessor.getInstance().get(this.endpoint, Map.of("offset", String.valueOf(offset), "limit", String.valueOf(limit)));
		}

		private Observable<String> manyResponses(int offset, int limit, int total) {
			int remaining = total - offset;
			int remainingPages = (int) Math.ceil((double)remaining / limit);
			return Observable.range(0, remainingPages).flatMap(page -> singleResponse(offset + page * limit, page == remainingPages - 1 && remaining % limit != 0 ? remaining % limit : limit));
		}

		@Override
		protected Observable<String> getResponses() {
			if(this.total == null) {
				// get a single response, get the total and then get the rest
				Observable<String> firstResponse = SpotifyAccessor.getInstance().get(this.endpoint, Map.of("offset", String.valueOf(this.offset), "limit", String.valueOf(this.limit)));
				return firstResponse.flatMap(response -> {
					int total = SpotifyGson.getInstance().fromJson(response, JsonObject.class).get("total").getAsInt();
					Observable<String> restResponses = total > this.limit ? manyResponses(this.offset + this.limit, this.limit, total) : Observable.empty();
					return Observable.concat(Observable.just(response), restResponses);
				});
			} else {
				// get all the responses at once
				return manyResponses(this.offset, this.limit, this.total);
			}
		}
}
