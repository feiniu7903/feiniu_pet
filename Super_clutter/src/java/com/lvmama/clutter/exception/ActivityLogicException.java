package com.lvmama.clutter.exception;

public class ActivityLogicException extends RuntimeException{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7933193637300835548L;

	
	public ActivityLogicException(String msg){
		super(msg);
	}
	public ActivityLogicException(String arg0,Throwable arg1) {
		super(arg0, arg1);
	}
}
