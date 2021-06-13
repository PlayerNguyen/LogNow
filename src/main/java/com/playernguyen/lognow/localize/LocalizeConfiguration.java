package com.playernguyen.lognow.localize;

import com.playernguyen.lognow.LogNow;
import com.playernguyen.lognow.configurations.AbstractFileConfiguration;

public class LocalizeConfiguration extends AbstractFileConfiguration<LocalizeConfigurationModel> {

	private static final String LANGUAGE_FILE_NAME = "language.yml";

	public LocalizeConfiguration(LogNow plugin) throws Exception {
		super(plugin, LANGUAGE_FILE_NAME, LocalizeConfigurationModel.class);
	}

}
