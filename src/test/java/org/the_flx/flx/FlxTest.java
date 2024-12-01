package org.the_flx.flx;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlxTest {
    @Test
    void score() {
        Result result = Flx.score("switch-to-buffer", "stb");
        assertEquals(247, result.score);

        result = Flx.score("TestSomeFunctionExterme", "met");
        assertEquals(57, result.score);

        result = Flx.score("MetaX_Version", "met");
        assertEquals(211, result.score);
    }
}
