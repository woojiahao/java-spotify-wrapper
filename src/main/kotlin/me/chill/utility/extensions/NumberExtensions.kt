package me.chill.utility.extensions

import me.chill.exceptions.SpotifyQueryException

fun Int.checkGreater(name: String, limit: Int = 50) =
  takeIf { it > limit }
    ?.let { throw SpotifyQueryException("$name cannot be greater than $limit") }

fun Int.checkLower(name: String, limit: Int = 0) =
  takeIf { it < limit }
    ?.let { throw SpotifyQueryException("$name cannot be less than $limit") }

fun Int.checkRange(rangeName: String, lower: Int = 1, upper: Int = 50) =
  takeIf { (it < lower) || (it > upper) }
    ?.let { throw SpotifyQueryException("$rangeName cannot be less than $lower or greater than $upper") }

fun Int.checkLimit(lower: Int = 1, upper: Int = 50) = checkRange("Limit", lower, upper)

fun Int.checkOffset() = checkLower("Offset")

fun Double.checkGreater(name: String, limit: Double = 50.0) =
  takeIf { it > limit }
    ?.let { throw SpotifyQueryException("$name cannot be greater than $limit") }

fun Double.checkLower(name: String, limit: Double = 0.0) =
  takeIf { it < limit }
    ?.let { throw SpotifyQueryException("$name cannot be less than $limit") }

fun Double.checkRange(rangeName: String, lower: Double = 1.0, upper: Double = 50.0) =
  takeIf { (it < lower) || (it > upper) }
    ?.let { throw SpotifyQueryException("$rangeName cannot be less than $lower or greater than $upper") }
