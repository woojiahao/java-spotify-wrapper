package me.chill.exceptions

private fun createErrorMessage(errMap: Map<String, String>) =
	"\n\n${errMap.map { "\t${it.key}: ${it.value}" }.joinToString("\n")}\n"

open class SpotifyException(errMap: Map<String, String>) : Exception(createErrorMessage(errMap))