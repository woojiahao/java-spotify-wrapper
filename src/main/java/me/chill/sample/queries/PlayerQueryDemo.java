package me.chill.sample.queries;

import me.chill.SpotifyUser;
import me.chill.exceptions.SpotifyQueryException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

class PlayerQueryDemo {
	public static void main(String[] args) {
		SpotifyUser user = new SpotifyUser("BQBwvNaFNT6ms7lttR9uk68iF2vCjnjbuyci_d5J5f5SCjeaj-lKyXDaM9qSKB-Thg4bdsoXBfTtON5P2r52EVkqFrGTKVobfmXNAcxdEIdNfK5aVrrtaKI0oyBTEzbScP_Gk2syMdN_0s4ZwRiyK9TqUYI-ZA_Hf-C6qqN4O0F7zCeJs89dtQ69aSAFrxx23gvMmlnqfag4YVWKWCfeVOysbKIXhx886PkISRO0YF8kBxe4sty0-F-8tNyaHyArGWyFHNeoMuMIqfLWj3dupw");

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


		IntStream.range(1, 10_000).forEach(i -> System.out.print("a"));
	}
}