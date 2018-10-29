package me.chill.sample.queries;

import me.chill.SpotifyUser;
import me.chill.models.CurrentlyPlayingContext;

class PlayerQueryDemo {
	public static void main(String[] args) {
		SpotifyUser user = new SpotifyUser("BQBykloPeLarbqSNNNjrJ8ZXH4PI4-dW9iydcD_N_A8uLod6rwBg77aEsfNLZ0TiYxYGBh5uyIRtBFLUTmM6SQaHxAyS8f1rXlaa79Vu9MJBr4NyqV1rr_iJKakMngeV3-g6kLAdhcpMa3kbggB_Og2uzw\n");

		CurrentlyPlayingContext context = user.getCurrentPlaybackInformation().build().execute();

		System.out.println(context);
	}
}