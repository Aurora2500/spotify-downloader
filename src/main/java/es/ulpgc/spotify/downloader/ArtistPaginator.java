package es.ulpgc.spotify.downloader;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;

public class ArtistPaginator extends ListPaginator<Artist> {
	public ArtistPaginator(Observable<String> ids) {
		super("/artists", 50, ids);
	}

	@Override
	protected Observable<Artist> interpretResponse(String response) {
		Gson gson = SpotifyGson.getInstance();
		JsonArray artists = gson.fromJson(response, JsonObject.class).getAsJsonArray("artists");
		return Observable.fromIterable(gson.fromJson(artists, new TypeToken<List<Artist>>(){}.getType()));
	}
}
