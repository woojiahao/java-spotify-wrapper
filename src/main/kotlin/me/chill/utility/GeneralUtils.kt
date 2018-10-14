package me.chill.utility

import com.google.gson.Gson
import com.google.gson.JsonObject
import khttp.responses.Response
import me.chill.models.RegularError
import me.chill.queries.SpotifyQueryException

fun createErrorMessage(errMap: Map<String, String>) =
	"\n\n${errMap.map { "\t${it.key}: ${it.value}" }.joinToString("\n")}\n"

fun Response.responseCheck() {
	if (statusCode >= 400) {
		val errorBody = Gson().fromJson(Gson().fromJson(text, JsonObject::class.java)["error"], RegularError::class.java)
		throw SpotifyQueryException(errorBody.status, errorBody.message)
	}
}