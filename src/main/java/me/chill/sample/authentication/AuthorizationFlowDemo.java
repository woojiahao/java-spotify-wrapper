package me.chill.sample.authentication;

import me.chill.SpotifyUser;
import me.chill.authentication.SpotifyAuthenticationComponent;
import me.chill.authentication.SpotifyAuthenticationHelper;
import me.chill.authentication.SpotifyAuthorizationFlow;
import me.chill.authentication.SpotifyScope;
import me.chill.exceptions.SpotifyAuthenticationException;

import java.util.List;
import java.util.Map;

public class AuthorizationFlowDemo {
  public static void main(String[] args) {
    String redirectUrl = "https://woojiahao.github.io";
    String clientId = "cea6a21eeb874d1d91dbaaccce0996f3";
    String clientSecret = "96729e5f290e496d8115d9e0bf27e515";

    SpotifyAuthenticationHelper helper = new SpotifyAuthenticationHelper.Builder()
      .clientId(clientId)
      .clientSecret(clientSecret)
      .redirectUrl(redirectUrl)
      .showDialog(true)
      .addScope(SpotifyScope.PlaybackRead)
      .build();

    SpotifyAuthorizationFlow flow = new SpotifyAuthorizationFlow(helper);


    String authorizationUrl = flow.generateAuthorizationUrl().toString();
    System.out.println(authorizationUrl);

    SpotifyUser user = getAuthorization(flow);

    if (user == null) return;

    List<String> genres = user.getAvailableGenreSeeds().build().execute();
    genres.forEach(System.out::println);

    System.out.println(user.getAccessToken());

    user.disableTimer();
  }

  private static SpotifyUser getAuthorization(SpotifyAuthorizationFlow flow) {
    String retrievedUrl = "https://woojiahao.github.io/?code=AQCXZjzK2dcsa4cYLziZJlwCi_CeYmRK9FfC0FyTf6ZqNxpFZeQDOCDulGYXMYxZu5JAj56_EYAuz6ZfHO7jS3jN_WfGjTz2OJ15722QeQ6YJo1ayhtL2UlVJA8_Veax2ior0Vg0IQ7S5inEwHAFedoiFM-Oohfn7M7wn3lVrqHyXDvNg2RqtzX-6FB4f4MwnW4PTy2JMy8C02Kj77lkHOg5zYnnEH2NtaItdJo";

    Map<SpotifyAuthorizationFlow.ParseComponent, String> authorizationParseMap = null;
    try {
      authorizationParseMap = flow.parseAuthorizationUrl(retrievedUrl);
    } catch (SpotifyAuthenticationException e) {

      if (e.getAuthorizationFlowComponentFail() != null) {
        if (e.getAuthorizationFlowComponentFail() == SpotifyAuthorizationFlow.ParseComponent.Error) {
          System.out.println("You cannot use the application without authorizing Spotify models");
        }
      }
    }

    if (authorizationParseMap != null) {
      String authorizationCode = authorizationParseMap.get(SpotifyAuthorizationFlow.ParseComponent.Code);

      Map<SpotifyAuthenticationComponent, String> exchangeInfo = null;
      try {
        exchangeInfo = flow.exchangeAuthorizationCode(authorizationCode);
      } catch (SpotifyAuthenticationException e) {
        e.printStackTrace();
      }

      if (exchangeInfo != null) {
        return flow.generateSpotifyUser(exchangeInfo);
      }
    }

    return null;
  }
}
