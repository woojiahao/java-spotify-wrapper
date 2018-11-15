package me.chill.sample.queries;

import me.chill.queries.follow.UserType;

import java.util.Map;

import static me.chill.sample.queries.UserStore.user;

// TODO: Create a secondary account to test this
public class FollowQueryDemo {
  public static void main(String[] args) {
    Map<String, Boolean> isFollowingMap = user
      .isFollowingUserOrArtist(UserType.Artist)
      .addIds("74ASZWbe4lXaubB36ztrGX", "74ASZWbe4lXaubB36ztrGX")
      .build()
      .execute();
    isFollowingMap.forEach((key, value) -> {
      System.out.println("User is " + value + " user: " + key);
    });

    Map<String, Boolean> areUserFollowing = user.areUsersFollowingPlaylist("2v3iNvBX8Ay1Gt2uXtUKUT")
      .addUsers("_woojiahao_,_woojiahao_")
      .build()
      .execute();
    areUserFollowing.forEach((key, value) -> {
      System.out.println("User " + key + " is " + value);
    });
  }
}
