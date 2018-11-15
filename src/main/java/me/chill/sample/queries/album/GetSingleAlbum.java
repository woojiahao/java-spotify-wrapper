package me.chill.sample.queries.album;

import me.chill.models.Album;

import static me.chill.sample.queries.UserStore.user;

public class GetSingleAlbum {
  public static void main(String[] args) {
    sync();
    async();
  }

  private static void sync() {
    Album album = user
      .getSingleAlbum("6akEvsycLGftJxYudPjmqK")
      .build()
      .execute();
    System.out.println(album);
  }

  private static void async() {
    user
      .getSingleAlbum("6akEvsycLGftJxYudPjmqK")
      .build()
      .executeAsync(album -> {
        System.out.println("Got album");
        System.out.println(album);
        return null;
      });
  }
}
