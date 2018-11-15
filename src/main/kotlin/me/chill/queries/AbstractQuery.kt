package me.chill.queries

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import khttp.get
import khttp.request
import khttp.responses.Response
import me.chill.utility.extensions.generateParameters
import me.chill.utility.request.RequestMethod
import me.chill.utility.request.generateHeader
import me.chill.utility.request.generateModificationHeader
import me.chill.utility.request.responseCheck
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
abstract class AbstractQuery<T>(
  private val accessToken: String,
  private val requestMethod: RequestMethod,
  private vararg val pathSegments: String) {

  protected val gson: Gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

  protected val endpoint = "https://api.spotify.com/v1/${pathSegments.joinToString("/") { it.replace("/", "") }}"

  protected fun query(
    params: Map<String, Any?> = mapOf(),
    data: Any? = null,
    isModification: Boolean = false,
    link: String = endpoint): Response {
    val headers = if (isModification) {
      generateModificationHeader(accessToken)
    } else {
      generateHeader(accessToken)
    }

    return request(requestMethod.name.toUpperCase(), link, headers, params.generateParameters(), data)
  }

  protected fun checkedQuery(
    params: Map<String, Any?> = mapOf(),
    data: Any? = null,
    isModification: Boolean = false,
    link: String = endpoint,
    check: (Response) -> Unit = { it.responseCheck() }): Response {

    val response = query(params, data, isModification, link)
    check(response)
    return response
  }

  abstract fun execute(): T

  /**
   * Executes the query in a separate thread and when the query returns, executes the callback with the input as the
   * result of the **execute()** method.
   *
   * @param callback Callback to perform when the query returns
   */
  // TODO: Make this return status code + actual data to return (?)
  fun executeAsync(callback: (T) -> Unit) {
    thread { callback(execute()) }
  }
}