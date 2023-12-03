package model.myADTs;

import java.io.BufferedReader;

public interface IFileTable {
    BufferedReader get(String key);
    boolean	isEmpty();
    BufferedReader put(String key, BufferedReader value);
    BufferedReader remove(String key);
    void clear();
}
