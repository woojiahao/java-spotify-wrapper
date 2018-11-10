package me.chill.models

import com.google.gson.annotations.SerializedName

/**
 * Represents a full album object returned from the Spotify Web API.
 *
 * Can also be used to represent a simplified album object.

 * @see <a href="https://developer.spotify.com/documentation/web-api/reference/object-model/#album-object-full">Album Documentation</a>
 * @author Woo Jia Hao
 *
 * @param [albumGroup] Relationship between the artist and the album, present when getting an artist's albums.
 * @param [albumType] Constant from [AlbumType] which signifies the type of album.
 * @param [artists] List of simplified [Artist]s of the album.
 * @param [availableMarkets] List of markets the album is available in, all markets are presented as [ISO 3166-1 alpha-2 country codes.](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2)
 * @param [copyrights] List of [Copyright] objects which represent the copyright statements of the album.
 * @param [externalIds] Map of known external IDs for the album.
 * @param [externalUrls] Map of known external URLs for the album.
 * @param [genres] List of genres used to classify the album, if the album is not classified yet, the list is empty.
 * @param [albumDetailsUrl] Link to the Web API endpoint providing full details of the album.
 * @param [id] [Spotify ID](https://developer.spotify.com/documentation/web-api/#spotify-uris-and-ids) of the album.
 * @param [images] List of [Image]s for the cover art for the album in various sizes, widest first.
 * @param [label] Label for the album.
 * @param [name] Name of the album, if the album is taken down, this will be blank.
 * @param [popularity] Popularity of the album calculated from the popularity of each track in the album, measured between 0 and 100.
 * @param [releaseDate] The date the album was first released, influenced by [releaseDatePrecision] eg. 1981-12-15 for `year`.
 * @param [releaseDatePrecision] The precision with which [releaseDate] value is known, `year`, `month`, `day`.
 * @param [restrictions] Reason why [track relinking](https://developer.spotify.com/documentation/general/guides/track-relinking-guide/) did not work, if track relinking was applied.
 * @param [tracks] [Paging] of simplified [Track] objects for the tracks in the album.
 * @param [type] Always *"album"*.
 * @param [albumSpotifyUri] The [Spotify URI](https://developer.spotify.com/documentation/general/guides/track-relinking-guide/) for the album.
 */
data class Album(
  val albumGroup: String?,
  val albumType: AlbumType,
  val artists: List<Artist>,
  val availableMarkets: List<String>,
  val copyrights: List<Copyright>?,
  val externalIds: Map<String, String>?,
  val externalUrls: Map<String, String>,
  val genres: List<String>?,
  @SerializedName("href") val albumDetailsUrl: String,
  val id: String,
  val images: List<Image>,
  val label: String?,
  val name: String,
  val popularity: Int?,
  val releaseDate: String,
  val releaseDatePrecision: ReleaseDatePrecision,
  val restrictions: Restriction,
  val tracks: Paging<Track>?,
  val type: String,
  @SerializedName("uri") val albumSpotifyUri: String
)