package nl.imine.itemplus.effects.effect;

import nl.imine.itemplus.BukkitStarter;
import nl.imine.itemplus.effects.Effect;
import nl.imine.itemplus.effects.source.EffectSource;
import nl.imine.itemplus.effects.source.PlayerEyeSource;
import nl.imine.itemplus.effects.target.EffectTarget;
import nl.imine.itemplus.settings.Setting;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 *
 * @author Dennis
 */
public class SeerStaffPrimaryEffect extends Effect {

    private static final String SEERSTAFF_NAME = BukkitStarter.getSettings().getString(Setting.SEERSTAFF_NAME);
    private static final short SEERSTAFF_DURABILITY = BukkitStarter.getSettings().getShort(Setting.SEERSTAFF_DURABILITY);
    private static final float SEERSTAFF_PRIMARY_XP_COST = BukkitStarter.getSettings().getFloat(Setting.SEERSTAFF_PRIMARY_XP_COST);
    
    public static SeerStaffPrimaryEffect setup() {
        return new SeerStaffPrimaryEffect(new PlayerEyeSource(), null, false);
    }

    private SeerStaffPrimaryEffect(EffectSource source, EffectTarget target, boolean isAlternate) {
        super(source, target, isAlternate, SEERSTAFF_DURABILITY, SEERSTAFF_PRIMARY_XP_COST, SEERSTAFF_NAME);
    }

    @Override
    public void applyEffect(Player player) {
        if (player.getExp() < EXPERIENCE_COST && player.getLevel() < 1 && !player.getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }

        if (!player.getGameMode().equals(GameMode.CREATIVE)) {

            float exp = player.getExp() - EXPERIENCE_COST;

            if (exp < 0) {
                player.setLevel(player.getLevel() - 1);
                player.setExp(1f + exp); //since exp will always be negative in this place it has to be added instead of extracted
            } else {
                player.setExp(player.getExp() - EXPERIENCE_COST);
            }
        }
       
        //atm it's not possible to make the effects
    }
}
