package me.chill.utility.extensions

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import khttp.responses.Response

inline fun <reified T> Gson.read(text: String): T =
  fromJson<T>(text, object : TypeToken<T>() {}.type)

inline fun <reified T> Gson.readArray(response: Response) =
  read<JsonArray>(response.text)
    .map { read<T>(it.toString()) }

inline fun <reified T> Gson.readFromJsonArray(arrayName: String, response: Response) =
  read<JsonObject>(response.text)
    .getAsJsonArray(arrayName)
    .map { read<T>(it.toString()) }

inline fun <K, reified V> Gson.createResponseMap(
  values: Set<K>,
  response: Response,
  arrayKey: String? = null): Map<K, V> =
  when {
    arrayKey != null -> values.toList().zip(readFromJsonArray<V>(arrayKey, response)).toMap()
    else -> values.toList().zip(read<JsonArray>(response.text).map { read<V>(it.asString) }).toMap()
  }
