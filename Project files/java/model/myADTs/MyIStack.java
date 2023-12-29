package model.myADTs;

import java.util.List;

public interface MyIStack <T>{
    T pop();
    void push(T element);
    int size();
    boolean isEmpty();
    void clear();
    String toString();
    List<String> toListOfStrings();
}
