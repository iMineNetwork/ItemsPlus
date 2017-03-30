package nl.imine.itemplus.effects.action;

import nl.imine.itemplus.effects.Effect;
import nl.imine.itemplus.effects.source.EffectSource;
import nl.imine.itemplus.effects.source.PlayerSource;
import nl.imine.itemplus.effects.target.CircleAreaOfEffectTarget;
import nl.imine.itemplus.effects.target.EffectTarget;
import org.bukkit.GameMode;

import org.bukkit.entity.Player;

public class SeerStaffSecondaryEffect extends Effect {

    public static SeerStaffSecondaryEffect setup() {
        return new SeerStaffSecondaryEffect(new PlayerSource(), new CircleAreaOfEffectTarget(3d), true, (short) 20, 0.2f);
    }

    private SeerStaffSecondaryEffect(EffectSource source, EffectTarget target, boolean isAlternate, short durability, float experienceCost) {
        super(source, target, isAlternate, durability, experienceCost);
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
