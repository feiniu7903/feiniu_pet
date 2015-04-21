package com.lvmama.passport.processor.impl.exception;

/**
 * 
 * 票种类型不存在
 * @author linkai
 *
 */
public class TicketTypeNonexistentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TicketTypeNonexistentException() {
		super();
	}

	public TicketTypeNonexistentException(String message, Throwable cause) {
		super(message, cause);
	}

	public TicketTypeNonexistentException(String message) {
		super(message);
	}

	public TicketTypeNonexistentException(Throwable cause) {
		super(cause);
	}
}
