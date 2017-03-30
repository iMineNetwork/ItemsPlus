package nl.imine.itemplus.effects.target;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AngleAreaOfEffectTarget implements AreaOfEffectTarget {

    private double angle;
    private double range;

    public AngleAreaOfEffectTarget(double angle, double range) {
        this.angle = angle;
        this.range = range;
    }

    @Override
    public List<LivingEntity> findTargets(Location origin, List<LivingEntity> entities) {
        return entities.stream()
                .filter(entity -> origin.getWorld().equals(entity.getWorld()))
                .filter(entity -> origin.distanceSquared(entity.getLocation()) < (range * range))
                .filter(entity -> {

                    float yaw = origin.getYaw() < 0 ? 360 + origin.getYaw() : origin.getYaw();
                    double angleMin = (yaw - (angle / 2)) < 0 ? 360 - (yaw - (angle / 2)) : (yaw - (angle / 2));
                    double angleMax = (yaw + (angle / 2)) < 0 ? 360 - (yaw + (angle / 2)) : (yaw + (angle / 2));

                    double dz = Math.max(origin.getZ(), entity.getLocation().getZ()) - Math.min(origin.getZ(), entity.getLocation().getZ());
                    double dx = Math.max(origin.getX(), entity.getLocation().getX()) - Math.min(origin.getX(), entity.getLocation().getX());

                    double calculatedAngle = (Math.atan(dz / dx) * (180 / Math.PI));

                    for (double z = Math.min(origin.getZ(), entity.getLocation().getZ()); z < Math.max(origin.getZ(), entity.getLocation().getZ()); z = (z + dz / 10)) {
                        for (double x = Math.min(origin.getX(), entity.getLocation().getX()); x < Math.max(origin.getX(), entity.getLocation().getX()); x = (x + dx / 10)) {
                            if(x == origin.getX() || x == entity.getLocation().getX()  || z == origin.getZ()  || z == entity.getLocation().getZ()) {
                                origin.getWorld().spigot().playEffect(new Location(origin.getWorld(), x, origin.getY(), z), Effect.COLOURED_DUST,
                                        1, 1, (255F / 255F) - 1F, (0F / 255F), (0F / 255F), 1, 0, 64);
                            }
                        }
                    }
                    for (double x = Math.min(origin.getX(), entity.getLocation().getX()); x < Math.max(origin.getX(), entity.getLocation().getX()); x = (x + dx / 10)) {
                        origin.getWorld().spigot().playEffect(new Location(origin.getWorld(), x, origin.getY(), (dz/dx) * x), Effect.COLOURED_DUST,
                                1, 1, (255F / 255F) - 1F, (0F / 255F), (0F / 255F), 1, 0, 64);
                    }

                    return (calculatedAngle > angleMin && calculatedAngle < angleMax);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Location> getEffectArea(Location origin, int amount) {
        return new ArrayList<>();
    }

    public double getAngle() {
        return angle;
    }

    public double getRange() {
        return range;
    }

}
