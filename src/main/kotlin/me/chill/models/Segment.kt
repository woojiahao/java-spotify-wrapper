package me.chill.models

data class Segment(
  val start: Double,
  val duration: Double,
  val confidence: Double,
  val loudnessStart: Double,
  val loudnessMax: Double,
  val loudnessMaxTime: Double,
  val loudnessEnd: Float,
  val pitches: List<Double>,
  val timbre: List<Double>
)