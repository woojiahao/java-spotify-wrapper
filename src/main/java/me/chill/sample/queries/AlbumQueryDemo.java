package me.chill.sample.queries;

import me.chill.SpotifyUser;
import me.chill.authentication.SpotifyAuthenticationComponent;
import me.chill.exceptions.SpotifyAuthenticationException;
import me.chill.authentication.SpotifyAuthenticationHelper;
import me.chill.authentication.SpotifyClientCredentialFlow;
import me.chill.models.Album;
import me.chill.models.Paging;
import me.chill.models.Track;

import java.util.List;
import java.util.Map;

public class AlbumQueryDemo {
	public static void main(String[] args) {
		String clientId = "cea6a21eeb874d1d91dbaaccce0996f3";
		String clientSecret = "96729e5f290e496d8115d9e0bf27e515";

		SpotifyAuthenticationHelper helper = new SpotifyAuthenticationHelper.Builder()
			.setClientId(clientId)
			.setClientSecret(clientSecret)
			.build();

		SpotifyClientCredentialFlow flow = new SpotifyClientCredentialFlow(helper);

		Map<SpotifyAuthenticationComponent, String> info = null;
		try {
			info = flow.requestAuthentication();
		} catch (SpotifyAuthenticationException e) {
			e.printStackTrace();
		}

		if (info != null) {
			SpotifyUser user = flow.generateSpotifyUser(info);

			Album album = user.getSingleAlbum("6akEvsycLGftJxYudPjmqK").build().execute();
			System.out.println(album.toString());

			Paging<Track> tracksInAlbum = user.getAlbumTracks("6akEvsycLGftJxYudPjmqK").limit(30).build().execute();
			System.out.println(tracksInAlbum.toString());

			List<Album> albums = user.getManyAlbums().addId("41MnTivkwTO3UUJ8DrqEJJ").addId("6JWc4iAiJ9FjyK0B59ABb4").build().execute();
			albums.forEach(a -> System.out.println(a.toString()));
		}
	}
}
