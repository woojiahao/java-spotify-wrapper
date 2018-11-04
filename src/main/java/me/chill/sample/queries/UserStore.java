package me.chill.sample.queries;

import me.chill.SpotifyUser;

public class UserStore {
	private static String accessToken = "BQCfO5Qras-cunrlmsvugnyKHZS48mGdmS64H211cWTh-eAS-19COEheV7wVE-KmIrhIWG83Zyc07fuMOOWR6f9Tf3OOVUw-X9xVVnM7fjdPm57MEEu0L6HtcWrwVF3BfSkAyQaWvr7TXIzWgbQj6sLFeVC_Um59z042F7EdoZN12tLHfSuhldLBOnYet-OCS7nEqoevClPP6zbtuglaD-jyQFP2Bg1Dkb4PwZZJ8w7gWjDjxko2319JPBLhGd585lvfehuM_TiPX4UyYdcw_A";

	public static SpotifyUser user = new SpotifyUser(accessToken);
}
