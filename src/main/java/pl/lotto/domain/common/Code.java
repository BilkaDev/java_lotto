package pl.lotto.domain.common;

import lombok.Getter;

/**
 * Enum representing various operation codes.
 */
@Getter
public enum Code {
    SUCCESS("Operation end success"),
    PERMIT("Access granted"),
    DENY("Access denied"),
    A1("The specified user with the given name does not exist"),
    A2("The specified data is invalid"),
    A3("The indicated token is empty or not valid"),
    A4("The user with the specified name does not exist"),
    A5("The user with the specified email already exists"),
    A6("Incorrect credentials");
    private final String label;

    Code(final String label) {
        this.label = label;
    }
}
