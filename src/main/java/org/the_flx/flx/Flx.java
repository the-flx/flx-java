package org.the_flx.flx;

import java.util.*;

public class Flx {
    /* Variables */

    private static final Character[] WORD_SEPARATORS = new Character[]{' ', '-', '_', ':', '.', '/', '\\',};

    private static final int DEFAULT_SCORE = -35;

    /* Functions */

    /**
     * Check if CH is a word character.
     *
     * @param ch The character to test.
     * @return True if CH is a word character.
     */
    private static Boolean word(Character ch) {
        if (ch == null) return false;

        return !Arrays.asList(WORD_SEPARATORS).contains(ch);
    }

    /**
     * Check if CH is an uppercase character.
     *
     * @param ch The chacater to test.
     * @return True if the CH is a Capital character.
     */
    private static Boolean capital(Character ch) {
        return word(ch) && ch == Character.toUpperCase(ch);
    }

    /**
     * Check if LAST_CH is the end of a word and CH the start of the next.
     *
     * @param lastCh The last character before CH.
     * @param ch     The current character we are checking with.
     * @return True if CH is at the boundary.
     */
    private static Boolean boundary(Character lastCh, Character ch) {
        if (lastCh == null) return true;

        if (!capital(lastCh) && capital(ch)) return true;

        if (!word(lastCh) && word(ch)) return true;

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
    private static void incVec(LinkedList<Integer> vec, Integer inc, Integer beg, Integer end) {
        int _inc = (inc == null) ? 1 : inc;
        int _beg = (beg == null) ? 0 : beg;
        int _end = (end == null) ? vec.size() : end;

        while (_beg < _end) {
            vec.set(_beg, vec.get(_beg) + _inc);
            ++_beg;
        }
    }

    /**
     * Return hash-table for string where keys are characters.
     * Value is a sorted list of indexes for character occurrences.
     */
    private static void getHashForString(HashMap<Integer, LinkedList<Integer>> result, String str) {
        result.clear();

        int strLen = str.length();
        int index = strLen - 1;
        char ch;
        int downCh;

        while (0 <= index) {
            ch = str.charAt(index);

            if (capital(ch)) {
                Util.dictInsert(result, ch, index);

                downCh = Character.toLowerCase(ch);
            } else {
                downCh = ch;
            }

            Util.dictInsert(result, downCh, index);

            --index;
        }
    }

    /**
     * Generate the heatmap vector of string.
     */
    public static void getHeatmapStr(LinkedList<Integer> scores, String str, Character groupSeparator) {
        int strLen = str.length();
        int strLastIndex = strLen - 1;
        scores.clear();

        for (int i = 0; i < strLen; ++i) {
            scores.add(DEFAULT_SCORE);
        }

        int penaltyLead = (int) '.';

        var inner = new LinkedList<Integer>(Arrays.asList(-1, 0));
        var groupAlist = new LinkedList<LinkedList<Integer>>(List.of(inner));

        // final char bonus
        scores.set(strLastIndex, scores.get(strLastIndex) + 1);

        // Establish baseline mapping
        Character lastCh = null;
        int groupWordCount = 0;
        int index1 = 0;

        for (char ch : str.toCharArray()) {
            // before we find any words, all separaters are
            // considered words of length 1.  This is so "foo/__ab"
            // gets penalized compared to "foo/ab".
            Character effectiveLastChar = ((groupWordCount == 0) ? null : lastCh);

            if (boundary(effectiveLastChar, ch)) {
                groupAlist.getFirst().add(2, index1);
            }

            if (!word(lastCh) && word(ch)) {
                ++groupWordCount;
            }

            // ++++ -45 penalize extension
            if (lastCh != null && lastCh == penaltyLead) {
                scores.set(index1, scores.get(index1) + -45);
            }

            if (groupSeparator != null && groupSeparator == ch) {
                groupAlist.getFirst().set(1, groupWordCount);

                groupWordCount = 0;
                groupAlist.addFirst(new LinkedList<Integer>(Arrays.asList(index1, groupWordCount)));
            }

            if (index1 == strLastIndex) {
                groupAlist.getFirst().set(1, groupWordCount);
            } else {
                lastCh = ch;
            }

            ++index1;
        }

        int groupCount = groupAlist.size();
        int separatorCount = groupCount - 1;

        // ++++ slash group-count penalty
        if (separatorCount != 0) {
            incVec(scores, groupCount * -2, null, null);
        }

        int index2 = separatorCount;
        Integer lastGroupLimit = null;
        boolean basepathFound = false;

        // score each group further
        for (LinkedList<Integer> group : groupAlist) {
            int groupStart = group.get(0);
            int wordCount = group.get(1);
            // this is the number of effective word groups
            int wordsLength = group.size() - 2;
            boolean basepathP = false;

            if (wordsLength != 0 && !basepathFound) {
                basepathFound = true;
                basepathP = true;
            }

            int num;
            if (basepathP) {
                // ++++ basepath separator-count boosts
                int boosts = 0;
                if (separatorCount > 1) boosts = separatorCount - 1;
                // ++++ basepath word count penalty
                int penalty = -wordCount;
                num = 35 + boosts + penalty;
            }
            // ++++ non-basepath penalties
            else {
                if (index2 == 0) num = -3;
                else num = -5 + (index2 - 1);
            }

            incVec(scores, num, groupStart + 1, lastGroupLimit);

            var cddrGroup = new LinkedList<Integer>(group);  // clone it
            cddrGroup.removeFirst();
            cddrGroup.removeFirst();

            int wordIndex = wordsLength - 1;
            int lastWord = (lastGroupLimit != null) ? lastGroupLimit : strLen;

            for (int word : cddrGroup) {
                // ++++  beg word bonus AND
                scores.set(word, scores.get(word) + 85);

                int index3 = word;
                int charI = 0;

                while (index3 < lastWord) {
                    scores.set(index3, scores.get(index3) + (-3 * wordIndex) -  // ++++ word order penalty
                            charI);  // ++++ char order penalty

                    ++charI;
                    ++index3;
                }

                lastWord = word;
                --wordIndex;
            }

            lastGroupLimit = groupStart + 1;
            --index2;
        }
    }

    /**
     * Return sublist bigger than VAL from sorted SORTED-LIST.
     * <p>
     * If VAL is nil, return entire list.
     */
    public static void biggerSublist(LinkedList<Integer> result, LinkedList<Integer> sortedList, Integer val) {
        if (sortedList == null)
            return;

        if (val != null) {
            for (Integer sub : sortedList) {
                if (sub > val) {
                    result.add(sub);
                }
            }
        } else {
            result.addAll(sortedList);
        }
    }

    /**
     * Recursively compute the best match for a string, passed as STR-INFO and
     * HEATMAP, according to QUERY.
     */
    public static void findBestMatch(LinkedList<Result> imatch,
                                     HashMap<Integer, LinkedList<Integer>> strInfo,
                                     LinkedList<Integer> heatmap,
                                     Integer greaterThan,
                                     String query, int queryLength,
                                     int qIndex,
                                     HashMap<Integer, LinkedList<Result>> matchCache) {
        int greaterNum = (greaterThan != null) ? greaterThan : 0;
        Integer hashKey = qIndex + (greaterNum * queryLength);
        LinkedList<Result> hashValue = Util.dictGet(matchCache, hashKey);

        if (hashValue != null)  // Process matchCache here
        {
            imatch.clear();
            imatch.addAll(hashValue);
        } else {
            int uchar = query.charAt(qIndex);
            LinkedList<Integer> sortedList = Util.dictGet(strInfo, uchar);
            var indexes = new LinkedList<Integer>();
            biggerSublist(indexes, sortedList, greaterThan);
            int tempScore;
            int bestScore = Integer.MIN_VALUE;

            if (qIndex >= queryLength - 1) {
                // At the tail end of the recursion, simply generate all possible
                // matches with their scores and return the list to parent.
                for (int index : indexes) {
                    var indices = new LinkedList<Integer>();
                    indices.add(index);
                    imatch.add(new Result(indices, heatmap.get(index), 0));
                }
            } else {
                for (int index : indexes) {
                    var elemGroup = new LinkedList<Result>();
                    findBestMatch(elemGroup,
                            new HashMap<Integer, LinkedList<Integer>>(strInfo),
                            new LinkedList<Integer>(heatmap),
                            index, query, queryLength, qIndex + 1, matchCache);

                    for (var elem : elemGroup) {
                        int caar = elem.indices.getFirst();
                        int cadr = elem.score;
                        int cddr = elem.tail;

                        if ((caar - 1) == index) {
                            tempScore = cadr + heatmap.get(index) +
                                    (Math.min(cddr, 3) * 15) +  // boost contiguous matches
                                    60;
                        } else {
                            tempScore = cadr + heatmap.get(index);
                        }

                        // We only care about the optimal match, so only forward the match
                        // with the best score to parent
                        if (tempScore > bestScore) {
                            bestScore = tempScore;

                            imatch.clear();
                            var indices = new LinkedList<Integer>(elem.indices);
                            indices.addFirst(index);
                            int tail = 0;
                            if ((caar - 1) == index)
                                tail = cddr + 1;
                            imatch.add(new Result(indices, tempScore, tail));
                        }
                    }
                }
            }

            // Calls are cached to avoid exponential time complexity
            Util.dictSet(matchCache, hashKey, new LinkedList<Result>(imatch));
        }
    }

    /**
     * Return best score matching QUERY against STR.
     *
     * @param str   The string to be scored.
     * @param query The query use to score STR.
     * @return Return the scoring object.
     */
    public static Result score(String str, String query) {
        if (str.isEmpty() || query.isEmpty())
            return null;

        var strInfo = new HashMap<Integer, LinkedList<Integer>>();
        getHashForString(strInfo, str);

        var heatmap = new LinkedList<Integer>();
        getHeatmapStr(heatmap, str, null);

        int queryLength = query.length();
        boolean fullMatchBoost = (1 < queryLength) && (queryLength < 5);
        var matchCache = new HashMap<Integer, LinkedList<Result>>();
        var optimalMatch = new LinkedList<Result>();
        findBestMatch(optimalMatch, strInfo, heatmap, null, query, queryLength, 0, matchCache);

        if (optimalMatch.isEmpty())
            return null;

        Result result1 = optimalMatch.getFirst();
        int caar = result1.indices.size();

        if (fullMatchBoost && caar == str.length()) {
            result1.score += 10000;
        }

        return result1;
    }

    /* Setter & Getter */

}
