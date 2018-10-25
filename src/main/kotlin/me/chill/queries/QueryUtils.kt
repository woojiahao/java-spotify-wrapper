package me.chill.queries

import com.google.gson.Gson
import com.google.gson.JsonArray
import khttp.responses.Response
import me.chill.exceptions.SpotifyQueryException

// TODO: Add a check to find duplicates in the list and display warning messages

private fun List<String>.trimList() = map { it.split(",") }.flatten().distinct()

fun List<String>.checkEmpty(listName: String) =
	trimList()
		.takeIf { it.isEmpty() }
		?.let { throw SpotifyQueryException("$listName list cannot be empty") }

fun List<String>.checkLimit(listName: String, limit: Int = 20) =
	trimList()
		.takeIf { it.size > limit }
		?.let { throw SpotifyQueryException("$listName cannot contain more than $limit entries") }

fun List<String>.generateNullableString() = trimList().takeIf { it.isNotEmpty() }?.joinToString(",")

fun List<String>.generateString() = trimList().joinToString(",")

fun Response.createCheckMap(ids: String, gson: Gson) =
	ids.split(",").zip(gson.fromJson(text, JsonArray::class.java).map { it.asBoolean }).toMap()