package me.chill.utility.extensions

import me.chill.exceptions.SpotifyQueryException

// TODO: Add a check to find duplicates in the list and display warning messages

private fun List<String>.trimList() = map { it.split(",") }.flatten().distinct()

fun List<String>.checkEmpty(listName: String) =
	trimList()
		.takeIf { it.isEmpty() }
		?.let { throw SpotifyQueryException("$listName list cannot be empty") }

fun List<String>.checkListSizeLimit(listName: String, limit: Int = 20) =
	trimList()
		.takeIf { it.size > limit }
		?.let { throw SpotifyQueryException("$listName cannot contain more than $limit entries") }

fun List<String>.generateNullableString() = trimList().takeIf { it.isNotEmpty() }?.joinToString(",")

fun List<String>.generateString() = trimList().joinToString(",")
