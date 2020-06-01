package com.savchuk.thirdLab.entity;

public enum EntityStatus {
    ACTIVE("ACTIVE"),
    NONACTIVE("NONACTIVE"),
    ERROR("ERROR");
    private String status;

    EntityStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
