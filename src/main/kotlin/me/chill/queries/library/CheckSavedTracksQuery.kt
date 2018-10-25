package me.chill.queries.library

import me.chill.queries.checkEmpty
import me.chill.queries.checkLimit
import me.chill.queries.createCheckMap
import me.chill.queries.generateString

class CheckSavedTracksQuery private constructor(
	private val accessToken: String,
	private val ids: String) : SpotifyLibraryQuery() {

	override fun execute(): Any {
		val parameters = mapOf("ids" to ids)

		val response = query("https://api.spotify.com/v1/me/tracks/contains", accessToken, parameters)

		return response.createCheckMap(ids, gson)
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

		fun build(): CheckSavedTracksQuery {
			tracks.checkEmpty("Tracks")
			tracks.checkLimit("Tracks", 50)

			return CheckSavedTracksQuery(accessToken, tracks.generateString())
		}
	}
}