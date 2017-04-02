package nl.imine.itemplus.effects.action;

import nl.imine.itemplus.BukkitStarter;
import nl.imine.itemplus.effects.Effect;
import nl.imine.itemplus.effects.source.EffectSource;
import nl.imine.itemplus.effects.source.PlayerEyeSource;
import nl.imine.itemplus.effects.target.EffectTarget;
import nl.imine.itemplus.settings.Setting;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

/**
 *
 * @author Dennis
 */
public class IceStaffPrimaryEffect extends Effect {

    private static final String ICESTAFF_NAME = BukkitStarter.getSettings().getString(Setting.ICESTAFF_NAME);
    private static final short ICESTAFF_DURABILITY = BukkitStarter.getSettings().getShort(Setting.ICESTAFF_DURABILITY);
    private static final float ICESTAFF_PRIMARY_XP_COST = BukkitStarter.getSettings().getFloat(Setting.ICESTAFF_PRIMARY_XP_COST);
    private static final double ICESTAFF_PRIMARY_SNOWBALL_VELOCITY_MULTIPLIER = BukkitStarter.getSettings().getDouble(Setting.ICESTAFF_PRIMARY_SNOWBALL_VELOCITY_MULTIPLIER);

    public static IceStaffPrimaryEffect setup() {
        return new IceStaffPrimaryEffect(new PlayerEyeSource(), null, false);
    }

    private IceStaffPrimaryEffect(EffectSource source, EffectTarget target, boolean isAlternate) {
        super(source, target, isAlternate, ICESTAFF_DURABILITY, ICESTAFF_PRIMARY_XP_COST, ICESTAFF_NAME);
    }

    @Override
    public void applyEffect(Player player) {
        if (player.getExp() < EXPERIENCE_COST && player.getLevel() < 1 && !player.getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }

        if (!player.getGameMode().equals(GameMode.CREATIVE)) {

            float exp = player.getExp() - EXPERIENCE_COST;

            if (exp < 0) {
                player.setLevel(player.getLevel() - 1);
                player.setExp(1f + exp); //since exp will always be negative in this place it has to be added instead of extracted
            } else {
                player.setExp(player.getExp() - EXPERIENCE_COST);
            }
        }

        Location location = source.getSource(player).add(player.getLocation().getDirection().multiply(ICESTAFF_PRIMARY_SNOWBALL_VELOCITY_MULTIPLIER));
        Snowball entity = (Snowball) location.getWorld().spawnEntity(location, EntityType.SNOWBALL);

        entity.setShooter(player);

        entity.setVelocity(location.getDirection());
        entity.addScoreboardTag("iceStaffSnowball");

        location.getWorld().playSound(location, Sound.BLOCK_SNOW_BREAK, 1.0f, 1.0f);
    }
}
