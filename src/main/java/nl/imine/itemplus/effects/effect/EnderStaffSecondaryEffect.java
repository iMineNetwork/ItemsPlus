package nl.imine.itemplus.effects.effect;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.ChatColor;
import nl.imine.itemplus.BukkitStarter;
import nl.imine.itemplus.effects.Effect;
import nl.imine.itemplus.effects.ParticleAnimation;
import nl.imine.itemplus.effects.source.EffectSource;
import nl.imine.itemplus.effects.source.PlayerSource;
import nl.imine.itemplus.effects.target.CircleAreaOfEffectTarget;
import nl.imine.itemplus.effects.target.EffectTarget;
import nl.imine.itemplus.settings.Setting;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EnderStaffSecondaryEffect extends Effect {

    private static final String ENDERSTAFF_NAME = BukkitStarter.getSettings().getString(Setting.ENDERSTAFF_NAME);
    private static final short ENDERSTAFF_DURABILITY = BukkitStarter.getSettings().getShort(Setting.ENDERSTAFF_DURABILITY);
    private static final float ENDERSTAFF_SECONDARY_XP_COST = BukkitStarter.getSettings().getFloat(Setting.ENDERSTAFF_SECONDARY_XP_COST);

    public static EnderStaffSecondaryEffect setup() {
        return new EnderStaffSecondaryEffect(new PlayerSource(), new CircleAreaOfEffectTarget(3d), true);
    }

    private EnderStaffSecondaryEffect(EffectSource source, EffectTarget target, boolean isAlternate) {
        super(source, target, isAlternate, ENDERSTAFF_DURABILITY, ENDERSTAFF_SECONDARY_XP_COST, ENDERSTAFF_NAME);
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
        task[0] = scheduledExecutorService.scheduleWithFixedDelay(new ParticleAnimation(task, location, target.getEffectArea(location, 50), Particle.ENCHANTMENT_TABLE, 100), 0L, 25L, TimeUnit.MILLISECONDS);
        location.getWorld().playSound(location, Sound.ENTITY_ENDERMITE_HURT, 1.0f, 1.0f);

        targets.stream()
                .filter(target -> target instanceof Player /*|| !HEALERSTAFF_SECONDARY_APPLY_TO_PLAYERS_ONLY*/)
                .forEach(target -> {
                    
                    
                    
                    
                    Location omdraaiLocatie = new Location(target.getWorld(), target.getLocation().getX(), target.getLocation().getY(), target.getLocation().getZ(), target.getLocation().getYaw() > 0 ? (target.getLocation().getYaw() - 180) : (target.getLocation().getYaw() + 180), target.getLocation().getPitch());
                    target.teleport(omdraaiLocatie);
                    target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,80,1,true,false));
                });

    }
}
