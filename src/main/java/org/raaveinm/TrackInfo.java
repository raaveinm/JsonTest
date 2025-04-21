package org.raaveinm;

public class TrackInfo {
    private final String name;
    private final String artist;
    private final String album;
    private final Integer length;

    public TrackInfo(String name, String artist, String album, Integer length) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.length = length;
    }

    public String getName() {return name;}
    public String getArtist() {return artist;}
    public String getAlbum() {return album;}
    public Integer getLength() {return length;}

    @Override
    public String toString() {
        return "TrackInfo [name=" + name +
                ", artist=" + artist + ", album=" + album +
                ", length=" + length + "]";
    }
}
