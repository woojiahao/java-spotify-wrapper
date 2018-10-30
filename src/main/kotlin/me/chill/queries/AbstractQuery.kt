package me.chill.queries

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import khttp.get
import khttp.request
import khttp.responses.Response
import me.chill.exceptions.SpotifyQueryException
import me.chill.models.RegularError
import me.chill.utility.extensions.generateParameters
import me.chill.utility.request.Header
import me.chill.utility.request.responseCheck
import kotlin.concurrent.thread

// TODO: Have 2 different forms of execute, one for synchronous calls and the other for asynchronous
abstract class AbstractQuery(private vararg val pathSegments: String) {
	protected val gson: Gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

	protected val primaryEndpoint = "https://api.spotify.com/"

	protected val queryEndpoint = "${primaryEndpoint}v1/${pathSegments.joinToString("/") { it.replace("/", "") }}"

	abstract fun execute(): Any?
}