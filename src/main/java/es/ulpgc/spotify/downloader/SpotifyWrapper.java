package es.ulpgc.spotify.downloader;

import java.util.List;

public class SpotifyWrapper {
    private final SpotifyAccessor accessor;

    public SpotifyWrapper(SpotifyAccessor accessor) {
        this.accessor = accessor;
    }

    public SpotifyGraph generateGraph(List<String> artists) {
        SpotifyGraph graph = new SpotifyGraph();
        //TODO
        return graph;
    }
}
