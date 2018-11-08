package me.chill.sample.queries;

import me.chill.SpotifyUser;

public class UserStore {
  private static String accessToken = "BQC1VKfCXAiNC6gWK-iDP5QaGHfJM7Qoy4PSolqbuExtfEv3YU6iUP1r5c59JkQ5CffCvCUx-KnuUedye7rVcH6yXhuzqXhFyeQdXC70wXsrvswtYJC7WWPTCHelPc9dRQrOoAG85wWSloEXlbfy3FENUb1_Pmsm8Kx1SWsaZeHoy_Dxxtl9dAHnw34n8oJUlmJaTumXs79DwSkOje_jjcznKrAKeyFGphXTLTONZxuUhy60a4oU3rWVTrU-j5SygaTbWthIMbhGVmsWlT0g_w";

  public static SpotifyUser user = new SpotifyUser(accessToken);
}
