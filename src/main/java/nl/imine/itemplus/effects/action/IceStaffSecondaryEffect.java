package nl.imine.itemplus.effects.action;

import nl.imine.itemplus.BukkitStarter;
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
import nl.imine.itemplus.effects.ParticleAnimation;

public class IceStaffSecondaryEffect extends Effect {

    public static IceStaffSecondaryEffect setup() {
        return new IceStaffSecondaryEffect(new PlayerSource(), new CircleAreaOfEffectTarget(3d), true, (short) 20, 0.2f);
    }

    private IceStaffSecondaryEffect(EffectSource source, EffectTarget target, boolean isAlternate, short durability, float experienceCost) {
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

        Location location = source.getSource(player);

        List<LivingEntity> targets = target.findTargets(location, player.getWorld().getLivingEntities());
        targets.remove(player);

        ScheduledFuture[] task = new ScheduledFuture[1];
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        task[0] = scheduledExecutorService.scheduleWithFixedDelay(new ParticleAnimation(task, location, target.getEffectArea(location, 5), Particle.SNOW_SHOVEL, 500), 0L, 25L, TimeUnit.MILLISECONDS);
        location.getWorld().playSound(location, Sound.BLOCK_SNOW_BREAK, 1.0f, 1.0f);

        targets.forEach(target -> {
            target.removePotionEffect(PotionEffectType.SLOW); //making sure the potionEffect overwrites the old one
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1, true, true, Color.SILVER));
        });
        
        player.removePotionEffect(PotionEffectType.SPEED); //making sure the potionEffect overwrites the old one
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 0, true, false), true);

    }
}
