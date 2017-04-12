package nl.imine.itemplus.staff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.imine.itemplus.effects.actions.Action;
import nl.imine.itemplus.settings.Settings;

/**
 * @author Dennis
 */
public class StaffManager {

    private static StaffManager instance;
    private static List<Staff> staffs = new ArrayList<>();

    private StaffManager() {
        List<Action> pa = new ArrayList<>();
        List<Action> sa = new ArrayList<>();

      
//        PotionEffect[] pe = {
//            new PotionEffect(PotionEffectType.SPEED, 200, 1, true, true),
//            new PotionEffect(PotionEffectType.HARM, 200, 1, true, true)
//        };

//        pa.add(new PotionEffectSourceAction(null, pe, true));
//        sa.add(new PotionEffectSourceAction(null, pe, true));

        Staff stf = new Staff("Staff x", pa, sa);
        
        Staff stf1 = new Staff("Staff y", pa, sa);
       
                
        Staff stf2 = new Staff("Staff z", pa, sa);
        staffs.add(stf);
        staffs.add(stf1);
        staffs.add(stf2);

    }

    public static StaffManager getInstance() {
        if (instance == null) {
            instance = new StaffManager();
        }
        return instance;
    }

    public static void loadStaffsFromConfig() {
        staffs.clear();
        staffs.addAll(Arrays.asList(Settings.getStaffs()));
    }

    public List<Staff> getStaffs() {
        return staffs;
    }

}
