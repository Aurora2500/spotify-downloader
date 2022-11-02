package es.ulpgc.spotify.downloader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseStore  implements Store {
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
                "\tname TEXT\n" +
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

    public void saveGraph(SpotifyGraph graph) {

    }
    public void close() {
    }
}
