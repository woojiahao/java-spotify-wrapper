package me.chill.queries.track

import me.chill.models.AudioFeatures
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.read
import me.chill.utility.request.query

class GetSingleTrackAudioFeaturesQuery private constructor(
  private val accessToken: String,
  private val trackId: String) : AbstractQuery<AudioFeatures>("audio-features", trackId) {

  override fun execute(): AudioFeatures = gson.read(query(endpoint, accessToken).text)

  class Builder(private val accessToken: String, private val trackId: String) {
    fun build() = GetSingleTrackAudioFeaturesQuery(accessToken, trackId.replace("spotify:track:", ""))
  }
}