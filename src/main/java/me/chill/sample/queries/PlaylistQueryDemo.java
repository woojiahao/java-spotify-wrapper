package me.chill.sample.queries;

import kotlin.Pair;

import static me.chill.sample.queries.UserStore.user;

public class PlaylistQueryDemo {
	public static void main(String[] args) {
		Pair<Boolean, String> result = user
			.addTrackToPlaylist("7Ga1gkkVHTTX5LJlTcGPKs")
			.addUri("spotify:track:2iUmqdfGZcHIhS3b9E9EWq")
			.position(1)
			.build()
			.execute();
		System.out.println(result.getFirst());
		System.out.println(result.getSecond());
	}
}
