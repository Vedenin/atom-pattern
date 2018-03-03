package com.github.vedenin.atoms.collections;


import com.github.vedenin.atom.annotations.Atom;
import com.github.vedenin.atom.annotations.BoilerPlate;
import com.github.vedenin.atom.annotations.Isotopes;
import com.github.vedenin.atom.annotations.Molecule;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * This Atom pattern (pattern that extends a Proxy/Facade pattern that have only minimal methods from original class)
 * This using to light-reference to another third party open-source libraries
 *
 * Created by Slava Vedenin on 12/16/2016.
 */
@SuppressWarnings("WeakerAccess")
@Atom({HashMultimap.class, Multimap.class, LinkedHashMultimap.class, TreeMultimap.class})
@Isotopes({HashMultimap.class, LinkedHashMultimap.class, TreeMultimap.class})
@Molecule({SetAtom.class, ListAtom.class})
public class MultimapAtom<K, V> implements Iterable<MultimapAtom.Entry<K,V>> {
    private final Multimap<K, V> original;

    public SetAtom<K> keySet() {
        return SetAtom.getAtom(original.keySet());
    }

    public ListAtom<V> get(K key) {
        return ListAtom.create(original.get(key));
    }

    public void set(K key, V value) {
        original.put(key, value);
    }

    public void put(K key, V value) {
        original.put(key, value);
    }

    public void putAll(K key, CollectionAtom<V> list) {
        original.putAll(key, list);
    }

    public void putAll(MultimapAtom<K, V> mapAtom) {
        original.putAll(mapAtom.original);
    }

    public int size() {
        return original.size();
    }

    public ListAtom<V> values() {
        return ListAtom.create(original.values());
    }

    public boolean containsEntry(V key, V value) {
        return original.containsEntry(key, value);
    }

    public boolean containsValue(V value) {
        return original.containsValue(value);
    }

    public SetAtom<K> getKeysByValue(V value) {
        SetAtom<K> keys = SetAtom.create();
        for(Map.Entry<K, V> entity: original.entries()) {
            if(entity.getValue().equals(value)) {
                keys.add(entity.getKey());
            }
        }
        return keys;
    }

    public static <T, K, U> Collector<T, ?, MultimapAtom<K,U>> getSetCollector(Function<? super T, ? extends K> keyMapper,
                                                                               Function<? super T, SetAtom<U>> valueMapper) {
        return Collector.of(MultimapAtom::create,
                (map, element) -> map.putAll(keyMapper.apply(element), valueMapper.apply(element)),
                (left, right) -> { left.putAll(right); return left; });

    }

    public static <T, K, U> Collector<T, ?, MultimapAtom<K,U>> getListCollector(Function<? super T, ? extends K> keyMapper,
                                                                               Function<? super T, ListAtom<U>> valueMapper) {
        return Collector.of(MultimapAtom::create,
                (map, element) -> map.putAll(keyMapper.apply(element), valueMapper.apply(element)),
                (left, right) -> { left.putAll(right); return left; });

    }

    public static <T, K, U> Collector<T, ?, MultimapAtom<K,U>> getCollector(Function<? super T, ? extends K> keyMapper,
                                                                            Function<? super T, U> valueMapper) {
        return Collector.of(MultimapAtom::create,
                (map, element) -> map.put(keyMapper.apply(element), valueMapper.apply(element)),
                (left, right) -> { left.putAll(right); return left; });

    }

    public Stream<Entry<K, V>> stream() {
        return original.keySet().stream().map((k) -> new Entry<>(k, original.get(k)));
    }


    // Just boilerplate code for Atom
    @BoilerPlate
    private MultimapAtom() {
        this.original = HashMultimap.create();
    }

    @BoilerPlate
    private MultimapAtom(Multimap<K, V> original) {
        this.original = original;
    }

    @BoilerPlate
    public static <K, V> MultimapAtom<K, V> create() {
        return new MultimapAtom<>();
    }

    @BoilerPlate
    public static <K extends Comparable, V extends Comparable> MultimapAtom<K, V> createTreeMap() {
        return new MultimapAtom<>(TreeMultimap.create());
    }

    @BoilerPlate
    public static <K, V> MultimapAtom<K, V> createLinkedMap() {
        return new MultimapAtom<>(LinkedHashMultimap.create());
    }

    @BoilerPlate
    @SafeVarargs
    public static <K> MultimapAtom<K, K> create(K... args) {
        HashMultimap<K, K> map = HashMultimap.create();
        K key = null;
        for(K arg : args) {
            if(key == null) {
                key = arg;
            } else {
                map.put(key, arg);
                key = null;
            }
        }
        return new MultimapAtom<>(map);
    }

    @BoilerPlate
    @SafeVarargs
    public static <K, V> MultimapAtom<K, V> create(Entry<K, V>... entries) {
        MultimapAtom<K,V> map = create();
        for(Entry<K,V> entry : entries) {
            map.putAll(entry.key, entry.values);
        }
        return map;
    }

    @BoilerPlate
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<K, V> entry: original.entries()) {
            builder.append("{ \"").append(entry.getKey()).append("\" : \"").append(entry.getValue()).append("\" }\n\r");
        }
        return builder.toString();
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Entry<K, V>>() {
            private Iterator<K> iterator = original.keySet().iterator();
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Entry<K, V> next() {
                K key = iterator.next();
                return new Entry<>(key, original.get(key));
            }
        };
    }


    public static class Entry<K, V>{
        private final K key;
        private final ListAtom<V> values;

        private Entry(K key, ListAtom<V> values) {
            this.key = key;
            this.values = values;
        }

        private Entry(K key, Collection<V> values) {
            this.key = key;
            this.values = ListAtom.create(values);
        }

        public K getKey() {
            return key;
        }

        public ListAtom<V> getValues() {
            return values;
        }

        @SafeVarargs
        public static <K,V> Entry<K, V> entity(K key, V... args) {
            return new Entry<>(key, ListAtom.getAtom(Arrays.asList(args)));
        }
    }
}
