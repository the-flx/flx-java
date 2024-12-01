package org.the_flx.flx;

import java.util.HashMap;
import java.util.LinkedList;

public class Util {
    /* Variables */

    /* Functions */

    public static void dictSet(HashMap<Integer, LinkedList<Result>> result, Integer key, LinkedList<Result> val) {
        if (key == null)
            return;

        if (!result.containsKey(key))
            result.put(key, val);

        result.put(key, val);
    }

    public static <T> LinkedList<T> dictGet(
            HashMap<Integer, LinkedList<T>> dict, Integer
                    key) {
        if (key == null)
            return null;

        if (!dict.containsKey(key))
            return null;

        return dict.get(key);
    }

    public static void dictInsert(HashMap<Integer, LinkedList<Integer>> result, int key, int val) {
        if (!result.containsKey(key))
            result.put(key, new LinkedList<Integer>());

        LinkedList<Integer> lst = result.get(key);
        lst.add(0, val);
    }

    /* Setter & Getter */

}
