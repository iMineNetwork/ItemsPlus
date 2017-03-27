package nl.imine.itemplus.effects.target;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public interface EffectTarget {

    List<LivingEntity> findTargets(Location origin, List<LivingEntity> entities);

    List<Location> getEffectArea(Location origin, int amount);
}
