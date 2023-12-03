package model.statements;

import model.MyException;
import model.ProgramState;
import model.expressions.IExpression;
import model.myADTs.MyIHeap;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.*;

public class OpenReadFile implements IStatement {

    private IExpression expression;

    public OpenReadFile(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIHeap heap = state.getHeap();
        IValue evaluatedExpression = expression.evaluate(state.getSymbolTable(), heap);
        if (evaluatedExpression.getType().equals(new StringType())) {
            String fileName = ((StringValue) evaluatedExpression).getValue();
            if (state.getFileTable().get(fileName) == null) {
                try {
                    BufferedReader fileDescriptor = new BufferedReader(new FileReader(fileName));
                    state.getFileTable().put(fileName, fileDescriptor);
                } catch (IOException error) {
                    throw new MyException(error.getMessage());
                }
            } else {
                throw new MyException("The file already exists");
            }
        } else {
            throw new MyException("File name is not a string");
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenReadFile(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "openReadFile(" + expression.toString() + ")";
    }
}
