import me.chill.SpotifyUser;
import me.chill.authentication.SpotifyAuthenticationComponent;
import me.chill.authentication.SpotifyAuthenticationHelper;
import me.chill.authentication.SpotifyClientCredentialFlow;
import me.chill.authentication.SpotifyAuthenticationException;

import java.util.Map;

public class ClientCredentialFlowDemo {
	public static void main(String[] args) {
		String clientId = "cea6a21eeb874d1d91dbaaccce0996f3";
		String clientSecret = "d4c86028bf4c4ac18938570c7fc9139f";

		/*
		Create a SpotifyAuthenticationHelper instance to hold onto the information that will be used during authentication.
		Depending on the authentication method used, some fields must be set, more about this can be found at <url>.
		 */
		SpotifyAuthenticationHelper helper = new SpotifyAuthenticationHelper.Builder()
			.setClientId(clientId)
			.setClientSecret(clientSecret)
			.build();

		/*
		Since we are going to engage the client credentials method Spotify provides, create an instance of the flow and
		supply the helper information.

		Checking of the required helper information is done at this stage

		REQUIRED FIELDS:
		- Client ID
		- Client Secret
		 */
		SpotifyClientCredentialFlow flow = new SpotifyClientCredentialFlow(helper);

		/*
		.requestAuthentication() returns a map with the access token and expiry duration of that token
		 */
		Map<SpotifyAuthenticationComponent, String> authenticationMap = null;
		try {
			authenticationMap = flow.requestAuthentication();
		} catch (SpotifyAuthenticationException e) {
			e.printStackTrace();
		}

		/*
		Generate a SpotifyUser instance to begin accessing the API
		 */
		if (authenticationMap != null) {
			SpotifyUser user = flow.generateSpotifyUser(authenticationMap);

			System.out.println("Access: " + user.getAccessToken());
		}
	}
}