package me.chill.sample.queries;

import me.chill.SpotifyUser;
import me.chill.queries.follow.IsFollowingUserOrArtistQuery;

import java.util.Map;

public class FollowQueryDemo {
	public static void main(String[] args) {
		SpotifyUser customUser = new SpotifyUser(
			"cea6a21eeb874d1d91dbaaccce0996f3",
			"14371e50d4c8423fa5d3804bd8f975b9",
			"BQDRi-HmzJ5qQBU1Bas4eFIed5VLXcnqcedTnvpUNGI2kK4iXJcJxg2CZSj8-sZImsZ72QR-2l3mqmWqHbAGnT1gtJlrDEV77ZNPfON_rte4Ss4EhxsQTU2uSy4WNBKtllHAGARIjsAiF4kfa21JvlnFSZrN",
			null,
			null);

		customUser.disableTimer();
		Map<String, Boolean> isFollowingMap = customUser
			.isFollowingUserOrArtist()
			.type(IsFollowingUserOrArtistQuery.UserType.Artist)
			.addId("74ASZWbe4lXaubB36ztrGX")
			.addId("74ASZWbe4lXaubB36ztrGX")
			.build()
			.execute();
		isFollowingMap.forEach((key, value) -> {
			System.out.println("User is " + value + " user: " + key);
		});

		Map<String, Boolean> areUserFollowing = customUser.areUsersFollowingPlaylist("2v3iNvBX8Ay1Gt2uXtUKUT")
			.addUser("_woojiahao_,_woojiahao_")
			.build().execute();
		areUserFollowing.forEach((key, value) -> {
			System.out.println("User " + key + " is " + value);
		});
	}
}
