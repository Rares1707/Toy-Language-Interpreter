package model.types;

import model.values.IValue;
import model.values.IntValue;

public class IntType implements IType {

    public static final int DEFAULT_VALUE = 0;

    public boolean equals(Object another){
        return (another instanceof IntType);
    }

    public String toString() { return "int";}

    @Override
    public IType deepCopy() {
        return new IntType();
    }

    @Override
    public IValue getDefaultValue() {
        return new IntValue(DEFAULT_VALUE);
    }
}
