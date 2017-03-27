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


public class FireStaffSecondaryEffect extends Effect {

    public static FireStaffSecondaryEffect setup() {
        return new FireStaffSecondaryEffect(new PlayerSource(), new CircleAreaOfEffectTarget(3d), true, (short) 17);
    }

    private FireStaffSecondaryEffect(EffectSource source, EffectTarget target, boolean isAlternate, short durability) {
        super(source, target, isAlternate, durability);
    }

    @Override
    public void applyEffect(Player player) {
        if (player.getExp() >= 0.2 || player.getLevel() >= 1 || player.getGameMode().equals(GameMode.CREATIVE)) {
            Location origin = source.getSource(player);

            List<LivingEntity> targets = target.findTargets(origin, player.getWorld().getLivingEntities());
            targets.remove(player);

            ScheduledFuture[] task = new ScheduledFuture[1];
            ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            task[0] = scheduledExecutorService.scheduleWithFixedDelay(new EffectClass(task, origin, target.getEffectArea(origin, 5)), 0L, 25L, TimeUnit.MILLISECONDS);
            origin.getWorld().playSound(origin, Sound.ITEM_FIRECHARGE_USE, 1.0f, 1.0f);

            targets.forEach(target -> target.setFireTicks(100));
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

    private static class EffectClass implements Runnable {

        private ScheduledFuture[] task;
        private Location origin;
        private List<Location> destinations;

        private final int animationFrames = 20;
        private int currentFrame = 0;

        public EffectClass(ScheduledFuture[] task, Location origin, List<Location> destinations) {
            this.task = task;
            this.origin = origin;
            this.destinations = destinations;
        }

        @Override
        public void run() {
            currentFrame++;
            Bukkit.getScheduler().runTask(BukkitStarter.getInstance(), () -> {
                for (Location location : destinations) {
                    double xDiff = origin.getX() - location.getX();
                    double zDiff = origin.getZ() - location.getZ();
                    Location loc = new Location(origin.getWorld(),
                            origin.getX() + (xDiff / animationFrames) * currentFrame,
                            origin.getY(),
                            origin.getZ() + (zDiff / animationFrames) * currentFrame);
                    location.getWorld().spawnParticle(Particle.FLAME, loc,
                            1, 0, 0, 0, 0.1);
                }
            });
            if (currentFrame >= animationFrames) {
                task[0].cancel(false);
            }
        }
    }
}
