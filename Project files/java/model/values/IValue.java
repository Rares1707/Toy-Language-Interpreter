package model.values;


import model.expressions.IExpression;
import model.types.IType;

public interface IValue {
    IType getType();

    IValue deepCopy();
}
