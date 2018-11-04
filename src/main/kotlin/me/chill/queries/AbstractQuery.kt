package me.chill.queries

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlin.concurrent.thread

/**
 * Represents a single query to an endpoint, consisting of a synchronous and asynchronous method of accessing the endpoints
 *
 * @param pathSegments Path segments that make up the query endpoint, values are appended to the end of **https://api.spotify.com/v1{@literal /}**
 * @param <T> Return type of the execute methods
 */
// TODO: Fix the damn asynchronous calls, fix race cases
abstract class AbstractQuery<T>(private vararg val pathSegments: String) {
  protected val gson: Gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

  protected val endpoint = "https://api.spotify.com/v1/${pathSegments.joinToString("/") { it.replace("/", "") }}"

  abstract fun execute(): T

  fun executeAsync(callback: (T) -> Unit) {
    thread { callback(execute()) }
  }
}