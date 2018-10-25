package me.chill.sample;

import com.neovisionaries.i18n.CountryCode;
import me.chill.SpotifyUser;
import me.chill.authentication.SpotifyAuthenticationComponent;
import me.chill.exceptions.SpotifyAuthenticationException;
import me.chill.authentication.SpotifyAuthenticationHelper;
import me.chill.authentication.SpotifyClientCredentialFlow;
import me.chill.models.*;
import me.chill.queries.browse.SeedQuery;

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

			Category category = user.getCategory("party").build().execute();
			System.out.println(category);

			Paging<Playlist> playlists = user.getCategoryPlaylists("party").limit(1).build().execute();
			System.out.println(playlists);

			Paging<Category> categories = user.getCategoryList().limit(1).build().execute();
			System.out.println(categories);

			FeaturedPlaylists featuredPlaylists = user.getFeaturedPlaylists().timestamp(2014, 10, 11, 1, 45, 9).build().execute();
			System.out.println(featuredPlaylists);

			NewReleases newReleases = user.getNewReleases().country(CountryCode.SE).build().execute();
			System.out.println(newReleases);

			Recommendation recommendation = user
				.getSeedRecommendation()
				.limit(1)
				.acousticness(SeedQuery.Flag.Min, 0.1)
				.addSeedArtist("4NHQUGzhtTLFvgF5SZesLK")
				.addSeedGenre("party")
				.addSeedGenre("chill")
				.danceability(SeedQuery.Flag.Max, 0.8)
				.danceability(SeedQuery.Flag.Min, 0.5)
				.duration(SeedQuery.Flag.Target, 1400)
				.build()
				.execute();

			recommendation.getSeeds().forEach(System.out::println);

			List<String> availableGenresForSeeds = user.getAvailableGenreSeeds().build().execute();
			availableGenresForSeeds.forEach(System.out::println);
		}
	}
}
