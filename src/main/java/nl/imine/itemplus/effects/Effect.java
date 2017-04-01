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

    protected final String NAME;

    public Effect(EffectSource source, EffectTarget target, boolean isAlternate, short itemDurability, float experienceCost, String NAME) {
        System.out.println("staff with durability: " + itemDurability + ", experienceCost: " + experienceCost + ", NAME:" + NAME);
        this.source = source;
        this.target = target;
        this.isAlternate = isAlternate;
        this.ITEM_DURABILITY = itemDurability;
        this.EXPERIENCE_COST = experienceCost;
        this.NAME = NAME;
    }

    public EffectSource getSource() {
        return source;
    }

    public EffectTarget getTarget() {
        return target;
    }

    public boolean isAlternate() {
        return isAlternate;
    }

    public short getItemDurability() {
        return ITEM_DURABILITY;
    }

    public String getStaffName() {
        return NAME;
    }

    public abstract void applyEffect(Player player);
}
