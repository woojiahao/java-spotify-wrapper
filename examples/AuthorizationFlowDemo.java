import me.chill.SpotifyUser;
import me.chill.authentication.SpotifyAuthenticationHelper;
import me.chill.authentication.SpotifyAuthorizationFlow;

import java.util.Map;

public class AuthorizationFlowDemo {
	public static void main(String[] args) {
		String clientId = "cea6a21eeb874d1d91dbaaccce0996f3";
		String redirectUrl = "https://woojiahao.github.io";
		String clientSecret = "599adc099986415db14142c7de6a023b";

		/*
		Create a SpotifyAuthenticationHelper instance to hold onto the information that will be used during authentication.
		Depending on the authentication method used, some fields must be set, more about this can be found at <url>.
		 */
		SpotifyAuthenticationHelper helper = new SpotifyAuthenticationHelper.Builder()
			.setClientId(clientId)
			.setClientSecret(clientSecret)
			.setRedirectUrl(redirectUrl)
			.setShowDialog(true)
			.build();

		/*
		Since we are going to engage the authorization flow method Spotify provides, create an instance of the flow and
		supply the helper information.

		Checking of the required helper information is done at this stage

		REQUIRED FIELDS:
		- Client ID
		- Client Secret
		- Redirect URL
		 */
		SpotifyAuthorizationFlow flow = new SpotifyAuthorizationFlow(helper);


		/*
		.generateLoginUrl() returns a URL object that holds the URL for the user to authorize your application.

		This is the first step of the authentication flow which is to gain the user's authorization.
		 */
		String authorizationUrl = flow.generateLoginUrl().toString();
		System.out.println(authorizationUrl);

		/*
		Depending on how you handle the user's authorization step, you should receive a redirect URL that contains several
		query parameters.
		 */
		String retrievedUrl = "https://woojiahao.github.io/?code=AQCrtYCcm4keCE7-oPBav8IO1lmRp6yRHvGAGYLBMVNpYr6pE4Zdk9vQNYzTYjG9CIf3onx3_Q2PKZEbGTIH75q1KtMApUXcUM6ZwJPZ4m9og2bRaQwZ28_MTjtN6MT9aU65pJwVGUyrgzL_uo0vfqU8grUuhLXkB3NkLCq10uzkSnU98xKaparFb-Y6L3dMie8v"

		/*
		.parseAuthorizationUrl() returns a map of the components of the retrieved URL

		This includes the authorization code that can be used to exchange for an access token.

		This will also throw a checked exception for when there was an error that occurred with the retrieval of the URL,
		some of the conditions checked for are error codes, state differences and missing codes.
		 */
		Map<SpotifyAuthorizationFlow.Component, String> authorizationParseMap = null;
		try {
			authorizationParseMap = flow.parseAuthorizationUrl(retrievedUrl);
		} catch (SpotifyAuthenticationException e) {
			/*
			Exception object holds onto what part of the parsing failed, which might be useful when you wish to warn users
			that they might not be able to use your application when they do not authorize.
			 */
			if (e.getComponentFail() != null) {
				if (e.getComponentFail() == SpotifyAuthorizationFlow.Component.Error) {
					System.out.println("You cannot use the application without authorizing Spotify access");
				}
			}
		}

		/*
		After the authorization token is successfully retrieved, you can request for an access token from the API.
		 */
		if (authorizationParseMap != null) {

			// The authorization code can be extracted using the Code constant
			String authorizationCode = authorizationParseMap.get(SpotifyAuthorizationFlow.Component.Code);

			/*
			.getAccessInfo() can be used to retrieve information about the attempt to exchange tokens.

			Throws an exception if the exchange attempt fails.
			 */
			Map<SpotifyAuthorizationFlow.AccessInfo, String> accessInfo = null;
			try {
				accessInfo = flow.getAccessInfo(authorizationCode);
			} catch (SpotifyAuthenticationException e) {
				e.printStackTrace();
			}

			/*
			Lastly, to begin accessing endpoints of the API using the library, generate a SpotifyUser object, giving it
			the access info map created from exchanging tokens
			 */
			if (accessInfo != null) {
				SpotifyUser user = flow.generateSpotifyUser(accessInfo);

				System.out.println("Access Token:" + user.getAccessToken());
				System.out.println("Refresh Token:" + user.getRefreshToken());

			}
		}
	}
}
