/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.itemplus.commands;

import net.md_5.bungee.api.ChatColor;
import nl.imine.itemplus.effects.EffectManager;
import nl.imine.itemplus.staff.StaffManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Dennis
 */
public class ItemsplusCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {

        if (strings.length == 0) {
            cs.sendMessage(ChatColor.RED + "valid subcommands:\n"
                    + "   getitems \n"
                    + "   reload \n"
                    + "   printstaffs");
            return true;
        }

        if (strings[0].equalsIgnoreCase("getitems")) {
            getItems(cs, strings);
            return true;
        }

        if (strings[0].equalsIgnoreCase("reload")) {
            reload();
            return true;
        }

        if (strings[0].equalsIgnoreCase("printstaffs")) {
            printStaffs();
            return true;
        }

        cs.sendMessage("subcommand not found");
        return true;
    }

    private void getItems(CommandSender cs, String[] strings) {
        if (!(cs instanceof Player)) {
            return;
        }

        Player player = (Player) cs;

        ItemStack[] items = new ItemStack[5];

        for (int i = 0; i < items.length; i++) {
            items[i] = new ItemStack(Material.DIAMOND_HOE);
            items[i].setDurability((short) (i + 17));
            ItemMeta itemMeta = items[i].getItemMeta();
            itemMeta.setUnbreakable(true);
            items[i].setItemMeta(itemMeta); //the effectmanager will only return an effect for a valid staff, and a valid staff needs unbreakable. 
            String displayName = EffectManager.getEffectManager().getEffect(items[i], false).getStaffName();
            itemMeta.setDisplayName(ChatColor.WHITE + displayName);
            items[i].setItemMeta(itemMeta);
        }

        player.getInventory().addItem(items);
    }

    private void reload() {
        StaffManager.loadStaffsFromConfig();
    }

    private void printStaffs() {
        StaffManager.getInstance().getStaffs().forEach(staff -> {
            System.out.println(staff.getName());
//                    + ", "
//                    + staff.getPrimaryActions().toString()
//                    + ", "
//                    + staff.getSecondaryActions().toString());
        });
    }
}
