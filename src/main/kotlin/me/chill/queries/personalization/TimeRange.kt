package me.chill.queries.personalization

enum class TimeRange {
	Long, Medium, Short;

	fun getValue() = "${name.toLowerCase()}_term"
}