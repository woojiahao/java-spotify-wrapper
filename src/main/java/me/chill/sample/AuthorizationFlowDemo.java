package me.chill.sample;

import me.chill.SpotifyUser;
import me.chill.authentication.SpotifyAuthenticationComponent;
import me.chill.authentication.SpotifyAuthenticationHelper;
import me.chill.authentication.SpotifyAuthorizationFlow;
import me.chill.authentication.SpotifyAuthenticationException;

import java.util.Map;
import java.util.Scanner;

public class AuthorizationFlowDemo {
	public static void main(String[] args) {
		String redirectUrl = "https://woojiahao.github.io";
		String clientId = "cea6a21eeb874d1d91dbaaccce0996f3";
		String clientSecret = "34dfe5eb8e5b4766a6fb4d8103605baa";

		SpotifyAuthenticationHelper helper = new SpotifyAuthenticationHelper.Builder()
			.setClientId(clientId)
			.setClientSecret(clientSecret)
			.setRedirectUrl(redirectUrl)
			.setShowDialog(true)
			.build();

		SpotifyAuthorizationFlow flow = new SpotifyAuthorizationFlow(helper);


		String authorizationUrl = flow.generateAuthorizationUrl().toString();
		System.out.println(authorizationUrl);

		SpotifyUser user = getAuthorization(flow);
	}

	private static SpotifyUser getAuthorization(SpotifyAuthorizationFlow flow) {
		String retrievedUrl = "https://woojiahao.github.io/?code=AQDjm43-u-Uer4eMSDmsFCY9RrxbbIho4qrIZypZSnqABNRvkgStLdJ081p8jIVsBvok59n2jBFKLbtfCrW_rNspFo2QZY8QoRKOXt2Vv3lnnBAh7aj0KifmAyB0Vc39hOzvsIr0QkqqlzFzlBWbIR2pAaRQz3XZXmqMrfcJmwX5g_mzmeLqPbAThX_6MkVTF_ly";

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
