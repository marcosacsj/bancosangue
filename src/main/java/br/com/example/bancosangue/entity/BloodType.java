package br.com.example.bancosangue.entity;

public enum BloodType {
    A("A"), B("B"), AB("AB"), O("O");

    private final String value;

    BloodType(String value) {
        this.value = value;
    }

    public static BloodType fromValue(String value) {
        for (BloodType bloodType : BloodType.values()) {
            if (bloodType.value.equalsIgnoreCase(value)) {
                return bloodType;
            }
        }
        throw new IllegalArgumentException("Invalid blood type: " + value);
    }

    public BloodType invert() {
        switch (this) {
            case A:
                return B;
            case B:
                return A;
            case AB:
                return O;
            case O:
                return AB;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    @Override
    public String toString() {
        return value;
    }
}

