package com.github.vedenin.atoms.collections;

import java.util.Collection;

/**
 * Collection interface proxy
 *
 * Created by vvedenin on 4/19/2017.
 */
public interface CollectionAtom <K> extends Iterable<K> {
    Collection<K> getOriginal();
}
