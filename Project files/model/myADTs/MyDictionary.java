package model.myADTs;

import java.util.*;

public class MyDictionary <K, V> implements MyIDictionary<K, V>{
    private Hashtable<K, V> dictionary;

    public MyDictionary() {
        dictionary = new Hashtable<K, V>();
    }

    public MyDictionary(Hashtable<K,V> dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public V get(Object key) {
        return dictionary.get(key);
    }

    @Override
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

    @Override
    public V put(K key, V value) {
        return dictionary.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return dictionary.remove(key);
    }

    @Override
    public int size() {
        return dictionary.size();
    }

    @Override
    public void clear() {
        dictionary.clear();
    }

    @Override
    public Collection<V> getValues() {
        return dictionary.values();
    }

    @Override
    public MyIDictionary<K, V> deepcopy() {
        return new MyDictionary<K, V>((Hashtable<K, V>) dictionary.clone());
    }

    @Override
    public String toString() {
        return "Symbol Table: " + dictionary.toString();
    }
}
