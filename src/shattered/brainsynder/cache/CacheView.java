package shattered.brainsynder.cache;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public interface CacheView<T, V> {
    List<V> asList();

    void clear ();

    default Set<V> asSet(){
        Set<V> set = new HashSet<>();
        set.addAll(asList());
        return set;
    }

    Map<T, V> asMap();

    default V remove (T key) {
        return asMap().remove(key);
    }

    default boolean hasKey (T key) {
        return asMap().containsKey(key);
    }

    default boolean hasValue (V value) {
        return asMap().containsValue(value);
    }

    default int size(){
        return asList().size();
    }

    V inject(T key, V value);

    default V get (T key) {
        return asMap().get(key);
    }

    default boolean isEmpty(){
        return asList().isEmpty();
    }

    default V getElementById(int id){
        return asList().get(id);
    }

    default V getElementById(String id) {
        return getElementById(Integer.parseInt(id));
    }

    default Stream<V> stream() {
        return asList().stream();
    }
}