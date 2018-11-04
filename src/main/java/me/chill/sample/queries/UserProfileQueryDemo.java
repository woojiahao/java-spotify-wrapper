package me.chill.sample.queries;

import me.chill.models.User;

import static me.chill.sample.queries.UserStore.user;

public class UserProfileQueryDemo {
	public static void main(String[] args) {
		User currentUser = user.getCurrentUserProfile().build().execute();
		System.out.println(currentUser);

		User otherUser = user.getUserProfile("Chill").build().execute();
		System.out.println(otherUser);
	}
}
