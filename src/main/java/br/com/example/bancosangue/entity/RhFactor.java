package br.com.example.bancosangue.entity;

public enum RhFactor {
    POSITIVE("+"), NEGATIVE("-");

    private final String value;

    RhFactor(String value) {
        this.value = value;
    }

    public static RhFactor fromValue(String value) {
        for (RhFactor rhFactor : RhFactor.values()) {
            if (rhFactor.value.equalsIgnoreCase(value)) {
                return rhFactor;
            }
        }
        throw new IllegalArgumentException("Invalid Rh factor: " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}

