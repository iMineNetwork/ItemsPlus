package nl.imine.itemplus;

import nl.imine.itemplus.commands.ItemsplusCommand;
import nl.imine.itemplus.settings.Settings;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitStarter extends JavaPlugin {

    private static Plugin plugin;
    private static Settings settings;

    @Override
    public void onEnable() {
        System.out.println("[ITEMS+] 1");
        plugin = this;
        System.out.println("[ITEMS+] 2");
        BukkitStarter.settings = new Settings(this.getConfig());
        System.out.println("[ITEMS+] 3");
        setUpConfig();
        System.out.println("[ITEMS+] 4");
        EffectListener.init();
        System.out.println("[ITEMS+] 5");
        this.getCommand("itemsplus").setExecutor(new ItemsplusCommand());
        System.out.println("[ITEMS+] 6");
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    public static Plugin getInstance() {
        return plugin;
    }
   
    public static Settings getSettings(){
        return settings;
    }
    
    
    private void setUpConfig() {
        settings.createDefaults();
        this.saveConfig();
    }
}
