package model.myADTs;

import java.io.BufferedReader;
import java.util.Hashtable;
import java.util.Set;

public class FileTable implements IFileTable{

    private Hashtable<String, BufferedReader> fileTable = new Hashtable<String, BufferedReader>();

    @Override
    public BufferedReader get(String key) {
        return fileTable.get(key);
    }

    @Override
    public boolean isEmpty() {
        return fileTable.isEmpty();
    }

    @Override
    public BufferedReader put(String key, BufferedReader value) {
        return fileTable.put(key, value);
    }

    @Override
    public BufferedReader remove(String key) {
        return fileTable.remove(key);
    }

    @Override
    public void clear() {
        fileTable.clear();
    }

    @Override
    public Set<String> keySet() {
        return fileTable.keySet();
    }

    @Override
    public String toString() {
        return  "FileTable: " + fileTable.keySet().toString();
    }


}
