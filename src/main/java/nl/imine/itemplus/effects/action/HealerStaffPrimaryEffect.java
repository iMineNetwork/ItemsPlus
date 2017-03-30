package nl.imine.itemplus.effects.action;

import nl.imine.itemplus.effects.Effect;
import nl.imine.itemplus.effects.source.EffectSource;
import nl.imine.itemplus.effects.source.PlayerEyeSource;
import nl.imine.itemplus.effects.target.EffectTarget;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Dennis
 */
public class HealerStaffPrimaryEffect extends Effect {

    public static HealerStaffPrimaryEffect setup() {
        return new HealerStaffPrimaryEffect(new PlayerEyeSource(), null, false, (short) 19, 0.2f);
    }

    private HealerStaffPrimaryEffect(EffectSource source, EffectTarget target, boolean isAlternate, short durability, float experienceCost) {
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

        Location location = source.getSource(player).add(player.getLocation().getDirection().multiply(1.1));

        player.removePotionEffect(PotionEffectType.ABSORPTION); 
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1200, player.getFoodLevel() / 2 - 1, true, true), true);

        location.getWorld().playSound(location, Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1.0f, 1.0f);
    }
}
