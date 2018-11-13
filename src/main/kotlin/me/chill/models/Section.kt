package me.chill.models

data class Section(
  val start: Double,
  val duration: Double,
  val confidence: Double,
  val loudness: Double,
  val tempo: Double,
  val tempoConfidence: Double,
  val key: Int,
  val keyConfidence: Double,
  val mode: Int,
  val modeConfidence: Double,
  val timeSignature: Int,
  val timeSignatureConfidence: Double
)