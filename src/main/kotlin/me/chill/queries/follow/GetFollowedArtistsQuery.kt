package me.chill.queries.follow

import me.chill.models.Artist
import me.chill.models.CursorBasedPaging
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkLimit
import me.chill.utility.request.query

class GetFollowedArtistsQuery private constructor(
  private val accessToken: String,
  private val limit: Int,
  private val after: String?) : AbstractQuery<CursorBasedPaging<Artist>>("me", "following") {

  override fun execute(): CursorBasedPaging<Artist> {
    val parameters = mapOf(
      "type" to "artist",
      "limit" to limit,
      "after" to after
    )

    val response = query(endpoint, accessToken, parameters)

    return gson.fromJson<CursorBasedPaging<Artist>>(response.jsonObject.getString("artists"), CursorBasedPaging::class.java)
  }

  class Builder(private val accessToken: String) {
    private var limit = 20
    private var after: String? = null

    fun limit(limit: Int): Builder {
      this.limit = limit
      return this
    }

    fun after(after: String): Builder {
      this.after = after
      return this
    }

    fun build(): GetFollowedArtistsQuery {
      limit.checkLimit()
      return GetFollowedArtistsQuery(accessToken, limit, after)
    }
  }
}