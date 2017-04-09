/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.itemplus.effects.actions.action.particle;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import nl.imine.itemplus.effects.ParticleAnimation;
import nl.imine.itemplus.effects.actions.Action;
import nl.imine.itemplus.effects.target.EffectTarget;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

/**
 *
 * @author Dennis
 */
public class ParticleAnimationAction implements Action, ConfigurationSerializable {

    Player source;
    Location location;
    protected EffectTarget target;
    Particle particle;
    int amount;
    int aantalParticles;
    long initialDelay;
    long delay;
    TimeUnit timeUnit;

    public ParticleAnimationAction(Player source, Location location, EffectTarget target, Particle particle, int amount, int aantalParticles, long initialDelay, long delay, TimeUnit timeUnit) {
        this.source = source;
        this.location = location;
        this.target = target;
        this.particle = particle;
        this.amount = amount;
        this.aantalParticles = aantalParticles;
        this.initialDelay = initialDelay;
        this.delay = delay;
        this.timeUnit = timeUnit;
    }

    @Override
    public void start() {
        ScheduledFuture[] task = new ScheduledFuture[1];
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        task[0] = scheduledExecutorService.scheduleWithFixedDelay(new ParticleAnimation(task, location, target.getEffectArea(location, amount), particle, aantalParticles), initialDelay, delay, timeUnit);
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> map = new HashMap<>();
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
