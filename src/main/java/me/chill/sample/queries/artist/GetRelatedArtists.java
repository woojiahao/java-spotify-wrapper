package me.chill.sample.queries.artist;

import me.chill.models.Artist;

import java.util.List;

import static me.chill.sample.queries.UserStore.user;

public class GetRelatedArtists {
  public static void main(String[] args) {
    sync();
    async();
  }

  private static void sync() {
    List<Artist> relatedArtist = user
      .getRelatedArtists("43ZHCT0cAZBISjO8DG9PnE")
      .build()
      .execute();
    System.out.println(relatedArtist);
  }

  private static void async() {
    user
      .getRelatedArtists("43ZHCT0cAZBISjO8DG9PnE")
      .build()
      .executeAsync(artists -> {
        System.out.println("Got Artists");
        System.out.println(artists.get(0));

        return null;
      });
  }
}
