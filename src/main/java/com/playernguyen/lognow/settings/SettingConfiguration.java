package com.playernguyen.lognow.settings;

import com.playernguyen.lognow.LogNow;
import com.playernguyen.lognow.configurations.AbstractFileConfiguration;

public class SettingConfiguration extends AbstractFileConfiguration<SettingConfigurationModel> {
	private static final String SETTING_CONFIGURATION_FILE_NAME = "settings.yml";

	public SettingConfiguration(LogNow plugin) throws Exception {
		super(plugin, SETTING_CONFIGURATION_FILE_NAME, SettingConfigurationModel.class);
	}

}
