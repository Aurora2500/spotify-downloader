package es.ulpgc.spotify.downloader;

import java.sql.*;

public class DatabaseStore implements Store {

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

    public DatabaseStoreVisitor getVisitor() {
        return new DatabaseStoreVisitor(this.connection);
    }

    static class Size {
        public final int artists;
        public final int albums;
        public final int tracks;

        public Size(int artists, int albums, int tracks) {
            this.artists = artists;
            this.albums = albums;
            this.tracks = tracks;
        }
    }

    public Size getSize() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT (SELECT COUNT(*) FROM artists), (SELECT COUNT(*) FROM albums), (SELECT COUNT(*) FROM tracks)");
        resultSet.next();
        int artists = resultSet.getInt(1);
        int albums = resultSet.getInt(2);
        int tracks = resultSet.getInt(3);
        return new Size(artists, albums, tracks);
    }

    public void close() throws SQLException {
        connection.close();
    }
}
