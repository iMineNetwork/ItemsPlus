package nl.imine.itemplus;

import nl.imine.itemplus.commands.ItemsplusCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitStarter extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        EffectListener.init();
        this.getCommand(".itemsplus").setExecutor(new ItemsplusCommand());
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    public static Plugin getInstance() {
        return plugin;
    }
}
