package com.automation.core.exceptions;

/**
 * This Exception is generated when the folder is not properly zipped.
 * 
 */
public class FolderZipException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message = null;
	 
    public FolderZipException() {
        super();
    }
 
    public FolderZipException(String message) {
        super(message);
        this.message = message;
    }
 
    public FolderZipException(Throwable cause) {
        super(cause);
    }
 
    @Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }

}
