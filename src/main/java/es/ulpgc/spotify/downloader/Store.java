package es.ulpgc.spotify.downloader;

public interface Store {
    //void saveGraph(SpotifyGraph graph) throws Exception;
    StoreVisitor getVisitor();

    void close() throws Exception;
}
