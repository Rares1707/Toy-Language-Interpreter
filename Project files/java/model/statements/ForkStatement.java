package model.statements;

import model.MyException;
import model.ProgramState;
import model.myADTs.MyIDictionary;
import model.myADTs.MyIStack;
import model.myADTs.MyStack;
import model.types.BoolType;
import model.types.IType;

public class ForkStatement implements IStatement{

    IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return new ProgramState(new MyStack<>(), state.getSymbolTable().deepcopy(),
                state.getOutputList(), statement.deepCopy(),
                state.getFileTable(), state.getHeap());
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeOfEachVariable) throws MyException {
        statement.typecheck(typeOfEachVariable.deepcopy());
        return typeOfEachVariable;
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ") ";
    }
}
