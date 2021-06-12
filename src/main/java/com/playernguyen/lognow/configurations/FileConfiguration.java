package com.playernguyen.lognow.configurations;

import com.osiris.dyml.DYModule;
import com.osiris.dyml.DYValue;
import com.osiris.dyml.exceptions.IllegalKeyException;
import com.osiris.dyml.exceptions.NotLoadedException;

/**
 * This abstract represents configuration and generates file.
 * 
 * @param <TModel> a configuration model which will be applied to this class
 */
public interface FileConfiguration<TModel extends FileConfigurationModel> {

	/**
	 * Get an item which configured in a file (cached in memory)
	 * 
	 * @param key key of config section
	 * @return configured value as DYValue
	 */
	DYValue get(TModel key);

	/**
	 * Set new value to the configuration file and then save it
	 * 
	 * @throws IllegalKeyException
	 * @throws NotLoadedException
	 */
	DYModule set(TModel key, DYValue object) throws NotLoadedException, IllegalKeyException;

}