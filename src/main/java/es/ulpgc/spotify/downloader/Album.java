package es.ulpgc.spotify.downloader;

import java.util.HashSet;
import java.util.Set;

public class Album implements SpotifyGraphFragment {
	private final String id;
	private final String title;
	private final String releaseDate;
	private final String releaseDatePrecision;
	private final String type;

	private final Set<String> artists;
	private final Set<String> tracks;

	public Album(String id, String title, String releaseDate, String releaseDatePrecision, String type) {
		this.id = id;
		this.title = title;
		this.releaseDate = releaseDate;
		this.releaseDatePrecision = releaseDatePrecision;
		this.type = type;

		artists = new HashSet<>();
		tracks = new HashSet<>();
	}

	public String id() {
		return id;
	}

	public String title() {
		return title;
	}

	public String releaseDate() {
		return releaseDate;
	}

	public String releaseDatePrecision() {
		return releaseDatePrecision;
	}

	public String type() {
		return type;
	}

	public Set<String> artists() {
		return artists;
	}

	public Set<String> tracks() {
		return tracks;
	}

	@Override
	public void accept(StoreVisitor store) throws Exception {
		store.visit(this);
	}
}
