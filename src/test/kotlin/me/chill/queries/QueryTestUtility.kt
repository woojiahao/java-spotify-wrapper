package me.chill.queries

import kotlin.test.assertEquals

inline fun <reified T> checkQueryReturnedType(query: Any) = assertEquals(T::class.java, query::class.java)