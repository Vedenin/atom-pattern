package com.github.vedenin.atoms.collections;

import java.util.Collection;

/**
 * Created by vvedenin on 4/19/2017.
 */
public interface CollectionAtom <K> extends Iterable<K> {
    Collection<K> getOriginal();
}
