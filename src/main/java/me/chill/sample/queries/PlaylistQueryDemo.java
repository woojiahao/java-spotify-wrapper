package me.chill.sample.queries;

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
    user.getCurrentUserPlaylists().limit(2).build().executeAsync(playlists -> {
      System.out.println(playlists);
      return null;
    });
  }
}
