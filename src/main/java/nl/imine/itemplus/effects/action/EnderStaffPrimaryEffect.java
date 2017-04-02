package nl.imine.itemplus.effects.action;

import java.util.HashSet;
import nl.imine.itemplus.BukkitStarter;
import nl.imine.itemplus.effects.Effect;
import nl.imine.itemplus.effects.source.EffectSource;
import nl.imine.itemplus.effects.source.PlayerEyeSource;
import nl.imine.itemplus.effects.target.EffectTarget;
import nl.imine.itemplus.settings.Setting;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 *
 * @author Dennis
 */
public class EnderStaffPrimaryEffect extends Effect {

    private static final String ENDERSTAFF_NAME = BukkitStarter.getSettings().getString(Setting.ENDERSTAFF_NAME);
    private static final short ENDERSTAFF_DURABILITY = BukkitStarter.getSettings().getShort(Setting.ENDERSTAFF_DURABILITY);
    private static final float ENDERSTAFF_PRIMARY_XP_COST = BukkitStarter.getSettings().getFloat(Setting.ENDERSTAFF_PRIMARY_XP_COST);
    private static final int ENDERSTAFF_MAX_TELEPORT_DISTANCE = BukkitStarter.getSettings().getInt(Setting.ENDERSTAFF_PRIMARY_MAX_TELEPORT_DISTANCE);
    private static final boolean ENDERSTAFF_RESET_FALLDISTANCE_ON_TELEPORT = BukkitStarter.getSettings().getBoolean(Setting.ENDERSTAFF_RESET_FALLDISTANCE_ON_TELEPORT);
    private static final int ENDERSTAFF_PARTICLES = BukkitStarter.getSettings().getInt(Setting.ENDERSTAFF_PRIMARY_PARTICLES);

    public static EnderStaffPrimaryEffect setup() {
        return new EnderStaffPrimaryEffect(new PlayerEyeSource(), null, false);
    }

    private EnderStaffPrimaryEffect(EffectSource source, EffectTarget target, boolean isAlternate) {
        super(source, target, isAlternate, ENDERSTAFF_DURABILITY, ENDERSTAFF_PRIMARY_XP_COST, ENDERSTAFF_NAME);
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

        if (ENDERSTAFF_RESET_FALLDISTANCE_ON_TELEPORT) {
            player.setFallDistance(0f);
        }
        Location destination;
        destination = player.getTargetBlock((HashSet<Byte>) null, ENDERSTAFF_MAX_TELEPORT_DISTANCE).getLocation();
        destination.setY(destination.getY() + 1); //preventing the player from getting getting teleported 1 block into the ground
        destination.setPitch(player.getLocation().getPitch());
        destination.setYaw(player.getLocation().getYaw());

        player.teleport(destination);

        Location particleLocation = new Location(destination.getWorld(), destination.getX(), destination.getY() - 1, destination.getZ());
        player.getWorld().spawnParticle(Particle.DRAGON_BREATH, particleLocation, ENDERSTAFF_PARTICLES, 0, 0, 0, 0.1);

        destination.getWorld().playSound(destination, Sound.ENTITY_ENDERMEN_TELEPORT, 1.0f, 1.0f);
    }
    public void setupConfiguration() {

    }
}
