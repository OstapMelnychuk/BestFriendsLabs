package com.savchuk.secondlab.entity;

public enum EntityStatus {
    ACTIVE(true),
    NONACTIVE(false);
    private boolean status;
    EntityStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }
}
