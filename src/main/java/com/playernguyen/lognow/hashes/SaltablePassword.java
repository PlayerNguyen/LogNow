package com.playernguyen.lognow.hashes;

public abstract class SaltablePassword implements InterfaceHashPassword {

	/**
	 * Generate salt for saltable password.
	 * 
	 * @return a generated salt uses for generate password
	 */
	public abstract String generateSalt();

}
