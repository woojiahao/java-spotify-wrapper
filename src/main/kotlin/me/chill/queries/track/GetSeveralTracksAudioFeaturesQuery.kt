package me.chill.queries.track

import me.chill.models.AudioFeatures
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkEmptyAndSizeLimit
import me.chill.utility.extensions.createResponseMap
import me.chill.utility.extensions.generateString
import me.chill.utility.extensions.splitAndAdd
import me.chill.utility.request.RequestMethod

class GetSeveralTracksAudioFeaturesQuery private constructor(
  private val accessToken: String,
  private val tracks: Set<String>) : AbstractQuery<Map<String, AudioFeatures>>(accessToken, RequestMethod.Get, "audio-features") {

  override fun execute(): Map<String, AudioFeatures> =
    gson.createResponseMap(
      tracks,
      checkedQuery(mapOf("ids" to tracks.generateString())),
      "audio_features"
    )

  class Builder(private val accessToken: String) {
    private val tracks = mutableSetOf<String>()

    fun addTracks(vararg tracks: String): Builder {
      this.tracks.splitAndAdd(tracks)
      return this
    }

    fun build(): GetSeveralTracksAudioFeaturesQuery {
      tracks.checkEmptyAndSizeLimit("Tracks", 100)

      return GetSeveralTracksAudioFeaturesQuery(accessToken, tracks)
    }
  }
}