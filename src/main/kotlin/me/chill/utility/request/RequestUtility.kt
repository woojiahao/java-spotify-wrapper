package me.chill.utility.request

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import khttp.responses.Response
import me.chill.exceptions.SpotifyQueryException
import me.chill.models.RegularError

// TODO: Add a method to handle empty put body requests

private val gson: Gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

fun generateHeader(accessToken: String) = Header.Builder().accessToken(accessToken).build().generate()

fun generateModificationHeader(accessToken: String) =
  Header.Builder().accessToken(accessToken).contentType(Header.Builder.ContentType.Json).build().generate()

fun Response.responseCheck(vararg exemptions: Int) {
  if (text.isBlank()) return
  if (statusCode >= 400 && !exemptions.contains(statusCode)) displayErrorMessage(this)
}

fun extractError(response: Response): RegularError =
  gson.fromJson(gson.fromJson(response.text, JsonObject::class.java)["error"], RegularError::class.java)


fun displayErrorMessage(response: Response) {
  response.text.takeIf { it.isBlank() }?.let { throw SpotifyQueryException(response.statusCode, null) }
  val errorBody = extractError(response)
  throw SpotifyQueryException(errorBody.status, errorBody.message)
}