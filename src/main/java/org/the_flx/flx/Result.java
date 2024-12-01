package org.the_flx.flx;

import java.util.List;

public class Result {
    /* Variables */

    public List<Integer> indices;
    public Integer score;
    public Integer tail;

    /* Functions */

    public Result(List<Integer> indices, Integer score, Integer tail) {
        this.indices = indices;
        this.score = score;
        this.tail = tail;
    }

    @Override
    public String toString() {
        return score + " " + indices;
    }

    /* Setter & Getter */

}
