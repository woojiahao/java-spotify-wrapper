package me.chill.sample.queries;

import me.chill.SpotifyUser;

public class UserStore {
  private static String accessToken = "BQAATALDVWOEK9YM1TUpM0QXqfQCko7oAhMMqHJHJAAHEKbEw6mSFsP1oGF68mk84AmTqlUm3YLypwKqDM8ozNChuWIO2K3eZcXRSnJOd_jnXIXyj65aPHSNIvUU4hczkvBE620w2LKhbBmfnfFapbavLD_K4Gx2ztRIrfJKRl4Cet_eWI62cLkmYxYPA6E0FWA1HFH4fbY71cd38sDEXXDDRyUAC6aW-nAyYhuDe7nM1fTQgOBRwaHnsd-TB69s-Bi5tIq4Xn3A7QHqgJPtEQ";

  public static SpotifyUser user = new SpotifyUser(accessToken);
}
