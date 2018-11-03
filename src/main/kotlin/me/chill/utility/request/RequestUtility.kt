package me.chill.utility.request

import com.google.gson.*
import khttp.get
import khttp.responses.Response
import me.chill.exceptions.SpotifyQueryException
import me.chill.models.RegularError
import me.chill.utility.extensions.generateParameters

// TODO: Add a method to handle empty put body requests

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
	text.takeIf { it.isBlank() }?.let { return }
	statusCode.takeIf { it >= 400 }?.let { displayErrorMessage(this) }
}

fun extractError(response: Response): RegularError =
	gson.fromJson(gson.fromJson(response.text, JsonObject::class.java)["error"], RegularError::class.java)


fun displayErrorMessage(response: Response) {
	response.text.takeIf { it.isBlank() }?.let { throw SpotifyQueryException(response.statusCode, null) }
	val errorBody = extractError(response)
	throw SpotifyQueryException(errorBody.status, errorBody.message)
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