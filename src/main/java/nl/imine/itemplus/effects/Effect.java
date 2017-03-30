package nl.imine.itemplus.effects;

import nl.imine.itemplus.effects.source.EffectSource;
import nl.imine.itemplus.effects.target.EffectTarget;
import org.bukkit.entity.Player;

public abstract class Effect {

    protected EffectSource source;
    protected EffectTarget target;
    protected boolean isAlternate;
    protected final float EXPERIENCE_COST;
    private final short ITEM_DURABILITY;

    public Effect(EffectSource source, EffectTarget target, boolean isAlternate, short itemDurability, float experienceCost) {
        this.source = source;
        this.target = target;
        this.isAlternate = isAlternate;
        this.ITEM_DURABILITY = itemDurability;
        this.EXPERIENCE_COST = experienceCost;
    }

    public EffectSource getSource() {
        return source;
    }

    public EffectTarget getTarget() {
        return target;
    }

    public boolean isAlternate(){
        return isAlternate;
    }

    public short getItemDurability() {
        return ITEM_DURABILITY;
    }

    public abstract void applyEffect(Player player);
}
