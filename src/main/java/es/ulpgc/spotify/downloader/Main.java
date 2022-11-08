package es.ulpgc.spotify.downloader;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        List<String> artists = getArtistsFromResource();
        DatabaseStore store = new DatabaseStore("jdbc:sqlite:spotify.db");

        long startTime = System.currentTimeMillis();

        SpotifyGraphGenerator.generateGraph(artists).blockingSubscribe(store.getVisitor());

        long endTime = System.currentTimeMillis();

        DatabaseStore.Size size = store.getSize();
        System.out.println("Saved " + size.artists + " artists, "
                + size.albums + " albums and "
                + size.tracks + " tracks with "
                + SpotifyAccessor.getInstance().count() + " request(s) in "
                + (endTime - startTime) + "ms");

        store.close();
    }

    private static List<String> getArtistsFromResource() throws IOException {
        InputStream artistStream = Main.class.getResourceAsStream("/artists.txt");
        try(artistStream) {
            assert artistStream != null;
            return new String(artistStream.readAllBytes())
                    .lines()
                    .collect(Collectors.toList());
        }
    }
}
