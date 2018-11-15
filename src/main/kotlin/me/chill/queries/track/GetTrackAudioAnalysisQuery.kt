package me.chill.queries.track

import me.chill.models.AudioAnalysis
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.read
import me.chill.utility.request.RequestMethod

class GetTrackAudioAnalysisQuery private constructor(
  private val accessToken: String,
  private val trackId: String) : AbstractQuery<AudioAnalysis>(accessToken, RequestMethod.Get, "audio-analysis", trackId) {

  override fun execute(): AudioAnalysis =
    gson.read(checkedQuery().text)

  class Builder(private val accessToken: String, private val trackId: String) {
    fun build() = GetTrackAudioAnalysisQuery(accessToken, trackId.replace("spotify:track:", ""))
  }
}