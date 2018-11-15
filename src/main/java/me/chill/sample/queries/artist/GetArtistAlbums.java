package me.chill.sample.queries.artist;

import me.chill.models.Album;
import me.chill.models.Paging;

import static me.chill.sample.queries.UserStore.user;

public class GetArtistAlbums {
  public static void main(String[] args) {
    sync();
    async();
  }

  private static void sync() {
    Paging<Album> artistAlbums = user
      .getArtistAlbums("0OdUWJ0sBjDrqHygGUXeCF")
      .build()
      .execute();
    artistAlbums.getItems().forEach(System.out::println);
  }

  private static void async() {
    user
      .getArtistAlbums("0OdUWJ0sBjDrqHygGUXeCF")
      .build()
      .executeAsync(albums -> {
        System.out.println("Got Albums");
        System.out.println(albums.getItems());

        return null;
      });
  }
}
