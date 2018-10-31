package me.chill.sample.queries;

import me.chill.SpotifyUser;
import me.chill.models.Album;
import me.chill.models.Paging;
import me.chill.models.Track;

import java.util.List;
import java.util.stream.IntStream;

public class AlbumQueryDemo {
	public static void main(String[] args) {
		SpotifyUser user = new SpotifyUser("BQBwvNaFNT6ms7lttR9uk68iF2vCjnjbuyci_d5J5f5SCjeaj-lKyXDaM9qSKB-Thg4bdsoXBfTtON5P2r52EVkqFrGTKVobfmXNAcxdEIdNfK5aVrrtaKI0oyBTEzbScP_Gk2syMdN_0s4ZwRiyK9TqUYI-ZA_Hf-C6qqN4O0F7zCeJs89dtQ69aSAFrxx23gvMmlnqfag4YVWKWCfeVOysbKIXhx886PkISRO0YF8kBxe4sty0-F-8tNyaHyArGWyFHNeoMuMIqfLWj3dupw");

//		Album album = user.getSingleAlbum("6akEvsycLGftJxYudPjmqK").build().execute();
//		System.out.println(album.toString());
//
//		Paging<Track> tracksInAlbum = user.getAlbumTracks("6akEvsycLGftJxYudPjmqK").limit(30).build().execute();
//		System.out.println(tracksInAlbum.toString());
//
//		List<Album> albums = user.getSeveralAlbums().addId("41MnTivkwTO3UUJ8DrqEJJ").addId("6JWc4iAiJ9FjyK0B59ABb4").build().execute();
//		albums.forEach(a -> System.out.println(a.toString()));
	}
}
