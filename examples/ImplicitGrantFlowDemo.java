import me.chill.SpotifyUser;
import me.chill.authentication.SpotifyAuthenticationComponent;
import me.chill.authentication.SpotifyAuthenticationException;
import me.chill.authentication.SpotifyAuthenticationHelper;
import me.chill.authentication.SpotifyImplicitGrantFlow;

import java.net.MalformedURLException;
import java.util.Map;

public class ImplicitGrantFlowDemo {
	public static void main(String[] args) {
		String redirectUrl = "https://woojiahao.github.io";
		String clientId = "cea6a21eeb874d1d91dbaaccce0996f3";

		SpotifyAuthenticationHelper helper = new SpotifyAuthenticationHelper.Builder()
			.setClientId(clientId)
			.setRedirectUrl(redirectUrl)
			.setShowDialog(true)
			.build();

		/*
		Since we are using the implicit grant flow, create an instance of it

		REQUIRED FIELDS:
		- Client ID
		- Redirect URL
		 */
		SpotifyImplicitGrantFlow flow = new SpotifyImplicitGrantFlow(helper);

		/*
		Use .generateAuthorizationUrl() to retrieve the URL to display to the users
		 */
		String url = flow.generateAuthorizationUrl().toString();
		System.out.println(url);

		/*
		Parse the resulting URL after the user decides to allow/deny your application
		 */
		Map<SpotifyAuthenticationComponent, String> info = null;
		try {
			info = flow.extractAuthorizationInfo("https://woojiahao.github.io/#access_token=BQALd4OROM04sKufsLZLHFhKfTEpjm51ZfV9I1fLWAuMwEwyA5Piu0-kfr7EV-xM8e8R3byk-SEsQuk2iCTRnL1i4QUujLJQ3bz51rsKfbofJwt5PrtbHhDL8Z_s_NE2eVguRf6ijmYiKPkkyDNLutP1BcjvRQI&token_type=Bearer&expires_in=3600");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SpotifyAuthenticationException e) {
			if (e.getImplicitGrantFlowComponentFail() != null) {
				if (e.getImplicitGrantFlowComponentFail() == SpotifyImplicitGrantFlow.ParseComponent.Error) {
					System.out.println("Cannot continue without your authorization");
				}
			}
			e.printStackTrace();
		}

		/*
		You can generate a spotify user from the previously extracted user and begin using the API
		 */
		if (info != null) {
			SpotifyUser user = flow.generateSpotifyUser(info);

			System.out.println("Access: " + user.getAccessToken());
		}
	}
}
