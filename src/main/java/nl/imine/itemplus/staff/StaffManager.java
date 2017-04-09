/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.itemplus.staff;

import java.util.ArrayList;
import java.util.List;
import nl.imine.itemplus.effects.actions.Action;
import nl.imine.itemplus.effects.actions.action.potioneffect.PotionEffectSourceAction;
import nl.imine.itemplus.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Dennis
 */
public class StaffManager {

    private static StaffManager instance;
    private static final List<Staff> STAFFS = new ArrayList<>();

    private StaffManager() {
        List<Action> pa = new ArrayList<>();
        List<Action> sa = new ArrayList<>();

        List<LivingEntity> p = new ArrayList<>();
        p.add(Bukkit.getOfflinePlayer("b265db93-c0eb-414c-a13c-1877815f7163").getPlayer());

        PotionEffect[] pe = {
            new PotionEffect(PotionEffectType.SPEED, 200, 1, true, true),
            new PotionEffect(PotionEffectType.HARM, 200, 1, true, true)
        };

        pa.add(new PotionEffectSourceAction(pe, p, true));
        sa.add(new PotionEffectSourceAction(pe, p, true));

        Staff stf = new Staff("staff 1", pa, sa);
        STAFFS.add(stf);

    }

    public static StaffManager getInstance() {
        if (instance == null) {
            instance = new StaffManager();
        }
        return instance;
    }

    public static void loadStaffsFromConfig() {
        STAFFS.clear();
        int i = 1;
        while (true) {
            System.out.println("loading staff");
            Staff staff = Settings.getStaff("Staff " + i);
            if (staff == null) {
                System.out.println("staff loading done");
                break;
            }
            STAFFS.add(staff);
            i++;
        }
    }

    public List<Staff> getStaffs() {
        return STAFFS;
    }

}
