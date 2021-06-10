package com.playernguyen.lognow;

import java.sql.SQLException;

import com.playernguyen.lognow.setting.SettingConfiguration;
import com.playernguyen.lognow.setting.SettingConfigurationModel;
import com.playernguyen.pndb.sql.DatabaseOptions;
import com.playernguyen.pndb.sql.adapt.DatabaseAdaptor;
import com.playernguyen.pndb.sql.hoster.DatabaseHoster;
import com.playernguyen.pndb.sql.query.DatabaseQueryBuilder;
import com.playernguyen.pndb.sql.sqlite.DatabaseHosterSQLite;
import com.playernguyen.pndb.sql.sqlite.DatabaseOptionsSQLite;

import org.bukkit.plugin.java.JavaPlugin;

public final class LogNow extends JavaPlugin {

    private SettingConfiguration settingConfiguration;
    private DatabaseHoster databaseHoster;

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            setupSetting();
            setupDatabase();
        } catch (Exception e) {
            // catch all setup error
            e.printStackTrace();
        }
    }

    private void setupDatabase() throws SQLException {
        // database type fetch
        String databaseType = settingConfiguration.get(SettingConfigurationModel.DATABASE_TYPE).asString();
        // sqlite set up
        if (databaseType.toLowerCase().equalsIgnoreCase("sqlite")) {
            DatabaseOptions option = new DatabaseOptionsSQLite(
                    this.getSettingConfiguration().get(SettingConfigurationModel.DATABASE_SQLITE_FILE_NAME).asString());
            this.databaseHoster = new DatabaseHosterSQLite(option);

            // connection test, failed will throw exception
            if (this.getDatabaseHoster().connection() == null) {
                throw new IllegalStateException("cannot generate connection...");
            }

            // set up table
            DatabaseQueryBuilder.newInstance(this.getDatabaseHoster()).executeCustomUpdate(
                    "CREATE TABLE IF NOT EXISTS lognow_user (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                            + "user_id VARCHAR NOT NULL, password VARCHAR NOT NULL, email VARCHAR NOT NULL)");

        }

    }

    private void setupSetting() throws Exception {
        this.getLogger().info("Loading setting configuration from file...");
        if (this.settingConfiguration == null) {
            this.settingConfiguration = new SettingConfiguration(this);
        } else {
            this.settingConfiguration.getYaml().load();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * 
     * @return setting configuration instance
     */
    public SettingConfiguration getSettingConfiguration() {
        return settingConfiguration;
    }

    /**
     * @return database hoster instance
     */
    public DatabaseHoster getDatabaseHoster() {
        return databaseHoster;
    }
}
