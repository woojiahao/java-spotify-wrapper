package me.chill.sample;

import me.chill.SpotifyUser;
import me.chill.authentication.SpotifyAuthenticationComponent;
import me.chill.authentication.SpotifyAuthenticationHelper;
import me.chill.authentication.SpotifyAuthorizationFlow;
import me.chill.authentication.SpotifyAuthenticationException;

import java.util.Map;

public class AuthorizationFlowDemo {
	public static void main(String[] args) {
		String redirectUrl = "https://woojiahao.github.io";
		String clientId = "cea6a21eeb874d1d91dbaaccce0996f3";
		String clientSecret = "d4c86028bf4c4ac18938570c7fc9139f";

		SpotifyAuthenticationHelper helper = new SpotifyAuthenticationHelper.Builder()
			.setClientId(clientId)
			.setClientSecret(clientSecret)
			.setRedirectUrl(redirectUrl)
			.setShowDialog(true)
			.build();

		SpotifyAuthorizationFlow flow = new SpotifyAuthorizationFlow(helper);


		String authorizationUrl = flow.generateAuthorizationUrl().toString();
		System.out.println(authorizationUrl);

		String retrievedUrl = "https://woojiahao.github.io/?code=AQBgedOxtNuz-Au1TqPlatJTy_R-4u7qQrPUBGwsQrmHiDf_fLX1xjdhD7gJU4Qu8GwMCBSCfkCmAkUQ5YGUPJLPygQA9lDkacLE6DGYtLJfCxyR2tOFa9RV8wkWw1ZEXp64RLcW7Fp80fplNyVUfRuiUdZhF-ETiGOP2FlDGF4FRDTXlMWgGb6lnBh9PBuu-ZKt";


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
				SpotifyUser user = flow.generateSpotifyUser(exchangeInfo);

				System.out.println("Access Token:" + user.getAccessToken());
				System.out.println("Refresh Token:" + user.getRefreshToken());
			}
		}
	}
}
