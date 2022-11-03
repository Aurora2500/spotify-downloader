package es.ulpgc.spotify.downloader;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ArtistPaginator extends ListPaginator<Artist> {
	public ArtistPaginator(SpotifyAccessor spotify, List<String> ids) {
		super(spotify, "/artists", 50, ids);
	}

	@Override
	protected List<Artist> interpretResponse(String response) {
		Gson gson = SpotifyGson.getInstance();
		JsonArray artists = gson.fromJson(response, JsonObject.class).getAsJsonArray("artists");
		return gson.fromJson(artists, new TypeToken<List<Artist>>(){}.getType());
	}
}
