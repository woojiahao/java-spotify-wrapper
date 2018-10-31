package me.chill.queries

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.concurrent.Callable
import java.util.concurrent.Future
import kotlin.concurrent.thread

/**
 * Represents a single query to an endpoint
 *
 * Contains 2 forms of execution, a synchronous call and asynchronous call
 */
// TODO: Fix the damn asynchronous calls
abstract class AbstractQuery (private vararg val pathSegments: String) {
	protected val gson: Gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

	protected val queryEndpoint = "https://api.spotify.com/v1/${pathSegments.joinToString("/") { it.replace("/", "") }}"

	abstract fun execute(): Any?

	open fun executeAsync(callback: (Any?) -> Unit)  {
		thread { callback(execute()) }
	}
}