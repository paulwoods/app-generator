package org.mrpaulwoods.sample1.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UUID id) {
        super("The user was not found: " + id);
    }

}
