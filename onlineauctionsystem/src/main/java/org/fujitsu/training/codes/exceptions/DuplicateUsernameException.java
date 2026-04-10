package org.fujitsu.training.codes.exceptions;

public class DuplicateUsernameException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateUsernameException(String message) {
        super(message);
    }
}
