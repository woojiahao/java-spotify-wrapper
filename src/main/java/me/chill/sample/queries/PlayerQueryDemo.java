package me.chill.sample.queries;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import me.chill.SpotifyUser;
import me.chill.models.*;

import java.util.List;

class PlayerQueryDemo {
	public static void main(String[] args) {
		SpotifyUser user = new SpotifyUser("BQDpBgOb747cliiyV7MIx2F8jUuNkWg9ZcpK6h1PdZ-aelTxpPhRXBykSknCkzeXwvVLI1mEsvHwU7gmPqSLaOuQcqshCQp7XciQr-1HBoveQaOTUWsVaLFCtZl9KWtqG5sQwUPYag9pogokayFzTd6d8w2x12DjJsNokiAvIUoMIsUr3hVyKGjfGEtd9YH4ZIrItysRbTbYPRPdf64-Z-8KywzXhZUGAho2RFxA_mdBVkImSm6seWkZ9QhnkHDmjh6L4YI7pZXVYFyNgJ1XxA");

//		List<Device> availableDevices = user.getAvailableDevices().build().execute();
//		availableDevices.forEach(System.out::println);
//
//		CurrentlyPlayingContext context = user.getCurrentPlaybackInformation().build().execute();
//		System.out.println(context);
//
//		// TODO: Make another account to test this
//		CursorBasedPaging<PlayHistory> recentlyPlayedTracks = user.getRecentlyPlayedTracks().limit(10).build().execute();
//		System.out.println(recentlyPlayedTracks);
//
//		CurrentlyPlaying currentlyPlaying = user.getCurrentlyPlayingTrack().build().execute();
//		System.out.println(currentlyPlaying);

//		user.pauseTrack().device("CHILLBOX").build().execute();

		user.seekTrack().device("CHILLBOX").position(0).build().execute(statusCode -> {
			if (statusCode == 204) {
				System.out.println("Seeking");
			} else {
				System.out.println("Failed");
			}

			return null;
		});
	}
}