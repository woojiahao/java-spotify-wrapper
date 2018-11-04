package me.chill.models

data class Recommendation(
  val seeds: List<RecommendationSeed>,
  val tracks: List<Track>
)