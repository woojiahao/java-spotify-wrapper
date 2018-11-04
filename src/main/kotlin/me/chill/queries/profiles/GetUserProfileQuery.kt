package me.chill.queries.profiles

import me.chill.models.User
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.read
import me.chill.utility.request.query

/**
 * Get public profile information about a Spotify user
 */
class GetUserProfileQuery private constructor(
	private val accessToken: String,
	private val userId: String) : AbstractQuery<User>("users", userId) {

	/**
	 * @throws SpotifyQueryException If the user id supplied is not found
	 * @return User's public profile information
	 */
	override fun execute() = gson.read<User>(query(queryEndpoint, accessToken).text)

	class Builder(private val accessToken: String, private val userId: String) {
		fun build() = GetUserProfileQuery(accessToken, userId)
	}
}