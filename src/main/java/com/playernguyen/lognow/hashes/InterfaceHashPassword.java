package com.playernguyen.lognow.hashes;

/**
 * Interface class to perform hash function.
 */
public interface InterfaceHashPassword {

    /**
     * Transform a raw password to a hashed password that will be stored in
     * database.
     * 
     * @param password a raw password
     * @return a hashed password
     */
    String hash(String password);

    /**
     * Validate your current password with a current hash that you are providing.
     * 
     * @param password a password
     * @param hash     a hash to valid
     * @return true whether match, false otherwise
     */
    boolean verify(String password, String hash);

}
