package es.ulpgc.spotify.downloader;

import java.sql.*;

public class DatabaseStore implements Store {
    private static final String INSERT_ARTISTS = "INSERT INTO artists (id, name, followers, popularity) VALUES (?, ?, ?, ?)";
    private static final String INSERT_ALBUMS = "INSERT INTO albums (id, title, release_date, release_date_precision, type) VALUES (?, ?, ?, ?, ?)";
    private static final String INSERT_TRACKS = "INSERT INTO tracks (id, title, album_id, duration, explicit, popularity) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String INSERT_ARTIST_ALBUMS = "INSERT INTO artists_albums (artist_id, album_id) VALUES (?, ?)";
    private static final String INSERT_ARTIST_TRACKS = "INSERT INTO artists_tracks (artist_id, track_id) VALUES (?, ?)";

    private final Connection connection;

    public DatabaseStore(String uri) throws SQLException {
        connection = DriverManager.getConnection(uri);
        assertTables();
    }

    private void assertTables() throws SQLException {
        // Make sure the tables exist.
        // If not, create them.
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS artists (\n" +
                "\tid TEXT PRIMARY KEY,\n" +
                "\tname TEXT,\n" +
                "\tfollowers INTEGER,\n" +
                "\tpopularity INTEGER\n" +
                ")");
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS albums (\n" +
                "\tid TEXT PRIMARY KEY,\n" +
                "\ttitle TEXT,\n" +
                "\trelease_date TEXT,\n" +
                "\trelease_date_precision TEXT,\n" +
                "\ttype TEXT\n" +
                ")");
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS tracks (\n" +
                "\tid TEXT PRIMARY KEY,\n" +
                "\ttitle TEXT,\n" +
                "\talbum_id TEXT,\n" +
                "\tduration INTEGER,\n" +
                "\texplicit BOOLEAN,\n" +
                "\tpopularity INTEGER,\n" +
                "\tFOREIGN KEY (album_id) REFERENCES albums(id)" +
                ")");
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS artists_albums (\n" +
                "\tartist_id TEXT,\n" +
                "\talbum_id TEXT,\n" +
                "\tFOREIGN KEY (artist_id) REFERENCES artists(id),\n" +
                "\tFOREIGN KEY (album_id) REFERENCES albums(id)\n" +
                ")");
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS artists_tracks (\n" +
                "\tartist_id TEXT,\n" +
                "\ttrack_id TEXT,\n" +
                "\tFOREIGN KEY (artist_id) REFERENCES artists(id),\n" +
                "\tFOREIGN KEY (track_id) REFERENCES tracks(id)\n" +
                ")");
    }

    public void saveGraph(SpotifyGraph graph) throws SQLException {
        connection.setAutoCommit(false);
        System.out.println("Saving artists...");
        PreparedStatement insertArtistStatement = connection.prepareStatement(INSERT_ARTISTS);
        for (Artist artist : graph.artists().values()) {
            insertArtistStatement.setString(1, artist.id());
            insertArtistStatement.setString(2, artist.name());
            insertArtistStatement.setInt(3, artist.followers());
            insertArtistStatement.setInt(4, artist.popularity());
            insertArtistStatement.addBatch();
        }
        insertArtistStatement.executeBatch();
        System.out.println("Saving albums...");
        PreparedStatement insertAlbumStatement = connection.prepareStatement(INSERT_ALBUMS);
        PreparedStatement insertArtistAlbumStatement = connection.prepareStatement(INSERT_ARTIST_ALBUMS);
        for(Album album : graph.albums().values()) {
            insertAlbumStatement.setString(1, album.id());
            insertAlbumStatement.setString(2, album.title());
            insertAlbumStatement.setString(3, album.releaseDate());
            insertAlbumStatement.setString(4, album.releaseDatePrecision());
            insertAlbumStatement.setString(5, album.type());
            insertAlbumStatement.addBatch();
            for (String artistId : album.artists()) {
                insertArtistAlbumStatement.setString(1, artistId);
                insertArtistAlbumStatement.setString(2, album.id());
                insertArtistAlbumStatement.addBatch();
            }
        }
        insertAlbumStatement.executeBatch();
        System.out.println("Saving tracks...");
        PreparedStatement insertTrackStatement = connection.prepareStatement(INSERT_TRACKS);
        PreparedStatement insertArtistTrackStatement = connection.prepareStatement(INSERT_ARTIST_TRACKS);
        for(Track track : graph.tracks().values()) {
            insertTrackStatement.setString(1, track.id());
            insertTrackStatement.setString(2, track.title());
            insertTrackStatement.setString(3, track.album());
            insertTrackStatement.setInt(4, track.duration());
            insertTrackStatement.setBoolean(5, track.explicit());
            insertTrackStatement.addBatch();
            for (String artistId : track.artists()) {
                insertArtistTrackStatement.setString(1, artistId);
                insertArtistTrackStatement.setString(2, track.id());
                insertArtistTrackStatement.addBatch();
            }
        }
        insertTrackStatement.executeBatch();
        insertArtistTrackStatement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
    }

    public void close() throws SQLException {
        connection.close();
    }
}
