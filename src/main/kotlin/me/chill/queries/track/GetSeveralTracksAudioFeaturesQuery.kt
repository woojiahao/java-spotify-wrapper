package me.chill.queries.track

import me.chill.models.AudioFeatures
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.*
import me.chill.utility.request.query

class GetSeveralTracksAudioFeaturesQuery private constructor(
  private val accessToken: String,
  private val tracks: String) : AbstractQuery<Map<String, AudioFeatures>>("audio-features") {

  override fun execute(): Map<String, AudioFeatures> =
    gson.createResponseMap(
      tracks.split(","),
      query(endpoint, accessToken, mapOf("ids" to tracks)),
      "audio_features"
    )

  class Builder(private val accessToken: String) {
    private val tracks = mutableSetOf<String>()

    fun addTracks(vararg tracks: String): Builder {
      this.tracks.splitAndAdd(tracks)
      return this
    }

    fun build(): GetSeveralTracksAudioFeaturesQuery {
      tracks.checkEmpty("Tracks")
      tracks.checkSizeLimit("Tracks", 100)

      return GetSeveralTracksAudioFeaturesQuery(accessToken, tracks.generateString())
    }
  }
}