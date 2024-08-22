package com.ecs.enums;

public enum InvoiceStatus {
    AWAITING_APPROVAL("Awaiting Approval"),APPROVED("Approved");

    private final String value;

    InvoiceStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
