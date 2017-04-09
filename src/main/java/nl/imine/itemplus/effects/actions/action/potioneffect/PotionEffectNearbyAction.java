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
public class PotionEffectNearbyAction implements Action, ConfigurationSerializable {

    PotionEffect[] potionEffects;
    List<LivingEntity> targets;
    double radius;
    boolean playersOnly;
    boolean overwriteOldEffects;

    public PotionEffectNearbyAction(Player source, PotionEffect[] potionEffects, double radius, boolean playersOnly, boolean overwriteOldEffects) {
        this.potionEffects = potionEffects;
        this.targets = (List<LivingEntity>) source.getNearbyEntities(radius, radius, radius).stream()
                .filter(entity -> entity != source)
                .filter(entity -> entity instanceof LivingEntity);
        this.radius = radius;
        this.playersOnly = playersOnly;
        this.overwriteOldEffects = overwriteOldEffects;
    }

    @Override
    public void start() {
        targets.stream()
                .filter(target -> target instanceof Player || !playersOnly)
                .forEach(target -> {
                    for (PotionEffect potionEffect : potionEffects) {
                        if (overwriteOldEffects) {
                            target.removePotionEffect(potionEffect.getType());
                        }
                        target.addPotionEffect(potionEffect);
                    }
                });
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> map = new HashMap<>();
        map.put("potionEffects", potionEffects);
        map.put("targets", targets);
        map.put("overwriteOldEffects", overwriteOldEffects);

        return map;
    }

}
