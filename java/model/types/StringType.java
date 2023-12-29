package model.types;

import model.values.IValue;
import model.values.StringValue;

public class StringType implements IType{
    public static final String DEFAULT_VALUE = "";

    @Override
    public IType deepCopy() {
        return new StringType();
    }

    @Override
    public IValue getDefaultValue() {
        return new StringValue(DEFAULT_VALUE);
    }

    @Override
    public String toString() {
        return "string";
    }

    public boolean equals(Object another){
        return (another instanceof StringType);
    }
}
