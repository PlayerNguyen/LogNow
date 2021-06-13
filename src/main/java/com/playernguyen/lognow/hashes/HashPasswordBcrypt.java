package com.playernguyen.lognow.hashes;

import com.playernguyen.lognow.LogNow;
import com.playernguyen.lognow.settings.SettingConfigurationModel;

import org.mindrot.jbcrypt.BCrypt;

public class HashPasswordBcrypt extends SaltablePassword {
	private final LogNow plugin;

	public HashPasswordBcrypt(LogNow plugin) throws ClassNotFoundException {
		this.plugin = plugin;

		// check that the library was compiled or not 
		Class.forName("org.mindrot.jbcrypt.BCrypt");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String generateSalt() {
		return BCrypt.gensalt(plugin.getSettingConfiguration().get(SettingConfigurationModel.BCRYPT_SALT_ROUNDS).asInt());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String hash(String password) {
		return BCrypt.hashpw(password, generateSalt());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean verify(String password, String hash) {
		return BCrypt.checkpw(password, hash);
	}

}
