package nl.imine.itemplus.effects.action;

import nl.imine.itemplus.effects.Effect;
import nl.imine.itemplus.effects.source.EffectSource;
import nl.imine.itemplus.effects.source.PlayerSource;
import nl.imine.itemplus.effects.target.CircleAreaOfEffectTarget;
import nl.imine.itemplus.effects.target.EffectTarget;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import nl.imine.itemplus.BukkitStarter;
import nl.imine.itemplus.effects.ParticleAnimation;
import nl.imine.itemplus.settings.Setting;

public class IceStaffSecondaryEffect extends Effect {

    private static final String ICESTAFF_NAME = BukkitStarter.getSettings().getString(Setting.ICESTAFF_NAME);
    private static final short ICESTAFF_DURABILITY = BukkitStarter.getSettings().getShort(Setting.ICESTAFF_DURABILITY);
    private static final float ICESTAFF_SECONDARY_XP_COST = BukkitStarter.getSettings().getFloat(Setting.ICESTAFF_SECONDARY_XP_COST);
    private static final PotionEffect[] ICESTAFF_SECONDARY_SOURCE_POTIONEFFECTS = BukkitStarter.getSettings().getPotionEffects(Setting.ICESTAFF_SECONDARY_SOURCE_POTIONEFFECTS);
    private static final PotionEffect[] ICESTAFF_SECONDARY_TARGET_POTIONEFFECTS = BukkitStarter.getSettings().getPotionEffects(Setting.ICESTAFF_SECONDARY_TARGET_POTIONEFFECTS);

    public static IceStaffSecondaryEffect setup() {
        return new IceStaffSecondaryEffect(new PlayerSource(), new CircleAreaOfEffectTarget(3d), true);
    }

    private IceStaffSecondaryEffect(EffectSource source, EffectTarget target, boolean isAlternate) {
        super(source, target, isAlternate, ICESTAFF_DURABILITY, ICESTAFF_SECONDARY_XP_COST, ICESTAFF_NAME);
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

        Location location = source.getSource(player);

        List<LivingEntity> targets = target.findTargets(location, player.getWorld().getLivingEntities());
        targets.remove(player);

        ScheduledFuture[] task = new ScheduledFuture[1];
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        task[0] = scheduledExecutorService.scheduleWithFixedDelay(new ParticleAnimation(task, location, target.getEffectArea(location, 5), Particle.SNOW_SHOVEL, 500), 0L, 25L, TimeUnit.MILLISECONDS);
        location.getWorld().playSound(location, Sound.BLOCK_SNOW_BREAK, 1.0f, 1.0f);

        targets.forEach(target -> {
            for (PotionEffect effect : ICESTAFF_SECONDARY_TARGET_POTIONEFFECTS) {
                target.removePotionEffect(effect.getType()); //making sure the potionEffect overwrites the old one
                target.addPotionEffect(effect);
            }

        });

          for (PotionEffect effect : ICESTAFF_SECONDARY_SOURCE_POTIONEFFECTS) {
            player.removePotionEffect(effect.getType()); //making sure the potionEffect overwrites the old one
            player.addPotionEffect(effect);
        }
    }
}
