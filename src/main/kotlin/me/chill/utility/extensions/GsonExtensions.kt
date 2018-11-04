package me.chill.utility.extensions

import com.google.gson.Gson

inline fun <reified T> Gson.read(text: String): T = fromJson<T>(text, T::class.java)