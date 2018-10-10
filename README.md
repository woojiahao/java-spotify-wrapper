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

They do so by using a authorization URL that the library can generate for you based on several parameters you can configure.

```java
// First, create a helper that will hold on to the parameters to be used
SpotifyAuthenticationHelper helper = new SpotifyAuthenticationHelper.Builder()
									.setClientId("cea6a21eeb874d1d91dbaaccce0996f3")
									.setRedirectUrl("https://woojiahao.github.io")
									.addScope(SpotifyScope.EmailRead)
									.build;

// Then, pass this helper to the AuthorizationFlow tool
AuthorizationFlow flow = new AuthorizationFlow(helper);

// Retrieve the URL to allow users to authorize your application
String url = flow.loginUrl;
System.out.println(url);
```

#### Retrieving Access Token
When the user has authorized your application, they will be redirected to the redirect URL with some parameters that dictate the state of the authorization.

The library is able to parse this parameters and return you the meaningful set of information like the authorization token that can be exchanged for an access and refresh token.

```java
Map<AuthorizationComponent, String> state = flow.parseAuthorization("https://woojiahao.github.io/<parameters>");

if (state.get(AuthorizationComponent.Status).equals("false")) {
	System.out.println("User did not authorize");
} else {	
	String authorizationToken = state.get(AuthorizationComponent.Token);	
	String accessToken = helper.retrieveAccessToken(authorizationToken);
	System.out.println("User's access token is " + accessToken);
}
```

#### Refreshing Access Token
The library also provides an automated means of refreshing access tokens.

```java
helper.refreshToken();
```

### Implicit Grant
This method of user authentication is recommended if the information you want to retrieve from the user's account is one-time and you won't need a persistent connection.

## Code Structure
> TODO: Create a directory list for the structure of the code

## Contributing
> TODO: Create a code of conduct + contributing guide

## License
java-spotify-wrapper is licensed under the MIT license, more about that can be found [here.](https://opensource.org/licenses/MIT)