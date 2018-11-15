package me.chill.sample.queries;

import me.chill.SpotifyUser;

public class UserStore {
  private static String accessToken = "BQAUeLtY0bT_WCO40GXxiEffgz2BoGWMVVdpvpWZFBq93scM1ZC6HFbQuHeMfhsI8_k0TKxE8UBeFRTNWqRWhIEL_v19AWGE32ZMCX0gp2lB-FHKmdOqTHVUf8yIs68v_vuS-DlDAGslBg0Pi5G_vxGF5z-bgF1ggvSKk1PgAWOhWsY9d9aoVfxUbhYf8TwxnLxOmYqargLL0xxE9h3Zt0lXX1I9evZVP-upWWLZVKgPJ9JLG6mmcEVehYrYj6qgRuH1kTK5E1rFMWqSM2k8p7WloGY";

  public static SpotifyUser user = new SpotifyUser(accessToken);
}
