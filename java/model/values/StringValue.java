package model.values;

import model.types.IType;
import model.types.StringType;

import java.util.Objects;

public class StringValue implements IValue{

    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    public StringValue(){value = StringType.DEFAULT_VALUE;}

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(value);
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue()
    {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StringValue))
            return false;
        return Objects.equals(value, ((StringValue) obj).getValue());
    }
}
