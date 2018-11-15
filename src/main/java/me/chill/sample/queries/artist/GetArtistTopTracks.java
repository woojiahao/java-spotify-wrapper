package me.chill.sample.queries.artist;

import com.neovisionaries.i18n.CountryCode;
import me.chill.models.Track;

import java.util.List;

import static me.chill.sample.queries.UserStore.user;

public class GetArtistTopTracks {
  public static void main(String[] args) {
    sync();
    async();
  }

  private static void sync() {
    List<Track> topTracks = user
      .getArtistTopTracks("43ZHCT0cAZBISjO8DG9PnE")
      .market(CountryCode.SG)
      .build()
      .execute();
    System.out.println(topTracks);
  }

  private static void async() {
    user
      .getArtistTopTracks("43ZHCT0cAZBISjO8DG9PnE")
      .market(CountryCode.SG)
      .build()
      .executeAsync(tracks -> {
        System.out.println("Got tracks");
        System.out.println(tracks.get(0));

        return null;
      });
  }
}
