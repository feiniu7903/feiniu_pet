package com.lvmama.push.handler;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lvmama.comm.bee.service.ebkpush.IEbkPushService;
import com.lvmama.comm.pet.po.ebkpush.EbkPushMessage;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.MD5;
import com.lvmama.push.model.ClientCommand;
import com.lvmama.push.model.ClientSessionInfo;
import com.lvmama.push.model.SessionManager;
import com.lvmama.push.status.SessionStatuObserver;
import com.lvmama.push.util.ConstantPush;
import com.lvmama.push.util.ZipUtil2;

public class EbkPushIoHandlerAdapter implements  IoHandler{
	
	
	    private static final Logger LOGGER = LoggerFactory.getLogger(EbkPushIoHandlerAdapter.class);

	    public void sessionCreated(IoSession session) throws Exception {
	       	LOGGER.info("session created:" + session.toString()+"remote :"+session.getRemoteAddress());  
	       	//session.getConfig().setWriterIdleTime(10);
	    
	    }

	    public void sessionOpened(IoSession session) throws Exception {
	   
	    }

	    public void sessionClosed(IoSession session) throws Exception {
	    	String udid = (String)session.getAttribute("udid");
	    	String userId = (String)session.getAttribute("userId");
	    	ClientSessionInfo csi = SessionManager.getInstance().getSessions().get(udid);
	    	if(csi!=null){
	    		csi.setState(ConstantPush.CLIENT_SESSION_STATUS.OFFLINE.name());
	    	}
    		session.close(false);
 	        // session = null;
	    	 LOGGER.info("userId["+userId+"] session closed remote :"+session.getRemoteAddress());
	    }

	    
	    public void sessionIdle(IoSession session, IdleStatus status)
	            throws Exception {
	    	 String udid = (String) session.getAttribute("udid");
	    	/**
	    	 * 由于客户端服务端心跳机制是被动型，
	    	 * 所以客户端在空闲的时候发送数据包来检测客户端是否在线
	    	 * 
	    	 * 
	    	 */
	    	 
	 		//session.write(">");
	
	    	 if(session.isConnected()){
	    		 LOGGER.info("session get udid:"+udid+" remote :"+session.getRemoteAddress());
		    	 ClientSessionInfo csi = SessionManager.getInstance().getSessions().get(udid);
		    	 if(csi!=null){
		    		 csi.setState(ConstantPush.CLIENT_SESSION_STATUS.FREE.name());
		    	 }  
	    	 } 

	    }

	    public void exceptionCaught(IoSession session, Throwable cause)
	            throws Exception {
//	        if (LOGGER.isWarnEnabled()) {
////	            LOGGER.warn("EXCEPTION, please implement "
////	                    + getClass().getName()
////	                    + ".exceptionCaught() for proper handling:", cause);
//	       
//	        }
	     	LOGGER.info("exceptionCaught:"+cause);
	        String udid = (String)session.getAttribute("udid");
	        LOGGER.info("udid:"+udid);
	    	String userId = (String)session.getAttribute("userId");
	    	
	    	ClientSessionInfo csi = SessionManager.getInstance().getSessions().get(udid);
	    	if(csi!=null){
	    		csi.setState(ConstantPush.CLIENT_SESSION_STATUS.OFFLINE.name());
	    	}
	    	LOGGER.info("userId["+userId+"] session exceptionCaught remote :"+session.getRemoteAddress());
	         // 关闭当前连接。如果参数 immediately为 true的话，连接会等到队列中所有的数据发送请求都完成之后才关闭；否则的话就立即关闭。
	        session.close(false);
	    }

	    public void messageReceived(IoSession session, Object message)
	            throws Exception {
            String baowenSrc = message.toString();// 原始报文
            
            LOGGER.info("recieved msg:"+baowenSrc);
            
            if(baowenSrc.startsWith("{")){
            	
            	Map<String,Object>  result = (Map<String,Object>)JSONObject.fromObject(baowenSrc);
                ClientCommand c = new ClientCommand(result);
                String udid = c.getUdid();
            	String userId = c.getUserId();
                if(c.isRegCommand()){	
                	if (SessionManager.getInstance().getSessions().get(udid)!=null){      		
                		ClientSessionInfo csi = SessionManager.getInstance().getSessions().get(udid);
                		csi.setSession(session);
                		csi.setState(ConstantPush.CLIENT_SESSION_STATUS.ONLINE.name());
                     	session.setAttribute("udid",udid);
	                	session.setAttribute("userId",userId);
	                	session.setAttribute("loginDate",new Date());
                	} else {
	                	ClientSessionInfo csi = new ClientSessionInfo();
	                	session.setAttribute("udid",udid);
	                	session.setAttribute("userId",userId);
	                	csi.setWifi(c.isWifi());
	                	csi.setNetWorkType(c.getNetWorkType());
	                	csi.setRemoteIp(session.getRemoteAddress().toString());
	                	session.setAttribute("loginDate",new Date());
	                	csi.setUserId(userId);
	                	csi.setUdid(udid);
	                	
	                	csi.setSession(session);
	                	
	                	SessionStatuObserver sso = new SessionStatuObserver();
	                	csi.addObserver(sso);
	                	
	                  	csi.setState(ConstantPush.CLIENT_SESSION_STATUS.ONLINE.name());
	                  	SessionManager.getInstance().putSession(udid, csi); 

                	}

                } else if(c.isSyncDataCommand()){
                	ClientSessionInfo csi = SessionManager.getInstance().getSessions().get(udid);
            		csi.setSession(session);
            		csi.setState(ConstantPush.CLIENT_SESSION_STATUS.ONLINE.name());
                 	session.setAttribute("udid",udid);
                	session.setAttribute("userId",userId);
                	session.setAttribute("loginDate",new Date());
                }
            }
           
	    }

	    public void messageSent(IoSession session, Object message) throws Exception {
	    	String msg = message.toString();
	    	String fingerPrinter = MD5.encode(ZipUtil2.uncompress(msg));
	    	LOGGER.info("消息已经发送 指纹信息为:"+fingerPrinter);
	    	List<Long> list = (List<Long>) session.getAttribute(fingerPrinter);
	    	IEbkPushService ebkPushService = (IEbkPushService) SpringBeanProxy.getBean("ebkPushService");
	    	for (Long long1 : list) {
				EbkPushMessage pushMessage = ebkPushService.selectMessageByPK(long1);
				pushMessage.setIsSuccess("Y");
				ebkPushService.updateEbkPushMessage(pushMessage);
			}
	    	session.setAttribute(fingerPrinter, null);
	    	list = null;
	    }

}
