package es.ulpgc.spotify.downloader;

import com.google.gson.JsonObject;
import io.reactivex.rxjava3.core.Observable;

import java.util.ArrayList;
import java.util.List;

public class AlbumsTracksPaginator extends NumberPaginator<String> {

    public AlbumsTracksPaginator(String id, int offset, int total) {
        super("/albums/" + id + "/tracks", 50, offset, total);
    }

    @Override
    protected Observable<String> interpretResponse(String response) {
        JsonObject object = SpotifyGson.getInstance().fromJson(response, JsonObject.class);
        List<String> tracks = new ArrayList<>();
        object.getAsJsonArray("items").forEach(item -> {
            String id = item.getAsJsonObject().get("id").getAsString();
            tracks.add(id);
        });
        return Observable.fromIterable(tracks);
    }
}
