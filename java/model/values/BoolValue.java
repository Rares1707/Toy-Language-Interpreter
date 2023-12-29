package model.values;
import model.types.*;

public class BoolValue implements IValue {

    private boolean value;

    public boolean equals(Object obj) {
        if (!(obj instanceof BoolValue))
            return false;
        return value == ((BoolValue) obj).getValue();
    }

    public BoolValue(boolean value) {
        this.value = value;
    }
    public BoolValue(){value = BoolType.DEFAULT_VALUE;}

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public IValue deepCopy() {
        return new BoolValue(value);
    }

    public boolean getValue() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
