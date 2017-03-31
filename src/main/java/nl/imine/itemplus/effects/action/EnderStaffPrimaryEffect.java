package nl.imine.itemplus.effects.action;

import java.util.HashSet;
import java.util.Set;
import nl.imine.itemplus.effects.Effect;
import nl.imine.itemplus.effects.source.EffectSource;
import nl.imine.itemplus.effects.source.PlayerEyeSource;
import nl.imine.itemplus.effects.target.EffectTarget;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author Dennis
 */
public class EnderStaffPrimaryEffect extends Effect {

    public static EnderStaffPrimaryEffect setup() {
        return new EnderStaffPrimaryEffect(new PlayerEyeSource(), null, false, (short) 17, 0.2f);
    }

    private EnderStaffPrimaryEffect(EffectSource source, EffectTarget target, boolean isAlternate, short durability, float experienceCost) {
        super(source, target, isAlternate, durability, experienceCost);
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

        player.setFallDistance(0f);

        Location destination;
        destination = player.getTargetBlock((HashSet<Byte>) null, 100).getLocation();
        destination.setY(destination.getY() + 1); //preventing the player from getting getting teleported 1 block into the ground
        destination.setPitch(player.getLocation().getPitch());
        destination.setYaw(player.getLocation().getYaw());

        player.teleport(destination);

        Location particleLocation = new Location(destination.getWorld(), destination.getX(), destination.getY() - 1, destination.getZ());
        player.getWorld().spawnParticle(Particle.DRAGON_BREATH, particleLocation, 150, 0, 0, 0, 0.1);
    }
}
