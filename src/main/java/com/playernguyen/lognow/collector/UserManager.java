package com.playernguyen.lognow.collector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import com.playernguyen.lognow.LogNow;
import com.playernguyen.lognow.core.UserProfile;
import com.playernguyen.lognow.setting.SettingConfigurationModel;
import com.playernguyen.pndb.sql.query.CriteriaBuilder;
import com.playernguyen.pndb.sql.query.CriteriaField;
import com.playernguyen.pndb.sql.query.DatabaseQueryBuilder;

public class UserManager {
	private final LogNow plugin;
	private final String table;

	public UserManager(LogNow plugin) {
		this.plugin = plugin;
		this.table = plugin.getSettingConfiguration().get(SettingConfigurationModel.DATABASE_MYSQL_USER_TABLE)
				.asString();
	}

	/**
	 * Find and retrieve the first element in query database.
	 * 
	 * @param uuid an unique id of a player that was storage
	 * @return an optional of user profile, can be null inside
	 * @throws SQLException sql errors catch
	 */
	public Optional<UserProfile> findOne(UUID uuid) throws SQLException {
		ResultSet resultSet = DatabaseQueryBuilder.newInstance(this.plugin.getDatabaseHoster()).selectAll()
				.criteria(CriteriaBuilder.newInstance().newField(CriteriaField.equal("uuid")))
				.executeQuery(uuid.toString());

		UserProfile profile = null;
		while (resultSet.next()) {
			String retrievedUUID = resultSet.getString("user_id");
			String retrievedEmail = resultSet.getString("email");
			profile = new UserProfile(UUID.fromString(retrievedUUID), retrievedEmail);
		}
		return Optional.of(profile);
	}

	public boolean addUser(UUID uuid, String password, String email) {
		DatabaseQueryBuilder.newInstance(plugin.getDatabaseHoster())
			.insert(table).values("uuid", "password", "email")
		return true;
	}

}
