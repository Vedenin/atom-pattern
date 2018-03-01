package com.github.vedenin.atoms.collections;


import com.github.vedenin.atom.annotations.Atom;
import com.github.vedenin.atom.annotations.BoilerPlate;
import com.github.vedenin.atom.annotations.Molecule;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

import java.util.Iterator;
import java.util.stream.Collector;
import java.util.stream.Stream;


/**
 * This Atom pattern (pattern that extends a Proxy/Facade pattern that have only minimal methods from original class)
 * This using to light-reference to another third party open-source libraries
 *
 * Created by Slava Vedenin on 12/16/2016.
 */
@SuppressWarnings("WeakerAccess")
@Atom({HashMultiset.class, Multisets.class, Multiset.class})
@Molecule({SetAtom.class})
public class MultisetAtom<K> implements CollectionAtom<K> {
    private final Multiset<K> set;

    public MultisetAtom<K> getCopyHighestCountFirst () {
        return MultisetAtom.getAtom(Multisets.copyHighestCountFirst(set));
    }

    public void add(K key) {
        set.add(key);
    }

    public void add(K key, int cnt) {
        set.add(key, cnt);
    }

    public int count(K key) {
        return set.count(key);
    }

    public boolean contains(K key) {
        return set.contains(key);
    }

    public int size() {
        return set.size();
    }

    public SetAtom<K> elementSet() {
        return SetAtom.getAtom(set.elementSet());
    }

    @Override
    public Iterator<K> iterator() {
        return set.iterator();
    }


    public void addAll(CollectionAtom<K> setAtom) {
        set.addAll(setAtom.getOriginal());
    }

    public Stream<Entry<K>> stream() {
       return set.entrySet().stream().map(Entry::new);
    }

    public static class Entry<E> {
        private final Multiset.Entry<E> entry;

        public Entry(Multiset.Entry<E> entry) {
            this.entry = entry;
        }

        public E getElement() {
            return entry.getElement();
        }

        public int getCount() {
            return entry.getCount();
        }

        public boolean equals(Object o) {
            return entry.equals(o);
        }

        public int hashCode() {
            return entry.hashCode();
        }

        public String toString() {
            return entry.toString();
        }
    }

    public static <T> Collector<T, ?, MultisetAtom<T>> getCollector() {
        return Collector.of(MultisetAtom::create, MultisetAtom::add, (left, right) -> { left.addAll(right); return left; });
    }

    public static <T> Collector<MultisetAtom.Entry<T>, ?, MultisetAtom<T>> getMultisetEntityCollector() {
        return Collector.of(MultisetAtom::create, (m, e) -> m.add(e.getElement(), e.getCount()), (left, right) -> { left.addAll(right); return left; });
    }

    // Just boilerplate code for Atom
    @BoilerPlate
    private MultisetAtom() {
        this.set = HashMultiset.create();
    }

    @BoilerPlate
    private MultisetAtom(Multiset<K> set) {
        this.set = set;
    }

    @BoilerPlate
    public static <K> MultisetAtom<K> getAtom(Multiset<K> set) {
        return new MultisetAtom<>(set);
    }

    @BoilerPlate
    public static <K> MultisetAtom<K> create() {
        return new MultisetAtom<>();
    }

    @BoilerPlate
    @Override
    public Multiset<K> getOriginal() {
        return set;
    }

    @BoilerPlate
    @Override
    public String toString() {
        return set.toString();
    }
}
