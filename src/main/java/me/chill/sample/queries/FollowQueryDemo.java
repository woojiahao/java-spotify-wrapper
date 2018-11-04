package me.chill.sample.queries;

import me.chill.SpotifyUser;
import me.chill.queries.follow.UserType;

import java.util.Map;

// TODO: Create a secondary account to test this
public class FollowQueryDemo {
  public static void main(String[] args) {
    SpotifyUser user = new SpotifyUser("BQBtVlAtzaNHAeD-6fhN0NIhSgRO8WlDt8mGurTX-YrLpjgij3CVLvU3Jq3KqEgjLmBmYEP2EShM0D2zgOtMMkoQSbvDiDTcD5RzXt2X93gbN7BLskRGWvFr61-8XXO_EnxparOxLHYsMf-d4cIoH-WwEa7aPcARGaPE3_GCK2VAKvvcyscs1KvAhO38kcjMef1i7yNAutZWsZVZrzF1tFrg2Xcsb7LPrChLqXAIDHdsZkMJa5YidCe5r7Ib-Rhaes3pbJEfCPrstH_qjZPf6g");

    Map<String, Boolean> isFollowingMap = user
      .isFollowingUserOrArtist(UserType.Artist)
      .addId("74ASZWbe4lXaubB36ztrGX")
      .addId("74ASZWbe4lXaubB36ztrGX")
      .build()
      .execute();
    isFollowingMap.forEach((key, value) -> {
      System.out.println("User is " + value + " user: " + key);
    });

    Map<String, Boolean> areUserFollowing = user.areUsersFollowingPlaylist("2v3iNvBX8Ay1Gt2uXtUKUT")
      .addUser("_woojiahao_,_woojiahao_")
      .build()
      .execute();
    areUserFollowing.forEach((key, value) -> {
      System.out.println("User " + key + " is " + value);
    });


  }
}
