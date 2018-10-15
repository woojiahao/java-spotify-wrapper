package me.chill.sample;

import me.chill.SpotifyUser;
import me.chill.authentication.SpotifyAuthenticationComponent;
import me.chill.authentication.SpotifyAuthenticationException;
import me.chill.authentication.SpotifyAuthenticationHelper;
import me.chill.authentication.SpotifyClientCredentialFlow;
import me.chill.models.Album;
import me.chill.models.Paging;
import me.chill.models.Track;

import java.util.Map;

public class AlbumQueryDemo {
	public static void main(String[] args) {
		String clientId = "cea6a21eeb874d1d91dbaaccce0996f3";
		String clientSecret = "5c8851ba3b3f4b4b8d71c791eb6865f9";

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
			System.out.println(album);

			Paging<Track> tracksInAlbum = user.getTracks("6akEvsycLGftJxYudPjmqK").limit(30).build().execute();
			System.out.println(tracksInAlbum);
		}
	}
}