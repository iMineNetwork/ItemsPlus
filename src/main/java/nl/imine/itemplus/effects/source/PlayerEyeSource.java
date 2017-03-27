package nl.imine.itemplus.effects.source;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerEyeSource extends PlayerSource {

    @Override
    public Location getSource(Player player) {
        return player.getEyeLocation();
    }
}
