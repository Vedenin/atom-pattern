package com.github.vedenin.atom.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation exceptions that be thrown from Atom class
 *
 * Created by Slava Vedenin on 5/10/2016.
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface AtomException {
    Class[] value() default {};
}
