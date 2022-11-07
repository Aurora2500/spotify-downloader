package es.ulpgc.spotify.downloader;

public interface SpotifyGraphFragment {
	void accept(StoreVisitor store) throws Exception;
}
