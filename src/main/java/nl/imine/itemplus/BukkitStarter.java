package nl.imine.itemplus;

import nl.imine.itemplus.commands.ItemsplusCommand;
import nl.imine.itemplus.settings.Settings;
import nl.imine.itemplus.staff.StaffManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitStarter extends JavaPlugin {

    private static Plugin plugin;
    private static Settings settings;
//    private StaffManager sm;

    @Override
    public void onEnable() {

        plugin = this;
        settings = new Settings(this.getConfig());

        setUpConfig();
        StaffManager.loadStaffsFromConfig();

//        EffectListener.init();
        this.getCommand("itemsplus").setExecutor(new ItemsplusCommand());

    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    public static Plugin getInstance() {
        return plugin;
    }

    public static Settings getSettings() {
        return settings;
    }

    private void setUpConfig() {
        settings.createDefaults();
        this.saveConfig();
    }
}
