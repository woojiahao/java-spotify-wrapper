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
//    user.getCurrentUserPlaylists().limit(2).build().executeAsync(playlists -> {
//      System.out.println(playlists);
//      return null;
//    });

//    Paging<Playlist> userPlaylists = user
//      .getUserPlaylists("_woojiahao_")
//      .limit(5)
//      .build()
//      .execute();
//    System.out.println(userPlaylists);
//
//    List<Image> coverImages = user
//      .getPlaylistCoverImage("7Ga1gkkVHTTX5LJlTcGPKs")
//      .build()
//      .execute();
//    coverImages.forEach(System.out::println);
//
//    Playlist playlist = user
//      .getSinglePlaylist("37i9dQZF1DX7F6T2n2fegs")
//      .build()
//      .execute();
//    System.out.println(playlist);

//    Paging<PlaylistTrack> tracks = user
//      .getPlaylistTracks("1VKXfRH6QHhJtmBb7R9cF2")
//      .limit(1)
//      .build()
//      .execute();
//    System.out.println(tracks);
//    System.out.println(tracks.getItems());
//    tracks.getItems().forEach(System.out::println);
//    System.out.println(tracks.getItems().get(0).getAddedBy().getDisplayName());

//    user
//      .removeTracksFromPlaylist("7fi9kPiFY8xh8lbGUUhroi")
//      .addTrack("4iV5W9uYEdYUVa79Axb7Rh", 2)
//      .addTrack("spotify:track:1301WleyT98MSxVHPZCA6M", 7)
//      .build()
//      .execute();
//
//    user.reorderPlaylistTracks("7fi9kPiFY8xh8lbGUUhroi")
//      .build()
//      .execute();

    user
      .replacePlaylistTracks("7fi9kPiFY8xh8lbGUUhroi")
      .addUris("0heVTeZw99vJl4QyJC0wyk")
      .build()
      .execute();
  }
}
