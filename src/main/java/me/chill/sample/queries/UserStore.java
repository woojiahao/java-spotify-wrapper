package me.chill.sample.queries;

import me.chill.SpotifyUser;

public class UserStore {
  private static String accessToken = "BQAPx5ixDl6A0e_VTlIH8BJiaffG0marHn2EKeHuPe2b-nJxj4lDcktmUyP48DTNGadX3ikE5-lz4LgQ0-mUz0n68Kjm2k1WrMqOJ7qU0Y_nVRlXH8ysR-88uIGstKYKSyqACO-taEjccdraxpfz7knzzY6IX3KTQd-hnRFpha2X1w8YCBxHyYo2ldrHieypgdVYTXRd-OIsmx8J63mZ9QLQ-eY1XtchoskP7FqhKFMPdoHUgLcOW-5vQM0dAIHSWQoL-5jDrHmrc5uDdJTuxw";

  public static SpotifyUser user = new SpotifyUser(accessToken);
}
