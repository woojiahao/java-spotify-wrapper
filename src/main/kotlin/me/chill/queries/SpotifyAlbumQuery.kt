package me.chill.queries

import com.neovisionaries.i18n.CountryCode
import khttp.get
import me.chill.models.Album
import me.chill.utility.responseCheck

class SpotifyAlbumQuery : Query() {
	private val primaryEndpoint = "https://api.spotify.com/v1/albums/"

	fun getAlbum(accessToken: String, id: String, countryCode: CountryCode?): Album {
		val headers = mapOf("Authorization" to "Bearer $accessToken")
		val parameters = countryCode?.let { mapOf("market" to countryCode.alpha2) } ?: emptyMap()

		val response = get("$primaryEndpoint$id", headers, parameters)

		response.responseCheck()

		val album = gson.fromJson(response.text, Album::class.java)

		return album
	}
}