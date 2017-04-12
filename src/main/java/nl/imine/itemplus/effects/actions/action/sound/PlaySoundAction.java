/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.itemplus.effects.actions.action.sound;

import java.util.HashMap;
import java.util.Map;
import nl.imine.itemplus.effects.actions.Action;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

/**
 *
 * @author Dennis
 */
public class PlaySoundAction implements Action, ConfigurationSerializable {

    Location location;
    Sound sound;
    float volume;
    float pitch;

    public PlaySoundAction(Player source, Location location, Sound sound, float volume, float pitch) {
        this.location = location;
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    @Override
    public void execute() {

        location.getWorld().playSound(location, sound, 1.0f, 1.0f);
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> map = new HashMap<>();
        map.put("Location", location);
        map.put("sound", sound);
        map.put("volume", volume);
        map.put("pitch", pitch);

        return map;
    }

}
