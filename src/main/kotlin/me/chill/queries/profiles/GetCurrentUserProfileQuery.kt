package me.chill.queries.profiles

import me.chill.models.User
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.read
import me.chill.utility.request.query

class GetCurrentUserProfileQuery private constructor(private val accessToken: String) : AbstractQuery<User>("me") {

	override fun execute(): User = gson.read<User>(query(queryEndpoint, accessToken).text)

	class Builder(private val accessToken: String) {
		fun build() = GetCurrentUserProfileQuery(accessToken)
	}
}