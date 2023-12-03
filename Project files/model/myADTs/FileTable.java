package model.myADTs;

import java.io.BufferedReader;
import java.util.Hashtable;

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
    public String toString() {
        //String fileTableAsString = "FileTable: {";
        //for (String key: fileTable.k)
        return  "FileTable: " + fileTable.keySet().toString();
    }
}
