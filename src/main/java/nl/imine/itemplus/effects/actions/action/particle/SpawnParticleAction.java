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
    Particle particle;
    int amount;

    public SpawnParticleAction(Location location, Particle particle, int amount) {
        this.location = location;
        this.particle = particle;
        this.amount = amount;
    }

    @Override
    public void execute() {
        location.getWorld().spawnParticle(particle, location, amount);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("Location", location);
        map.put("Particle", particle);
        map.put("amount", amount);
        return map;
    }

}
