package me.chill.sample.queries;

import me.chill.SpotifyUser;

public class UserStore {
  private static String accessToken = "BQCSiVSMw0AE4YLXwegdmO8hmGgGYGo8xW8fxniphilhANi2ANnQbqxaKhv88ycQJrpRG3Irx5WSJJ-hVeFHCxWSG8vxdGjWDShmZdi3JCUT9uRaqedYUZJ39_buMKrFGzZAwjIagCINgOa6RyLEgZhmaVikx5n3j2yVvMwGuf66gc3CZ8QMnSzMYdEqF_0A095WplLnpXzcZRdr2aBaOBB-wEt2O1iwh93Bom2YEDnEQyjyfHC-Km6e1HYNP8angrjdB3HBglh-Gx8Fu0gGBPsg9fs";

  public static SpotifyUser user = new SpotifyUser(accessToken);
}
