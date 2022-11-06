package es.ulpgc.spotify.downloader;

public interface Store {
    void saveGraph(SpotifyGraph graph) throws Exception;
    void close() throws Exception;
}
