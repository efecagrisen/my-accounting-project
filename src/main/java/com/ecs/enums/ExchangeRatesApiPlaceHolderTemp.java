package com.ecs.enums;

public enum ExchangeRatesApiPlaceHolderTemp {

    EURO( "1"),
    BRITISH_POUND("1"),
    CANADIAN_DOLLAR("1"),
    JAPANESE_YEN("1"),
    INDIAN_RUPEE("1");

    private final String value;

    ExchangeRatesApiPlaceHolderTemp(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
