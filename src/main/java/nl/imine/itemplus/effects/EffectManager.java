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
import nl.imine.itemplus.effects.action.SeerStaffPrimaryEffect;

public class EffectManager {

    private final List<Effect> effects = new ArrayList<>();

    public static EffectManager init() {
        EffectManager effectManager = new EffectManager();
        effectManager.registerEffect(EnderStaffPrimaryEffect.setup());
        effectManager.registerEffect(EnderStaffSecondaryEffect.setup());
        effectManager.registerEffect(FireStaffSecondaryEffect.setup());
        effectManager.registerEffect(FireStaffPrimaryEffect.setup());
        effectManager.registerEffect(HealerStaffPrimaryEffect.setup());
        effectManager.registerEffect(HealerStaffSecondaryEffect.setup());
        effectManager.registerEffect(IceStaffSecondaryEffect.setup());
        effectManager.registerEffect(IceStaffPrimaryEffect.setup());
//        effectManager.registerEffect(SeerStaffPrimaryEffect.setup());
//        effectManager.registerEffect(SeerStaffSecondaryEffect.setup());
        return effectManager;
    }

    public void registerEffect(Effect effect) {
        effects.add(effect);
    }

    /**
     * Looks up an {@link Effect} by {@link ItemStack}
     *
     * @param itemStack The {@link ItemStack} to findTargets the effect of
     * @param isSneaking
     * @return the {@link Effect} that origins from this {@link ItemStack}
     */
    public Effect getEffect(ItemStack itemStack, boolean isSneaking) {
        return effects.stream()
                .filter(effect -> itemStack.getItemMeta().isUnbreakable())
                .filter(effect -> itemStack.getDurability() == effect.getItemDurability())
                .filter(effect -> isSneaking == effect.isAlternate)
                .findFirst()
                .orElse(null);
    }

    public boolean hasEffect(ItemStack itemStack, boolean isSneaking) {
        return effects.stream().anyMatch(effect -> (effect.getItemDurability() == itemStack.getDurability() && effect.isAlternate == isSneaking));
    }
}
