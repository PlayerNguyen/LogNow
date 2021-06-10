package com.playernguyen.lognow.setting;

import com.playernguyen.lognow.configuration.AbstractFileConfiguration;

import org.bukkit.plugin.Plugin;

public class SettingConfiguration extends AbstractFileConfiguration<SettingConfigurationModel> {
	private static final String SETTING_CONFIGURATION_FILE_NAME = "settings.yml";

	public SettingConfiguration(Plugin plugin)
			throws Exception {
		super(plugin, SETTING_CONFIGURATION_FILE_NAME, SettingConfigurationModel.class);
		
	}


	
}
