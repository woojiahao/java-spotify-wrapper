package me.chill.sample.queries;

import me.chill.models.Image;
import me.chill.models.Paging;
import me.chill.models.Playlist;

import java.util.List;

import static me.chill.sample.queries.UserStore.user;

public class PlaylistQueryDemo {
  public static void main(String[] args) {
//		Pair<Boolean, String> result = user
//			.addTrackToPlaylist("7Ga1gkkVHTTX5LJlTcGPKs")
//			.addUri("spotify:track:2iUmqdfGZcHIhS3b9E9EWq")
//			.position(1)
//			.build()
//			.execute();
//		System.out.println(result.getFirst());
//		System.out.println(result.getSecond());

//		user.changePlaylistDetails("7Ga1gkkVHTTX5LJlTcGPKs")
//			.description("Some catchy tunes from our past!")
//			.build()
//			.execute();

//		user.createPlaylist("Test").build().execute();
//    user.getCurrentUserPlaylists().limit(2).build().executeAsync(playlists -> {
//      System.out.println(playlists);
//      return null;
//    });

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
  }
}
