package model.values;



import model.types.*;

public class IntValue implements IValue {
    private int value;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IntValue))
            return false;
        return value == ((IntValue) obj).getValue();
    }

    public IntValue(int value) {
        this.value = value;
    }
    public IntValue(){value = IntType.DEFAULT_VALUE;}
    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(value);
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }

}
