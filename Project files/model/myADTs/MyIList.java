package model.myADTs;

public interface MyIList<T> {
    boolean add(T element);
    void add(int index, T element);
    void clear();
    String toString();
    boolean isEmpty();
}
