package me.chill.sample.queries;

import kotlin.Pair;
import me.chill.models.Image;
import me.chill.models.Paging;
import me.chill.models.Playlist;
import me.chill.models.PlaylistTrack;

import java.util.List;

import static me.chill.sample.queries.UserStore.user;

public class PlaylistQueryDemo {
  public static void main(String[] args) {
    Pair<Boolean, String> result = user
      .addTrackToPlaylist("7Ga1gkkVHTTX5LJlTcGPKs")
      .addUris("2iUmqdfGZcHIhS3b9E9EWq")
      .position(1)
      .build()
      .execute();
    System.out.println(result.getFirst());
    System.out.println(result.getSecond());

    user.changePlaylistDetails("7Ga1gkkVHTTX5LJlTcGPKs")
      .description("Some catchy tunes from our past!")
      .build()
      .execute();

    user
      .createPlaylist("Test")
      .build()
      .execute();

    Paging<Playlist> playlists = user
      .getCurrentUserPlaylists()
      .limit(2)
      .build()
      .execute();
    System.out.println(playlists);

    Paging<Playlist> userPlaylists = user
      .getUserPlaylists("_woojiahao_")
      .limit(5)
      .build()
      .execute();
    System.out.println(userPlaylists);

    List<Image> coverImages = user
      .getPlaylistCoverImage("7Ga1gkkVHTTX5LJlTcGPKs")
      .build()
      .execute();
    coverImages.forEach(System.out::println);

    Playlist playlist = user
      .getSinglePlaylist("37i9dQZF1DX7F6T2n2fegs")
      .build()
      .execute();
    System.out.println(playlist);

    Paging<PlaylistTrack> tracks = user
      .getPlaylistTracks("1VKXfRH6QHhJtmBb7R9cF2")
      .limit(1)
      .build()
      .execute();
    System.out.println(tracks);

    user
      .removeTracksFromPlaylist("7fi9kPiFY8xh8lbGUUhroi")
      .addTrack("4iV5W9uYEdYUVa79Axb7Rh", 2)
      .addTrack("spotify:track:1301WleyT98MSxVHPZCA6M", 7)
      .build()
      .execute();

    user.reorderPlaylistTracks("7fi9kPiFY8xh8lbGUUhroi")
      .build()
      .execute();

    user
      .replacePlaylistTracks("7fi9kPiFY8xh8lbGUUhroi")
      .addUris("0heVTeZw99vJl4QyJC0wyk")
      .build()
      .execute();

    user
      .uploadCustomPlaylistImage("7Ga1gkkVHTTX5LJlTcGPKs")
      .cover("C:\\Users\\Chill\\Pictures\\Wallpapers\\capra-demon-1920×1080.jpg")
      .build()
      .executeAsync(status -> {
        System.out.println("Set");
        System.out.println(status ? "Success" : "Failure");

        return null;
      });
  }
}
