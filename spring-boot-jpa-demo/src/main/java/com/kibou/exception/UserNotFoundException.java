package com.kibou.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2853659883593106718L;
	
	public UserNotFoundException(){
		super();
	}
	
	public UserNotFoundException(String message){
		super(message);
	}
	
/*	public UserNotFoundException(String message,Throwable cause){
		super(message, cause);
	}*/
}
