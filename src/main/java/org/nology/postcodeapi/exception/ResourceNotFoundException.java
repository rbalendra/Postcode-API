package org.nology.postcodeapi.exception;


//      ╭──────────────────────────────────────────────────────────╮
//      │  Thrown when a requested resource (suburb/postcode)      │
//      │  cannot be found.                                        │
//      ╰──────────────────────────────────────────────────────────╯

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
