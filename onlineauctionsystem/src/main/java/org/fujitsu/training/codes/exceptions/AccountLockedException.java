package org.fujitsu.training.codes.exceptions;

public class AccountLockedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AccountLockedException(String message) {
        super(message);
    }
}
