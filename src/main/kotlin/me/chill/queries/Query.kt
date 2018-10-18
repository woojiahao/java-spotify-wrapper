package me.chill.queries

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import khttp.get
import khttp.responses.Response
import me.chill.utility.responseCheck

abstract class Query {
	protected val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

	protected val primaryEndpoint = "https://api.spotify.com/"

	protected fun generateHeaders(accessToken: String) = mapOf("Authorization" to "Bearer $accessToken")

	protected fun Map<String, Any?>.generateParameters() = mapNotNull { it.value?.let { _ -> Pair(it.key, it.value.toString()) } }.toMap()

	protected fun query(endpoint: String, accessToken: String, parameters: Map<String, Any?> = mapOf()): Response {
		val response = get(endpoint, generateHeaders(accessToken), parameters.generateParameters())
		response.responseCheck()
		return response
	}

	abstract fun execute(): Any
}