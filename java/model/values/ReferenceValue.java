package model.values;

import model.types.IType;
import model.types.ReferenceType;

public class ReferenceValue implements IValue{
    private int address;
    private IType locationType;

    public IType getLocationType(){ return  locationType; }

    public ReferenceValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public IType getType() {
        return new ReferenceType(locationType);
    }

    public int getAddress() {
        return address;
    }

    @Override
    public IValue deepCopy() {
        return new ReferenceValue(address, locationType.deepCopy());
    }

    @Override
    public String toString() {
        return "("+ address + ", " + locationType + ')';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReferenceValue)
            return address == ((ReferenceValue) obj).getAddress() &&
                    locationType == ((ReferenceValue) obj).locationType;
        return false;

    }
}
