package me.chill.sample.queries;

import static me.chill.sample.queries.UserStore.user;

public class TrackQueryDemo {
  public static void main(String[] args) {
    user
      .getTrackAudioAnalysis("spotify:track:2iUmqdfGZcHIhS3b9E9EWq")
      .build()
      .executeAsync(analysis -> {
        System.out.println("Received analysis");
        System.out.println(analysis);
        return null;
      });
  }
}