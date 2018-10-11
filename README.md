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
The Spotify Web API offers 3 methods of [user authentication.](https://developer.spotify.com/documentation/general/guides/authorization-guide/)

The library offers means to authenticate users in all 3 ways. 

### Authorization Flow
This form of authorization is used when you need to have a persistent session for the user and want the user to be 
able to continually access the API without having to authorize themselves everytime their access token expires.

[Example implementation of the authorization flow using the library](https://github.com/woojiahao/java-spotify-wrapper/blob/master/examples/AuthorizationFlowDemo.java)

[More information about the process steps](https://woojiahao.github.io/java-spotify-wrapper/authentication_guide?id=authorization-flow)

### Implicit Grant
This method of user authentication is recommended if the information you want to retrieve from the user's account is 
one-time and you won't need a persistent connection.

### Client Credentials
This method of user authentication is used for server-to-server communication and does not require any authorization. 
However, because of that, there is no refresh token provided.

[Example implementation of the client credential flow using the library](https://github.com/woojiahao/java-spotify-wrapper/blob/master/examples/ClientCredentialFlowDemo.java)

[More information about the process steps](https://woojiahao.github.io/java-spotify-wrapper/authentication_guide?id=client-credential-flow)

## Code Structure
> TODO: Create a directory list for the structure of the code

## Contributing
> TODO: Create a code of conduct + contributing guide

## License
Simple to use java-spotify-wrapper is licensed under the MIT license, more about that can be found [here.](https://opensource.org/licenses/MIT)