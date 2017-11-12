package com.sneakerate.marketing.domain;

public enum Gender {
    All("All"),
    Men("Men"),
    Women("Women");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
