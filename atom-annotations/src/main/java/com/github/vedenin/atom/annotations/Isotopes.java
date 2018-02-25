package com.github.vedenin.atom.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * NOT NECESSARY ANNOTATION!
 *
 * Isotopes annotation for Atom class
 * This annotation can be used to describe that this Atom have different
 * variant of original classes, for example Atom using to parse
 * json can be proxied GSON or jackson libraries
 *
 * Created by Slava Vedenin on 5/10/2016.
 */
@Target({ TYPE})
@Retention(SOURCE)
public @interface Isotopes {
    Class[] value();
}
