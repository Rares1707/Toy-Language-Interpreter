package model.statements;

import model.MyException;
import model.ProgramState;
import model.expressions.IExpression;
import model.myADTs.MyIDictionary;
import model.myADTs.MyIHeap;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;


public class ReadNumberFromFile implements IStatement{
    private IExpression fileNameExpression;
    private String variableName;

    public ReadNumberFromFile(IExpression fileNameExpression, String variableName) {
        this.fileNameExpression = fileNameExpression;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (state.getSymbolTable().get(variableName) == null)
            throw new MyException("variable is not declared");
        if (!state.getSymbolTable().get(variableName).getType().equals(new IntType()))
            throw new MyException(("variable is not of type int"));

        MyIHeap heap = state.getHeap();
        IValue fileNameValue = fileNameExpression.evaluate(state.getSymbolTable(), heap);
        if (!fileNameValue.getType().equals(new StringType()))
            throw new MyException("file name is not a string");

        String fileNameAsString = ((StringValue) fileNameValue).getValue();

        BufferedReader fileDescriptor = state.getFileTable().get(fileNameAsString);
        if (fileDescriptor == null)
            throw new MyException("file doesn't exist in the file table");

        String line;
        try
        {
            line = fileDescriptor.readLine();
        }
        catch (IOException error)
        {
            throw new MyException(error.getMessage());
        }

        IntValue readValue;
        if (line == null)
        {
            readValue = new IntValue();
        }
        else
        {
            readValue = new IntValue(Integer.parseInt(line));
        }
        state.getSymbolTable().put(variableName, readValue);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadNumberFromFile(fileNameExpression.deepCopy(), variableName);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeOfEachVariable) throws MyException {
        IType typeOfExpression = fileNameExpression.typecheck(typeOfEachVariable);
        IType typeOfVariable = typeOfEachVariable.get(variableName);
        if (!typeOfExpression.equals(new StringType()))
            throw new MyException("read number from file statement: the name of the file is not a string");
        if (!typeOfVariable.equals(new IntType()))
            throw new MyException("read number from file statement: the type of the variable is not int");

        return typeOfEachVariable;

    }

    @Override
    public String toString() {
        return "read(" + fileNameExpression.toString() + ", " + variableName + ')';
    }
}
