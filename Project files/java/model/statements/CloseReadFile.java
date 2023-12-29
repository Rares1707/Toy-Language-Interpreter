package model.statements;

import model.MyException;
import model.ProgramState;
import model.expressions.IExpression;
import model.myADTs.MyIDictionary;
import model.types.IType;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.IOException;

public class CloseReadFile implements IStatement {

    private IExpression expression;

    public CloseReadFile(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IValue evaluatedExpression = expression.evaluate(state.getSymbolTable(), state.getHeap());
        if (!evaluatedExpression.getType().equals(new StringType()))
            throw new MyException("File name is not a string");

        String fileName = ((StringValue) evaluatedExpression).getValue();
        if (state.getFileTable().get(fileName) == null)
            throw new MyException("The file does not  exists");

        try
        {
            state.getFileTable().get(fileName).close();
        }
        catch (IOException error)
        {
            throw new MyException(error.getMessage());
        }
        state.getFileTable().remove(fileName);

        return null;
}

    @Override
    public IStatement deepCopy() {
        return new CloseReadFile(expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeOfEachVariable) throws MyException {
        IType typeOfExpression = expression.typecheck(typeOfEachVariable);
        if (typeOfExpression.equals(new StringType()))
            return typeOfEachVariable;
        throw new MyException("close file statement: the name of the file is not a string");
    }

    @Override
    public String toString() {
        return "close(" + expression.toString() + ')';
    }
}
