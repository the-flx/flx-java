package org.the_flx.flx;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlxTest {
    @Test
    void score() {
        Result result = Flx.score("switch-to-buffer", "stb");
        assertEquals(247, result.score);
    }
}
