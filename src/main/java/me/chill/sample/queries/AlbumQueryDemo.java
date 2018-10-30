package me.chill.sample.queries;

import me.chill.SpotifyUser;
import me.chill.models.Album;
import me.chill.models.Paging;
import me.chill.models.Track;

import java.util.List;

public class AlbumQueryDemo {
	public static void main(String[] args) {
		SpotifyUser user = new SpotifyUser("BQCAaTIN0csJXOT2kpwaZtz2k57FIzgZ7PuvwCGTQ7AgAUOmVS__m3OUhh_CpVUEiscPIvb0Aq3hAzglNav1jSP_iE43azjjNugGwKN-1NhQ9ck1Hn7iLB6G_ptTWQwPBWNWdhIq4i8vxmcUApJZHfRXAX0ahYB7OUlqaOiiZLP7v8H4seLG0O1M6zHRkLNHYyGpgkQVSoknM9WxRFsUpmBjOBmlCCJj8LO12rRHOy-PTYV3IOhNK7-wk_uuLnOzWEH-zAUbfpvaGDMuMgfECg");

		Album album = user.getSingleAlbum("6akEvsycLGftJxYudPjmqK").build().execute();
		System.out.println(album.toString());

		Paging<Track> tracksInAlbum = user.getAlbumTracks("6akEvsycLGftJxYudPjmqK").limit(30).build().execute();
		System.out.println(tracksInAlbum.toString());

		List<Album> albums = user.getSeveralAlbums().addId("41MnTivkwTO3UUJ8DrqEJJ").addId("6JWc4iAiJ9FjyK0B59ABb4").build().execute();
		albums.forEach(a -> System.out.println(a.toString()));

	}
}
