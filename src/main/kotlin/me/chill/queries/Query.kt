package me.chill.queries

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder

abstract class Query {
	protected val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

	protected fun generateHeaders(authorizationToken: String) = mapOf("Authorization" to "Bearer $authorizationToken")

	abstract fun execute(): Any
}