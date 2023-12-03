package model.expressions;

import model.MyException;
import model.myADTs.MyIDictionary;
import model.myADTs.MyIHeap;
import model.types.IntType;
import model.values.IntValue;
import model.values.IValue;

public class ArithmeticExpression implements IExpression{
    private IExpression firstExpression;
    private IExpression secondExpression;
    private int operator;

    public ArithmeticExpression(int operator, IExpression firstExpression, IExpression secondExpression) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }

    public IValue evaluate(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyException {
        if (operator != '*' && operator != '+' && operator != '/' && operator != '-')
            throw new MyException("arithmetic operator is not supported");

        IValue firstValue,secondValue;
        firstValue = firstExpression.evaluate(table, heap);
        if (firstValue.getType().equals(new IntType())) {
            secondValue = secondExpression.evaluate(table, heap);
            if (secondValue.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) firstValue;
                IntValue i2 = (IntValue)secondValue;
                int n1,n2;
                n1= i1.getValue();
                n2 = i2.getValue();
                if (operator=='+') return new IntValue(n1+n2);
                if (operator =='-') return new IntValue(n1-n2);
                if(operator=='*') return new IntValue(n1*n2);
                if(operator=='/')
                    if(n2==0) throw new MyException("division by zero");
                    else return new IntValue(n1/n2);
            }else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
        return firstValue;
    }

    @Override
    public IExpression deepCopy() {
        return new ArithmeticExpression(operator, firstExpression.deepCopy(), secondExpression.deepCopy());
    }

    @Override
    public String toString() {
        return firstExpression.toString() + (char) operator + secondExpression.toString();
    }
}
