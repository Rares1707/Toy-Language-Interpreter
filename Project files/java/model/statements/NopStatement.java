package model.statements;

import model.MyException;
import model.ProgramState;
import model.myADTs.MyIDictionary;
import model.types.IType;

public class NopStatement implements IStatement{

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NopStatement();
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeOfEachVariable) throws MyException {
        return typeOfEachVariable;
    }

    @Override
    public String toString() {
        return "nop";
    }
}
