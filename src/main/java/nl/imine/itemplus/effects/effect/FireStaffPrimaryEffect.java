package nl.imine.itemplus.effects.effect;

import nl.imine.itemplus.BukkitStarter;
import nl.imine.itemplus.effects.Effect;
import nl.imine.itemplus.effects.source.EffectSource;
import nl.imine.itemplus.effects.source.PlayerEyeSource;
import nl.imine.itemplus.effects.target.EffectTarget;
import nl.imine.itemplus.settings.Setting;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

public class FireStaffPrimaryEffect extends Effect {

    private static final String FIRESTAFF_NAME = BukkitStarter.getSettings().getString(Setting.FIRESTAFF_NAME);
    private static final short FIRESTAFF_DURABILITY = BukkitStarter.getSettings().getShort(Setting.FIRESTAFF_DURABILITY);
    private static final float FIRESTAFF_PRIMARY_XP_COST = BukkitStarter.getSettings().getFloat(Setting.FIRESTAFF_PRIMARY_XP_COST);
    private static final double FIRESTAFF_PRIMARY_VELOCITY_MULTIPLIER = BukkitStarter.getSettings().getDouble(Setting.FIRESTAFF_PRIMARY_VELOCITY_MULTIPLIER);
    private static final float FIRESTAFF_PRIMARY_EXPLODE_POWER = BukkitStarter.getSettings().getFloat(Setting.FIRESTAFF_PRIMARY_EXPLODE_POWER);
    

    public static FireStaffPrimaryEffect setup() {
        return new FireStaffPrimaryEffect(new PlayerEyeSource(), null, false);
    }

    private FireStaffPrimaryEffect(EffectSource source, EffectTarget target, boolean isAlternate) {
        super(source, target, isAlternate, FIRESTAFF_DURABILITY, FIRESTAFF_PRIMARY_XP_COST, FIRESTAFF_NAME);
    }

    @Override
    public void applyEffect(Player player) {
        if (player.getExp() >= FIRESTAFF_PRIMARY_XP_COST || player.getLevel() >= 1 || player.getGameMode().equals(GameMode.CREATIVE)) {
            Location origin = source.getSource(player).add(player.getLocation().getDirection().multiply(1.1));
            Fireball entity = (Fireball) origin.getWorld().spawnEntity(origin, EntityType.FIREBALL);
            entity.setYield(FIRESTAFF_PRIMARY_EXPLODE_POWER);
            entity.setShooter(player);
            entity.setVelocity(player.getLocation().getDirection().multiply(FIRESTAFF_PRIMARY_VELOCITY_MULTIPLIER));

            origin.getWorld().playSound(origin, Sound.ITEM_FIRECHARGE_USE, 1.0f, 1.0f);

            if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                if (player.getExp() < FIRESTAFF_PRIMARY_XP_COST) {
                    player.setLevel(player.getLevel() - 1);
                    player.setExp(1f - FIRESTAFF_PRIMARY_XP_COST);
                } else {
                    player.setExp(player.getExp() - FIRESTAFF_PRIMARY_XP_COST);
                }
            }
        }
    }
}
