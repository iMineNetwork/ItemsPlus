package nl.imine.itemplus.effects.action;

import nl.imine.itemplus.effects.Effect;
import nl.imine.itemplus.effects.source.EffectSource;
import nl.imine.itemplus.effects.source.PlayerEyeSource;
import nl.imine.itemplus.effects.target.EffectTarget;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

public class FireStaffPrimaryEffect extends Effect {

    public static FireStaffPrimaryEffect setup() {
        return new FireStaffPrimaryEffect(new PlayerEyeSource(), null, false, (short) 18, 0.2f);
    }

    private FireStaffPrimaryEffect(EffectSource source, EffectTarget target, boolean isAlternate, short durability, float experienceCost) {
        super(source, target, isAlternate, durability, experienceCost);
    }

    @Override
    public void applyEffect(Player player) {
        if (player.getExp() >= 0.2 || player.getLevel() >= 1 || player.getGameMode().equals(GameMode.CREATIVE)) {
            Location origin = source.getSource(player).add(player.getLocation().getDirection().multiply(1.1));
            Fireball entity = (Fireball) origin.getWorld().spawnEntity(origin, EntityType.FIREBALL);
            entity.setYield(0);
            entity.setShooter(player);
            entity.setVelocity(player.getLocation().getDirection());

            origin.getWorld().playSound(origin, Sound.ITEM_FIRECHARGE_USE, 1.0f, 1.0f);

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
