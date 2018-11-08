package me.chill.queries.playlist

import me.chill.exceptions.SpotifyQueryException
import me.chill.queries.checkQueryReturnedType
import org.junit.Test
import java.util.UUID.randomUUID
import kotlin.test.BeforeTest

class AddTrackToPlaylistQueryBuilderTest {
  private var builder: AddTracksToPlaylistQuery.Builder? = null

  @BeforeTest fun setup() {
    builder = AddTracksToPlaylistQuery.Builder("access_token", "37i9dQZF1DX7KWRIlkUpsl")
  }

  @Test(expected = SpotifyQueryException::class)
  fun negative_position_throws_exception() {
    builder?.position(-1)?.build()
  }

  @Test(expected = SpotifyQueryException::class)
  fun uri_size_above_100_throws_exception() {
    generateUniqueUris(101).forEach { builder?.addUris(it) }
    builder?.build()
  }

  @Test fun positive_position_returns_query() {
    val query = builder?.position(10)?.build()!!
    checkQueryReturnedType<AddTracksToPlaylistQuery>(query)
  }

  @Test fun uri_below_100_returns_query() {
    generateUniqueUris(50).forEach { builder?.addUris(it) }
    val query = builder?.build()!!
    checkQueryReturnedType<AddTracksToPlaylistQuery>(query)
  }

  private fun generateUniqueUris(size: Int) =
    (0..(size + 1)).map { randomUUID().toString() }
}