package nl.imine.itemplus.settings;

import org.bukkit.configuration.file.FileConfiguration;

public class Settings {

    private final FileConfiguration configuration;

    public Settings(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    public void createDefaults() {
        configuration.addDefault(Setting.ENDERSTAFF_NAME, "Enderstaff");
        configuration.addDefault(Setting.ENDERSTAFF_DURABILITY, 17);
        configuration.addDefault(Setting.ENDERSTAFF_PRIMARY_XP_COST, "0.2f");
        configuration.addDefault(Setting.ENDERSTAFF_PRIMARY_MAX_TELEPORT_DISTANCE, 30);
        configuration.addDefault(Setting.ENDERSTAFF_RESET_FALLDISTANCE_ON_TELEPORT, true);
        configuration.addDefault(Setting.ENDERSTAFF_PRIMARY_PARTICLES, 150);
        configuration.addDefault(Setting.ENDERSTAFF_SECONDARY_XP_COST, "0.2f");

        configuration.addDefault(Setting.FIRESTAFF_NAME, "FireStaff");
        configuration.addDefault(Setting.FIRESTAFF_DURABILITY, 18);
        configuration.addDefault(Setting.FIRESTAFF_PRIMARY_XP_COST, "0.2f");
        configuration.addDefault(Setting.FIRESTAFF_SECONDARY_XP_COST, "0.2f");
        //configuration.addDefault(Setting.FIRESTAFF_PRIMARY_PARTICLES, 150);

        configuration.addDefault(Setting.HEALERSTAFF_NAME, "HealerStaff");
        configuration.addDefault(Setting.HEALERSTAFF_DURABILITY, 19);
        configuration.addDefault(Setting.HEALERSTAFF_PRIMARY_XP_COST, "0.2f");
        configuration.addDefault(Setting.HEALERSTAFF_PRIMARY_PARTICLES, 150);
        configuration.addDefault(Setting.HEALERSTAFF_SECONDARY_XP_COST, "0.2f");

        configuration.addDefault(Setting.ICESTAFF_NAME, "IceStaff");
        configuration.addDefault(Setting.ICESTAFF_DURABILITY, 20);
        configuration.addDefault(Setting.ICESTAFF_PRIMARY_XP_COST, "0.2f");
        configuration.addDefault(Setting.ICESTAFF_PRIMARY_PARTICLES, 150);
        configuration.addDefault(Setting.ICESTAFF_SECONDARY_XP_COST, "0.2f");

        configuration.addDefault(Setting.SEERSTAFF_NAME, "SeerStaff");
        configuration.addDefault(Setting.SEERSTAFF_DURABILITY, 21);
        configuration.addDefault(Setting.SEERSTAFF_PRIMARY_XP_COST, "0.2f");
        configuration.addDefault(Setting.SEERSTAFF_PRIMARY_PARTICLES, 150);
        configuration.addDefault(Setting.SEERSTAFF_SECONDARY_XP_COST, "0.2f");

        configuration.options().copyDefaults(true);
    }

    public int getInt(String configPath) {
        return configuration.getInt(configPath);
    }

    public Long getLong(String configPath) {
        return configuration.getLong(configPath);
    }

    public String getString(String configPath) {
        return configuration.getString(configPath);
    }

    public boolean getBoolean(String configPath) {
        return configuration.getBoolean(configPath);
    }

    public short getShort(String configPath) {
        return Short.parseShort(configuration.getString(configPath));
    }

    public float getFloat(String configPath) {
        try {
            return Float.parseFloat(configuration.getString(configPath));
        } catch(NullPointerException ex) {
            return 0;
        }catch(Exception ex) {
            return -1;
        }
    }

    public double getDouble(String configPath) {
        return configuration.getDouble(configPath);
    }

}
