package model.statements;

import model.MyException;
import model.ProgramState;
import model.expressions.IExpression;
import model.myADTs.MyIDictionary;
import model.myADTs.MyIStack;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;

import java.util.Stack;

public class WhileStatement implements IStatement{
    IExpression condition;
    IStatement statementToBeExecuted;

    public WhileStatement(IExpression condition, IStatement statementToBeExecuted) {
        this.condition = condition;
        this.statementToBeExecuted = statementToBeExecuted;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (!(condition.evaluate(state.getSymbolTable(), state.getHeap()) instanceof BoolValue))
            throw new MyException("condition was not evaluated to a bool value");

        if (((BoolValue) condition.evaluate(state.getSymbolTable(), state.getHeap())).getValue())
        {
            MyIStack<IStatement> executionStack = state.getStack();
            executionStack.push(this.deepCopy());
            executionStack.push(statementToBeExecuted.deepCopy());
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(condition.deepCopy(), statementToBeExecuted.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeOfEachVariable) throws MyException {
        IType typeOfExpression = condition.typecheck(typeOfEachVariable);
        if (!typeOfExpression.equals(new BoolType()))
            throw new MyException("The condition of while is not a boolean");

        statementToBeExecuted.typecheck(typeOfEachVariable.deepcopy());

        return typeOfEachVariable;
    }

    @Override
    public String toString() {
        return "while(" + condition.toString() + ") " +
                statementToBeExecuted.toString();
    }
}
