package me.chill.queries.library

import khttp.delete
import me.chill.queries.checkListSizeLimit
import me.chill.queries.generateNullableString

class RemoveSavedTracksQuery private constructor(
	private val accessToken: String,
	private val ids: String?): SpotifyLibraryQuery() {

	override fun execute(): Any {
		val parameters = mapOf("ids" to ids).generateParameters()

		val headers = mapOf(
			"Authorization" to "Bearer $accessToken",
			"Content-Type" to "application/json"
		)

		val response = delete(libraryEndpoint, headers, parameters)
		response.responseCheck()

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

		fun build(): RemoveSavedTracksQuery {
			tracks.checkListSizeLimit("tracks", 50)

			return RemoveSavedTracksQuery(accessToken, tracks.generateNullableString())
		}
	}
}