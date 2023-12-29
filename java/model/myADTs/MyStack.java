package model.myADTs;
import java.util.*;
public class MyStack<T> implements MyIStack<T>{
    private Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<T>();
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T element) {
        stack.push(element);
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public String toString()
    {
        //return stack.stream().map(Objects::toString).reduce((acc, res) -> res + acc + "\n").orElse("\n");
        return "Execution Stack: " + stack.toString();
    }

    @Override
    public List<String> toListOfStrings() {
        return stack.stream().map(Object::toString).toList();
    }
}
