package model.types;

import model.values.IValue;
import model.values.ReferenceValue;

public class ReferenceType implements IType{
    private IType typeOfValueToWhichIPoint;

    public ReferenceType(IType typeOfValueToWhichIPoint) {
        this.typeOfValueToWhichIPoint = typeOfValueToWhichIPoint;
    }
    public IType getTypeOfValueToWhichIPoint() {
        return typeOfValueToWhichIPoint;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReferenceType)
            return  (typeOfValueToWhichIPoint.equals(((ReferenceType) obj).getTypeOfValueToWhichIPoint()));
        return false;
    }
    @Override
    public String toString() {
        return "Ref " + typeOfValueToWhichIPoint.toString();
    }
    @Override
    public IType deepCopy() {
        return new ReferenceType(typeOfValueToWhichIPoint.deepCopy());
    }
    @Override
    public IValue getDefaultValue() {
        return new ReferenceValue(0, typeOfValueToWhichIPoint);
    }
}
