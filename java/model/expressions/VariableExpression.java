package model.expressions;

import model.MyException;
import model.myADTs.MyIDictionary;
import model.myADTs.MyIHeap;
import model.types.IType;
import model.values.IValue;

public class VariableExpression implements IExpression{
    private String idOfVariable;

    public VariableExpression(String idOfVariable) {
        this.idOfVariable = idOfVariable;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyException {
        return table.get(idOfVariable);
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(idOfVariable);
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeOfEachVariable) throws MyException {
        return typeOfEachVariable.get(idOfVariable);
    }

    @Override
    public String toString() {
        return idOfVariable;
    }
}
