package model.statements;

import model.MyException;
import model.ProgramState;
import model.expressions.IExpression;
import model.myADTs.MyIHeap;
import model.types.BoolType;
import model.types.IType;
import model.types.ReferenceType;
import model.values.ReferenceValue;

public class HeapAllocationStatement implements IStatement{
    private String variableName;
    private IExpression expression;

    public HeapAllocationStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (state.getSymbolTable().get(variableName) == null)
            throw new MyException("variable not defined");

        if (!(state.getSymbolTable().get(variableName) instanceof ReferenceValue))
            throw new MyException("variable is not of reference type");

        MyIHeap heap = state.getHeap();
        IType typeOfTheExpression = expression.evaluate(state.getSymbolTable(), heap).getType();
        IType typeOfVariableToWhichTheReferenceIsPointing =
                ((ReferenceValue) state.getSymbolTable().get(variableName)).getLocationType();
        if (!typeOfTheExpression.equals(typeOfVariableToWhichTheReferenceIsPointing))
            throw new MyException("the type of the expression does not match the type of the reference");

        heap.put(expression.evaluate(state.getSymbolTable(), heap));

        state.getSymbolTable().put(variableName, new ReferenceValue(
                heap.getLastAddressGenerated(), expression.evaluate(state.getSymbolTable(), heap).getType()));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocationStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "new(" + variableName + ", " + expression.toString() + ')';
    }

}
