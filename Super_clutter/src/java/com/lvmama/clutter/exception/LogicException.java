package com.lvmama.clutter.exception;

public class LogicException extends RuntimeException{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7933193637300835548L;

	
	public LogicException(String msg){
		super(msg);
	}
	public LogicException(String arg0,Throwable arg1) {
		super(arg0, arg1);
	}
}
