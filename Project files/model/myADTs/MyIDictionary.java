package model.myADTs;

import java.util.Collection;
import java.util.Map;

public interface MyIDictionary <K,V>{
    V get(Object key);
    boolean	isEmpty();
    V put(K key, V value);
    V remove(Object key);
    int	size();

    void clear();

    Collection<V> getValues();

    MyIDictionary<K, V> deepcopy();
}
