package me.chill.queries.browse

import com.neovisionaries.i18n.CountryCode
import me.chill.exceptions.SpotifyQueryException
import me.chill.models.Recommendation
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.*
import me.chill.utility.request.RequestMethod

class GetRecommendationFromSeedQuery private constructor(
  private val accessToken: String,
  private val limit: Int,
  private val attributes: Map<String, *>,
  private val seedArtists: Set<String>,
  private val seedGenres: Set<String>,
  private val seedTracks: Set<String>,
  private val market: String?) : AbstractQuery<Recommendation>(accessToken, RequestMethod.Get, "recommendations") {

  enum class Flag { Max, Min, Target }

  private data class Attribute<T : Number>(
    var min: T? = null,
    var max: T? = null,
    var target: T? = null
  )

  override fun execute(): Recommendation {
    val parameters = mutableMapOf(
      "limit" to limit,
      "market" to market,
      "seed_artists" to seedArtists.generateNullableString(),
      "seed_genres" to seedGenres.generateNullableString(),
      "seed_tracks" to seedTracks.generateNullableString()
    )

    attributes.forEach { key, attribute ->
      parameters["max_$key"] = (attribute as Attribute<*>).max
      parameters["min_$key"] = attribute.min
      parameters["target_$key"] = attribute.target
    }

    return gson.read(checkedQuery(parameters).text)
  }

  class Builder(private val accessToken: String) {
    private var limit = 20
    private var market: CountryCode? = null

    private val attributes = mapOf(
      "acousticness" to Attribute<Double>(),
      "danceability" to Attribute<Double>(),
      "duration_ms" to Attribute<Int>(),
      "energy" to Attribute<Double>(),
      "instrumentalness" to Attribute<Double>(),
      "key" to Attribute<Int>(),
      "liveness" to Attribute<Double>(),
      "loudness" to Attribute<Double>(),
      "mode" to Attribute<Int>(),
      "popularity" to Attribute<Int>(),
      "speechiness" to Attribute<Double>(),
      "tempo" to Attribute<Double>(),
      "time_signature" to Attribute<Int>(),
      "valence" to Attribute<Double>()
    )

    private val seedArtists = mutableSetOf<String>()
    private val seedGenres = mutableSetOf<String>()
    private val seedTracks = mutableSetOf<String>()

    fun limit(limit: Int): Builder {
      this.limit = limit
      return this
    }

    fun market(market: CountryCode): Builder {
      this.market = market
      return this
    }

    fun addSeedArtists(vararg seedArtists: String): Builder {
      this.seedArtists.splitAndAdd(seedArtists)
      return this
    }

    fun addSeedGenres(vararg seedGenres: String): Builder {
      this.seedGenres.splitAndAdd(seedGenres)
      return this
    }

    fun addSeedTracks(vararg seedTracks: String): Builder {
      this.seedTracks.splitAndAdd(seedTracks)
      return this
    }

    fun acousticness(flag: Flag, acousticness: Double): Builder {
      acousticness.checkRange("Acousticness", 0.0, 1.0)
      setAttribute(flag, "acousticness", acousticness)
      return this
    }

    fun danceability(flag: Flag, danceability: Double): Builder {
      danceability.checkRange("Danceability", 0.0, 1.0)
      setAttribute(flag, "danceability", danceability)
      return this
    }

    fun duration(flag: Flag, duration: Int): Builder {
      duration.checkLower("Duration")
      setAttribute(flag, "duration_ms", duration)
      return this
    }

    fun energy(flag: Flag, energy: Double): Builder {
      energy.checkRange("Energy", 0.0, 1.0)
      setAttribute(flag, "energy", energy)
      return this
    }

    fun instrumentalness(flag: Flag, instrumentalness: Double): Builder {
      instrumentalness.checkRange("Instrumentalness", 0.0, 1.0)
      setAttribute(flag, "instrumentalness", instrumentalness)
      return this
    }

    fun key(flag: Flag, key: Int): Builder {
      setAttribute(flag, "key", key)
      return this
    }

    fun liveness(flag: Flag, liveness: Double): Builder {
      liveness.checkRange("Liveness", 0.0, 1.0)
      setAttribute(flag, "liveness", liveness)
      return this
    }

    fun loudness(flag: Flag, loudness: Double): Builder {
      loudness.checkRange("Loudness", 0.0, 1.0)
      setAttribute(flag, "loudness", loudness)
      return this
    }

    fun mode(flag: Flag, mode: Int): Builder {
      if (!arrayOf(0, 1).contains(mode)) throw SpotifyQueryException("Mode must be either 0 or 1")
      setAttribute(flag, "mode", mode)
      return this
    }

    fun popularity(flag: Flag, popularity: Int): Builder {
      popularity.checkRange("Popularity", upper = 100)
      setAttribute(flag, "popularity", popularity)
      return this
    }

    fun speechiness(flag: Flag, speechiness: Double): Builder {
      speechiness.checkRange("Speechiness", 0.0, 1.0)
      setAttribute(flag, "speechiness", speechiness)
      return this
    }

    fun tempo(flag: Flag, tempo: Double): Builder {
      tempo.checkLower("Tempo")
      setAttribute(flag, "tempo", tempo)
      return this
    }

    fun timeSignature(flag: Flag, timeSignature: Int): Builder {
      timeSignature.checkLower("Time Signature")
      setAttribute(flag, "time_signature", timeSignature)
      return this
    }

    fun valence(flag: Flag, valence: Double): Builder {
      valence.checkRange("Valence", 0.0, 1.0)
      setAttribute(flag, "valence", valence)
      return this
    }

    fun build(): GetRecommendationFromSeedQuery {
      val totalSeedSize = seedArtists.size + seedGenres.size + seedTracks.size
      totalSeedSize.checkRange("Seed values", upper = 5)

      limit.checkLimit(upper = 100)

      return GetRecommendationFromSeedQuery(
        accessToken,
        limit,
        attributes,
        seedArtists,
        seedGenres,
        seedTracks,
        market?.alpha2
      )
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Number> setAttribute(flag: Flag, key: String, value: T) {
      val attribute = attributes[key] ?: return

      when (flag) {
        Flag.Max -> (attribute as Attribute<T>).max = value
        Flag.Min -> (attribute as Attribute<T>).min = value
        Flag.Target -> (attribute as Attribute<T>).target = value
      }
    }
  }
}