package me.chill.utility.extensions

fun Map<String, Any?>.generateParameters() =
  mapNotNull { it.value?.let { _ -> Pair(it.key, it.value.toString()) } }.toMap()
