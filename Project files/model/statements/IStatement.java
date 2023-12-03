package model.statements;

import model.MyException;
import model.ProgramState;

public interface IStatement {

        ProgramState execute(ProgramState state) throws MyException;

        IStatement deepCopy();

}
