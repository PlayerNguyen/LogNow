package com.playernguyen.lognow;

import com.playernguyen.lognow.setting.SettingConfiguration;

import org.bukkit.plugin.java.JavaPlugin;

public final class LogNow extends JavaPlugin {

    private SettingConfiguration settingConfiguration;

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            setupSetting();
            // setupDatabase();
        } catch (Exception e) {
            // catch all setup error
            e.printStackTrace();
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
}
