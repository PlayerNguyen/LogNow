package com.playernguyen.lognow.settings;

import com.playernguyen.lognow.configurations.FileConfigurationModel;

public enum SettingConfigurationModel implements FileConfigurationModel {

	DEVELOPMENT_MODE_ENABLE("DevelopmentMode.Enable", false,
			"Enhancing the code by using development tools of this plugin.|"
					+ "This option will leak your information such as |" + "your database configuration, so be careful"),

	DATABASE_TYPE("Database.Type", "sqlite", "Your database type that you want to store ", "credential information.",
			" available: sqlite, mysql"),

	DATABASE_SQLITE_FILE_NAME("Database.SQLiteFileName", "storage.sqlite",
			"Database file name when the Database.Type set to sqlite."),

	DATABASE_MYSQL_HOST("Database.MySQL.Host", "localhost", "Database host, as default is localhost"),
	DATABASE_MYSQL_PORT("Database.MySQL.Port", "3306", "Database port, as default is 3306"),
	DATABASE_MYSQL_USERNAME("Database.MySQL.Username", "root", "Database username settings."),
	DATABASE_MYSQL_PASSWORD("Database.MySQL.Password", "123", "Database password settings."),
	DATABASE_MYSQL_DATABASE("Database.MySQL.Database", "lognow", "Database database name, as default is lognow"),
	DATABASE_MYSQL_OPTION("Database.MySQL.Option", "?useSSL=true", "Database option parameter, as default is lognow"),
	DATABASE_MYSQL_USER_TABLE("Database.MySQL.UserTable", "lognow_user", "Database user table"),

	AUTH_HASH_ALGORITHM("Auth.HashAlgorithm", "bcrypt", "Select your hash algorithm to completely protect your ",
			" users' password. ", "  available: bcrypt"),

	BCRYPT_SALT_ROUNDS("Bcrypt.SaltRounds", "10", "Log rounds that use for hash password by bcrypt algorithm")

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
