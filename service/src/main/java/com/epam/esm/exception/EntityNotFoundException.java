package com.epam.esm.exception;

public class EntityNotFoundException extends RuntimeException {
    private Long id;

    public EntityNotFoundException(String message, Long id) {
        super(message);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
