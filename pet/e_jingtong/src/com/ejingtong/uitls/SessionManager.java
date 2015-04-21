package com.ejingtong.uitls;

import org.apache.mina.core.session.IoSession;

public class SessionManager {
	private static SessionManager instance;
	
	private IoSession session;
	public static SessionManager getInstance(){
		if(instance==null){
			synchronized (SessionManager.class) {
				instance = new SessionManager();
			}
		}
		return instance;
	}
	
	private SessionManager() {
		
		
	}
	
	
	public void putSession(IoSession session){
		this.session = session;
	}
	
	public void removeSession(){
		this.session = null;
	}
	
	public IoSession getCurrentSession(){
		return this.session;
	}
}
