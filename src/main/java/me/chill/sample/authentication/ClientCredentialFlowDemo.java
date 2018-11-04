package me.chill.sample.authentication;

import me.chill.SpotifyUser;
import me.chill.authentication.SpotifyAuthenticationComponent;
import me.chill.authentication.SpotifyAuthenticationHelper;
import me.chill.authentication.SpotifyClientCredentialFlow;
import me.chill.exceptions.SpotifyAuthenticationException;

import java.util.Map;

public class ClientCredentialFlowDemo {
  public static void main(String[] args) {
    String clientId = "cea6a21eeb874d1d91dbaaccce0996f3";
    String clientSecret = "d4c86028bf4c4ac18938570c7fc9139f";

    SpotifyAuthenticationHelper helper = new SpotifyAuthenticationHelper.Builder()
      .clientId(clientId)
      .clientSecret(clientSecret)
      .build();

    SpotifyClientCredentialFlow flow = new SpotifyClientCredentialFlow(helper);

    Map<SpotifyAuthenticationComponent, String> authenticationMap = null;
    try {
      authenticationMap = flow.requestAuthentication();
    } catch (SpotifyAuthenticationException e) {
      e.printStackTrace();
    }

    if (authenticationMap != null) {
      SpotifyUser user = flow.generateSpotifyUser(authenticationMap);

      System.out.println("Access: " + user.getAccessToken());
    }
  }
}