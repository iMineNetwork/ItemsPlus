package nl.imine.itemplus.effects.source;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface EffectSource {

    Location getSource(Player player);
}
