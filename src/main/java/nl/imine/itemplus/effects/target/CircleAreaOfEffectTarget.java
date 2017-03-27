package nl.imine.itemplus.effects.target;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CircleAreaOfEffectTarget implements AreaOfEffectTarget{

    private double range;

    public CircleAreaOfEffectTarget(double range) {
        this.range = range;
    }

    @Override
    public List<LivingEntity> findTargets(Location origin, List<LivingEntity> entities) {
        return entities.stream()
                .filter(entity -> origin.getWorld().equals(entity.getWorld()))
                .filter(entity -> origin.distanceSquared(entity.getLocation()) < (range * range))
                .collect(Collectors.toList());
    }

    @Override
    public List<Location> getEffectArea(Location origin, int amount) {
        ArrayList<Location> locations = new ArrayList<>();
        for (double i = 0; i < amount; i++) {
            double x = origin.getX() + range * Math.cos(Math.toRadians(((360D / amount) * i)));
            double y = origin.getZ() + range * Math.sin(Math.toRadians(((360D / amount) * i)));
            locations.add(new Location(origin.getWorld(), x, origin.getY(), y));
        }
        return locations;
    }
}
