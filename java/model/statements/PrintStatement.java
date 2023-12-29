package model.statements;

import model.MyException;
import model.ProgramState;
import model.expressions.IExpression;
import model.myADTs.MyIDictionary;
import model.myADTs.MyIHeap;
import model.types.IType;


public class PrintStatement implements IStatement{
    private IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIHeap heap = state.getHeap();
        state.getOutputList().add(expression.evaluate(state.getSymbolTable(), heap));
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeOfEachVariable) throws MyException {
        expression.typecheck(typeOfEachVariable);
        return typeOfEachVariable;
    }
}
