package nl.imine.itemplus.effects.source;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerSource implements EffectSource {

    public PlayerSource() {
    }

    @Override
    public Location getSource(Player player) {
        return player.getLocation();
    }
}
