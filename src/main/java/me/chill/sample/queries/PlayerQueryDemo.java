package me.chill.sample.queries;

import me.chill.SpotifyUser;
import me.chill.models.*;

import java.util.List;

class PlayerQueryDemo {
	public static void main(String[] args) {
		SpotifyUser user = new SpotifyUser("BQCNsi-uubb-DhFazJ0doyAOfp67ivyxF6stj5DBYaDB1OdxbWgGrDavQl6GcQaBxpoOckHEe892B9d9mnhF_Mvzm_WyPLf4yZA8KhiMzfcBZs5NOFmGWlO2ZYgAaH9PqHA0zq4juZTjgAUrutAW_X7uBuzx58JEzUhgXGTHobyX40cFwL6ouh5iNfv3KgZ7H8S52GYJjCS5p0jpZhsp590HEwGdoJ74HUBtA7-IiOfSw8Xo4zFyu8M2BK57fqd-uH70GZ7GOth1oplpCm5Qjw");

		List<Device> availableDevices = user.getAvailableDevices().build().execute();
		availableDevices.forEach(System.out::println);

		CurrentlyPlayingContext context = user.getCurrentPlaybackInformation().build().execute();
		System.out.println(context);

		// TODO: Make another account to test this
		CursorBasedPaging<PlayHistory> recentlyPlayedTracks = user.getRecentlyPlayedTracks().limit(10).build().execute();
		System.out.println(recentlyPlayedTracks);

		CurrentlyPlaying currentlyPlaying = user.getCurrentlyPlayingTrack().build().execute();
		System.out.println(currentlyPlaying);

		user.pauseTrack().device("CHILLBOX").build().executeAsync(obj -> {
			boolean b = (Boolean) obj;
			if (b) System.out.println("Premium");
			else System.out.println("Not premium");
			return null;
		});

		user.seekTrack().position(2500).build().executeAsync(obj -> {
			boolean stat = (Boolean) obj;
			if (!stat) System.out.println("Not premium");

			return null;
		});
	}
}