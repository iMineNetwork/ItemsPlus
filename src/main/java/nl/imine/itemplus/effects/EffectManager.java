package nl.imine.itemplus.effects;

import nl.imine.itemplus.effects.action.FireStaffPrimaryEffect;
import nl.imine.itemplus.effects.action.FireStaffSecondaryEffect;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import nl.imine.itemplus.effects.action.EnderStaffPrimaryEffect;
import nl.imine.itemplus.effects.action.EnderStaffSecondaryEffect;
import nl.imine.itemplus.effects.action.HealerStaffPrimaryEffect;
import nl.imine.itemplus.effects.action.HealerStaffSecondaryEffect;
import nl.imine.itemplus.effects.action.IceStaffPrimaryEffect;
import nl.imine.itemplus.effects.action.IceStaffSecondaryEffect;

public class EffectManager {

    private static final List<Effect> EFFECTS = new ArrayList<>();

    public static EffectManager init() {
        EffectManager effectManager = new EffectManager();
        EffectManager.registerEffect(EnderStaffPrimaryEffect.setup());
        EffectManager.registerEffect(EnderStaffSecondaryEffect.setup());
        EffectManager.registerEffect(FireStaffSecondaryEffect.setup());
        EffectManager.registerEffect(FireStaffPrimaryEffect.setup());
        EffectManager.registerEffect(HealerStaffPrimaryEffect.setup());
        EffectManager.registerEffect(HealerStaffSecondaryEffect.setup());
        EffectManager.registerEffect(IceStaffSecondaryEffect.setup());
        EffectManager.registerEffect(IceStaffPrimaryEffect.setup());
//        effectManager.registerEffect(SeerStaffPrimaryEffect.setup());
//        effectManager.registerEffect(SeerStaffSecondaryEffect.setup());
        return effectManager;
    }

    public static void registerEffect(Effect effect) {
        EFFECTS.add(effect);
    }

    /**
     * Looks up an {@link Effect} by {@link ItemStack}
     *
     * @param itemStack The {@link ItemStack} to findTargets the effect of
     * @param isSneaking
     * @return the {@link Effect} that origins from this {@link ItemStack}
     */
    public static Effect getEffect(ItemStack itemStack, boolean isSneaking) {
        return EFFECTS.stream()
                .filter(effect -> itemStack.getItemMeta().isUnbreakable())
                .filter(effect -> itemStack.getDurability() == effect.getItemDurability())
                .filter(effect -> isSneaking == effect.isAlternate)
                .findFirst()
                .orElse(null);
    }

    public static boolean hasEffect(ItemStack itemStack, boolean isSneaking) {
        return EFFECTS.stream().anyMatch(effect -> (effect.getItemDurability() == itemStack.getDurability() && effect.isAlternate == isSneaking));
    }
}
