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
    private final Multiset<K> original;

    public MultisetAtom<K> getCopyHighestCountFirst () {
        return MultisetAtom.getAtom(Multisets.copyHighestCountFirst(original));
    }

    public void add(K key) {
        original.add(key);
    }

    public void add(K key, int cnt) {
        original.add(key, cnt);
    }

    public int count(K key) {
        return original.count(key);
    }

    public boolean contains(K key) {
        return original.contains(key);
    }

    public int size() {
        return original.size();
    }

    public SetAtom<K> elementSet() {
        return SetAtom.getAtom(original.elementSet());
    }

    @Override
    public Iterator<K> iterator() {
        return original.iterator();
    }


    public void addAll(CollectionAtom<K> setAtom) {
        original.addAll(setAtom.getOriginal());
    }

    public Stream<Entry<K>> stream() {
       return original.entrySet().stream().map(Entry::new);
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Entry<?> entry1 = (Entry<?>) o;

            return entry != null ? entry.equals(entry1.entry) : entry1.entry == null;

        }

        @Override
        public int hashCode() {
            return entry != null ? entry.hashCode() : 0;
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
        this.original = HashMultiset.create();
    }

    @BoilerPlate
    private MultisetAtom(Multiset<K> original) {
        this.original = original;
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
        return original;
    }

    @BoilerPlate
    @Override
    public String toString() {
        return original.toString();
    }
}
