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

public class FireStaffSecondaryEffect extends Effect {

    private static final String FIRESTAFF_NAME = BukkitStarter.getSettings().getString(Setting.FIRESTAFF_NAME);
    private static final short FIRESTAFF_DURABILITY = BukkitStarter.getSettings().getShort(Setting.FIRESTAFF_DURABILITY);
    private static final float FIRESTAFF_SECONDARY_XP_COST = BukkitStarter.getSettings().getFloat(Setting.FIRESTAFF_SECONDARY_XP_COST);
    
    public static FireStaffSecondaryEffect setup() {
        return new FireStaffSecondaryEffect(new PlayerSource(), new CircleAreaOfEffectTarget(3d), true);
    }

    private FireStaffSecondaryEffect(EffectSource source, EffectTarget target, boolean isAlternate) {
         super(source, target, isAlternate, FIRESTAFF_DURABILITY, FIRESTAFF_SECONDARY_XP_COST, FIRESTAFF_NAME);
    }

    @Override
    public void applyEffect(Player player) {
        if (player.getExp() >= 0.2 || player.getLevel() >= 1 || player.getGameMode().equals(GameMode.CREATIVE)) {
            Location origin = source.getSource(player);

            List<LivingEntity> targets = target.findTargets(origin, player.getWorld().getLivingEntities());
            targets.remove(player);

            ScheduledFuture[] task = new ScheduledFuture[1];
            ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            task[0] = scheduledExecutorService.scheduleWithFixedDelay(new ParticleAnimation(task, origin, target.getEffectArea(origin, 5), Particle.FLAME, 100), 0L, 25L, TimeUnit.MILLISECONDS);
            origin.getWorld().playSound(origin, Sound.ITEM_FIRECHARGE_USE, 1.0f, 1.0f);

            targets.forEach(target -> target.setFireTicks(100));

            //making sure the potionEffects overwrite the old ones
            player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            player.removePotionEffect(PotionEffectType.SLOW);

            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 60, 0, true, false), true);
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 4, true, false), true);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 255, true, false), true);
            if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                if (player.getExp() < 0.2) {
                    player.setLevel(player.getLevel() - 1);
                    player.setExp(0.8f);
                } else {
                    player.setExp(player.getExp() - 0.2f);
                }
            }
        }
    }
}
