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

public class HealerStaffSecondaryEffect extends Effect {

    private static final String HEALERSTAFF_NAME = BukkitStarter.getSettings().getString(Setting.HEALERSTAFF_NAME);
    private static final short HEALERSTAFF_DURABILITY = BukkitStarter.getSettings().getShort(Setting.HEALERSTAFF_DURABILITY);
    private static final float HEALERSTAFF_SECONDARY_XP_COST = BukkitStarter.getSettings().getFloat(Setting.HEALERSTAFF_SECONDARY_XP_COST);

    public static HealerStaffSecondaryEffect setup() {
        return new HealerStaffSecondaryEffect(new PlayerSource(), new CircleAreaOfEffectTarget(3d), true);
    }

    private HealerStaffSecondaryEffect(EffectSource source, EffectTarget target, boolean isAlternate) {
        super(source, target, isAlternate, HEALERSTAFF_DURABILITY, HEALERSTAFF_SECONDARY_XP_COST, HEALERSTAFF_NAME);
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
        task[0] = scheduledExecutorService.scheduleWithFixedDelay(new ParticleAnimation(task, location, target.getEffectArea(location, 50), Particle.HEART, 100), 0L, 25L, TimeUnit.MILLISECONDS);
        location.getWorld().playSound(location, Sound.BLOCK_SNOW_BREAK, 1.0f, 1.0f);

        targets.stream()
                .filter(target -> target instanceof Player)
                .forEach(target -> {
                    target.removePotionEffect(PotionEffectType.REGENERATION); //making sure the potionEffect overwrites the old one
                    target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2, true, true, Color.RED));
                });
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 0, true, false), true);

        location.getWorld().playSound(location, Sound.ITEM_TOTEM_USE, 1.0f, 1.0f);
    }
}
