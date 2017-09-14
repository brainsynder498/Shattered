package shattered.brainsynder.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstractCacheView<T, V> implements CacheView<T, V> {
    private final Map<T, V> elements = new HashMap<>();

    @Override
    public void clear() {
        elements.clear();
    }

    @Override
    public Map<T, V> asMap() {
        return elements;
    }

    @Override
    public List<V> asList() {
        List<V> list = new ArrayList<>();
        list.addAll(elements.values());
        return list;
    }

    @Override
    public V inject(T key, V value) {
        return elements.put(key, value);
    }
}