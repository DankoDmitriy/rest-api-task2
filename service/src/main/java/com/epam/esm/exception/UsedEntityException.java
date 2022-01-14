package com.epam.esm.exception;

public class UsedEntityException extends RuntimeException {
    private final Long id;

    public UsedEntityException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
