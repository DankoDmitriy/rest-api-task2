package com.epam.esm.model.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;


public class AuditListener {
    private final static String INSERT = "INSERT";
    private final static String UPDATE = "UPDATE";
    private final static String DELETE = "DELETE";

    private static Logger logger = LogManager.getLogger();

    @PrePersist
    public void onPrePersist(Object entity) {
        audit(INSERT, entity);
    }

    @PreUpdate
    public void onPreUpdate(Object entity) {
        audit(UPDATE, entity);
    }

    @PreRemove
    public void onPreRemove(Object entity) {
        audit(DELETE, entity);
    }

    private void audit(String operation, Object entity) {
        logger.info("{} : {}", operation, entity);
    }
}
