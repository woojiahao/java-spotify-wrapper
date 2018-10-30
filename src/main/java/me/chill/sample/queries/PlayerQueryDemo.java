package me.chill.sample.queries;

import me.chill.SpotifyUser;
import me.chill.models.CurrentlyPlayingContext;
import me.chill.models.CursorBasedPaging;
import me.chill.models.Device;
import me.chill.models.PlayHistory;

import java.util.List;

class PlayerQueryDemo {
	public static void main(String[] args) {
		SpotifyUser user = new SpotifyUser("BQBtVlAtzaNHAeD-6fhN0NIhSgRO8WlDt8mGurTX-YrLpjgij3CVLvU3Jq3KqEgjLmBmYEP2EShM0D2zgOtMMkoQSbvDiDTcD5RzXt2X93gbN7BLskRGWvFr61-8XXO_EnxparOxLHYsMf-d4cIoH-WwEa7aPcARGaPE3_GCK2VAKvvcyscs1KvAhO38kcjMef1i7yNAutZWsZVZrzF1tFrg2Xcsb7LPrChLqXAIDHdsZkMJa5YidCe5r7Ib-Rhaes3pbJEfCPrstH_qjZPf6g");

		List<Device> availableDevices = user.getAvailableDevices().build().execute();
		availableDevices.forEach(System.out::println);

		CurrentlyPlayingContext context = user.getCurrentPlaybackInformation().build().execute();
		System.out.println(context);

		// TODO: Make another account to test this
		CursorBasedPaging<PlayHistory> recentlyPlayedTracks = user.getRecentlyPlayedTracks().limit(10).build().execute();
		System.out.println(recentlyPlayedTracks);
	}
}