package es.ulpgc.spotify.downloader;

public interface Store {
    void saveGraph(SpotifyGraph graph);
    void close();
}
