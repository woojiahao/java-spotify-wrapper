package me.chill.queries.player

import me.chill.exceptions.SpotifyQueryException
import org.junit.Test
import kotlin.test.BeforeTest

class PlayTrackQueryBuilderTest {
  private var builder: PlayTrackQuery.Builder? = null

  @BeforeTest fun setup() {
    builder = PlayTrackQuery.Builder("access_token")
  }

  @Test(expected = SpotifyQueryException::class)
  fun setting_multiple_uri_types_throws_exception() {
    builder
      ?.addUri("spotify:track:4iV5W9uYEdYUVa79Axb7Rh")
      ?.addUri("spotify:track:1301WleyT98MSxVHPZCA6M")
      ?.contextUri("spotify:album:1Je1IMUlBXcx1Fz0WE7oPT")
      ?.build()
  }

  @Test(expected = SpotifyQueryException::class)
  fun setting_offset_properties_without_uri_types_throws_exception() {
    builder?.offsetPosition(10)?.build()
  }

  @Test(expected = SpotifyQueryException::class)
  fun setting_multiple_offset_properties_with_contextUri_throws_exception() {
    builder
      ?.contextUri("spotify:album:1Je1IMUlBXcx1Fz0WE7oPT")
      ?.offsetPosition(10)
      ?.offsetUri("spotify:track:4iV5W9uYEdYUVa79Axb7Rh")
      ?.build()
  }
}