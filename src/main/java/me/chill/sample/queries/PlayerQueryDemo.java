package me.chill.sample.queries;

import me.chill.SpotifyUser;
import me.chill.models.*;

import java.util.List;

class PlayerQueryDemo {
	public static void main(String[] args) {
		SpotifyUser user = new SpotifyUser("BQASM4lrjaJmXUwZn1WXVEkENEws2WcuwoVVG6SlH3yB0rnAO4wCmBobiO5y0_E7XD5RdqhPeQ_Y29kE1gKEJkfpxkc9-mVcSoXc7IBfi0pb5elDXMQu1AfDd6JEV2k1jUc7ekXOU6deFoHc0_yehsvwWeIagmIQR9B2WWZg6w4QrboeRAZPaybhwlnq1EKK0hMIZzny8yWzsSknaegrG_XERe2AjZwW51tXmgHa61jybpx8DV-a3M8Sic8SV5qz2K9xpuiyLDOQ2gfA2UE_SQ");

		List<Device> availableDevices = user.getAvailableDevices().build().execute();
		availableDevices.forEach(System.out::println);

		CurrentlyPlayingContext context = user.getCurrentPlaybackInformation().build().execute();
		System.out.println(context);

		// TODO: Make another account to test this
		CursorBasedPaging<PlayHistory> recentlyPlayedTracks = user.getRecentlyPlayedTracks().limit(1).build().execute();
		System.out.println(recentlyPlayedTracks);

		CurrentlyPlaying currentlyPlaying = user.getCurrentlyPlayingTrack().build().execute();
		System.out.println(currentlyPlaying);

		Boolean setVolumeStatus = user.setVolume(78).device("CHILLBOX").build().execute();
		System.out.println("Volume status: " + setVolumeStatus);

		user.pauseTrack().device("CHILLBOX").build().executeAsync(obj -> {
			System.out.println("Pausing track");
			boolean b = (Boolean) obj;
			if (b) System.out.println("Premium");
			else System.out.println("Not premium");
			return null;
		});

		user.seekTrack().position(2500).build().executeAsync(obj -> {
			System.out.println("Seeking track");
			System.out.println((Boolean) obj ? "Premium" : "Not premium");
			return null;
		});

		user.setRepeatMode(RepeatState.Track).device("CHILLBOX").build().executeAsync(obj -> {
			System.out.println("Setting repeat");
			System.out.println((Boolean) obj ? "Premium" : "Not premium");
			return null;
		});

		user.setVolume(100).device("CHILLBOX").build().executeAsync(obj -> {
			System.out.println("Setting volume");
			System.out.println((Boolean) obj ? "Premium" : "Not premium");
			return null;
		});
	}
}