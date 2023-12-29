package model.statements;

import model.MyException;
import model.ProgramState;
import model.myADTs.MyIDictionary;
import model.types.IType;
import model.values.BoolValue;
import model.values.IntValue;

import java.util.Objects;

public class VariableDeclarationStatement implements IStatement{
    private String variableName;
    private IType variableType;

    public VariableDeclarationStatement(String variableName, IType variableType) {
        this.variableName = variableName;
        this.variableType = variableType;
    }

    @Override
    public String toString() {
        return variableType.toString() + ' ' + variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (state.getSymbolTable().get(variableName) != null)
        {
            throw new MyException("variable is already declared");
        }
        else
        {
            state.getSymbolTable().put(variableName, variableType.getDefaultValue());
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclarationStatement(variableName, variableType.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeOfEachVariable) throws MyException {
        typeOfEachVariable.put(variableName, variableType);
        return typeOfEachVariable;
    }


}
