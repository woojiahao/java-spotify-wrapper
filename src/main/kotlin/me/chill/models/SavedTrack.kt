package me.chill.models

// TODO: Privatise addedAt and replace it with a custom getter to return a date object instead
data class SavedTrack(
	val addedAt: String,
	val track: Track
)