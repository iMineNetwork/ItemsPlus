/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.itemplus.effects.actions.action.teleport;

import java.util.List;
import java.util.Map;
import nl.imine.itemplus.effects.actions.Action;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

/**
 *
 * @author Dennis
 */
public class TeleportRelativePositionAction implements Action, ConfigurationSerializable {

    final List<Entity> targets;
    Location relativeLocation;

    public TeleportRelativePositionAction(List<Entity> targets, Location location) {
        this.targets = targets;
        this.relativeLocation = location;
    }

    @Override
    public void execute() {
        targets.forEach(target -> {
            Location targetLocation = new Location(
                    target.getLocation().getWorld(),
                    target.getLocation().getX() + relativeLocation.getX(),
                    target.getLocation().getY() + relativeLocation.getY(),
                    target.getLocation().getZ() + relativeLocation.getZ(),
                    target.getLocation().getYaw() + relativeLocation.getYaw(),
                    target.getLocation().getPitch() + relativeLocation.getPitch());
            
            target.teleport(targetLocation, TeleportCause.PLUGIN);
        });
    }

    @Override
    public Map<String, Object> serialize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
