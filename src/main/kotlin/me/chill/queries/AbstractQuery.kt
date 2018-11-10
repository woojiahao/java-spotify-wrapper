package me.chill.queries

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlin.concurrent.thread

/**
 * Query to an endpoint.
 *
 * **Inheritance rules:**
 * - Override the **execute()** method with the behavior for calling the endpoint
 * - Specify the endpoint path segment via the primary constructor
 *
 * @author Woo Jia Hao
 * @param pathSegments Path segments of the query's endpoint url
 */
// TODO: Fix the asynchronous calls to use a proper thread pool
abstract class AbstractQuery<T>(private vararg val pathSegments: String) {
  protected val gson: Gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

  protected val endpoint = "https://api.spotify.com/v1/${pathSegments.joinToString("/") { it.replace("/", "") }}"

  abstract fun execute(): T

  /**
   * Executes the query in a separate thread and when the query returns, executes the callback with the input as the
   * result of the **execute()** method.
   *
   * @param callback Callback to perform when the query returns
   */
  fun executeAsync(callback: (T) -> Unit) {
    thread { callback(execute()) }
  }
}