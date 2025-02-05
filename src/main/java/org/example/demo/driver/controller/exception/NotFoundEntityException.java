package org.example.demo.driver.controller.exception;

/**
 * Custom exception for representing a not found entity.
 * Extends {@link NullPointerException}.
 */
public class NotFoundEntityException extends NullPointerException {

    /**
     * Constructs a NotFoundEntityException with the specified entity and ID.
     *
     * @param entity The entity name.
     * @param id     The ID of the entity.
     */
    public NotFoundEntityException(String entity, String id) {
        super(String.format("Not found %s with id %s", entity, id));
    }

}

