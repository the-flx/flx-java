package org.the_flx.flx;

import java.util.HashMap;
import java.util.LinkedList;

public class Util {
    /* Variables */

    /* Functions */

    public static void dictInsert(HashMap<Integer, LinkedList<Integer>> result, int key, int val) {
        if (!result.containsKey(key))
            result.put(key, new LinkedList<Integer>());

        LinkedList<Integer> lst = result.get(key);
        lst.add(0, val);
    }

    /* Setter & Getter */

}
