package model.expressions;

import model.MyException;
import model.myADTs.MyIDictionary;
import model.myADTs.MyIHeap;
import model.types.IType;
import model.values.IValue;

public class ValueExpression implements IExpression{
    private IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyException {
        return value;
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(value.deepCopy());
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeOfEachVariable) throws MyException {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
