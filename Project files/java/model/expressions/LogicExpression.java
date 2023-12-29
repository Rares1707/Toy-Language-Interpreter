package model.expressions;

import model.MyException;
import model.myADTs.MyIDictionary;
import model.myADTs.MyIHeap;
import model.types.BoolType;
import model.types.IType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;


public class LogicExpression implements IExpression{
    private IExpression firstExpression;
    private IExpression secondExpression;
    int operator;

    public LogicExpression(IExpression firstExpression, IExpression secondExpression, int operator) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "LogicExpression{" +
                "firstExpression=" + firstExpression.toString() +
                ", secondExpression=" + secondExpression.toString() +
                ", operator=" + operator +
                '}';
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyException {
        IValue firstValue, secondValue;
        firstValue = firstExpression.evaluate(table, heap);
        secondValue = secondExpression.evaluate(table, heap);

        if (operator != '|' && operator != '&')
        {
            throw new MyException("logic operator not supported");
        }

        if (firstValue.getType().equals(secondValue.getType()) && firstValue.getType().equals(new BoolType()))
        {
            BoolValue firstValueAsBool = (BoolValue) firstValue;
            BoolValue secondValueAsBool = (BoolValue) secondValue;
            if (operator == '&')
            {
                return new BoolValue(firstValueAsBool.getValue() && secondValueAsBool.getValue());
            }
            if (operator == '|')
            {
                return new BoolValue(firstValueAsBool.getValue() || secondValueAsBool.getValue());
            }
        }
        else
        {
            throw new MyException("Both operands must be of boolean type");
        }
        return null;
    }

    @Override
    public IExpression deepCopy() {
        return new LogicExpression(firstExpression.deepCopy(), secondExpression.deepCopy(), operator);
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeOfEachVariable) throws MyException {
        IType typeOfFirstExpression = firstExpression.typecheck(typeOfEachVariable);
        IType typeOfSecondExpression = secondExpression.typecheck(typeOfEachVariable);

        if (!typeOfFirstExpression.equals(new BoolType()))
            throw new MyException("first operand is not a boolean");
        if (!typeOfSecondExpression.equals(new BoolType()))
            throw new MyException("second operand is not a boolean");

        return new BoolType();
    }
}
