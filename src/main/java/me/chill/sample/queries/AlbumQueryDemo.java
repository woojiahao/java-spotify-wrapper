package me.chill.sample.queries;

import me.chill.SpotifyUser;
import me.chill.models.Album;

import java.util.stream.IntStream;

public class AlbumQueryDemo {
	public static void main(String[] args) {
		SpotifyUser user = new SpotifyUser("BQCNsi-uubb-DhFazJ0doyAOfp67ivyxF6stj5DBYaDB1OdxbWgGrDavQl6GcQaBxpoOckHEe892B9d9mnhF_Mvzm_WyPLf4yZA8KhiMzfcBZs5NOFmGWlO2ZYgAaH9PqHA0zq4juZTjgAUrutAW_X7uBuzx58JEzUhgXGTHobyX40cFwL6ouh5iNfv3KgZ7H8S52GYJjCS5p0jpZhsp590HEwGdoJ74HUBtA7-IiOfSw8Xo4zFyu8M2BK57fqd-uH70GZ7GOth1oplpCm5Qjw");

//		Album album = user.getSingleAlbum("6akEvsycLGftJxYudPjmqK").build().execute();
//		System.out.println(album.toString());
//
//		Paging<Track> tracksInAlbum = user.getAlbumTracks("6akEvsycLGftJxYudPjmqK").limit(30).build().execute();
//		System.out.println(tracksInAlbum.toString());
//
//		List<Album> albums = user.getSeveralAlbums().addId("41MnTivkwTO3UUJ8DrqEJJ").addId("6JWc4iAiJ9FjyK0B59ABb4").build().execute();
//		albums.forEach(a -> System.out.println(a.toString()));

		user.getSingleAlbum("6akEvsycLGftJxYudPjmqK").build().executeAsync(obj -> {
			Album a = (Album) obj;

			System.out.println(a);
			return null;
		});

		IntStream.range(1, 10_000).forEach(i -> System.out.print("a"));
	}
}
