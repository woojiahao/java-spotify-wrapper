package me.chill.sample.queries;

import com.neovisionaries.i18n.CountryCode;
import me.chill.SpotifyUser;
import me.chill.models.*;
import me.chill.queries.browse.GetRecommendationFromSeedQuery;

import java.util.List;

public class BrowseQueryDemo {
  public static void main(String[] args) {
    SpotifyUser user = new SpotifyUser("BQCAaTIN0csJXOT2kpwaZtz2k57FIzgZ7PuvwCGTQ7AgAUOmVS__m3OUhh_CpVUEiscPIvb0Aq3hAzglNav1jSP_iE43azjjNugGwKN-1NhQ9ck1Hn7iLB6G_ptTWQwPBWNWdhIq4i8vxmcUApJZHfRXAX0ahYB7OUlqaOiiZLP7v8H4seLG0O1M6zHRkLNHYyGpgkQVSoknM9WxRFsUpmBjOBmlCCJj8LO12rRHOy-PTYV3IOhNK7-wk_uuLnOzWEH-zAUbfpvaGDMuMgfECg");

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
      .getRecommendationsFromSeed()
      .limit(1)
      .acousticness(GetRecommendationFromSeedQuery.Flag.Min, 0.1)
      .addSeedArtist("4NHQUGzhtTLFvgF5SZesLK")
      .addSeedGenre("party")
      .addSeedGenre("chill")
      .danceability(GetRecommendationFromSeedQuery.Flag.Max, 0.8)
      .danceability(GetRecommendationFromSeedQuery.Flag.Min, 0.5)
      .duration(GetRecommendationFromSeedQuery.Flag.Target, 1400)
      .build()
      .execute();

    recommendation.getSeeds().forEach(System.out::println);

    List<String> availableGenresForSeeds = user.getAvailableGenreSeeds().build().execute();
    availableGenresForSeeds.forEach(System.out::println);
  }
}
