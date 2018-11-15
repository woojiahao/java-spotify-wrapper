package me.chill.sample.queries.artist;

import me.chill.models.Artist;

import static me.chill.sample.queries.UserStore.user;

public class GetSingleArtist {
  public static void main(String[] args) {
    sync();
    async();
  }

  private static void sync() {
    Artist artist = user
      .getSingleArtist("0OdUWJ0sBjDrqHygGUXeCF")
      .build()
      .execute();
    System.out.println(artist);
  }

  private static void async() {
    user
      .getSingleArtist("0OdUWJ0sBjDrqHygGUXeCF")
      .build()
      .executeAsync(artist -> {
        System.out.println("Got Artist");
        System.out.println(artist);

        return null;
      });
  }
}
