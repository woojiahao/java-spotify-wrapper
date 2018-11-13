package me.chill.models

data class AudioAnalysis (
  val bars: List<TimeInterval>,
  val beats: List<TimeInterval>,
  val sections: List<Section>,
  val segments: List<Segment>,
  val tatums: List<TimeInterval>
)