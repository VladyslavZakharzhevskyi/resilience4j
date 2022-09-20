package com.dataart.idle.resilience4j.model;

import java.util.UUID;

public class DataTransferResponse {

    private final UUID id = UUID.randomUUID();
    private String supplier;
    private byte[] content;
    private Type type;

    public DataTransferResponse(String supplier, byte[] content, Type type) {
        this.supplier = supplier;
        this.content = content;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public String getSupplier() {
        return supplier;
    }

    public byte[] getContent() {
        return content;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        TEXT, IMAGE, RESOURCE
    }

}
