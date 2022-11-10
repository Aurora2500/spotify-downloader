package es.ulpgc.spotify.downloader;

public interface SpotifyGraphFragment {
	void accept(StoreVisitor visitor) throws Exception;
}
