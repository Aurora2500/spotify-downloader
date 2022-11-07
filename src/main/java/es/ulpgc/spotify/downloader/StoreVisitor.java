package es.ulpgc.spotify.downloader;

import io.reactivex.rxjava3.core.Observer;

public interface StoreVisitor extends Observer<SpotifyGraphFragment> {
	void visit(Artist artist) throws Exception;
	void visit(Album album) throws Exception;
	void visit(Track track) throws Exception;
}
