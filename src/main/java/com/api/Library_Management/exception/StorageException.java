package com.api.Library_Management.exception;

public class StorageException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9186129083510469990L;

	public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Exception e) {
        super(message, e);
    }
}
