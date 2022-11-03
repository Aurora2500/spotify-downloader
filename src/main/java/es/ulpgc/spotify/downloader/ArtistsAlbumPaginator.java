package es.ulpgc.spotify.downloader;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ArtistsAlbumPaginator extends NumberPaginator<Album> {

	public ArtistsAlbumPaginator(SpotifyAccessor spotify, String artistId) {
		super(spotify, "/artists/" + artistId + "/albums", null);
	}

	@Override
	protected List<Album> interpretResponse(String response) {
		JsonObject object = SpotifyGson.getInstance().fromJson(response, JsonObject.class);
		if (this.total == null) {
			this.total = object.get("total").getAsInt();
		}
		return SpotifyGson.getInstance().fromJson(object.getAsJsonArray("items"), new TypeToken<List<Album>>() {
		}.getType());
	}
}
