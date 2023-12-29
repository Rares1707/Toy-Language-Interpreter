package model.expressions;

import model.MyException;
import model.myADTs.MyIDictionary;
import model.myADTs.MyIHeap;
import model.types.IType;
import model.values.IValue;

public interface IExpression {
    IValue evaluate(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyException;

    IExpression deepCopy();

    IType typecheck(MyIDictionary<String, IType> typeOfEachVariable) throws MyException;
}