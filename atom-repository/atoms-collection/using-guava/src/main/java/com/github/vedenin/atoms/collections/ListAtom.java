package com.github.vedenin.atoms.collections;

import com.github.vedenin.atom.annotations.Atom;
import com.github.vedenin.atom.annotations.BoilerPlate;
import com.github.vedenin.atom.annotations.Molecule;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * This Atom pattern (pattern that extends a Proxy/Facade pattern that have only minimal methods from original class)
 * This using to light-reference to another third party open-source libraries
 *
 * Created by Slava Vedenin on 12/16/2016.
 */
@SuppressWarnings({"WeakerAccess", "unused", "unchecked"})
@Atom({ArrayList.class, List.class})
@Molecule({MultimapAtom.class, MultisetAtom.class, SetAtom.class})
public class ListAtom<K> implements CollectionAtom<K> {
    private final List<K> original;

    @Override
    public Iterator<K> iterator() {
        return original.iterator();
    }

    public void add(K key) {
        if(key != null) {
            original.add(key);
        }
    }

    public void addAll(CollectionAtom<K> keys) {
        if(keys != null) {
            original.addAll(keys.getOriginal());
        }
    }

    public boolean contains(K key) {
        return key != null && original.contains(key);
    }

    public ListAtom<K> difference(ListAtom<K> setAtom) {
        SetAtom<K> atom = SetAtom.create(original).difference(SetAtom.create(setAtom));
        return ListAtom.create(atom.getOriginal());
    }

    public boolean isEmpty() {
        return original.isEmpty();
    }

    public Stream<K> stream() {
        return original.stream();
    }

    public int size() {
        return original.size();
    }

    public void removeAll(ListAtom<K> removedSet) {
        if(removedSet != null) {
            original.removeAll(removedSet.getOriginal());
        }
    }

    public ListAtom<K> intersection(ListAtom<K> list2) {
        SetAtom<K> atom = SetAtom.create(original).intersection(SetAtom.create(list2));
        return ListAtom.create(atom.getOriginal());
    }

    public static <T> Collector<T, ?, ListAtom<T>> getCollector() {
        return Collector.of(ListAtom::create, ListAtom::add, (left, right) -> { left.addAll(right); return left; });
    }

    public K get(int i) {
        return original.get(i);
    }

    public static <K> ListAtom<K> singletonList(K s) {
        return ListAtom.create(Collections.singletonList(s));
    }

    public static Collector<CharSequence, ?, String> getJoiningCollectors(String s) {
        return Collectors.joining(s);
    }

    public static Collector<CharSequence, ?, String> getJoiningCollectors() {
        return Collectors.joining();
    }

    public SetAtom<K> getSet() {
        return SetAtom.create(this);
    }

    public ListAtom<K> reverse(){
        return ListAtom.getAtom(Lists.reverse(original));
    }

    public ListAtom<K> subList(int fromIndex, int toIndex) {
        return ListAtom.getAtom(original.subList(fromIndex, toIndex));
    }

    // Just boilerplate code for Atom
    @BoilerPlate
    private ListAtom() {
        this.original = new ArrayList<>();
    }

    @BoilerPlate
    private ListAtom(int size) {
        this.original = new ArrayList<>(size);
    }

    @BoilerPlate
    private ListAtom(List<K> original) {
        this.original = original;
    }

    @BoilerPlate
    public static <K> ListAtom<K> getAtom(List<K> list) {
        if(list != null) {
            return new ListAtom<>(list);
        } else {
            return ListAtom.create();
        }
    }

    @BoilerPlate
    public static <K> ListAtom<K> create(Collection<K> collection) {
        return new ListAtom<>(new ArrayList<>(collection));
    }

    @BoilerPlate
    public static <K> ListAtom<K> create(K ... arg) {
        return new ListAtom<>(Arrays.asList(arg));
    }

    @BoilerPlate
    public static <K> ListAtom<K> createFromArray(K[] array) {
        if(array != null) {
            return new ListAtom<>(Arrays.asList(array));
        } else {
            return ListAtom.create();
        }
    }

    @BoilerPlate
    public static <K> ListAtom<K> create() {
        return new ListAtom<>();
    }

    @BoilerPlate
    public static <K> ListAtom<K> create(int size) {
        return new ListAtom<>();
    }

    @BoilerPlate
    public List<K> getOriginal() {
        return original;
    }

    @BoilerPlate
    @Override
    public String toString() {
        return original.stream().map(Object::toString).collect(getJoiningCollectors("\n\r"));
    }

}
