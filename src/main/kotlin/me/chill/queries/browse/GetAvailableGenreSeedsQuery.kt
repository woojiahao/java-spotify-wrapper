package me.chill.queries.browse

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.readFromJsonArray
import me.chill.utility.request.RequestMethod

class GetAvailableGenreSeedsQuery private constructor(
  private val accessToken: String) : AbstractQuery<List<String>>(accessToken, RequestMethod.Get, "recommendations", "available-genre-seeds") {

  override fun execute(): List<String> =
    gson.readFromJsonArray("genres", checkedQuery())

  class Builder(private val accessToken: String) {
    fun build() = GetAvailableGenreSeedsQuery(accessToken)
  }
}