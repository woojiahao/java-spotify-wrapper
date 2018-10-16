package me.chill.sample;

import com.neovisionaries.i18n.CountryCode;
import com.sun.org.apache.xalan.internal.xsltc.dom.SortingIterator;
import me.chill.SpotifyUser;
import me.chill.authentication.SpotifyAuthenticationComponent;
import me.chill.authentication.SpotifyAuthenticationException;
import me.chill.authentication.SpotifyAuthenticationHelper;
import me.chill.authentication.SpotifyClientCredentialFlow;
import me.chill.models.Album;
import me.chill.models.Artist;
import me.chill.models.Paging;
import me.chill.models.Track;

import java.util.List;
import java.util.Map;

public class ArtistQueryDemo {
	public static void main(String[] args) {
		String clientId = "cea6a21eeb874d1d91dbaaccce0996f3";
		String clientSecret = "34dfe5eb8e5b4766a6fb4d8103605baa";

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

			Artist artist = user.getSingleArtist("0OdUWJ0sBjDrqHygGUXeCF").build().execute();
			System.out.println(artist);
		}

	}
}