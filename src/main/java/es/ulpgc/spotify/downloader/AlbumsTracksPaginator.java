package es.ulpgc.spotify.downloader;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class AlbumsTracksPaginator extends NumberPaginator<String> {

    public AlbumsTracksPaginator(SpotifyAccessor spotify, String id, int offset, int total) {
        super(spotify, "/albums/" + id + "/tracks", 50, offset, total);
    }

    @Override
    protected List<String> interpretResponse(String response) {
        JsonObject object = SpotifyGson.getInstance().fromJson(response, JsonObject.class);
        List<String> tracks = new ArrayList<>();
        object.getAsJsonArray("items").forEach(item -> {
            String id = item.getAsJsonObject().get("id").getAsString();
            tracks.add(id);
        });
        return tracks;
    }
}
