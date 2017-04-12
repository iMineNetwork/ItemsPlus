/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.itemplus.staff;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import nl.imine.itemplus.effects.actions.Action;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 *
 * @author Dennis
 */
public class Staff implements ConfigurationSerializable {

    private String name;
//    private final List<Action> PRIMARY_ACTIONS;
//    private final List<Action> SECONDARY_ACTIONS;

    private Staff() {
//        PRIMARY_ACTIONS = null;
//        SECONDARY_ACTIONS = null;
    }

    public Staff(String name, List<Action> primaryActions, List<Action> secondaryActions) {
        this.name = name;
//        this.PRIMARY_ACTIONS = primaryActions;
//        this.SECONDARY_ACTIONS = secondaryActions;
    }

//    public void performPrimaryActions() {
//        PRIMARY_ACTIONS.forEach(action -> action.execute());
//    }
//    public void performSecondaryActions() {
//        SECONDARY_ACTIONS.forEach(action -> action.execute());
//    }
    
    public String getName() {
        return name;
    }

//    public List<Action> getPrimaryActions() {
//        return PRIMARY_ACTIONS;
//    }
//
//    public List<Action> getSecondaryActions() {
//        return SECONDARY_ACTIONS;
//    }
    public Staff(Map<String, Object> map) {

        System.out.println("CREATE INSTANCE VIA MAP");
        name = (String) map.get("Name");
//        PRIMARY_ACTIONS = null;//(List<Action>) map.get("Primary");
//        SECONDARY_ACTIONS = null;//(List<Action>) map.get("Secondary");

//        this(getName(map), getPrimaryActions(map), getSecondaryActions(map));
    }

    public static String getName(Map<String, Object> map) {
        Object ret = map.get("name");
        if (ret instanceof String) {
            return (String) ret;
        }
        throw new NoSuchElementException(map + " does not contain " + "\"name\"");
    }

//    public static List<Action> getPrimaryActions(Map<String, Object> map) {
//        Object ret = map.get("Primary");
//        if (ret instanceof List) {
//            return (List<Action>) ret;
//        }
//        throw new NoSuchElementException(map + " does not contain " + "\"Primary\"");
//    }
//    public static List<Action> getSecondaryActions(Map<String, Object> map) {
//        Object ret = map.get("Secondary");
//        if (ret instanceof List) {
//            return (List<Action>) ret;
//        }
//        throw new NoSuchElementException(map + " does not contain " + "\"Secondary\"");
//    }
    @Override
    public Map<String, Object> serialize() {
//        return (Map<String, Object>) ImmutableMap.of("Name", name, "Primary", PRIMARY_ACTIONS, "Secondary", SECONDARY_ACTIONS);

        Map<String, Object> staff = new HashMap<>();

        staff.put("Name", name);
//        staff.put("Primary", PRIMARY_ACTIONS);
//        staff.put("Secondary", SECONDARY_ACTIONS);
//
        return staff;
    }
}
