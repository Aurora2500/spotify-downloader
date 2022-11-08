package es.ulpgc.spotify.downloader;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;

public class TrackPaginator extends ListPaginator<Track> {
	public TrackPaginator(Observable<String> trackIds) {
		super("/tracks", 50, trackIds);
	}

	@Override
	protected Observable<Track> interpretResponse(String response) {
		JsonObject object = SpotifyGson.getInstance().fromJson(response, JsonObject.class);
		return Observable.fromIterable(SpotifyGson.getInstance().fromJson(object.getAsJsonArray("tracks"), new TypeToken<List<Track>>() {
		}.getType()));
	}
}
