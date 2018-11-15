package me.chill.sample.queries.album;

import me.chill.models.Paging;
import me.chill.models.Track;

import static me.chill.sample.queries.UserStore.user;

public class GetAlbumTracks {
  public static void main(String[] args) {
    sync();
    async();
  }

  private static void sync() {
    Paging<Track> tracksInAlbum = user
      .getAlbumTracks("6akEvsycLGftJxYudPjmqK")
      .limit(1)
      .build()
      .execute();
    System.out.println(tracksInAlbum);
  }

  private static void async() {
    user
      .getAlbumTracks("6akEvsycLGftJxYudPjmqK")
      .limit(1)
      .build()
      .executeAsync(tracks -> {
        System.out.println("Got tracks");
        System.out.println(tracks.getItems());

        return null;
      });
  }
}
