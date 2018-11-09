package me.chill.sample.queries;

import me.chill.SpotifyUser;

public class UserStore {
  private static String accessToken = "BQCtE_GWsCL0IJyLdPjz9ljdeM8Wt5RlpUxTmYUEV_VRspGhcmFNko84KFBHAeBNSvNjcIxeOu6NbERzo00p9oX4xqFu9jD48mXKZFiZzTqXN7j_AUQSX0iqOdYm_1nsqLWCKbv66laEfSP4GtMPunOrAjQGKfPTDmY9uu2ppSoIB_L-EBoZQ6K7jwMlREywViywJMu86aNoO2jwQmZZubgkC-l1F5obKC1yJ8f_agI126BPlfbSVSz4xxL2nY3n-sIcI2LVZbdsH07VuRbwitAdjq8";

  public static SpotifyUser user = new SpotifyUser(accessToken);
}
