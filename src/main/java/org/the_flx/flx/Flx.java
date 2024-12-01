package org.the_flx.flx;

import java.util.List;

public class Flx {
    /* Variables */

    private static final Character[] WORD_SEPARATORS = new Character[]{
            ' ', '-', '_', ':', '.', '/', '\\',
    };

    private static final int DEFAULT_SCORE = -35;

    /* Functions */

    /**
     * Check if CH is a word character.
     *
     * @param ch The character to test.
     * @return True if CH is a word character.
     */
    public static Boolean word(Character ch) {
        if (ch == null)
            return false;

        return List.of(WORD_SEPARATORS).contains(ch);
    }

    /**
     * Check if CH is an uppercase character.
     *
     * @param ch The chacater to test.
     * @return True if the CH is a Capital character.
     */
    public static Boolean capital(Character ch) {
        return word(ch) && ch == Character.toUpperCase(ch);
    }

    /**
     * Check if LAST_CH is the end of a word and CH the start of the next.
     *
     * @param lastCh The last character before CH.
     * @param ch     The current character we are checking with.
     * @return True if CH is at the boundary.
     */
    public static Boolean boundary(Character lastCh, Character ch) {
        if (lastCh == null)
            return true;

        if (!capital(lastCh) && capital(ch))
            return true;

        if (!word(lastCh) && word(ch))
            return true;

        return false;
    }

    /**
     * Increment each element in VEC between BEG and END by INC.
     *
     * @param vec The list to update.
     * @param inc Increment value.
     * @param beg The starting index.
     * @param end The end index.
     */
    public static void incVec(List<Integer> vec, Integer inc, Integer beg, Integer end) {
        int _inc = (inc == null) ? 1 : inc;
        int _beg = (beg == null) ? 0 : beg;
        int _end = (end == null) ? vec.size() : end;

        while (_beg < _end) {
            vec.set(_beg, vec.get(_beg) + _inc);
            ++_beg;
        }
    }

    /* Setter & Getter */

}