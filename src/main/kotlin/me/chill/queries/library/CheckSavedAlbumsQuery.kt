package me.chill.queries.library

import com.google.gson.JsonArray
import me.chill.queries.checkEmpty
import me.chill.queries.checkLimit
import me.chill.queries.generateString

class CheckSavedAlbumsQuery private constructor(
	private val accessToken: String,
	private val ids: String) : SpotifyLibraryQuery() {

	override fun execute(): Map<String, Boolean> {
		val parameters = mapOf("ids" to ids)

		val response = query("${libraryEndpoint}contains", accessToken, parameters)

		return ids.split(",").zip(gson.fromJson(response.text, JsonArray::class.java).map { it.asBoolean }).toMap()
	}

	class Builder(private val accessToken: String) {
		private val albums = mutableListOf<String>()

		fun addAlbum(album: String): Builder {
			albums.add(album)
			return this
		}

		fun setAlbums(albums: List<String>): Builder {
			this.albums.clear()
			this.albums.addAll(albums)
			return this
		}

		fun build(): CheckSavedAlbumsQuery {
			albums.checkEmpty("Albums")
			albums.checkLimit("Albums", 50)

			return CheckSavedAlbumsQuery(accessToken, albums.generateString())
		}
	}
}