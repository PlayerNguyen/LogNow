package com.playernguyen.lognow;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.playernguyen.lognow.collector.UserManager;
import com.playernguyen.lognow.hashes.HashPasswordBcrypt;
import com.playernguyen.lognow.hashes.InterfaceHashPassword;
import com.playernguyen.lognow.localize.LocalizeConfiguration;
import com.playernguyen.lognow.settings.SettingConfiguration;
import com.playernguyen.lognow.settings.SettingConfigurationModel;
import com.playernguyen.lognow.watch.DebugWatcher;
import com.playernguyen.pndb.sql.DatabaseOptions;
import com.playernguyen.pndb.sql.hoster.DatabaseHoster;
import com.playernguyen.pndb.sql.mysql.DatabaseHosterMySQL;
import com.playernguyen.pndb.sql.mysql.DatabaseOptionsMySQL;
import com.playernguyen.pndb.sql.query.DatabaseQueryBuilder;
import com.playernguyen.pndb.sql.sqlite.DatabaseHosterSQLite;
import com.playernguyen.pndb.sql.sqlite.DatabaseOptionsSQLite;

import org.bukkit.plugin.java.JavaPlugin;
import org.mindrot.jbcrypt.BCrypt;

public final class LogNow extends JavaPlugin {

    private boolean isDevelopment = false;

    private SettingConfiguration settingConfiguration;
    private LocalizeConfiguration localizeConfiguration;
    private DebugWatcher debugWatcher;
    private DatabaseHoster databaseHoster;
    private InterfaceHashPassword hashSystem;
    private UserManager userManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            setupSetting();
            setupLocalize();
            setupDatabase();
            setupCypher();
            setupUserManager();

            // test, for development mode
            if (isDevelopment) {
                testBcrypt();
                testDatabase();
            }
        } catch (Exception e) {
            // catch all setup error
            e.printStackTrace();
        }
    }

    private void setupLocalize() throws Exception {
        this.localizeConfiguration = new LocalizeConfiguration(this);
    }

    private void testDatabase() throws Exception {
        userManager.reset();
        userManager.createUser(UUID.randomUUID(), "12345", "emailller@gmail.com");
    }

    private void setupUserManager() {
        // if (userManager == null) {
        // this.userManager = new UserManager(this);
        // }
        this.userManager = new UserManager(this);
    }

    /**
     * cypher setup, use for hashing password
     * 
     * @throws ClassNotFoundException not found library (developer process)
     */
    private void setupCypher() throws ClassNotFoundException {
        String algorithm = getSettingConfiguration().get(SettingConfigurationModel.AUTH_HASH_ALGORITHM).asString();

        // bcrypt
        if (algorithm.equalsIgnoreCase("bcrypt")) {
            this.hashSystem = new HashPasswordBcrypt(this);
            return;
        }

        // not found algorithm
        throw new UnsupportedOperationException(String.format("cannot found algorithm: %s ", algorithm));
    }

    /**
     * this function call to test bcrypt
     */
    private void testBcrypt() {
        this.getDebugWatcher().debug("testing bcrypt generator...");
        List<String> hashes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String salt = BCrypt.gensalt(10);
            hashes.add(BCrypt.hashpw("password" + i, salt));
        }

        this.getDebugWatcher().map(hashes, "bcrypt generator");
    }

    private void setupDatabase() throws SQLException {
        // database type fetch
        this.getDebugWatcher().debug("Fetching database type...");
        String databaseType = settingConfiguration.get(SettingConfigurationModel.DATABASE_TYPE).asString();
        this.getDebugWatcher().debug(String.format("Detected database type %s", databaseType));
        // sqlite set up
        if (databaseType.toLowerCase().equalsIgnoreCase("sqlite")) {

            File sqliteFile = new File(this.getDataFolder(),
                    this.getSettingConfiguration().get(SettingConfigurationModel.DATABASE_SQLITE_FILE_NAME).asString());
            this.getDebugWatcher().debug(String.format("Creating SQLite file with path %s", sqliteFile.getPath()));

            DatabaseOptions option = new DatabaseOptionsSQLite(sqliteFile.getAbsolutePath());
            this.databaseHoster = new DatabaseHosterSQLite(option);
            this.getDebugWatcher().debug("Generating hoster for sqlite...");

            // connection test, failed will throw exception
            if (this.getDatabaseHoster().connection() == null) {
                throw new IllegalStateException("cannot generate connection...");
            }

            // set up table
            // user database
            DatabaseQueryBuilder.newInstance(this.getDatabaseHoster()).executeCustomUpdate(
                    "CREATE TABLE IF NOT EXISTS lognow_user (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                            + "user_id VARCHAR NOT NULL, password VARCHAR NOT NULL, email VARCHAR NOT NULL)");
            this.getDebugWatcher().debug("Creating a lognow_user table...");
            return;
        }

        // mysql set up
        if (databaseType.toLowerCase().equalsIgnoreCase("mysql")) {
            this.getDebugWatcher().debug("Selected mysql option as default database");
            DatabaseOptions options = new DatabaseOptionsMySQL(
                    getSettingConfiguration().get(SettingConfigurationModel.DATABASE_MYSQL_HOST).asString(),
                    getSettingConfiguration().get(SettingConfigurationModel.DATABASE_MYSQL_PORT).asString(),
                    getSettingConfiguration().get(SettingConfigurationModel.DATABASE_MYSQL_USERNAME).asString(),
                    getSettingConfiguration().get(SettingConfigurationModel.DATABASE_MYSQL_PASSWORD).asString(),
                    getSettingConfiguration().get(SettingConfigurationModel.DATABASE_MYSQL_DATABASE).asString(),
                    getSettingConfiguration().get(SettingConfigurationModel.DATABASE_MYSQL_OPTION).asString());
            this.databaseHoster = new DatabaseHosterMySQL(options);
            this.getDebugWatcher().debug("Generating option and hoster...");

            this.getDebugWatcher().debug("Creating connection to database (database connection test)...");
            // connection test, failed will throw exception
            if (this.getDatabaseHoster().connection() == null) {
                throw new IllegalStateException("cannot generate connection...");
            }

            this.getDebugWatcher().debug("Creating tables unless it existed ");
            DatabaseQueryBuilder.newInstance(this.getDatabaseHoster()).executeCustomUpdate(
                    "CREATE TABLE IF NOT EXISTS lognow_user (id INT(32) NOT NULL PRIMARY KEY AUTO_INCREMENT, "
                            + "user_id VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL)");
            return;
        }

    }

    private void setupSetting() throws Exception {
        this.getLogger().info("Loading setting configuration from file...");
        if (this.settingConfiguration == null) {
            this.settingConfiguration = new SettingConfiguration(this);

            this.isDevelopment = getSettingConfiguration().get(SettingConfigurationModel.DEVELOPMENT_MODE_ENABLE)
                    .asBoolean();
            this.debugWatcher = new DebugWatcher(this);
            this.getDebugWatcher().debug("debug enable...");

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

    /**
     * Debug watcher is a class that uses for profile plugin.
     * 
     * @return debug watcher class
     */
    public DebugWatcher getDebugWatcher() {
        return debugWatcher;
    }

    /**
     * 
     * @return true whether user turn on the development mode, false otherwise
     */
    public boolean isDevelopment() {
        return isDevelopment;
    }

    /**
     * 
     * @return hash system, use for hashing password
     */
    public InterfaceHashPassword getHashSystem() {
        return hashSystem;
    }

    /**
     * 
     * @return contains all language configuration
     */
    public LocalizeConfiguration getLocalizeConfiguration() {
        return localizeConfiguration;
    }
}
