package me.chill.authentication

import java.net.URLEncoder

fun generateLoginPageUrl(token: String, redirectUrl: String): String {
	val scopes = "user-read-private user-read-email"
	return "https://accounts.spotify.com/authorize" +
		"?response_type=code" +
		"&client_id=$token" +
		"&scope=${URLEncoder.encode(scopes, "UTF-8")}" +
		"&redirect_uri=${URLEncoder.encode(redirectUrl, "UTF-8")}"
}