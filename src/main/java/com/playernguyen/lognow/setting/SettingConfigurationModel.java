package com.playernguyen.lognow.setting;

import com.playernguyen.lognow.configuration.FileConfigurationModel;

public enum SettingConfigurationModel implements FileConfigurationModel {

	DEVELOPMENT_MODE("DevelopmentMode.Enable", false, "Enhancing the code by using development tools of this plugin.|"
			+ "This option will leak your information such as |" + "your database configuration, so be careful");

	private final String key;
	private final Object value;
	private final String comments;

	SettingConfigurationModel(String key, Object value, String comments) {
		this.key = key;
		this.value = value;
		this.comments = comments;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public String[] getComments() {
		return comments.split("\\|");
	}

}
