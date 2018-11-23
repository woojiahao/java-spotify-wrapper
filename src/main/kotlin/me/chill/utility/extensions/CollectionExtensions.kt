package me.chill.utility.extensions

// TODO: Create additional parameter for conditionalMap

fun Map<String, Any?>.generateParameters() =
  mapNotNull { item -> item.value?.let { Pair(item.key, item.value.toString()) } }.toMap()

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

fun MutableSet<String>.splitAndAdd(data: String, pipes: String = ",") {
  addAll(data.split(pipes).map { it.trim() })
}

fun MutableSet<String>.splitAndAdd(data: Array<out String>, pipes: String = ",") {
  addAll(data.map {
        it.split(pipes)
          .map { i -> i.trim() }
      }.flatten()
  )
}

fun <T> Set<T>.checkEmpty(setName: String) {
  check(!isEmpty()) { "$setName cannot be empty" }
}

fun <T> Set<T>.checkSizeLimit(setName: String, limit: Int = 20) {
  check(size <= limit) { "$setName cannot contain more than $limit entries" }
}

// TODO: Figure out how to unit test this
fun <T> Set<T>.checkEmptyAndSizeLimit(setName: String, limit: Int = 20) {
  checkEmpty(setName)
  checkSizeLimit(setName, limit)
}

fun <T> Set<T>.generateString() =
  joinToString(",") { it.toString() }

fun <T> Set<T>.generateNullableString() =
  takeIf { it.isNotEmpty() }?.joinToString(",") { it.toString() }