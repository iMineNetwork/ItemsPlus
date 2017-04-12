/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.itemplus.effects.actions.action.potioneffect;

import java.util.HashMap;
import java.util.Map;
import nl.imine.itemplus.effects.actions.Action;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

/**
 * @author Dennis
 */
public class PotionEffectSourceAction implements Action, ConfigurationSerializable {

    Player source;
    PotionEffect[] potionEffects;
    boolean overwriteOldEffects;

    public PotionEffectSourceAction(Player source, PotionEffect[] potionEffects, boolean overwriteOldEffects) {
        this.source = source;
        this.potionEffects = potionEffects;
        this.overwriteOldEffects = overwriteOldEffects;
    }
    
    public PotionEffectSourceAction(PotionEffect[] potionEffects, boolean overwriteOldEffects) {
        this.source = null;
        this.potionEffects = potionEffects;
        this.overwriteOldEffects = overwriteOldEffects;
    }

    @Override
    public void execute() {
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
        map.put("overwriteOldEffects", overwriteOldEffects);
        map.put("overwrite_old_effects", overwriteOldEffects);

        return map;
    }

}
