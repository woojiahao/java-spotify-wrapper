package me.chill.queries.playlist

import khttp.post
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkListSizeLimit
import me.chill.utility.extensions.checkLower
import me.chill.utility.request.displayErrorMessage
import me.chill.utility.request.generateModificationHeader
import me.chill.utility.request.responseCheck

class AddTracksToPlaylistQuery private constructor(
	private val accessToken: String,
	private val playlistId: String,
	private val uris: List<String>,
	private val position: Int?) : AbstractQuery<Pair<Boolean, String?>>("playlists", playlistId, "tracks") {

	override fun execute(): Pair<Boolean, String?> {
		val body = gson.toJson(mapOf(
			"uris" to uris,
			"position" to position
		))

		val response = post(queryEndpoint, generateModificationHeader(accessToken), data = body)
		response.statusCode.takeIf { it == 403 }?.let { return Pair(false, null) }
		response.responseCheck()

		return Pair(true, response.jsonObject.getString("snapshot_id"))
	}

	class Builder(private val accessToken: String, private val playlistId: String) {
		private val uris = mutableListOf<String>()
		private var position: Int? = null

		fun position(position: Int): Builder {
			this.position = position
			return this
		}

		fun addUri(uri: String): Builder {
			uris.add(uri)
			return this
		}

		fun setUris(uris: List<String>): Builder {
			this.uris.clear()
			this.uris.addAll(uris)
			return this
		}

		fun build(): AddTracksToPlaylistQuery {
			position?.checkLower("Position")
			uris.checkListSizeLimit("URI", 100)

			return AddTracksToPlaylistQuery(accessToken, playlistId, uris, position)
		}
	}
}