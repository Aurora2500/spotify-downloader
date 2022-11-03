package es.ulpgc.spotify.downloader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;

public final class SpotifyGson {
	private static Gson instance = null;

	public static Gson getInstance() {
		if(instance == null) {
			instance = createGson();
		}
		return instance;
	}

	private static Gson createGson() {
		GsonBuilder builder = new GsonBuilder();

		builder.registerTypeAdapter(Artist.class, (JsonDeserializer<Artist>) (json, typeOfT, context) -> {
			JsonObject jsonObject = json.getAsJsonObject();
			String id = jsonObject.get("id").getAsString();
			String name = jsonObject.get("name").getAsString();
			int followers = jsonObject.get("followers").getAsJsonObject().get("total").getAsInt();
			int popularity = jsonObject.get("popularity").getAsInt();
			return new Artist(id, name, followers, popularity);
		});

		builder.registerTypeAdapter(Album.class, (JsonDeserializer<Album>) (json, typeOfT, context) -> {
			JsonObject jsonObject = json.getAsJsonObject();
			String id = jsonObject.get("id").getAsString();
			String name = jsonObject.get("name").getAsString();
			String releaseDate = jsonObject.get("release_date").getAsString();
			String releaseDatePrecision = jsonObject.get("release_date").getAsString();
			String type = jsonObject.get("type").getAsString();
			return new Album(id, name, releaseDate, releaseDatePrecision, type);
		});

		builder.registerTypeAdapter(Track.class, (JsonDeserializer<Track>) (json, typeOfT, context) -> {
			JsonObject jsonObject = json.getAsJsonObject();
			String id = jsonObject.get("id").getAsString();
			String name = jsonObject.get("name").getAsString();
			int duration = jsonObject.get("duration_ms").getAsInt();
			boolean explicit = jsonObject.get("explicit").getAsBoolean();
			String album = jsonObject.get("album").getAsJsonObject().get("id").getAsString();
			return new Track(id, name, duration, explicit, album);
		});

		return builder.create();
	}
}
