package com.ecs.enums;

public enum ClientVendorType {

    VENDOR("Vendor"),CLIENT("Client");

    private final String value;

    ClientVendorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
