package com.automation.core.exceptions;


public class MailSendingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private String message = null;
	 
	    public MailSendingException() {
	        super();
	    }
	 
	    public MailSendingException(String message) {
	        super(message);
	        this.message = message;
	    }
	 
	    public MailSendingException(Throwable cause) {
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
