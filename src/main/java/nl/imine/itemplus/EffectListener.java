package nl.imine.itemplus;

import nl.imine.itemplus.effects.Effect;
import nl.imine.itemplus.effects.EffectManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class EffectListener implements Listener {

    private EffectManager effectManager;

    public static void init() {
        Bukkit.getServer().getPluginManager().registerEvents(new EffectListener(EffectManager.init()), BukkitStarter.getInstance());
    }

    public EffectListener(EffectManager effectManager) {
        this.effectManager = effectManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent evt) {
        if (evt.getItem() != null) {
            if (evt.getAction().equals(Action.RIGHT_CLICK_BLOCK) || evt.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                if (evt.getItem().getType().equals(Material.DIAMOND_HOE)) {
                    boolean isSneaking = evt.getPlayer().isSneaking();
                    //Determine which hand should be used. Only 1 item can be used per interact event, taking priority on the main hand.
                    EquipmentSlot usedSlot = null;
                    if (evt.getHand().equals(EquipmentSlot.OFF_HAND) && !effectManager.hasEffect(evt.getPlayer().getEquipment().getItemInMainHand(), isSneaking) && effectManager.hasEffect(evt.getItem(), isSneaking)) {
                        usedSlot = EquipmentSlot.OFF_HAND;
                    }
                    if (evt.getHand().equals(EquipmentSlot.HAND) && effectManager.hasEffect(evt.getItem(), isSneaking)) {
                        usedSlot = EquipmentSlot.HAND;
                    }

                    if (usedSlot != null) {
                        Effect effect = effectManager.getEffect(evt.getItem(), isSneaking);
                        if (effect != null) {
                            effect.applyEffect(evt.getPlayer());
                        }
                        evt.setCancelled(true);
                    }
                }
            }
        }
    }
}
