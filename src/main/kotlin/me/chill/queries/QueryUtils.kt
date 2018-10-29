package me.chill.queries

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import khttp.responses.Response
import me.chill.exceptions.SpotifyQueryException
import me.chill.models.Device

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

fun Response.createCheckMap(ids: String, gson: Gson) =
	ids.split(",").zip(gson.fromJson(text, JsonArray::class.java).map { it.asBoolean }).toMap()

fun Int.checkGreater(name: String, limit: Int = 50) =
	takeIf { it > limit }
		?.let { throw SpotifyQueryException("$name cannot be greater than $limit") }

fun Int.checkLower(name: String, limit: Int = 0) =
	takeIf { it < limit }
		?.let { throw SpotifyQueryException("$name cannot be less than $limit") }

fun Int.checkRange(rangeName: String, lower: Int = 1, upper: Int = 50) =
	takeIf { (it < lower) || (it > upper) }
		?.let { throw SpotifyQueryException("$rangeName cannot be less than $lower or greater than $upper") }

fun Double.checkGreater(name: String, limit: Double = 50.0) =
	takeIf { it > limit }
		?.let { throw SpotifyQueryException("$name cannot be greater than $limit") }

fun Double.checkLower(name: String, limit: Double = 0.0) =
	takeIf { it < limit }
		?.let { throw SpotifyQueryException("$name cannot be less than $limit") }

fun Double.checkRange(rangeName: String, lower: Double = 1.0, upper: Double = 50.0) =
	takeIf { (it < lower) || (it > upper) }
		?.let { throw SpotifyQueryException("$rangeName cannot be less than $lower or greater than $upper") }

fun Int.checkLimit(lower: Int = 1, upper: Int = 50) = checkRange("Limit", lower, upper)

fun Int.checkOffset() = checkLower("Offset")

fun Gson.readFromJsonArray(arrayName: String, content: String) =
	fromJson(content, JsonObject::class.java)
		.getAsJsonArray(arrayName)
		.map { fromJson(it, Device::class.java) }
