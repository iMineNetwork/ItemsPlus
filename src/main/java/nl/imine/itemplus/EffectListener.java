package nl.imine.itemplus;

import java.util.ArrayList;
import java.util.List;
import nl.imine.itemplus.effects.Effect;
import nl.imine.itemplus.effects.EffectManager;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectListener implements Listener {

    private EffectManager effectManager;
    List<Player> bowCooldown = new ArrayList<>();

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
                } else if (evt.getItem().getType().equals(Material.BOW)) {

                    evt.setCancelled(true);
                    Player player = (Player) evt.getPlayer();

                    if (bowCooldown.contains(player)) {
                        return;
                    }

                    //checks if the player has an arrow to shoot and removes it from his inventory
                    for (ItemStack item : player.getInventory().getContents()) {
                        if (item.getType() == Material.ARROW) {
                            item.setAmount(item.getAmount() - 1);
                            break;
                        }
                        return;
                    }

                    Location location = new Location(player.getLocation().getWorld(), player.getLocation().getX(), player.getLocation().getY() + 1, player.getLocation().getZ());

                    Arrow arrow = (Arrow) location.getWorld().spawnEntity(location, EntityType.ARROW);
                    arrow.setShooter(player);
                    arrow.setVelocity(player.getLocation().getDirection().multiply(2));

                    bowCooldown.add(player);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitStarter.getInstance(), () -> {
                        bowCooldown.remove(player);
                    }, 100L);
                }
            }
        }
    }

    @EventHandler
    public void onSnowballHit(EntityDamageByEntityEvent edbee) {

        if (!edbee.getDamager().getScoreboardTags().contains("iceStaffSnowball")) {
            return;
        }

        if (!(edbee.getEntity() instanceof LivingEntity)) {
            return;
        }

        LivingEntity entity = (LivingEntity) edbee.getEntity();
        entity.damage(1); // true damage, armor is ignored
        entity.setVelocity(edbee.getDamager().getVelocity()); //snowballs do not hit the player, and because of that he doesn't get knockback 
        entity.removePotionEffect(PotionEffectType.SLOW); //making sure the potionEffect overwrites the old one
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1, true, true, Color.SILVER));

    }

}
