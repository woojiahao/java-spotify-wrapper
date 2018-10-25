package me.chill.queries.browse

import com.neovisionaries.i18n.CountryCode
import me.chill.models.Recommendation
import me.chill.queries.SpotifyQueryException

// TODO: Make an enumeration for the genre types
class SeedQuery private constructor(
	private val accessToken: String,
	private val limit: Int,
	private val attributes: Map<String, *>,
	private val seedArtists: String?,
	private val seedGenres: String?,
	private val seedTracks: String?,
	private val market: String?) : SpotifyBrowseQuery() {

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
			"seed_artists" to seedArtists,
			"seed_genres" to seedGenres,
			"seed_tracks" to seedTracks
		)

		attributes.forEach { key, attribute ->
			parameters["max_$key"] = (attribute as Attribute<*>).max
			parameters["min_$key"] = attribute.min
			parameters["target_$key"] = attribute.target
		}

		val response = query("https://api.spotify.com/v1/recommendations", accessToken, parameters)

		return gson.fromJson(response.text, Recommendation::class.java)
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

		private val seedArtists = mutableListOf<String>()
		private val seedGenres = mutableListOf<String>()
		private val seedTracks = mutableListOf<String>()

		fun limit(limit: Int): Builder {
			if (limit < 1 || limit > 100) throw SpotifyQueryException("Limit cannot be less than 1 or greater than a 100")
			this.limit = limit
			return this
		}

		fun market(market: CountryCode): Builder {
			this.market = market
			return this
		}

		fun addSeedArtist(seedArtist: String): Builder {
			seedArtists.addAll(seedArtist.split(","))
			return this
		}

		fun setSeedArtists(seedArtists: List<String>): Builder {
			this.seedArtists.clear()
			this.seedArtists.addAll(seedArtists)
			return this
		}

		fun addSeedGenre(seedGenre: String): Builder {
			seedGenres.addAll(seedGenre.split(","))
			return this
		}

		fun setSeedGenres(seedGenres: List<String>): Builder {
			this.seedGenres.clear()
			this.seedGenres.addAll(seedGenres)
			return this
		}

		fun addSeedTrack(seedTrack: String): Builder {
			seedTracks.addAll(seedTrack.split(","))
			return this
		}

		fun setSeedTracks(seedTracks: List<String>): Builder {
			this.seedTracks.clear()
			this.seedTracks.addAll(seedTracks)
			return this
		}

		fun acousticness(flag: Flag, acousticness: Double): Builder {
			if (acousticness < 0.0 || acousticness > 1.0) throw SpotifyQueryException("Acousticness must be between 0.0 and 1.0")
			setAttribute(flag, "acousticness", acousticness)
			return this
		}

		fun danceability(flag: Flag, danceability: Double): Builder {
			if (danceability < 0.0 || danceability > 1.0) throw SpotifyQueryException("Danceability must be between 0.0 and 1.0")
			setAttribute(flag, "danceability", danceability)
			return this
		}

		fun duration(flag: Flag, duration: Int): Builder {
			if (duration < 0.0) throw SpotifyQueryException("Duration must be greater than 0")
			setAttribute(flag, "duration_ms", duration)
			return this
		}

		fun energy(flag: Flag, energy: Double): Builder {
			if (energy < 0.0 || energy > 1.0) throw SpotifyQueryException("Energy must be between 0.0 and 1.0")
			setAttribute(flag, "energy", energy)
			return this
		}

		fun instrumentalness(flag: Flag, instrumentalness: Double): Builder {
			if (instrumentalness < 0.0 || instrumentalness > 1.0) throw SpotifyQueryException("Instrumentalness must be between 0.0 and 1.0")
			setAttribute(flag, "instrumentalness", instrumentalness)
			return this
		}

		fun key(flag: Flag, key: Int): Builder {
			setAttribute(flag, "key", key)
			return this
		}

		fun liveness(flag: Flag, liveness: Double): Builder {
			if (liveness < 0.0 || liveness > 1.0) throw SpotifyQueryException("Liveness must be between 0.0 and 1.0")
			setAttribute(flag, "liveness", liveness)
			return this
		}

		fun loudness(flag: Flag, loudness: Double): Builder {
			if (loudness < 0.0) throw SpotifyQueryException("Loudness must be greater than 0.0")
			setAttribute(flag, "loudness", loudness)
			return this
		}

		fun mode(flag: Flag, mode: Int): Builder {
			if (!arrayOf(0, 1).contains(mode)) throw SpotifyQueryException("Mode must be either 0 or 1")
			setAttribute(flag, "mode", mode)
			return this
		}

		fun popularity(flag: Flag, popularity: Int): Builder {
			if (popularity < 0 || popularity > 100) throw SpotifyQueryException("Popularity must be between 0 and 100")
			setAttribute(flag, "popularity", popularity)
			return this
		}

		fun speechiness(flag: Flag, speechiness: Double): Builder {
			if (speechiness < 0.0 || speechiness > 1.0) throw SpotifyQueryException("Speechiness must be between 0.0 and 1.0")
			setAttribute(flag, "speechiness", speechiness)
			return this
		}

		fun tempo(flag: Flag, tempo: Double): Builder {
			if (tempo < 0.0) throw SpotifyQueryException("Tempo must be greater than 0.0")
			setAttribute(flag, "tempo", tempo)
			return this
		}

		fun timeSignature(flag: Flag, timeSignature: Int): Builder {
			if (timeSignature < 0) throw SpotifyQueryException("Time Signature must be greater than 0")
			setAttribute(flag, "time_signature", timeSignature)
			return this
		}

		fun valence(flag: Flag, valence: Double): Builder {
			if (valence < 0.0 || valence > 1.0) throw SpotifyQueryException("Valence must be between 0.0 and 1.0")
			setAttribute(flag, "valence", valence)
			return this
		}

		fun build(): SeedQuery {
			val totalSeedSize = seedArtists.size + seedGenres.size + seedTracks.size
			if (totalSeedSize < 0) {
				throw SpotifyQueryException("At least 1 seed has to be provided")
			}
			if (totalSeedSize > 5) {
				throw SpotifyQueryException("Seed values are restricted to only 5 a time")
			}

			return SeedQuery(
				accessToken,
				limit,
				attributes,
				seedArtists.takeIf { it.isNotEmpty() }?.joinToString(","),
				seedGenres.takeIf { it.isNotEmpty() }?.joinToString(","),
				seedTracks.takeIf { it.isNotEmpty() }?.joinToString(","),
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