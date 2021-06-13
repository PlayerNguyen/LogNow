package com.playernguyen.lognow.localize;

import com.playernguyen.lognow.configurations.FileConfigurationModel;

public enum LocalizeConfigurationModel implements FileConfigurationModel {

  ;

 
	private final String key;
	private final Object value;
	private final String comments;

	LocalizeConfigurationModel(String key, Object value, String comments) {
		this.key = key;
		this.value = value;
		this.comments = comments;
	}

	LocalizeConfigurationModel(String key, Object value, String... comments) {
		this.key = key;
		this.value = value;
		this.comments = String.join("|", comments);
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
