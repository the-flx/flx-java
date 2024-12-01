package org.example;

import org.the_flx.flx.Flx;
import org.the_flx.flx.Result;

public class Main {
    public static void main(String[] args) {
        Result result = Flx.score("buffer-file-name", "bfn");

        System.out.println("Result: " + result);
    }
}