# Java Spotify Wrapper
Java wrapper for the [Spotify web API](https://developer.spotify.com/documentation/web-api/)

## Contents

1. [Anonymous Access](https://github.com/woojiahao/java-spotify-wrapper#anonymous-access)
2. [User Authentication](https://github.com/woojiahao/java-spotify-wrapper#user-authentication)
  
    1. [Authorization Flow](https://github.com/woojiahao/java-spotify-wrapper#authorization-flow)
    2. [Implicit Grant](https://github.com/woojiahao/java-spotify-wrapper#implicit-grant)
    3. [Client Credentials Flow](https://github.com/woojiahao/java-spotify-wrapper#client-credentials-flow)

3. [Code Structure](https://github.com/woojiahao/java-spotify-wrapper#code-structure)
4. [Contributing](https://github.com/woojiahao/java-spotify-wrapper#contributing)
5. [License](https://github.com/woojiahao/java-spotify-wrapper#license)

## Anonymous Access
Some of Spotify's Web API is available to users even without an access token or performing any form of user authentication,
the library supports the access of these resources.

## User Authentication
The Spotify Web API offers 3 methods of user authentication that can be used by developers, these methods are all explained
in detail in their official [authentication guide.](https://developer.spotify.com/documentation/general/guides/authorization-guide/)

The library offers means to authenticate users in all 3 ways. 

### Authorization Flow
This form of authorization is used when you wish to have a persistent session for the user and want the user to be 
able to continually access the API without having to authorize themselves everytime their access token is expired.

The process is the most complex out of the three, but the library is able to assist you in easily gaining accessing to and
managing the user's access token and refresh token.

#### Getting User's Authorization
Users have to authorize for your application to access parts of their Spotify account, as defined by your selected 
[scopes.](https://developer.spotify.com/documentation/general/guides/scopes/)

**Note:** At the very least, the **Client ID** and **Redirect URL** *must* be set for the helper, otherwise, a 
`SpotifyAuthenticationException` is thrown.

**Usage:**
```java
// First, create a helper that will hold on to the parameters to be used
SpotifyAuthenticationHelper helper = new SpotifyAuthenticationHelper.Builder()
								.setClientId("cea6a21eeb874d1d91dbaaccce0996f3")
								.setRedirectUrl("https://woojiahao.github.io")
								.build();

SpotifyAuthorizationFlow flow = new SpotifyAuthorizationFlow(helper);

// Retrieve the URL to allow users to authorize your application
URL url = flow.generateLoginUrl();

// The url will be:
// https://accounts.spotify.com/authorize
//  ?client_id=cea6a21eeb874d1d91dbaaccce0996f3
//  &redirect_uri=https%3A%2F%2Fwoojiahao.github.io
//  &response_type=code
System.out.println(url);
```

#### Retrieving Access Token
When the user has authorized your application, they will be redirected to the redirect URL with some parameters that 
dictate the state of the authorization.

**Note:** `SpotifyAuthenticationException` will be thrown if there are problems detected when parsing the authentication
url, the conditions that cause these exceptions are below:

|Error|Sent State|Recevied State|Code|Exception Reason|
|---|---|---|---|---|
|`✓`|`-`|`-`|`-`|If there is an error detected, there was an issue with the authentication process or the user denied authorization|
|`-`|`-`|`-`|`-`|There is no code given even when there were no errors to occur, this is a problem of the Spotify Web API, report it as a bug|
|`-`|`✓`|`✓`|`-`|The state that was sent does not correspond with the state that was received|
|`-`|`✓`|`-`|`-`|There was no state received even when a state was sent|

**Usage:**

```java

try {
	Map<SpotifyAuthorizationFlow.Component, String> status = 
		flow.parseAuthorizationUrl("https://woojiahao.github.io/<parameters>");

	String authorizationToken = status.get(SpotifyAuthorizationFlow.Component.Code);
	String accessToken = flow.retrieveAccessToken(authorizationToken);
	System.out.println("User's access token is " + accessToken);
} catch (SpotifyAuthenticationException e) {
	e.printStackTrace();
}
```

#### Refreshing Access Token
The library also provides an automated means of refreshing access tokens.

```java
flow.refreshToken();
```

### Implicit Grant
This method of user authentication is recommended if the information you want to retrieve from the user's account is 
one-time and you won't need a persistent connection.

## Code Structure
> TODO: Create a directory list for the structure of the code

## Contributing
> TODO: Create a code of conduct + contributing guide

## License
java-spotify-wrapper is licensed under the MIT license, more about that can be found [here.](https://opensource.org/licenses/MIT)