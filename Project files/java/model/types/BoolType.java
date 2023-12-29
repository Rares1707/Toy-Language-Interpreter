package model.types;

import model.values.BoolValue;
import model.values.IValue;

public class BoolType implements IType {

    public static final boolean DEFAULT_VALUE = false;

    public boolean equals(Object another){
        return (another instanceof BoolType);
    }

    public String toString() { return "bool"; }

    @Override
    public IType deepCopy() {
        return new BoolType();
    }

    @Override
    public IValue getDefaultValue() {
        return new BoolValue(DEFAULT_VALUE);
    }
}
