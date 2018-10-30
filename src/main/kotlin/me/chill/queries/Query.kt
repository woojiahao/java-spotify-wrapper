package me.chill.queries

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import khttp.get
import khttp.head
import khttp.request
import khttp.responses.Response
import me.chill.exceptions.SpotifyQueryException
import me.chill.models.RegularError
import kotlin.concurrent.thread

abstract class Query {
	protected enum class Method { Get, Put, Post, Delete; }

	protected val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

	protected val primaryEndpoint = "https://api.spotify.com/"

	protected fun generateHeaders(accessToken: String) = mapOf("Authorization" to "Bearer $accessToken")

	protected fun Map<String, Any?>.generateParameters() = mapNotNull { it.value?.let { _ -> Pair(it.key, it.value.toString()) } }.toMap()

	protected fun query(endpoint: String, accessToken: String, parameters: Map<String, Any?> = mapOf()): Response {
		val response = get(endpoint, generateHeaders(accessToken), parameters.generateParameters())
		response.responseCheck()
		return response
	}

	protected fun put(endpoint: String, accessToken: String, parameters: Map<String, Any?> = mapOf()): Response {
		val headers = generateModificationHeaders(accessToken)

		val response = khttp.put(endpoint, headers, parameters.generateParameters())
		response.responseCheck()

		return response
	}

	/**
	 * data has to be set to "_" by default because khttp does not actually allow you to set the content-length of a body
	 */
	protected fun asyncRequest(
		method: Method,
		url: String,
		headers: Map<String, String> = mapOf(),
		parameters: Map<String, String> = mapOf(),
		data: String? = "_",
		callback: (Response) -> Unit) {
		thread {
			callback(request(method.name.toUpperCase(), url, headers, parameters, data))
		}
	}

	protected fun generateModificationHeaders(accessToken: String) =
		mapOf(
			"Authorization" to "Bearer $accessToken",
			"Content-Type" to "application/json"
		)

	protected fun Response.responseCheck() {
		text.takeIf { it.isEmpty() }?.let { return }
		statusCode.takeIf { it >= 400 }?.let {
			val errorBody = gson.fromJson(gson.fromJson(text, JsonObject::class.java)["error"], RegularError::class.java)
			throw SpotifyQueryException(errorBody.status, errorBody.message)
		}
	}

	private fun List<String>.trimList() = map { it.split(",") }.flatten()

	protected fun checkEmpty(list: List<String>, listName: String) =
		list.trimList()
			.takeIf { it.isEmpty() }
			?.let { throw SpotifyQueryException("$listName cannot be empty") }

	protected fun List<String>.checkLimit(listName: String, limit: Int = 20) =
		trimList()
			.takeIf { it.size > limit }
			?.let { throw SpotifyQueryException("$listName cannot contain more than $limit entries") }

	protected fun List<String>.generateString() = trimList().asSequence().distinct().joinToString(",")

	abstract fun execute(): Any?
}