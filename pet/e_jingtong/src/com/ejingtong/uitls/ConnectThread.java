package com.ejingtong.uitls;

import com.ejingtong.common.Constans;
import com.ejingtong.push.ClientStart;

public class ConnectThread extends Thread{
	
//	private static ConnectThread instance;
//	  public static ConnectThread getInstance(){
//		  synchronized (ConnectThread.class) {
//			if(instance==null){
//				instance = new ConnectThread();
//			}
//		}
//		  return instance;
//	  }
//	  
//	 private ConnectThread(){
//		  
//	  }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		synchronized (Constans.CONNECT_LOCK) {
			System.out.println("#####重连线程启动.....");
			if(SessionManager.getInstance().getCurrentSession()==null||!SessionManager.getInstance().getCurrentSession().isConnected()){
				ClientStart.getInstance().disConnect();
				ClientStart.getInstance().pushRegist();
			}
			
		}
		
	}
}
