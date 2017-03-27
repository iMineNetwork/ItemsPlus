package nl.imine.itemplus.effects;

import nl.imine.itemplus.effects.action.FireStaffPrimaryEffect;
import nl.imine.itemplus.effects.action.FireStaffSecondaryEffect;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EffectManager {

    private List<Effect> effects = new ArrayList<>();


    public static EffectManager init(){
        EffectManager effectManager = new EffectManager();
        effectManager.registerEffect(FireStaffSecondaryEffect.setup());
        effectManager.registerEffect(FireStaffPrimaryEffect.setup());
        return effectManager;
    }

    public void registerEffect(Effect effect){
        effects.add(effect);
    }

    /**
     * Looks up an {@link Effect} by {@link ItemStack}
     * @param itemStack The {@link ItemStack} to findTargets the effect of
     * @return the {@link Effect} that origins from this {@link ItemStack}
     */
    public Effect getEffect(ItemStack itemStack, boolean isSneaking){
        return effects.stream()
                .filter(effect -> itemStack.getDurability() == effect.getItemDurability())
                .filter(effect -> isSneaking == effect.isAlternate)
                .findFirst()
                .orElse(null);
    }

    public boolean hasEffect(ItemStack itemStack, boolean isSneaking){
        for(Effect effect : effects){
            if(effect.getItemDurability() == itemStack.getDurability() && effect.isAlternate == isSneaking){
                return true;
            }
        }
        return false;
    }
}
