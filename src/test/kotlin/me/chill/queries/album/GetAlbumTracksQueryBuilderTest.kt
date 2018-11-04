package me.chill.queries.album

import me.chill.exceptions.SpotifyQueryException
import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class GetAlbumTracksQueryBuilderTest {
	private var builder: GetAlbumTracksQuery.Builder? = null

	private fun checkQuery(query: Any) {
		assertEquals(GetAlbumTracksQuery::class, query::class)
	}

	@BeforeTest fun setup() {
		builder = GetAlbumTracksQuery.Builder("6akEvsycLGftJxYudPjmqK", "access_token")
	}

	@Test(expected = SpotifyQueryException::class)
	fun setting_limit_below_1_throws_exception() {
		builder?.limit(0)?.build()
	}

	@Test(expected = SpotifyQueryException::class)
	fun setting_limit_above_50_throws_exception() {
		builder?.limit(51)?.build()
	}

	@Test fun setting_limit_within_range_returns_query() {
		val query = builder?.limit(10)?.build()!!
		checkQuery(query)
	}

	@Test fun setting_limit_to_1_returns_query() {
		val query = builder?.limit(1)?.build()!!
		checkQuery(query)
	}

	@Test fun setting_limit_to_50_returns_query() {
		val query = builder?.limit(50)?.build()!!
		checkQuery(query)
	}

	@Test(expected = SpotifyQueryException::class)
	fun setting_offset_below_0_throws_exception() {
		builder?.offset(-1)?.build()
	}

	@Test fun setting_offset_above_1_returns_query() {
		val query = builder?.offset(10)?.build()!!
		checkQuery(query)
	}
}