/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.navigation.page;

/**
 * Exception thrown when a URL resolver cannot resolve an URL.
 */
public class PageNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7174830500551320118L;

	/**
	 * Constructs a new page not found exception with {@code null} as its detailed message. The cause is not
	 * initialized, and may subsequently be initialized by a call to {@link #initCause}.
	 */
	public PageNotFoundException() {
		super();
	}

	/**
	 * Constructs a new page not found exception with the specified detail message. The cause is not initialized, and
	 * may subsequently be initialized by a call to {@link #initCause}.
	 *
	 * @param message
	 *            the detail message. The detail message is saved for later retrieval by the {@link #getMessage()}
	 *            method.
	 */
	public PageNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructs a new page not found exception with the specified detail message and cause.
	 * <p>
	 * Note that the detailed message associated with {@code cause} is <i>not</i> automatically incorporated in this
	 * exception's detailed message.
	 *
	 * @param message
	 *            the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public PageNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new page not found exception with the specified cause and a detail message of
	 * <tt>(cause==null ? null : cause.toString())</tt> (which typically contains the class and detail message of
	 * <tt>cause</tt>). This constructor is useful for exceptions that are little more than wrappers for other
	 * throwables.
	 *
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public PageNotFoundException(Throwable cause) {
		super(cause);
	}

}
