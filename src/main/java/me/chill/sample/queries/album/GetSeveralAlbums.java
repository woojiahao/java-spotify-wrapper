package me.chill.sample.queries.album;

import me.chill.models.Album;

import java.util.List;

import static me.chill.sample.queries.UserStore.user;

public class GetSeveralAlbums {
  public static void main(String[] args) {
    sync();
    async();
  }

  private static void sync() {
    List<Album> albums = user
      .getSeveralAlbums()
      .addAlbums("41MnTivkwTO3UUJ8DrqEJJ")
      .addAlbums("6JWc4iAiJ9FjyK0B59ABb4")
      .build()
      .execute();
    albums.forEach(System.out::println);
  }

  private static void async() {
    user
      .getSeveralAlbums()
      .addAlbums("41MnTivkwTO3UUJ8DrqEJJ", "6JWc4iAiJ9FjyK0B59ABb4")
      .build()
      .executeAsync(albums -> {
        System.out.println("Got Albums");
        albums.forEach(System.out::println);

        return null;
      });
  }
}
