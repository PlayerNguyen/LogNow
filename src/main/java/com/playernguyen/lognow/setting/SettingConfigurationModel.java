package com.playernguyen.lognow.setting;

import com.playernguyen.lognow.configuration.FileConfigurationModel;

public enum SettingConfigurationModel implements FileConfigurationModel {

	DEVELOPMENT_MODE("DevelopmentMode.Enable", false,
			"Enhancing the code by using development tools of this plugin.|"
					+ "This option will leak your information such as |" + "your database configuration, so be careful"),

	DATABASE_TYPE("Database.Type", "sqlite", "Your database type that you want to store ", "credential information.",
			" available: sqlite, mysql"),

	DATABASE_SQLITE_FILE_NAME("Database.SQLiteFileName", "storage.sqlite",
			"Database file name when the Database.Type set to sqlite.")

	;

	private final String key;
	private final Object value;
	private final String comments;

	SettingConfigurationModel(String key, Object value, String comments) {
		this.key = key;
		this.value = value;
		this.comments = comments;
	}

	SettingConfigurationModel(String key, Object value, String... comments) {
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
