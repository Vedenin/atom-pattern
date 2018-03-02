package com.github.vedenin.atoms.io;

import com.github.vedenin.atom.annotations.AtomUtils;

import java.util.Arrays;

/**
 * Logging Atom (todo change to LogAtom)
 *
 * Created by vvedenin on 4/20/2017.
 */
@AtomUtils(System.class)
@SuppressWarnings("unused")
public class SystemOutAtom {
    public static <T> void printArray(T[] array) {
        Arrays.stream(array).forEach(System.out::println);
    }

    public static <T> void println(T obj) {
        System.out.println(obj);
    }

    public static void println() {
        System.out.println();
    }
}
