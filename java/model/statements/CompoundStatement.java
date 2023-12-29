package model.statements;

import model.MyException;
import model.ProgramState;
import model.myADTs.MyIDictionary;
import model.myADTs.MyIStack;
import model.types.IType;

public class CompoundStatement implements IStatement{
    private IStatement firstStatement;
    private IStatement secondStatement;

    public CompoundStatement(IStatement firstStatement, IStatement secondStatement) {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }


    public String toString() {
        return "(" + firstStatement.toString() + ";" + secondStatement.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack=state.getStack();
        stack.push(secondStatement);
        stack.push(firstStatement);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(firstStatement.deepCopy(), secondStatement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeOfEachVariable) throws MyException {
        return secondStatement.typecheck(firstStatement.typecheck(typeOfEachVariable));
    }
}
