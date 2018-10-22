package me.chill.sample;

import me.chill.SpotifyUser;
import me.chill.authentication.SpotifyAuthenticationHelper;
import me.chill.authentication.SpotifyScope;
import me.chill.queries.follow.IsFollowingQuery;

import java.util.Map;

public class FollowQueryDemo {
	public static void main(String[] args) {
		SpotifyUser customUser = new SpotifyUser(
			"cea6a21eeb874d1d91dbaaccce0996f3",
			"14371e50d4c8423fa5d3804bd8f975b9",
			"BQDRi-HmzJ5qQBU1Bas4eFIed5VLXcnqcedTnvpUNGI2kK4iXJcJxg2CZSj8-sZImsZ72QR-2l3mqmWqHbAGnT1gtJlrDEV77ZNPfON_rte4Ss4EhxsQTU2uSy4WNBKtllHAGARIjsAiF4kfa21JvlnFSZrN",
			null,
			null);

		Map<String, Boolean> isFollowingMap = customUser
			.isFollowing()
			.type(IsFollowingQuery.UserType.Artist)
			.addId("74ASZWbe4lXaubB36ztrGX")
			.addId("74ASZWbe4lXaubB36ztrGX")
			.build()
			.execute();
		isFollowingMap.forEach((key, value) -> {
			System.out.println("User is " + value + " user: " + key);
		});
	}
}
