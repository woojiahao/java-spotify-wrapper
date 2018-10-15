# Java Spotify Wrapper
Simple to use Java wrapper for the [Spotify web API](https://developer.spotify.com/documentation/web-api/)

## Contents

<ol>
	<li><a href="https://github.com/woojiahao/java-spotify-wrapper#anonymous-access">Anonymous Access</a></li>
	<li><a href="https://github.com/woojiahao/java-spotify-wrapper#sample-usage">Sample Usage</a></li>
	<li>
        <a href="https://github.com/woojiahao/java-spotify-wrapper#user-authentication">User Authentication</a>
        <ul>
            <li><a href="https://github.com/woojiahao/java-spotify-wrapper#authorization-flow">Authorization Flow</a></li>
            <li><a href="https://github.com/woojiahao/java-spotify-wrapper#implicit-grant">Implicit Grant</a></li>
            <li><a href="https://github.com/woojiahao/java-spotify-wrapper#client-credentials-flow">Credit Credentials Flow</a></li>
        </ul>
    </li>
	<li><a href="https://github.com/woojiahao/java-spotify-wrapper#code-structure">Code Structure</a></li>
	<li><a href="https://github.com/woojiahao/java-spotify-wrapper#contributing">Contributing</a></li>
	<li><a href="https://github.com/woojiahao/java-spotify-wrapper#examples">Examples</a></li>
	<li><a href="https://github.com/woojiahao/java-spotify-wrapper#license">License</a></li>
</ol>

## Anonymous Access
Some of Spotify's Web API is available to users even without an access token or performing any form of user authentication,
the library supports the access of these resources.

## Sample Usage
In order to begin using the Spotify API, you have to create what is known as a **SpotifyUser**. This can be done either 
by following a flow and [generating one](https://woojiahao.github.io/java-spotify-wrapper/#/authentication_guide), or 
manually creating one yourself:

```java
SpotifyUser oneTimeUser = new SpotifyUser("<access_token>");
SpotifyUser refreshUser = new SpotifyUser("<access_token>", "refresh_token");
```

After that, you can proceed to apply the methods you wish to use on this **SpotifyUser** object.

**Getting An Album:**

```java
Album album = oneTimeUser.getAlbum("0sNOF9WDwhWunNAHPD3Baj").build().execute();
System.out.println(album.getArtist());
```

More information:

* [Examples](https://github.com/woojiahao/java-spotify-wrapper/tree/master/examples)
* [Documentation](https://woojiahao.github.io/java-spotify-wrapper/)
* [JavaDoc]()

## User Authentication
The Spotify Web API offers 3 methods of [user authentication.](https://developer.spotify.com/documentation/general/guides/authorization-guide/)

The library offers means to authenticate users in all 3 ways. 

### Authorization Flow
This form of authorization is used when you need to have a persistent session for the user and want the user to be 
able to continually access the API without having to authorize themselves everytime their access token expires.

[Example implementation of the authorization flow using the library](https://github.com/woojiahao/java-spotify-wrapper/blob/master/examples/AuthorizationFlowDemo.java)

[More information about the process steps](https://woojiahao.github.io/java-spotify-wrapper/#/authentication_guide?id=authorization-flow)

### Implicit Grant
This method of user authentication is recommended if the information you want to retrieve from the user's account is 
one-time and you won't need a persistent connection.

[Example implementation of the implicit grant flow using the library](https://github.com/woojiahao/java-spotify-wrapper/blob/master/examples/ImplicitGrantDemo.java)

[More information about the process steps](https://woojiahao.github.io/java-spotify-wrapper/#/authentication_guide?id=implicit-grant)

### Client Credentials
This method of user authentication is used for server-to-server communication and does not require any authorization. 
However, because of that, there is no refresh token provided.

[Example implementation of the client credential flow using the library](https://github.com/woojiahao/java-spotify-wrapper/blob/master/examples/ClientCredentialFlowDemo.java)

[More information about the process steps](https://woojiahao.github.io/java-spotify-wrapper/#/authentication_guide?id=client-credential-flow)

## Code Structure
> TODO: Create a directory list for the structure of the code

## Contributing
> TODO: Create a code of conduct + contributing guide

## Examples

<ul>
    <li>Albums
        <ul>
            <li><a href="https://github.com/woojiahao/java-spotify-wrapper/blob/b6b593ac15988109763cf94cb48c171fd6ecf2a6/src/main/java/me/chill/sample/AlbumQueryDemo.java#L37">Get an album</a><li>
            <li><a href="https://github.com/woojiahao/java-spotify-wrapper/blob/b6b593ac15988109763cf94cb48c171fd6ecf2a6/src/main/java/me/chill/sample/AlbumQueryDemo.java#L40">Get an album's tracks</a><li>
            <li><a href="https://github.com/woojiahao/java-spotify-wrapper/blob/b6b593ac15988109763cf94cb48c171fd6ecf2a6/src/main/java/me/chill/sample/AlbumQueryDemo.java#L43">Get several albums</a><li>
        </ul>
    </li>
</ul>

## License
java-spotify-wrapper is licensed under the MIT license, more about that can be found [here.](https://opensource.org/licenses/MIT)