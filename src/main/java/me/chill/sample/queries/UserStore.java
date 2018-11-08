package me.chill.sample.queries;

import me.chill.SpotifyUser;

public class UserStore {
  private static String accessToken = "BQCG88Wi0awcx6zbluUWLoGLihjBT2qhfHZBbidcvVv9Ln38sOA-e-ROYgfWtjzodP-k0dVmF_i4_PhFwaZkUeuP2VeEtPUUfSTt6sw2JqN4yhpmwU9BeHiifnJEBQ6LJi1bzxfwYYkiG5ur0nRjQUBbF8nVkP4rL6R__h11sw3mlUxvpUQWnMoo_2t11ZmxW_KGtTq52zAfu30t3EJjLyHar3KzuGtlssc3eCnUuof0-XU1ganjyv4lGrSubEmo4q1L2HQ84IUj8eajd_XBCT143uwCW6jqfJBY6vX6";

  public static SpotifyUser user = new SpotifyUser(accessToken);
}
