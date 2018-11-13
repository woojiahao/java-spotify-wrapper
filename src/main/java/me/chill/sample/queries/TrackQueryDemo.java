package me.chill.sample.queries;

import java.util.Map;

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

    user
      .getSingleTrackAudioFeatures("2iUmqdfGZcHIhS3b9E9EWq")
      .build()
      .executeAsync(features -> {
        System.out.println("Received features");
        System.out.println(features);
        return null;
      });

    user
      .getSeveralTracksAudioFeatures()
      .addTracks("2iUmqdfGZcHIhS3b9E9EWq", "0l4DTppOxy7NUaEcwXuOb6")
      .build()
      .executeAsync(results -> {
        for (Map.Entry item : results.entrySet()) {
          System.out.println("Key: " + item.getKey());
          System.out.println("Value: " + item.getValue());
        }
        return null;
      });
  }
}