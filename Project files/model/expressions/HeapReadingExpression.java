package model.expressions;

import model.MyException;
import model.myADTs.MyIDictionary;
import model.myADTs.MyIHeap;
import model.values.IValue;
import model.values.ReferenceValue;

public class HeapReadingExpression implements IExpression{
    IExpression expression;

    public HeapReadingExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyException {
        if (!(expression.evaluate(table, heap) instanceof ReferenceValue))
            throw new MyException("the expression was not evaluated to a reference value");

        int addressOfReference = ((ReferenceValue) expression.evaluate(table, heap)).getAddress();
        if (heap.get(addressOfReference) == null)
            throw new MyException("invalid address");
        
        return heap.get(addressOfReference);
    }

    @Override
    public IExpression deepCopy() {
        return new HeapReadingExpression(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "readHeap(" + expression.toString() +')';
    }
}
