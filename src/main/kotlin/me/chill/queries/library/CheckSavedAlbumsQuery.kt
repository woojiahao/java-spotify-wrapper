package me.chill.queries.library

import me.chill.exceptions.SpotifyQueryException
import me.chill.queries.checkEmpty
import me.chill.queries.checkLimit
import me.chill.queries.generateString

class CheckSavedAlbumsQuery private constructor(
	private val accessToken: String,
	private val ids: String) : SpotifyLibraryQuery(){

	override fun execute(): Any {
		TODO("not implemented")
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