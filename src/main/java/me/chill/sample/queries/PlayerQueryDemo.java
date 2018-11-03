package me.chill.sample.queries;

import me.chill.models.*;

import java.util.List;

import static me.chill.sample.queries.UserStore.user;

class PlayerQueryDemo {
	public static void main(String[] args) {
		List<Device> availableDevices = user.getAvailableDevices().build().execute();
		availableDevices.forEach(System.out::println);

		CurrentlyPlayingContext context = user.getCurrentPlaybackInformation().build().execute();
		System.out.println(context);

		// TODO: Make another account to test this
		CursorBasedPaging<PlayHistory> recentlyPlayedTracks = user.getRecentlyPlayedTracks().limit(1).build().execute();
		System.out.println(recentlyPlayedTracks);

		CurrentlyPlaying currentlyPlaying = user.getCurrentlyPlayingTrack().build().execute();
		System.out.println(currentlyPlaying);

		user.pauseTrack()
			.device("CHILLBOX")
			.build()
			.executeAsync(status -> {
				System.out.println("Pausing track");
				System.out.println(status ? "Success" : "Failed");
				return null;
			});

		user.seekTrack()
			.position(2500)
			.build()
			.executeAsync(status -> {
				System.out.println("Seeking track");
				System.out.println(status ? "Success" : "Failed");
				return null;
			});

		user.setRepeatMode(RepeatState.Track)
			.device("CHILLBOX")
			.build()
			.executeAsync(status -> {
				System.out.println("Setting repeat");
				System.out.println(status ? "Success" : "Failed");
				return null;
			});

		user.setVolume(100)
			.device("CHILLBOX")
			.build()
			.executeAsync(status -> {
				System.out.println("Setting volume");
				System.out.println(status ? "Success" : "Failed");
				return null;
			});

		user.nextTrack()
			.device("CHILLBOX")
			.build()
			.executeAsync(status -> {
				System.out.println("Next track");
				System.out.println(status ? "Success" : "Failed");
				return null;
			});

		user.previousTrack()
			.build()
			.executeAsync(status -> {
				System.out.println("Previous track");
				System.out.println(status ? "Success" : "Failed");
				return null;
			});

		user.playTrack()
			.contextUri("spotify:album:1Je1IMUlBXcx1Fz0WE7oPT")
			.offsetPosition(10)
			.build()
			.executeAsync(status -> {
				System.out.println("Playing track");
				System.out.println(status);
				return null;
			});

		user.toggleShuffle(false)
			.build()
			.executeAsync(status -> {
				System.out.println("Toggling shuffle");
				System.out.println(status);
				return null;
			});

		user.transferPlayback("CHILLBOX").build().executeAsync(status -> {
			System.out.println("Transfering playback");
			return null;
		});
	}
}