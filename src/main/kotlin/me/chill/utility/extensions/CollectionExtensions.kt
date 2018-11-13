package me.chill.utility.extensions

import me.chill.exceptions.SpotifyQueryException

fun Map<String, Any?>.generateParameters() =
  mapNotNull { item -> item.value?.let { Pair(item.key, item.value.toString()) } }.toMap()

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

/**
 * Only applies transform on elements of the list which satisfies the predicate
 *
 * @param predicate Predicate each element will be checked against
 * @param transform Transform to be applied to each element satisfying the predicate
 * @return List of strings
 */
fun <T> Set<T>.conditionalMap(
  predicate: (T) -> Boolean,
  transform: (T) -> T) =
  map {
    if (predicate(it)) transform(it)
    else it
  }

// TODO: Add a check to find duplicates in the list and display warning messages
// TODO: Write unit tests for these extension methods

// TODO: Create additional parameter for conditionalMap
private fun List<String>.trimList() = map { it.split(",") }.flatten().distinct()

fun List<String>.checkEmpty(listName: String) =
  trimList()
    .takeIf { it.isEmpty() }
    ?.let { throw SpotifyQueryException("$listName list cannot be empty") }

fun List<String>.checkListSizeLimit(listName: String, limit: Int = 20) =
  trimList()
    .takeIf { it.size > limit }
    ?.let { throw SpotifyQueryException("$listName cannot contain more than $limit entries") }

fun MutableSet<String>.splitAndAdd(data: String, pipes: String = ",") {
  addAll(data.split(pipes))
}

fun MutableSet<String>.splitAndAdd(data: Array<out String>, pipes: String = ",") {
  addAll(data.map { it.split(pipes) }.flatten())
}

fun <T> Set<T>.checkEmpty(setName: String) {
  check(!isEmpty()) { "$setName cannot be empty" }
}

fun <T> Set<T>.checkSizeLimit(setName: String, limit: Int = 20) {
  check(size <= limit) { "$setName cannot contain more than $limit entries" }
}

fun <T> Set<T>.checkEmptyAndSizeLimit(setName: String, limit: Int = 20) {
  checkEmpty(setName)
  checkSizeLimit(setName, limit)
}

fun <T> Set<T>.generateString() =
  joinToString(",") { it.toString() }

fun <T> Set<T>.generateNullableString() =
  takeIf { it.isNotEmpty() }?.joinToString(",") { it.toString() }

fun List<String>.generateNullableString() =
  trimList().takeIf { it.isNotEmpty() }?.joinToString(",")

fun List<String>.generateString() =
  trimList().joinToString(",")

fun <T> List<T>.hasDuplicates() = size != distinct().size

fun <T> Set<T>.hasDuplicates() = size != distinct().size