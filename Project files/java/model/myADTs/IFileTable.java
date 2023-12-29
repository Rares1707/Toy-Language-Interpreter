package model.myADTs;

import java.io.BufferedReader;
import java.util.Set;

public interface IFileTable {
    BufferedReader get(String key);
    boolean	isEmpty();
    BufferedReader put(String key, BufferedReader value);
    BufferedReader remove(String key);
    void clear();

    Set<String> keySet();
}
