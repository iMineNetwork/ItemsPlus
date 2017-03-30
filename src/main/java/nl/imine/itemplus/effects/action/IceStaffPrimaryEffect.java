/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.itemplus.effects.action;

import nl.imine.itemplus.effects.Effect;
import nl.imine.itemplus.effects.source.EffectSource;
import nl.imine.itemplus.effects.source.PlayerEyeSource;
import nl.imine.itemplus.effects.target.EffectTarget;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

/**
 *
 * @author Dennis
 */
public class IceStaffPrimaryEffect extends Effect {

    public static IceStaffPrimaryEffect setup() {
        return new IceStaffPrimaryEffect(new PlayerEyeSource(), null, false, (short) 20, 0.2f);
    }

    private IceStaffPrimaryEffect(EffectSource source, EffectTarget target, boolean isAlternate, short durability, float experienceCost) {
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
        Snowball entity = (Snowball) location.getWorld().spawnEntity(location, EntityType.SNOWBALL);

        entity.setShooter(player);

        entity.setVelocity(player.getLocation().getDirection());
        entity.addScoreboardTag("iceStaffSnowball");
        
        location.getWorld().playSound(location, Sound.BLOCK_SNOW_BREAK, 1.0f, 1.0f);
    }
}
