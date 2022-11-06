package es.ulpgc.spotify.downloader;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        List<String> artists = getArtistsFromResource();
        //Store store = new DatabaseStore("jdbc:sqlite:spotify.db");
        SpotifyAccessor spotify = new SpotifyAccessor();
        SpotifyGraphGenerator wrapper = new SpotifyGraphGenerator(spotify);

        long startTime = System.currentTimeMillis();

        SpotifyGraph graph = wrapper.generateGraph(artists);
        //store.saveGraph(graph);
        //store.close();

        long endTime = System.currentTimeMillis();

        System.out.println("Saved " + graph.artists().size() + " artists, "
                + graph.albums().size() + " albums and "
                + graph.tracks().size() + " tracks with "
                + spotify.count() + " request(s) in "
                + (endTime - startTime) + "ms");
    }

    private static List<String> getArtistsFromResource() throws IOException {
        InputStream artistStream = Main.class.getResourceAsStream("/short.txt");
        try(artistStream) {
            assert artistStream != null;
            return new String(artistStream.readAllBytes())
                    .lines()
                    .collect(Collectors.toList());
        }
    }
}
