package model.myADTs;

import java.util.ArrayList;
import java.util.List;

public class MyList <T> implements MyIList<T>{

    private List<T> list;

    public MyList() {
        list = new ArrayList<>();
    }

    @Override
    public boolean add(T element) {
        return list.add(element);
    }

    @Override
    public void add(int index, T element) {
        list.add(index, element);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public List<String> getContentAsListOfStrings() {
        List<String> listOfStrings = new ArrayList<>();
        for (T element : list)
            listOfStrings.add(element.toString());
        return listOfStrings;
    }

    @Override
    public String toString() {
        return "Output: " + list.toString();
    }
}
