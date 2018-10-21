# Album Endpoint
## Get An Album
Get information about an album using the album's ID. 

An access token is **required** to access this endpoint.

|Property|Method|Data Type|Restriction|
|---|---|---|---|
|market|market(CountryCode)|[CountryCode Constant](http://takahikokawasaki.github.io/nv-i18n/)|None|

**Example:**
```java
Album album = user
    .getSingleAlbum("6akEvsycLGftJxYudPjmqK")
    .market(CountryCode.SE)
    .build()
    .execute();
System.out.println(album.getName() + " has " + album.getGenres.size() + " genres.");
```

* [More information](https://developer.spotify.com/documentation/web-api/reference/albums/get-album/) on this endpoint

## Get An Album's Tracks
Get information about the tracks inside an album.

An access token is **required** to access this endpoint.

**Example:**
```java
Paging<Track> tracks = user
    .getAlbumTracks("6akEvsycLGftJxYudPjmqK")
    .limit(10)
    .build()
    .execute();
```

## Get Several Albums
