package me.chill.sample.queries;

import me.chill.SpotifyUser;

public class UserStore {
	private static String accessToken = "BQCsjnH29w9iFmKdDA2zMCA_54ZbnDTdpMk-MTbS8HJGlxbn3rU6kVUjAZgW2Le8RGFE5LKTIy0etVsypmlW0Vys0jtctAoj3fVpBj08i42oIbnoeOZda0-FL3ERXV2GdxyVtbLs2jiPpcF6b_GeF4ees99mB4KKrx6IjhNtDZ-586BqIslAq2UYP8pUxm8NjivWFLGVraiNogWJvWz_LIo6B4VtqUHzMEg463TYzArwJgjGO7z3wtCkb8uUtb-CuVfrJ4fOnz3jaDhvwx5tXg";

	public static SpotifyUser user = new SpotifyUser(accessToken);
}
