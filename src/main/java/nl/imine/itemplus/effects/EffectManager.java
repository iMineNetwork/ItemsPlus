package nl.imine.itemplus.effects;

import nl.imine.itemplus.effects.effect.FireStaffPrimaryEffect;
import nl.imine.itemplus.effects.effect.FireStaffSecondaryEffect;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import nl.imine.itemplus.effects.effect.EnderStaffPrimaryEffect;
import nl.imine.itemplus.effects.effect.EnderStaffSecondaryEffect;
import nl.imine.itemplus.effects.effect.HealerStaffPrimaryEffect;
import nl.imine.itemplus.effects.effect.HealerStaffSecondaryEffect;
import nl.imine.itemplus.effects.effect.IceStaffPrimaryEffect;
import nl.imine.itemplus.effects.effect.IceStaffSecondaryEffect;
import nl.imine.itemplus.effects.effect.SeerStaffPrimaryEffect;
import nl.imine.itemplus.effects.effect.SeerStaffSecondaryEffect;

public class EffectManager {

    private static EffectManager instance;

    private final List<Effect> EFFECTS = new ArrayList<>();

    public static EffectManager init() {
        instance = new EffectManager();
        try {
            instance.registerEffect(EnderStaffPrimaryEffect.setup());
            instance.registerEffect(EnderStaffSecondaryEffect.setup());
            instance.registerEffect(FireStaffPrimaryEffect.setup());
            instance.registerEffect(FireStaffSecondaryEffect.setup());
            instance.registerEffect(HealerStaffPrimaryEffect.setup());
            instance.registerEffect(HealerStaffSecondaryEffect.setup());
            instance.registerEffect(IceStaffSecondaryEffect.setup());
            instance.registerEffect(IceStaffPrimaryEffect.setup());
            instance.registerEffect(SeerStaffPrimaryEffect.setup());
            instance.registerEffect(SeerStaffSecondaryEffect.setup());
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return instance;
    }

    public static EffectManager getEffectManager() {
        return instance;
    }

    public void registerEffect(Effect effect) {
        EFFECTS.add(effect);
    }

    public List<Effect> getEffects() {
        return EFFECTS;
    }

    /**
     * Looks up an {@link Effect} by {@link ItemStack}
     *
     * @param itemStack The {@link ItemStack} to findTargets the effect of
     * @param isSneaking
     * @return the {@link Effect} that origins from this {@link ItemStack}
     */
    public Effect getEffect(ItemStack itemStack, boolean isSneaking) {
        return EFFECTS.stream()
                .filter(effect -> itemStack.getItemMeta().isUnbreakable())
                .filter(effect -> itemStack.getDurability() == effect.getItemDurability())
                .filter(effect -> isSneaking == effect.isAlternate)
                .findFirst()
                .orElse(null);
    }

    public boolean hasEffect(ItemStack itemStack, boolean isSneaking) {
        return EFFECTS.stream().anyMatch(effect -> (effect.getItemDurability() == itemStack.getDurability() && effect.isAlternate == isSneaking));
    }
}
