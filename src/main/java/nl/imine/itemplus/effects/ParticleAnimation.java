/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.itemplus.effects;

import java.util.List;
import java.util.concurrent.ScheduledFuture;
import nl.imine.itemplus.BukkitStarter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticleAnimation implements Runnable {

    private final ScheduledFuture[] task;
    private final Location origin;
    private final List<Location> destinations;
    private final Particle PARTICLE;

    private final int PARTICLES_PER_DESTINATION;
    private final int PARTICLES_PER_FRAME;

    private final int animationFrames = 20;
    private int currentFrame = 0;

    public ParticleAnimation(ScheduledFuture[] task, Location origin, List<Location> destinations, Particle PARTICLE, int AANTAL_PARTICLES) {
        this.task = task;
        this.origin = origin;
        this.destinations = destinations;
        this.PARTICLE = PARTICLE;
        this.PARTICLES_PER_DESTINATION = AANTAL_PARTICLES / destinations.size();
        this.PARTICLES_PER_FRAME = PARTICLES_PER_DESTINATION / animationFrames;
    }

    @Override
    public void run() {
        currentFrame++;

        Bukkit.getScheduler().runTask(BukkitStarter.getInstance(), () -> {
            destinations.forEach((location) -> {
                double xDiff = origin.getX() - location.getX();
                double zDiff = origin.getZ() - location.getZ();
                Location loc = new Location(origin.getWorld(),
                        origin.getX() + (xDiff / animationFrames) * currentFrame,
                        origin.getY(),
                        origin.getZ() + (zDiff / animationFrames) * currentFrame);

                //als er niet genoeg particles zijn voor deze frame ga dan direct door naar de volgende zonder te wachten
                while (PARTICLES_PER_DESTINATION < animationFrames && currentFrame % (animationFrames / PARTICLES_PER_DESTINATION) != 0 && currentFrame < animationFrames) {
                    currentFrame++;
                }
                
                if (PARTICLES_PER_DESTINATION < animationFrames) {
                    if (currentFrame % (animationFrames / PARTICLES_PER_DESTINATION) == 0) {
                        location.getWorld().spawnParticle(PARTICLE, loc,
                                1, 0, 0, 0, 0.1);
                    }
                } else {
                    location.getWorld().spawnParticle(PARTICLE, loc,
                            PARTICLES_PER_FRAME, 0, 0, 0, 0.1);
                }

            });
        });
        if (currentFrame >= animationFrames) {
            task[0].cancel(false);
        }
    }
}
