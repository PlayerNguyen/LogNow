package com.playernguyen.lognow.collector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import com.playernguyen.lognow.LogNow;
import com.playernguyen.lognow.core.UserProfile;
import com.playernguyen.lognow.settings.SettingConfigurationModel;
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
		ResultSet resultSet = DatabaseQueryBuilder.newInstance(this.plugin.getDatabaseHoster()).selectAll(table)
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

	/**
	 * Create new user into database.
	 * 
	 * @param uuid     an uuid of user
	 * @param password a password of user
	 * @param email    an email of user
	 * @return true whether added, false otherwise
	 * @throws SQLException sql errors
	 */
	public boolean createUser(UUID uuid, String password, String email) throws SQLException {
		return DatabaseQueryBuilder.newInstance(plugin.getDatabaseHoster()).insert(table)
				.values("user_id", "password", "email")
				.executeUpdate(uuid.toString(), plugin.getHashSystem().hash(password), email) == 1;
	}

	/**
	 * ! ONLY WORK WHEN DEVELOPMENT MODE ENABLED.<br>
	 * 
	 * Reset development code.
	 * 
	 * @throws Exception sql errors
	 */
	public void reset() throws Exception {
		if (plugin.isDevelopment()) {
			this.plugin.getDebugWatcher().debug("resetting & truncating user table...");
			// reset
			DatabaseQueryBuilder.newInstance(this.plugin.getDatabaseHoster())
					.executeCustomUpdate(String.format("TRUNCATE %s", this.table));
		}
	}

}
