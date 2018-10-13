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
[Example implementation of the authorization flow using the library](https://github.com/woojiahao/java-spotify-wrapper/blob/master/examples/AuthorizationFlowDemo.java)

#### Getting User's Authorization
Users have to authorize for your application to access parts of their Spotify account, as defined by your selected 
[scopes.](https://developer.spotify.com/documentation/general/guides/scopes/)

All authentication processes will take in a `SpotifyAuthenticationHelper` instance, which holds on to any key information
needed to begin authentication.

You can retrieve the authorization url to redirect users to via the `.generateAuthorizationUrl()` method.

**Note:** At the very least, the **Client ID**, **Client Secret** and **Redirect URL** *must* be set for the helper, 
otherwise, an unchecked `SpotifyAuthenticationException` is thrown and the program is terminated.

**Usage:**
```java
SpotifyAuthenticationHelper helper = new SpotifyAuthenticationHelper.Builder()
    .setClientId(clientId)
    .setClientSecret(clientSecret)
    .setRedirectUrl(redirectUrl)
    .setShowDialog(true)
    .build();

SpotifyAuthorizationFlow flow = new SpotifyAuthorizationFlow(helper);
URL authorizationUrl = flow.generateAuthorizationUrl();
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
    if (e.getAuthorizationFlowComponentFail() != null) {
        if (e.getAuthorizationFlowComponentFail() == SpotifyAuthorizationFlow.ParseComponent.Error) {
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

## Implicit Grant
**Benefits:**

* Simple
* No need for client secret

**Drawbacks:**

* No refresh token given

### Process
[Example implementation of the implicit grant using the library](https://github.com/woojiahao/java-spotify-wrapper/blob/master/examples/ImplicitGrantFlowDemo.java)

#### Getting User's Authorization
Much like the [authorization flow](authentication_guide.md?id=authorization-flow), you have to get the user's authorization
before being able to access the API on their behalf. This means you will need to display to the user, the authorization
screen, which can be generated using the `.generateAuthorizationUrl()` method.

**Note:** At the very least, the **Client ID** and **Redirect URL** *must* be set for the helper, 
otherwise, an unchecked `SpotifyAuthenticationException` is thrown and the program is terminated.

**Usage:**

```java
SpotifyAuthenticationHelper helper = new SpotifyAuthenticationHelper.Builder()
    .setClientId(clientId)
    .setRedirectUrl(redirectUrl)
    .setShowDialog(true)
    .build();

SpotifyImplicitGrantFlow flow = new SpotifyImplicitGrantFlow(helper);

URL url = flow.generateAuthorizationUrl();
System.out.println(url);
```

#### Retrieving Access Token
After you have gotten the user's authorization, you can then parse the result of that operation and extract the access 
token.

**Usage:**

```java
Map<SpotifyAuthenticationComponent, String> info = null;
try {
    info = flow.extractAuthorizationInfo(retrievedUrl);
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
```

#### Creating a Spotify User
Once you've successfully gotten the access token, you can then create a `SpotifyUser` object and begin accessing the 
API.

**Usage:**
```java
if (info != null) {
    SpotifyUser user = flow.generateSpotifyUser(info);

    System.out.println("Access Token:" + user.getAccessToken());
}
```

## Client Credential Flow
**Benefits:**

* No need for authorization
* Simple
* Ideal for server-to-server communications

**Drawbacks:**

* No refresh token given

### Process
[Example implementation of the client credential flow using the library](https://github.com/woojiahao/java-spotify-wrapper/blob/master/examples/ClientCredentialFlowDemo.java)

#### Getting Access Token
Since this flow of authentication is simple, there is no authorization step and thus, all you need to do to begin using
this form of authentication is to request for an access token.

This can be done using the `.requestAuthentication()` method which returns a map that contains the access token and expiry
duration of the token.

**Note:** `SpotifyAuthenticationException` thrown when the response invalid or there was an error with the client id or 
secret.

**Usage:**

```java
SpotifyAuthenticationHelper helper = new SpotifyAuthenticationHelper.Builder()
    .setClientId(clientId)
    .setClientSecret(clientSecret)
    .build();

SpotifyClientCredentialFlow flow = new SpotifyClientCredentialFlow(helper);

Map<SpotifyAuthenticationComponent, String> authenticationMap = null;
try {
    authenticationMap = flow.requestAuthentication();
} catch (SpotifyAuthenticationException e) {
    e.printStackTrace();
}
```

#### Creating a Spotify User
Once you've retrieved a valid access token, you can then generate a `SpotifyUser` to begin interacting with the API.

**Usage:**

```java
if (authenticationMap != null) {
    SpotifyUser user = flow.generateSpotifyUser(authenticationMap);

    System.out.println("Access: " + user.getAccessToken());
}
```