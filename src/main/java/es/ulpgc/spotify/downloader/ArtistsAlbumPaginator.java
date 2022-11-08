package es.ulpgc.spotify.downloader;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;

public class ArtistsAlbumPaginator extends NumberPaginator<Album> {

	public ArtistsAlbumPaginator(String artistId) {
		super("/artists/" + artistId + "/albums", 50, 0, null);
	}

	@Override
	protected Observable<Album> interpretResponse(String response) {
		JsonObject object = SpotifyGson.getInstance().fromJson(response, JsonObject.class);
		return Observable.fromIterable(SpotifyGson.getInstance().fromJson(object.getAsJsonArray("items"), new TypeToken<List<Album>>() {
		}.getType()));
	}
}
