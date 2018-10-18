package me.chill.sample;

import me.chill.SpotifyUser;
import me.chill.authentication.SpotifyAuthenticationComponent;
import me.chill.authentication.SpotifyAuthenticationException;
import me.chill.authentication.SpotifyAuthenticationHelper;
import me.chill.authentication.SpotifyClientCredentialFlow;
import me.chill.models.*;
import me.chill.queries.browse.FeaturedPlaylists;

import java.util.List;
import java.util.Map;

public class BrowseQueryDemo {
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

//			Category category = user.getCategory("party").build().execute();
//			System.out.println(category);
//
//			Paging<Playlist> playlists = user.getCategoryPlaylists("party").limit(1).build().execute();
//			System.out.println(playlists);
//
//			Paging<Category> categories = user.getCategoryList().limit(1).build().execute();
//			System.out.println(categories);

			FeaturedPlaylists featuredPlaylists = user.getFeaturedPlaylists().timestamp(2014, 10, 11, 1, 45, 9).build().execute();
			System.out.println(featuredPlaylists);
		}
	}
}
