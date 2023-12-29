package model.statements;

import model.MyException;
import model.ProgramState;
import model.expressions.IExpression;
import model.myADTs.MyIDictionary;
import model.myADTs.MyIHeap;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;

import java.util.Objects;

public class IfStatement implements IStatement{

    private IExpression expression;
    private IStatement thenStatement;
    private IStatement elseStatement;

    public IfStatement(IExpression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public String toString() {
        return "if (" + expression.toString() +
                ") then {" + thenStatement.toString() +
                "} else {" + elseStatement.toString() +
                '}';
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIHeap heap = state.getHeap();
        if (!expression.evaluate(state.getSymbolTable(), heap).getType().equals(new BoolType()))
        {
            throw new MyException("expression is not a boolean");
        }
        if ( ((BoolValue) expression.evaluate(state.getSymbolTable(), heap)).getValue() == true)
        {
            state.getStack().push(thenStatement);
        }
        else
        {
            state.getStack().push(elseStatement);
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeOfEachVariable) throws MyException {
        IType typeOfExpression = expression.typecheck(typeOfEachVariable);
        //System.out.println(typeOfExpression.toString() + '\n');
        if (!typeOfExpression.equals(new BoolType()))
            throw new MyException("The condition of IF is not a boolean");

        thenStatement.typecheck(typeOfEachVariable.deepcopy());
        elseStatement.typecheck(typeOfEachVariable.deepcopy());

        return typeOfEachVariable;
    }
}
