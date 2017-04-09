/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.itemplus.effects.actions.action.potioneffect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.imine.itemplus.effects.actions.Action;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

/**
 *
 * @author Dennis
 */
public class PotionEffectSourceAction implements Action, ConfigurationSerializable {

    Player source;
    PotionEffect[] potionEffects;
    boolean playersOnly;
    boolean overwriteOldEffects;

    public PotionEffectSourceAction(Player source, PotionEffect[] potionEffects, boolean playersOnly, boolean overwriteOldEffects) {
        this.source = source;
        this.potionEffects = potionEffects;
        this.playersOnly = playersOnly;
        this.overwriteOldEffects = overwriteOldEffects;
    }

    @Override
    public void start() {
        for (PotionEffect potionEffect : potionEffects) {
            if (overwriteOldEffects) {
                source.removePotionEffect(potionEffect.getType());
            }
            source.addPotionEffect(potionEffect);
        }
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> map = new HashMap<>();

        map.put("potionEffects", potionEffects);
        map.put("playersOnly", playersOnly);
        map.put("overwriteOldEffects", overwriteOldEffects);

        return map;
    }

}
