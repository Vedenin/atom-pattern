package com.github.vedenin.atom.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation for Atom class that give link to test class
 *
 * Created by Slava Vedenin on 5/10/2016.
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface AtomTest {
    String[] value() default {};
}
