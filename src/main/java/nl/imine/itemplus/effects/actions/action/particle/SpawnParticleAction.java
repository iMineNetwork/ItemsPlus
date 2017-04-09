/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.itemplus.effects.actions.action.particle;

import java.util.HashMap;
import java.util.Map;
import nl.imine.itemplus.effects.actions.Action;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 *
 * @author Dennis
 */
public class SpawnParticleAction implements Action, ConfigurationSerializable {

    Location location;
    Particle p;
    int amount;

    public SpawnParticleAction(Location location, Particle p,int amount) {
        this.location = location;
        this.p = p;
        this.amount = amount;
    }

    
    @Override
    public void start() {
        location.getWorld().spawnParticle(p, location, amount);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("Location", location);
        map.put("target", target);
        map.put("particle", particle);
        map.put("amount", amount);
        map.put("aantal_particles", aantalParticles);
        map.put("initialDelay", initialDelay);
        map.put("delay", delay);
        map.put("timeUnit", timeUnit);

        return map;
    }

}
