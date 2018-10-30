package me.chill.utility.request

import com.google.gson.*
import khttp.get
import khttp.request
import khttp.responses.Response
import me.chill.exceptions.SpotifyQueryException
import me.chill.models.RegularError
import me.chill.utility.extensions.generateParameters
import kotlin.concurrent.thread

private val gson: Gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

fun generateHeader(accessToken: String) = Header.Builder().accessToken(accessToken).build().generate()

fun generateModificationHeader(accessToken: String) =
	Header.Builder().accessToken(accessToken).contentType(Header.Builder.ContentType.Json).build().generate()

fun Response.createCheckMap(ids: String) =
	ids.split(",").zip(gson.fromJson(text, JsonArray::class.java).map { it.asBoolean }).toMap()

inline fun <reified T> Gson.readFromJsonArray(arrayName: String, response: Response) =
	fromJson(response.text, JsonObject::class.java)
		.getAsJsonArray(arrayName)
		.map { fromJson(it, T::class.java) }

fun Response.responseCheck() {
	text.takeIf { it.isEmpty() }?.let { return }
	statusCode.takeIf { it >= 400 }?.let {
		val errorBody = gson.fromJson(gson.fromJson(text, JsonObject::class.java)["error"], RegularError::class.java)
		throw SpotifyQueryException(errorBody.status, errorBody.message)
	}
}

fun query(endpoint: String, accessToken: String, parameters: Map<String, Any?> = mapOf()): Response {
	val response = get(endpoint, generateHeader(accessToken), parameters.generateParameters())
	response.responseCheck()
	return response
}

fun put(endpoint: String, accessToken: String, parameters: Map<String, Any?> = mapOf()): Response {
	val response = khttp.put(endpoint, generateModificationHeader(accessToken), parameters.generateParameters())
	response.responseCheck()
	return response
}

/**
 * data has to be set to "_" by default because khttp does not actually allow you to set the content-length of a body
 */
fun asyncRequest(
	method: RequestMethod,
	url: String,
	headers: Map<String, String> = mapOf(),
	parameters: Map<String, String> = mapOf(),
	data: String? = "_",
	callback: (Response) -> Unit) {
	thread {
		callback(request(method.name.toUpperCase(), url, headers, parameters, data))
	}
}