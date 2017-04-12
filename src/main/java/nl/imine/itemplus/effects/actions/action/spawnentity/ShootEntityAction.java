/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.itemplus.effects.actions.action.spawnentity;

import java.util.Map;
import nl.imine.itemplus.effects.actions.Action;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

/**
 *
 * @author Dennis
 */
public class ShootEntityAction implements Action, ConfigurationSerializable {

    Player source;
    EntityType entityType;
    Location location;
    double velocity;
    String scoreboardTag;
    float yield;
    boolean critical;
    int knockbackStrength;
    int fireticks;

    public ShootEntityAction(Player source, EntityType entityType, double velocity, String scoreboardTag) {
        this.source = source;
        this.entityType = entityType;
        this.location = source.getLocation();
        this.velocity = velocity;
        this.scoreboardTag = scoreboardTag;
    }

    public ShootEntityAction(Player shooter, EntityType entityType, double velocity, String scoreboardTag, float yield) {
        this.source = shooter;
        this.entityType = entityType;
        this.location = shooter.getLocation();
        this.velocity = velocity;
        this.scoreboardTag = scoreboardTag;
        this.yield = yield;
    }

    public ShootEntityAction(Player shooter, EntityType entityType, double velocity, String scoreboardTag, boolean critical, int knockbackStrength, int fireticks) {
        this.source = shooter;
        this.entityType = entityType;
        this.location = shooter.getLocation();
        this.velocity = velocity;
        this.scoreboardTag = scoreboardTag;
        this.critical = critical;
        this.knockbackStrength = knockbackStrength;
        this.fireticks = fireticks;
    }

    @Override
    public void execute() {
        Entity entity = (Entity) location.getWorld().spawnEntity(location, entityType);
        entity.setVelocity(location.getDirection().multiply(velocity));
        entity.addScoreboardTag(scoreboardTag);

        if (!(entity instanceof Projectile)) {
            return;
        }

        Projectile projectile = (Projectile) entity;
        projectile.setShooter(source);

        if (projectile instanceof Explosive) {
            Explosive explosive = (Explosive) projectile;
            explosive.setYield(yield);
            return;
        }

        if (projectile instanceof Arrow) {
            Arrow arrow = (Arrow) projectile;
            arrow.setCritical(critical);
            arrow.setKnockbackStrength(knockbackStrength);
            arrow.setFireTicks(fireticks);
        }
    }

    @Override
    public Map<String, Object> serialize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
