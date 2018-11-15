package me.chill.sample.queries.artist;

import me.chill.models.Artist;

import java.util.List;

import static me.chill.sample.queries.UserStore.user;

public class GetSeveralArtists {
  public static void main(String[] args) {
    sync();
    async();
  }

  private static void sync() {
    List<Artist> artists = user
      .getSeveralArtists()
      .addArtists("0oSGxfWSnnOXhD2fKuz2Gy")
      .build()
      .execute();
    System.out.println(artists);
  }

  private static void async() {
    user
      .getSeveralArtists()
      .addArtists("0oSGxfWSnnOXhD2fKuz2Gy")
      .build()
      .executeAsync(artists -> {
        System.out.println("Got artists");
        System.out.println(artists.get(0));

        return null;
      });
  }
}
