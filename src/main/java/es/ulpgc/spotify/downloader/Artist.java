package es.ulpgc.spotify.downloader;

public class Artist implements SpotifyGraphFragment {
    private final String id;
    private final String name;
    private final int followers;
    private final int popularity;

    public Artist(String id, String name, int followers, int popularity) {
        this.id = id;
        this.name = name;
        this.followers = followers;
        this.popularity = popularity;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public int followers() {
        return followers;
    }

    public int popularity() {
        return popularity;
    }


    @Override
    public void accept(StoreVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
