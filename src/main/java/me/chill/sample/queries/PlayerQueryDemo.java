package me.chill.sample.queries;

import me.chill.SpotifyUser;
import me.chill.models.*;

import java.util.List;

class PlayerQueryDemo {
	public static void main(String[] args) {
		SpotifyUser user = new SpotifyUser("BQASPh3daItDW4-RhVBjwxqFzDXr2kDWUVe_41Prxlu-QBF0OyYnTHvIcZn7dcgQJK5cdOLsjekOLiF2Ge1qex-HKMyvKAy5ZwKNy4Byrfsf2q17QobT9NDT8PoNoqoaDkE7vYRYMKv6ZLr_v6bDVPcABfVPkDsF3uPrCzUXIJ3Pe5iTbyhmnXR5ciW12xVocjzUVblIPwV_U6h5JGPtW_UNBV5DRlU1ko7dAYNWXMcUP_tUeKNK9X9FTiDnZ5sQrrfIgvcHRUVPiQTKbolkiw");

		List<Device> availableDevices = user.getAvailableDevices().build().execute();
		availableDevices.forEach(System.out::println);

		CurrentlyPlayingContext context = user.getCurrentPlaybackInformation().build().execute();
		System.out.println(context);

		// TODO: Make another account to test this
		CursorBasedPaging<PlayHistory> recentlyPlayedTracks = user.getRecentlyPlayedTracks().limit(1).build().execute();
		System.out.println(recentlyPlayedTracks);

		CurrentlyPlaying currentlyPlaying = user.getCurrentlyPlayingTrack().build().execute();
		System.out.println(currentlyPlaying);

		user.pauseTrack().device("CHILLBOX").build().executeAsync(status -> {
			System.out.println("Pausing track");
			System.out.println(status ? "Success" : "Failed");
			return null;
		});

		user.seekTrack().position(2500).build().executeAsync(status -> {
			System.out.println("Seeking track");
			System.out.println(status ? "Success" : "Failed");
			return null;
		});

		user.setRepeatMode(RepeatState.Track).device("CHILLBOX").build().executeAsync(status -> {
			System.out.println("Setting repeat");
			System.out.println(status ? "Success" : "Failed");
			return null;
		});

		user.setVolume(100).device("CHILLBOX").build().executeAsync(status -> {
			System.out.println("Setting volume");
			System.out.println(status ? "Success" : "Failed");
			return null;
		});

		user.nextTrack().device("CHILLBOX").build().executeAsync(status -> {
			System.out.println("Next track");
			System.out.println(status ? "Success" : "Failed");
			return null;
		});

		user.previousTrack().build().executeAsync(status -> {
			System.out.println("Previous track");
			System.out.println(status ? "Success" : "Failed");
			return null;
		});
	}
}