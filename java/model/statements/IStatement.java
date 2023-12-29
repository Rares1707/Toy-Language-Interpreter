package model.statements;

import model.MyException;
import model.ProgramState;
import model.myADTs.MyIDictionary;
import model.types.IType;

import java.lang.reflect.Type;

public interface IStatement {

        ProgramState execute(ProgramState state) throws MyException;

        IStatement deepCopy();

        MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeOfEachVariable) throws MyException;

}
