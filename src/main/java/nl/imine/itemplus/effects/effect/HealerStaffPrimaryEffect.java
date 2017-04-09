package nl.imine.itemplus.effects.effect;

import nl.imine.itemplus.BukkitStarter;
import nl.imine.itemplus.effects.Effect;
import nl.imine.itemplus.effects.source.EffectSource;
import nl.imine.itemplus.effects.source.PlayerEyeSource;
import nl.imine.itemplus.effects.target.EffectTarget;
import nl.imine.itemplus.settings.Setting;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Dennis
 */
public class HealerStaffPrimaryEffect extends Effect {

    private static final String HEALERSTAFF_NAME = BukkitStarter.getSettings().getString(Setting.HEALERSTAFF_NAME);
    private static final short HEALERSTAFF_DURABILITY = BukkitStarter.getSettings().getShort(Setting.HEALERSTAFF_DURABILITY);
    private static final float HEALERSTAFF_PRIMARY_XP_COST = BukkitStarter.getSettings().getFloat(Setting.HEALERSTAFF_PRIMARY_XP_COST);
    private static final PotionEffect[] HEALERSTAFF_PRIMARY_POTIONEFFECTS = BukkitStarter.getSettings().getPotionEffects(Setting.HEALERSTAFF_PRIMARY_POTIONEFFECTS);

    public static HealerStaffPrimaryEffect setup() {
        return new HealerStaffPrimaryEffect(new PlayerEyeSource(), null, false);
    }

    private HealerStaffPrimaryEffect(EffectSource source, EffectTarget target, boolean isAlternate) {
        super(source, target, isAlternate, HEALERSTAFF_DURABILITY, HEALERSTAFF_PRIMARY_XP_COST, HEALERSTAFF_NAME);
    }

    @Override
    public void applyEffect(Player player) {
        if (player.getExp() < EXPERIENCE_COST && player.getLevel() < 1 && !player.getGameMode().equals(GameMode.CREATIVE) || player.getFoodLevel() <= 0) {
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

        Location location = source.getSource(player);

        for (PotionEffect effect : HEALERSTAFF_PRIMARY_POTIONEFFECTS) {
            player.removePotionEffect(effect.getType());
            if (effect.getType().equals(PotionEffectType.ABSORPTION)) {
                //modifying the effect so it will be equal to the players foodlevel-
                player.addPotionEffect(new PotionEffect(effect.getType(), effect.getDuration(), player.getFoodLevel() / 4 - 1, effect.isAmbient(), effect.hasParticles(), effect.getColor()));
                continue;
            }
            player.addPotionEffect(effect);
        }
        player.setFoodLevel(player.getFoodLevel() - 1);

        location.getWorld().playSound(location, Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1.0f, 1.0f);
        location.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location, 50, 0.5, 1, 0.5, 0.1);
    }
}
