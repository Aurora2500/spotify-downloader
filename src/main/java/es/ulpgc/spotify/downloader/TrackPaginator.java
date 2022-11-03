package es.ulpgc.spotify.downloader;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class TrackPaginator extends ListPaginator<Track> {
	public TrackPaginator(SpotifyAccessor accessor, List<String> trackIds) {
		super(accessor, "/tracks", 50, trackIds);
	}

	@Override
	protected List<Track> interpretResponse(String response) {
		JsonObject object = SpotifyGson.getInstance().fromJson(response, JsonObject.class);
		return SpotifyGson.getInstance().fromJson(object.getAsJsonArray("tracks"), new TypeToken<List<Track>>() {
		}.getType());
	}
}
