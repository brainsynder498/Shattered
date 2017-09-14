package shattered.brainsynder.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortedCacheView<T,V> extends AbstractCacheView<T,V> implements CacheView<T,V> {
    private final Comparator<V> comparator;
    public SortedCacheView(Comparator<V> comparator) {
        this.comparator = comparator;
    }

    @Override
    public List<V> asList() {
        List<V> list = new ArrayList<>(asMap().values());
        list.sort(comparator);
        return Collections.unmodifiableList(list);
    }
}
