package me.chill.sample;

import me.chill.SpotifyUser;
import me.chill.authentication.SpotifyAuthenticationComponent;
import me.chill.authentication.SpotifyAuthenticationHelper;
import me.chill.authentication.SpotifyAuthorizationFlow;
import me.chill.exceptions.SpotifyAuthenticationException;

import java.util.List;
import java.util.Map;

public class AuthorizationFlowDemo {
	public static void main(String[] args) {
		String redirectUrl = "https://woojiahao.github.io";
		String clientId = "cea6a21eeb874d1d91dbaaccce0996f3";
		String clientSecret = "14371e50d4c8423fa5d3804bd8f975b9";

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

		if (user == null) return;

		List<String> genres = user.getAvailableGenreSeeds().build().execute();
		genres.forEach(System.out::println);

//		user.disableTimer();
	}

	private static SpotifyUser getAuthorization(SpotifyAuthorizationFlow flow) {
		String retrievedUrl = "https://woojiahao.github.io/?code=AQCDXNNdRuCU2s_6mSs_ZdrfBGk21DznGJGBWWPPq8DTnM30fIuciBHanfqZO1jO3dmKU1OkhlOLjw8KYfn9C570jC6HIXRJZtrdcxpvRA-knCt13_emHXdLM13M7mq0B-kmPGTJZE8ZroxRMS1QzmYi-aPh5hin69ZRup_tednAv_AgCl7yrnifpbDCKgVlafht";

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
