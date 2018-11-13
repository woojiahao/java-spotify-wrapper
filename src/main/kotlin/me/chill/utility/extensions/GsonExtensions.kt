package me.chill.utility.extensions

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import khttp.responses.Response

inline fun <reified T> Gson.read(text: String): T =
  fromJson<T>(text, object : TypeToken<T>() {}.type)

inline fun <reified T> Gson.readFromJsonArray(arrayName: String, response: Response) =
  read<JsonObject>(response.text)
    .getAsJsonArray(arrayName)
    .map { read<T>(it.toString()) }

/**
 * Creates a Map<K, V> from a Response.
 *
 * @throws IllegalStateException If the [values] list contain duplicates
 * @param values Values to represent the keys, must be unique.
 * @param response Response to extract the values from.
 * @param arrayKey Key of an array within the [response], if no key is supplied, the entire [response] body is assumed to be a [JsonArray].
 * @return Map<K, V> formed by zipping [values] with the [JsonArray] from [response].
 */
inline fun <K, reified V> Gson.createResponseMap(
  values: List<K>,
  response: Response,
  arrayKey: String? = null): Map<K, V> {

  if (values.hasDuplicates()) throw IllegalStateException("Values list cannot contain duplicates")

  return if (arrayKey != null) {
    values.zip(readFromJsonArray<V>(arrayKey, response)).toMap()
  } else {
    values.zip(read<JsonArray>(response.text).map { read<V>(it.asString) }).toMap()
  }
}

inline fun <K, reified V> Gson.createResponseMap(
  values: Set<K>,
  response: Response,
  arrayKey: String? = null): Map<K, V> =
  when {
    arrayKey != null -> values.toList().zip(readFromJsonArray<V>(arrayKey, response)).toMap()
    else -> values.toList().zip(read<JsonArray>(response.text).map { read<V>(it.asString) }).toMap()
  }

