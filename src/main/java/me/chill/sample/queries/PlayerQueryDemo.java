package me.chill.sample.queries;

import me.chill.SpotifyUser;
import me.chill.models.*;

import java.util.List;

class PlayerQueryDemo {
	public static void main(String[] args) {
		SpotifyUser user = new SpotifyUser("BQBe8tT_8FYGMvPjVJxNnejdYidHzIpkdzu_j44licxs_mhjbbdcjjJblCvbch3SiqOaI4agBAQEpdLR2ZQFN3zPQptVy6tuPkqmyJRRezup4zzs89x69yfXvxOIs3-L_MdzV7hd3ifQG3qo0rR9HWOPUgchkKjqCCWIa49h-1lR_3aUG0LW_0qcahZWZIbLDl-MqVQIIkBy6KupNCDR5z5UVolyxTTWtw4GzcsbFg0RqqQofTcB-Rf2-UkLfgMLx2kpZOuPq2nKHqA6gc9cqg");

		List<Device> availableDevices = user.getAvailableDevices().build().execute();
		availableDevices.forEach(System.out::println);

		CurrentlyPlayingContext context = user.getCurrentPlaybackInformation().build().execute();
		System.out.println(context);

		// TODO: Make another account to test this
		CursorBasedPaging<PlayHistory> recentlyPlayedTracks = user.getRecentlyPlayedTracks().limit(10).build().execute();
		System.out.println(recentlyPlayedTracks);

		CurrentlyPlaying currentlyPlaying = user.getCurrentlyPlayingTrack().build().execute();
		System.out.println(currentlyPlaying);
	}
}