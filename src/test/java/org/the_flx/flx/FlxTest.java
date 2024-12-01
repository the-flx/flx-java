package org.the_flx.flx;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlxTest {
//    @Test
//    void word()
//    {
//        assertEquals(true, Flx.word('c'));
//        assertEquals(false, Flx.word(' '));
//    }
//
//    @Test
//    void capital()
//    {
//        assertEquals(true, Flx.capital('C'));
//        assertEquals(false, Flx.capital('c'));
//    }

    @Test
    void score_stb() {
        Result result = Flx.score("switch-to-buffer", "stb");
        assertEquals(237, result.score);
    }

    @Test
    void score_TestSomeFunctionExterme() {
        Result result = Flx.score("TestSomeFunctionExterme", "met");
        assertEquals(57, result.score);
    }

    @Test
    void score_MetaX_Version() {
        Result result = Flx.score("MetaX_Version", "met");
        assertEquals(211, result.score);
    }
}
