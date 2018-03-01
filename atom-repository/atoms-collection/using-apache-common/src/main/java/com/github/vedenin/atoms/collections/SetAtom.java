package com.github.vedenin.atoms.collections;

import com.github.vedenin.atom.annotations.Atom;
import com.github.vedenin.atom.annotations.BoilerPlate;
import com.github.vedenin.atom.annotations.Isotopes;
import com.github.vedenin.atom.annotations.Molecule;
import org.apache.commons.collections4.SetUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * This Atom pattern (pattern that extends a Proxy/Facade pattern that have only minimal methods from original class)
 * This using to light-reference to another third party open-source libraries
 * <p>
 * Created by Slava Vedenin on 12/16/2016.
 */
@SuppressWarnings("WeakerAccess")
@Atom({HashSet.class, Set.class, TreeSet.class, LinkedHashSet.class})
@Isotopes({HashSet.class, TreeSet.class, LinkedHashSet.class})
@Molecule({MultimapAtom.class, MultisetAtom.class})
public class SetAtom<K> implements CollectionAtom<K> {
    private final Set<K> set;

    @Override
    public Iterator<K> iterator() {
        return set.iterator();
    }

    public void add(K key) {
        if (key != null) {
            set.add(key);
        }
    }

    public void addAll(CollectionAtom<K> keys) {
        if (keys != null) {
            set.addAll(keys.getOriginal());
        }
    }

    public boolean contains(K key) {
        return key != null && set.contains(key);
    }

    public SetAtom<K> difference(SetAtom<K> setAtom) {
        return getAtom(SetUtils.difference(set, setAtom.getOriginal()));
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public Stream<K> stream() {
        return set.stream();
    }

    public int size() {
        return set.size();
    }

    public void removeAll(SetAtom<K> removedSet) {
        if (removedSet != null) {
            set.removeAll(removedSet.getOriginal());
        }
    }

    public SetAtom<K> intersection(SetAtom<K> set2) {
        return getAtom(SetUtils.intersection(set, set2.getOriginal()));
    }

    public static <T> Collector<T, ?, SetAtom<T>> getCollector() {
        return Collector.of(SetAtom::create, SetAtom::add, (left, right) -> {
            left.addAll(right);
            return left;
        });
    }

    public static <T> Collector<T, ?, SetAtom<T>> getTreeSetCollector() {
        return Collector.of(SetAtom::createTreeSet, SetAtom::add, (left, right) -> {
            left.addAll(right);
            return left;
        });
    }

    public static <T> Collector<T, ?, SetAtom<T>> getLinkedSetCollector() {
        return Collector.of(SetAtom::createLinkedSet, SetAtom::add, (left, right) -> {
            left.addAll(right);
            return left;
        });
    }

    public static <T> Collector<T, ?, SetAtom<T>> getCollectorForSet(Set<?> set) {
        if (set instanceof SortedSet) {
            return SetAtom.getTreeSetCollector();
        } else if (set instanceof LinkedHashSet) {
            return SetAtom.getLinkedSetCollector();
        }
        return SetAtom.getCollector();
    }

    public ListAtom<K> toList() {
        return ListAtom.create(set);
    }

    // Just boilerplate code for Atom
    @BoilerPlate
    public SetAtom() {
        this.set = new HashSet<>();
    }

    @BoilerPlate
    public SetAtom(int size) {
        this.set = new HashSet<>(size);
    }

    @BoilerPlate
    public SetAtom(Set<K> set) {
        this.set = set;
    }

    @BoilerPlate
    public SetAtom(Collection<K> set) {
        this.set = new HashSet<>(set);
    }

    @BoilerPlate
    public static <K> SetAtom<K> getAtom(Set<K> set) {
        if (set != null) {
            return new SetAtom<>(set);
        } else {
            return SetAtom.create();
        }
    }

    @BoilerPlate
    public static <K> SetAtom<K> create(Collection<K> collection) {
        return new SetAtom<>(new HashSet<>(collection));
    }

    @BoilerPlate
    public static <K> SetAtom<K> create(ListAtom<K> list) {
        return new SetAtom<>(new HashSet<>(list.getOriginal()));
    }

    @BoilerPlate
    public static <K> SetAtom<K> create() {
        return new SetAtom<>();
    }

    @BoilerPlate
    public static <K> SetAtom<K> create(int size) {
        return new SetAtom<>();
    }

    @BoilerPlate
    public static <K> SetAtom<K> create(K... args) {
        return SetAtom.create(new HashSet<>(Arrays.asList(args)));
    }

    @BoilerPlate
    public static <K> SetAtom<K> createTreeSet() {
        return new SetAtom<>(new TreeSet<>());
    }

    @BoilerPlate
    public static <K> SetAtom<K> createTreeSet(CollectionAtom<K> collection) {
        return new SetAtom<>(new TreeSet<>(collection.getOriginal()));
    }

    @BoilerPlate
    public static <K> SetAtom<K> createLinkedSet() {
        return new SetAtom<>(new LinkedHashSet<>());
    }

    @BoilerPlate
    public static <K> SetAtom<K> createLinkedSet(int size) {
        return new SetAtom<>(new LinkedHashSet<>(size));
    }

    @BoilerPlate
    public Set<K> getOriginal() {
        return set;
    }

    @BoilerPlate
    public static Collector<CharSequence, ?, String> getJoiningCollectors(String s) {
        return Collectors.joining(s);
    }

    @BoilerPlate
    @Override
    public String toString() {
        return set.stream().map(Object::toString).collect(getJoiningCollectors(" , "));
    }
}
