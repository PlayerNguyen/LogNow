package com.playernguyen.lognow.configuration;

/**
 * File configuration model to set configuration more specific.
 */
public interface FileConfigurationModel {

	/**
	 * @return an unique and immutable key to load the config
	 */
	String getKey();

	/**
	 * @return persist value when plugin first loaded
	 */
	Object getValue();

	/**
	 * 
	 * @return comment lines when plugin first loaded
	 */
	String[] getComments();
}
