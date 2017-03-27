package nl.imine.itemplus;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitStarter extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        EffectListener.init();
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    public static Plugin getInstance(){
        return plugin;
    }
}
