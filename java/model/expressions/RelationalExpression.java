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

public class RelationalExpression implements IExpression{
    String operator;
    IExpression firstExpression;
    IExpression secondExpression;

    public RelationalExpression(String operator, IExpression firstExpression, IExpression secondExpression) {
        this.operator = operator;
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyException {

        IValue firstValue = firstExpression.evaluate(table, heap);
        IValue secondValue = secondExpression.evaluate(table, heap);
        if (firstValue.getType().equals(secondValue.getType()) && firstValue.getType().equals(new IntType()))
        {
            int firstValueAsInt = ((IntValue) firstValue).getValue();
            int secondValueAsInt = ((IntValue) secondValue).getValue();
            if (operator.equals("=="))
                return new BoolValue(firstValueAsInt==secondValueAsInt);
            if (operator.equals("!="))
                return new BoolValue(firstValueAsInt!=secondValueAsInt);
            if (operator.equals("<="))
                return new BoolValue(firstValueAsInt<=secondValueAsInt);
            if (operator.equals("<"))
                return new BoolValue(firstValueAsInt<secondValueAsInt);
            if (operator.equals(">"))
                return new BoolValue(firstValueAsInt>secondValueAsInt);
            if (operator.equals(">="))
                return new BoolValue(firstValueAsInt>=secondValueAsInt);
            throw new MyException("operator not supported");
        }
        else
        {
            throw new MyException("both operands should be of type int");
        }
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(operator, firstExpression.deepCopy(), secondExpression.deepCopy());
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeOfEachVariable) throws MyException {
        IType typeOfFirstExpression = firstExpression.typecheck(typeOfEachVariable);
        IType typeOfSecondExpression = secondExpression.typecheck(typeOfEachVariable);

        if (!typeOfFirstExpression.equals(new IntType()))
            throw new MyException("first operand is not an integer");
        if (!typeOfSecondExpression.equals(new IntType()))
            throw new MyException("second operand is not an integer");

        return new BoolType();
    }

    @Override
    public String toString() {
        return firstExpression.toString() + operator + secondExpression.toString();
    }
}
