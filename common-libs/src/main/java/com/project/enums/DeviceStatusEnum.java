package com.project.enums;

public enum DeviceStatusEnum {
    PREPARE("PREPARE"),
    MAINTENANCE("MAINTENANCE");

    private final String status;

    DeviceStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
