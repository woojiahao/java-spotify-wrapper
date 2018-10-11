# Authentication Guide
Spotify offers 3 forms of user authentication that each have their own applications. The 
[Spotify Web API documentation](https://developer.spotify.com/documentation/general/guides/authorization-guide) goes 
through each of them in detail.

As the official documentation covers the intricacies of the authentication process in terms of web calls, the processes
described here will only cover the steps the library offers handling for.

## Authorization Flow
**Benefits:**

* Persistent session for the user as a refresh token is available
* Users do not have to re-authorize their application once their token expires

**Drawbacks:**

* Tedious process

### Process
#### Getting User's Authorization
Users have to authorize for your application to access parts of their Spotify account, as defined by your selected 
[scopes.](https://developer.spotify.com/documentation/general/guides/scopes/)

All authentication processes will take in a `SpotifyAuthenticationHelper` instance, which holds on to any key information
needed to begin authentication.

You can retrieve the authorization url to redirect users to via the `.generateLoginUrl()` method.

**Note:** At the very least, the **Client ID**, **Client Secret** and **Redirect URL** *must* be set for the helper, 
otherwise, an unchecked `SpotifyAuthenticationException` is thrown and the program is terminated.

**Usage:**
```java
SpotifyAuthenticationHelper helper = new SpotifyAuthenticationHelper.Builder()
                                .setClientId("cea6a21eeb874d1d91dbaaccce0996f3")
                                .setClientSecret("599adc099986415db14142c7de6a023b")
								.setRedirectUrl("https://woojiahao.github.io")
								.build();

SpotifyAuthorizationFlow flow = new SpotifyAuthorizationFlow(helper);
URL url = flow.generateLoginUrl();
System.out.println(url);
```

#### Retrieving Authorization Code
When the user has authorized your application, they will be redirected to the redirect URL with some parameters that 
dictate the state of the authorization.

When parsing, the library performs a set of checks and if everything is successful, the components of the parsed result
is accessible via a Map, with keys corresponding to the part of the parsed result, with the **authorization code** available
using the `SpotifyAuthorizationFlow.ParseComponent.Code` key.

**Note:** `SpotifyAuthenticationException` will be thrown if there are problems detected when parsing the authentication
url, the conditions that cause these exceptions are below:

| Error | Sent State | Recevied State | Code  | Exception Reason                                                                                                             |
| :---: | :--------: | :------------: | :---: | ---------------------------------------------------------------------------------------------------------------------------- |
| ✓     | -          | -              | -     | If there is an error detected, there was an issue with the authentication process or the user denied authorization           |
| -     | -          | -              | -     | There is no code given even when there were no errors to occur, this is a problem of the Spotify Web API, report it as a bug |
| -     | ✓          | ✓              | -     | The state that was sent does not correspond with the state that was received                                                 |
| -     | ✓          | -              | -     | There was no state received even when a state was sent                                                                       |

**Usage:**

```java
Map<SpotifyAuthorizationFlow.ParseComponent, String> authorizationParseMap = null;
try {
    authorizationParseMap = flow.parseAuthorizationUrl(retrievedUrl);
} catch (SpotifyAuthenticationException e) {
    if (e.getParseComponentFail() != null) {
        if (e.getParseComponentFail() == SpotifyAuthorizationFlow.ParseComponent.Error) {
            System.out.println("You cannot use the application without authorizing Spotify access");
        }
    }
}

String authorizationCode = authorizationParseMap.get(SpotifyAuthorizationFlow.ParseComponent.Code);
```

#### Getting Access Token 
With the authorization code, you can retrieve an access token for the user.

This can be done using the `.exchangeAuthorizationCode()` method, which performs attempts to exchange the authorization 
code and if successful, will return a Map that contains the key components of the exchange, like the **access token**, 
**refresh token** and **expiry duration**.

Each of the components can be accessed used an `SpotifyAuthorizationFlow.ExchangeComponent` constant.

**Note:** `SpotifyAuthenticationException` thrown when there is an error code found with the exchange process.

**Usage:**

```java
Map<SpotifyAuthorizationFlow.ExchangeComponent, String> exchangeInfo = null;
try {
    exchangeInfo = flow.exchangeAuthorizationCode(authorizationCode);
} catch (SpotifyAuthenticationException e) {
    e.printStackTrace();
}

String accessToken = exchangeInfo.get(SpotifyAuthorizationFlow.ExchangeComponent.AccessToken);
```

#### Creating a Spotify User
Once you've successfully exchanged an access token, you can then create a `SpotifyUser` object and begin accessing the 
API.

**Usage:**
```java
if (exchangeInfo != null) {
    SpotifyUser user = flow.generateSpotifyUser(exchangeInfo);

    System.out.println("Access Token:" + user.getAccessToken());
    System.out.println("Refresh Token:" + user.getRefreshToken());
}
```