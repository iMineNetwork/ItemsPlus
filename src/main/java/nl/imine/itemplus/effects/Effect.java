package nl.imine.itemplus.effects;

import nl.imine.itemplus.effects.source.EffectSource;
import nl.imine.itemplus.effects.target.EffectTarget;
import org.bukkit.entity.Player;

public abstract class Effect {

    protected EffectSource source;
    protected EffectTarget target;
    protected boolean isAlternate;

    private short itemDurability;

    public Effect(EffectSource source, EffectTarget target, boolean isAlternate, short itemDurability) {
        this.source = source;
        this.target = target;
        this.isAlternate = isAlternate;
        this.itemDurability = itemDurability;
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
        return itemDurability;
    }

    public abstract void applyEffect(Player player);
}
