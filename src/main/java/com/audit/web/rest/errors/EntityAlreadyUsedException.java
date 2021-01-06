package com.audit.web.rest.errors;

public class EntityAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public EntityAlreadyUsedException() {
        super(ErrorConstants.ENTITY_ALREADY_USED_TYPE, "Entity already used!", "userManagement", "entityAlreadyUsed");
    }
}
