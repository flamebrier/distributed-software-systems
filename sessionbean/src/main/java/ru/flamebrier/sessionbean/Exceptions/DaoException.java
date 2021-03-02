package ru.flamebrier.sessionbean.Exceptions;

public class DaoException extends Exception {

	private static final long serialVersionUID = -2715666229998776465L;
	
	public DaoException(String message) {
		super(message);
	}
	
	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}
}