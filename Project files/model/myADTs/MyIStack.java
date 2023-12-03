package model.myADTs;

public interface MyIStack <T>{
    T pop();
    void push(T element);
    int size();
    boolean isEmpty();
    void clear();
    String toString();
}
