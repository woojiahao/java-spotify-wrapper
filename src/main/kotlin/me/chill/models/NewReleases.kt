package me.chill.models

data class NewReleases(
	val message: String?,
	val albums: Paging<Album>
)