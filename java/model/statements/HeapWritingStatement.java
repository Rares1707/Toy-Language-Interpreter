package model.statements;

import model.MyException;
import model.ProgramState;
import model.expressions.IExpression;
import model.myADTs.MyIDictionary;
import model.types.IType;
import model.types.ReferenceType;
import model.values.ReferenceValue;

public class HeapWritingStatement implements IStatement{
    String variableName;
    IExpression newValue;

    public HeapWritingStatement(String variableName, IExpression newValue) {
        this.variableName = variableName;
        this.newValue = newValue;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (state.getSymbolTable().get(variableName) == null)
            throw new MyException("variable does not exist");
        if (!(state.getSymbolTable().get(variableName).getType() instanceof ReferenceType))
            throw new MyException("variable is not of reference type");

        int addressOfVariable = ((ReferenceValue) state.getSymbolTable().get(variableName)).getAddress();
        if (state.getHeap().get(addressOfVariable) == null)
            throw new MyException("the address of the variable is not a key in the heap");

        IType locationTypeOfVariable = ((ReferenceValue) state.getSymbolTable().get(variableName)).getLocationType();
        IType typeOfNewValue = newValue.evaluate(state.getSymbolTable(), state.getHeap()).getType();

        if (!typeOfNewValue.equals(locationTypeOfVariable))
            throw new MyException("The heap location and the new value must be of the same type");

        state.getHeap().changeValue(addressOfVariable,
                newValue.evaluate(state.getSymbolTable(), state.getHeap()));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapWritingStatement(variableName, newValue.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeOfEachVariable) throws MyException {
        IType typeOfExpression = newValue.typecheck(typeOfEachVariable);
        IType typeOfVariable = typeOfEachVariable.get(variableName);

        if (typeOfVariable.equals(new ReferenceType(typeOfExpression)))
            return typeOfEachVariable;

        throw new MyException("heap writing statement: right hand side and " +
                "left hand side have different types");
    }

    @Override
    public String toString() {
        return "writeHeap(" + variableName + ", " + newValue.toString() + ")";
    }
}
