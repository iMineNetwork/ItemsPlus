package nl.imine.itemplus.effects.action;

import net.md_5.bungee.api.ChatColor;
import nl.imine.itemplus.BukkitStarter;
import nl.imine.itemplus.effects.Effect;
import nl.imine.itemplus.effects.source.EffectSource;
import nl.imine.itemplus.effects.source.PlayerSource;
import nl.imine.itemplus.effects.target.CircleAreaOfEffectTarget;
import nl.imine.itemplus.effects.target.EffectTarget;
import nl.imine.itemplus.settings.Setting;
import org.bukkit.GameMode;

import org.bukkit.entity.Player;

public class EnderStaffSecondaryEffect extends Effect {

    private static final String ENDERSTAFF_NAME = BukkitStarter.getSettings().getString(Setting.ENDERSTAFF_NAME);
    private static final short ENDERSTAFF_DURABILITY = BukkitStarter.getSettings().getShort(Setting.ENDERSTAFF_DURABILITY);
    private static final float ENDERSTAFF_SECONDARY_XP_COST = BukkitStarter.getSettings().getFloat(Setting.ENDERSTAFF_SECONDARY_XP_COST);

    public static EnderStaffSecondaryEffect setup() {
        return new EnderStaffSecondaryEffect(new PlayerSource(), new CircleAreaOfEffectTarget(3d), true);
    }

    private EnderStaffSecondaryEffect(EffectSource source, EffectTarget target, boolean isAlternate) {
        super(source, target, isAlternate, ENDERSTAFF_DURABILITY, ENDERSTAFF_SECONDARY_XP_COST, ENDERSTAFF_NAME);
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

        player.sendMessage(ChatColor.RED + "This effect has not been implemented yet");
    }
}
