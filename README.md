# Java Spotify Wrapper
Simple to use Java wrapper for the [Spotify web API](https://developer.spotify.com/documentation/web-api/)

## Contents
1. [Anonymous Access](https://github.com/woojiahao/java-spotify-wrapper#anonymous-access)
2. [Sample Usage](https://github.com/woojiahao/java-spotify-wrapper#sample-usage)
3. [User Authentication](https://github.com/woojiahao/java-spotify-wrapper#user-authentication)
   * [Authorization Flow](https://github.com/woojiahao/java-spotify-wrapper#authorization-flow)
   * [Implicit Grant](https://github.com/woojiahao/java-spotify-wrapper#implicit-grant)
   * [Credit Credentials Flow](https://github.com/woojiahao/java-spotify-wrapper#client-credentials-flow)
4. [Code Structure](https://github.com/woojiahao/java-spotify-wrapper#code-structure)
5. [Examples](https://github.com/woojiahao/java-spotify-wrapper#examples)
6. [Contributing](https://github.com/woojiahao/java-spotify-wrapper#contributing)
7. [License](https://github.com/woojiahao/java-spotify-wrapper#license)
8. [Checklist](https://github.com/woojiahao/java-spotify-wrapper#checklist)

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

**Example: Getting an album**

```java
Album album = oneTimeUser.getAlbum("0sNOF9WDwhWunNAHPD3Baj").build().execute();
System.out.println(album.getArtist());
```

**Example: Generating recommendations based off a seed**

```java
Recommendation recommendation = oneTimeUser
    .getSeedRecommendation()
    .limit(5)
    .addSeedGenre("party")
    .setSeedArtists(listOf("4NHQUGzhtTLFvgF5SZesLK", "43ZHCT0cAZBISjO8DG9PnE"))
    .danceability(SeedQuery.Flag.Min, 0.6)
    .build()
    .execute();
recommendation.getSeeds().forEach(System.out::println)
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
### Folder Structure
```
(/)
|
|_____(docs) - Library's documentation
|
|_____(src/main) - Source code and examples
|     |
|     |_____(java/me/chill/sample) - Example usage  
|     |
|     |_____(kotlin/me/chill) - Library root
|           |
|           |_____(authentication) - Code for authentication method
|           |
|           |_____(models) - Every data model used
|           |
|           |_____(queries) - Contains sub-folders for each endpoint
|           |
|           |_____(utility) - General utility functions
|           |
|           |_____[SpotifyUser.kt] - Authenticated user type
|  
|_____[.gitignore]
|
|_____[CODE_OF_CONDUCT.md] 
|
|_____[CONTRIBUTING.md] 
|
|_____[LICENSE.md] 
|
|_____[README.md] 
|
|_____[pom.xml] 
```

## Contributing
The project's code of conduct can be found [here.](https://github.com/woojiahao/java-spotify-wrapper/blob/master/CODE_OF_CONDUCT.md)

If you wish to make a contribution, be sure to read the [contribution guide.](https://github.com/woojiahao/java-spotify-wrapper/blob/master/CONTRIBUTING.md)

## Examples

* **Albums**
    * [Get an album](https://github.com/woojiahao/java-spotify-wrapper/blob/b6b593ac15988109763cf94cb48c171fd6ecf2a6/src/main/java/me/chill/sample/AlbumQueryDemo.java#L37)
    * [Get an album's tracks](https://github.com/woojiahao/java-spotify-wrapper/blob/b6b593ac15988109763cf94cb48c171fd6ecf2a6/src/main/java/me/chill/sample/AlbumQueryDemo.java#L40)
    * [Get several albums](https://github.com/woojiahao/java-spotify-wrapper/blob/b6b593ac15988109763cf94cb48c171fd6ecf2a6/src/main/java/me/chill/sample/AlbumQueryDemo.java#L43)
* **Artists**
    * [Get an artist](https://github.com/woojiahao/java-spotify-wrapper/blob/d2d99b8a0cdee55f5399134709f5d8018307790d/src/main/java/me/chill/sample/ArtistQueryDemo.java#L39)
    * [Get an artist's albums](https://github.com/woojiahao/java-spotify-wrapper/blob/d2d99b8a0cdee55f5399134709f5d8018307790d/src/main/java/me/chill/sample/ArtistQueryDemo.java#L42)
    * [Get an artist's top tracks](https://github.com/woojiahao/java-spotify-wrapper/blob/d2d99b8a0cdee55f5399134709f5d8018307790d/src/main/java/me/chill/sample/ArtistQueryDemo.java#L45)
    * [Get an artist's related artists](https://github.com/woojiahao/java-spotify-wrapper/blob/d2d99b8a0cdee55f5399134709f5d8018307790d/src/main/java/me/chill/sample/ArtistQueryDemo.java#L48)
    * [Get several artists](https://github.com/woojiahao/java-spotify-wrapper/blob/d2d99b8a0cdee55f5399134709f5d8018307790d/src/main/java/me/chill/sample/ArtistQueryDemo.java#L51)
* **Browse**
    * [Get a category](https://github.com/woojiahao/java-spotify-wrapper/blob/d2d99b8a0cdee55f5399134709f5d8018307790d/src/main/java/me/chill/sample/BrowseQueryDemo.java#L37)
    * [Get a category's playlists](https://github.com/woojiahao/java-spotify-wrapper/blob/d2d99b8a0cdee55f5399134709f5d8018307790d/src/main/java/me/chill/sample/BrowseQueryDemo.java#L40)
    * [Get a list of categories](https://github.com/woojiahao/java-spotify-wrapper/blob/d2d99b8a0cdee55f5399134709f5d8018307790d/src/main/java/me/chill/sample/BrowseQueryDemo.java#L43)
    * [Get a list of featured playlists](https://github.com/woojiahao/java-spotify-wrapper/blob/d2d99b8a0cdee55f5399134709f5d8018307790d/src/main/java/me/chill/sample/BrowseQueryDemo.java#L46)
    * [Get a list of new releases](https://github.com/woojiahao/java-spotify-wrapper/blob/d2d99b8a0cdee55f5399134709f5d8018307790d/src/main/java/me/chill/sample/BrowseQueryDemo.java#L49)
    * [Get a list of recommendations](https://github.com/woojiahao/java-spotify-wrapper/blob/d2d99b8a0cdee55f5399134709f5d8018307790d/src/main/java/me/chill/sample/BrowseQueryDemo.java#L52)
    * [Get a list of available genre seeds](https://github.com/woojiahao/java-spotify-wrapper/blob/d2d99b8a0cdee55f5399134709f5d8018307790d/src/main/java/me/chill/sample/BrowseQueryDemo.java#L67)

## License
java-spotify-wrapper is licensed under the MIT license, more about that can be found [here.](https://opensource.org/licenses/MIT)

## Checklist
* [ ] Endpoints
    * [X] Album
    * [X] Artist
    * [X] Browse
    * [ ] Follow
    * [ ] Library
    * [ ] Personalization
    * [ ] Player
    * [ ] Playlists
    * [ ] Search
    * [ ] User Profile 
* [ ] Administrative
    * [X] Contribution Guide
    * [ ] Deploy to Maven Central
    * [ ] Issues Templates
        * [ ] Bug Reporting
        * [ ] Feature
    * [ ] PR Templates
    * [ ] Documentation Templates
* [ ] Sample Application
* [ ] Testing
    * [ ] CI
    * [ ] Unit Tests