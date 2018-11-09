package me.chill.utility.extensions

import me.chill.exceptions.SpotifyQueryException

fun Map<String, Any?>.generateParameters() =
  mapNotNull { it.value?.let { _ -> Pair(it.key, it.value.toString()) } }.toMap()

/**
 * Only applies transform on elements of the list which satisfies the predicate
 *
 * @param predicate Predicate each element will be checked against
 * @param transform Transform to be applied to each element satisfying the predicate
 * @return List of strings
 */
fun <T> List<T>.conditionalMap(
  predicate: (T) -> Boolean,
  transform: (T) -> T) =
  map {
    if (predicate(it)) transform(it)
    else it
  }

// TODO: Add a check to find duplicates in the list and display warning messages
// TODO: Write unit tests for these extension methods

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