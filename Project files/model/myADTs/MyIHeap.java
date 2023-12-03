package model.myADTs;

import model.values.IValue;

import java.io.BufferedReader;
import java.util.Map;
import java.util.Set;

public interface MyIHeap {
    IValue get(int key);
    boolean	isEmpty();
    IValue put(IValue value);

    IValue changeValue(int address, IValue newValue);
    IValue remove(int key);

    int getLastAddressGenerated();

    void setContent(Map<Integer, IValue>  newContent);

    Map<Integer, IValue> getContent();

    void clear();
}
