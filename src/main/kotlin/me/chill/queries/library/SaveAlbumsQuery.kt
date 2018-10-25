package me.chill.queries.library

import me.chill.queries.checkListSizeLimit
import me.chill.queries.generateNullableString

class SaveAlbumsQuery private constructor(
	private val accessToken: String,
	private val ids: String?) : SpotifyLibraryQuery() {

	override fun execute(): Any {
		val parameters = mapOf("ids" to ids)

		val response = put(libraryEndpoint, accessToken, parameters)

		return response.statusCode == 201
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

		fun build(): SaveAlbumsQuery {
			albums.checkListSizeLimit("Albums", 50)

			return SaveAlbumsQuery(accessToken, albums.generateNullableString())
		}
	}
}