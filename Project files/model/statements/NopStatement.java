package model.statements;

import model.MyException;
import model.ProgramState;

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
    public String toString() {
        return "nop";
    }
}
