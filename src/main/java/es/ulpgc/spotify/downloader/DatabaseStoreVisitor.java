package es.ulpgc.spotify.downloader;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseStoreVisitor implements StoreVisitor {
	private static final String INSERT_ARTISTS = "INSERT INTO artists (id, name, followers, popularity) VALUES (?, ?, ?, ?)";
	private static final String INSERT_ALBUMS = "INSERT INTO albums (id, title, release_date, release_date_precision, type) VALUES (?, ?, ?, ?, ?)";
	private static final String INSERT_TRACKS = "INSERT INTO tracks (id, title, album_id, duration, explicit, popularity) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String INSERT_ARTIST_ALBUMS = "INSERT INTO artists_albums (artist_id, album_id) VALUES (?, ?)";
	private static final String INSERT_ARTIST_TRACKS = "INSERT INTO artists_tracks (artist_id, track_id) VALUES (?, ?)";
	private final Connection connection;
	public DatabaseStoreVisitor(Connection connection) {
		this.connection = connection;
	}


	@Override
	public void visit(Artist artist) throws Exception {
		PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ARTISTS);
		preparedStatement.setString(1, artist.id());
		preparedStatement.setString(2, artist.name());
		preparedStatement.setInt(3, artist.followers());
		preparedStatement.setInt(4, artist.popularity());
		preparedStatement.execute();
	}

	@Override
	public void visit(Album album) throws Exception {
		PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ALBUMS);
		preparedStatement.setString(1, album.id());
		preparedStatement.setString(2, album.title());
		preparedStatement.setString(3, album.releaseDate());
		preparedStatement.setString(4, album.releaseDatePrecision());
		preparedStatement.setString(5, album.type());
		try {
			preparedStatement.execute();
		} catch (SQLException e) {
			System.out.println("Error inserting album: " + album.id());
			throw e;
		}
		PreparedStatement insertArtistAlbumStatement = connection.prepareStatement(INSERT_ARTIST_ALBUMS);
		for (String artistId : album.artists()) {
			insertArtistAlbumStatement.setString(1, artistId);
			insertArtistAlbumStatement.setString(2, album.id());
			insertArtistAlbumStatement.execute();
		}
	}

	@Override
	public void visit(Track track) throws Exception {
		PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TRACKS);
		preparedStatement.setString(1, track.id());
		preparedStatement.setString(2, track.title());
		preparedStatement.setString(3, track.album());
		preparedStatement.setInt(4, track.duration());
		preparedStatement.setBoolean(5, track.explicit());
		preparedStatement.setInt(6, track.popularity());
		preparedStatement.execute();
		PreparedStatement insertArtistTrackStatement = connection.prepareStatement(INSERT_ARTIST_TRACKS);
		for (String artistId : track.artists()) {
			insertArtistTrackStatement.setString(1, artistId);
			insertArtistTrackStatement.setString(2, track.id());
			insertArtistTrackStatement.execute();
		}
	}

	@Override
	public void onSubscribe(@NonNull Disposable d) {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onNext(@NonNull SpotifyGraphFragment spotifyGraphFragment) {
		try {
			spotifyGraphFragment.accept(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onError(@NonNull Throwable e) {
		throw new RuntimeException(e);
	}

	@Override
	public void onComplete() {
		try {
			connection.commit();
			connection.setAutoCommit(true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
