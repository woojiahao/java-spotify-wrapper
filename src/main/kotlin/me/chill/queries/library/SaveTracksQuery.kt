package me.chill.queries.library

import me.chill.queries.checkListSizeLimit
import me.chill.queries.generateNullableString

class SaveTracksQuery private constructor(
	private val accessToken: String,
	private val ids: String?) : SpotifyLibraryQuery() {

	override fun execute(): Any {
		val parameters = mapOf("ids" to ids)

		val response = put(libraryEndpoint, accessToken, parameters)

		return response.statusCode == 200
	}

	class Builder(private val accessToken: String) {
		private val tracks = mutableListOf<String>()

		fun addTrack(track: String): Builder {
			tracks.add(track)
			return this
		}

		fun setTracks(tracks: List<String>): Builder {
			this.tracks.clear()
			this.tracks.addAll(tracks)
			return this
		}

		fun build(): SaveTracksQuery {
			tracks.checkListSizeLimit("Tracks", 50)

			return SaveTracksQuery(accessToken, tracks.generateNullableString())
		}

	}
}