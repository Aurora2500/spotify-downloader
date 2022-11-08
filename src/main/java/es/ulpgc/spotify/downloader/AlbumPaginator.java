package es.ulpgc.spotify.downloader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.reactivex.rxjava3.core.Observable;

import java.util.ArrayList;
import java.util.List;

public class AlbumPaginator extends ListPaginator<PartialAlbumLinks> {

	public AlbumPaginator(Observable<String> ids) {
		super("/albums", 20, ids);
	}

	@Override
	protected Observable<PartialAlbumLinks> interpretResponse(String response) {
		JsonObject object = SpotifyGson.getInstance().fromJson(response, JsonObject.class);
		JsonArray items = object.getAsJsonArray("albums");
		List<PartialAlbumLinks> albums = new ArrayList<>();
		items.forEach(item -> {
			JsonObject itemObject = item.getAsJsonObject();
			String id = itemObject.get("id").getAsString();
			List<String> artists = new ArrayList<>();
			itemObject.get("artists").getAsJsonArray().forEach(artist -> artists.add(artist.getAsJsonObject().get("id").getAsString()) );
			int totalTracks = itemObject.get("total_tracks").getAsInt();
			List<String> tracks = new ArrayList<>();
			itemObject.getAsJsonObject("tracks").getAsJsonArray("items").forEach(track -> tracks.add(track.getAsJsonObject().get("id").getAsString()) );
			Observable<String> trackIds = Observable.fromIterable(tracks);
			if(tracks.size() < totalTracks) {
				trackIds = trackIds.concatWith(new AlbumsTracksPaginator(id, tracks.size(), totalTracks).get());
			}
			albums.add(new PartialAlbumLinks(id, artists, trackIds));
		});
		return Observable.fromIterable(albums);
	}
}
