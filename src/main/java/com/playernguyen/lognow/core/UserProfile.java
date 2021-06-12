package com.playernguyen.lognow.core;

import java.util.UUID;

/**
 * A retrieved profile when got from query
 */
public class UserProfile {
	private final UUID uuid;
	private final String email;

	public UserProfile(UUID uuid, String email) {
		this.uuid = uuid;
		this.email = email;
	}

	public UUID getUniqueId() {
		return uuid;
	}

	public String getEmail() {
		return email;
	}

}
