package me.chill.sample.queries;

import me.chill.models.Album;
import me.chill.models.Paging;
import me.chill.models.Track;

import java.util.List;

import static me.chill.sample.queries.UserStore.user;

public class AlbumQueryDemo {
  public static void main(String[] args) {
    Album album = user
      .getSingleAlbum("6akEvsycLGftJxYudPjmqK")
      .build()
      .execute();
    System.out.println(album.toString());

    Paging<Track> tracksInAlbum = user
      .getAlbumTracks("6akEvsycLGftJxYudPjmqK")
      .limit(30)
      .build()
      .execute();
    System.out.println(tracksInAlbum.toString());

    List<Album> albums = user
      .getSeveralAlbums()
      .addAlbum("41MnTivkwTO3UUJ8DrqEJJ")
      .addAlbum("6JWc4iAiJ9FjyK0B59ABb4")
      .build()
      .execute();
    albums.forEach(a -> System.out.println(a.toString()));
  }
}
