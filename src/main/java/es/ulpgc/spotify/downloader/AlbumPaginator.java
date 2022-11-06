package es.ulpgc.spotify.downloader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class AlbumPaginator extends ListPaginator<PartialAlbumLinks> {

	public AlbumPaginator(SpotifyAccessor spotify, List<String> ids) {
		super(spotify, "/albums", 20, ids);
	}

	@Override
	protected List<PartialAlbumLinks> interpretResponse(String response) {
		JsonObject object = SpotifyGson.getInstance().fromJson(response, JsonObject.class);
		JsonArray albums = object.getAsJsonArray("albums");
		List<PartialAlbumLinks> al = new ArrayList<>();
		albums.forEach(item -> {
			JsonObject album = item.getAsJsonObject();
			String id = album.get("id").getAsString();
			List<String> authors = new ArrayList<>();
			album.getAsJsonArray("artists").forEach(artist -> {
				String artistId = artist.getAsJsonObject().get("id").getAsString();
				authors.add(artistId);
			});
			int trackNumber = album.get("total_tracks").getAsInt();
			List<String> tracks = new ArrayList<>();
			album.getAsJsonObject("tracks").getAsJsonArray("items").forEach(track -> {
				String trackId = track.getAsJsonObject().get("id").getAsString();
				tracks.add(trackId);
			});
			al.add(new PartialAlbumLinks(id, authors, tracks, trackNumber));
		});
		return al;
	}
}
