package me.chill.sample.queries;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import me.chill.SpotifyUser;
import me.chill.exceptions.SpotifyQueryException;
import me.chill.models.*;

import java.util.List;

class PlayerQueryDemo {
	public static void main(String[] args) {
		SpotifyUser user = new SpotifyUser("BQDqToxD0wqEtGLODiAmialTqpHL31110wNbWpHbieII-1KOp8IRa7QbWVDRrr2tyUdOEAMlaa5geuUyB-y5ZQfjvZFLgITovDz6Y9kznMw8afZzwoRaJkH9SQIl2d7WX44I7rvddrJKnz8iaahnALFgHYXaFxH-JtdtpWn_Q9r1X6-cgiFM92dxvCBNe_40YmpTwpwesoC-IuF_ide0WxuSz9AHd-1AIlub6dtrbhIPLNmElfVptHST2lhD-aCqwZCKZTIUAql4IQjEE4lxrg");

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

		try {
			user.seekTrack().device("CHILLBOX").position(0).build().execute();
		} catch (SpotifyQueryException e) {
			e.printStackTrace();
		}
	}
}