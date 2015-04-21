package com.test;

import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lvmama.comm.utils.UUIDGenerator;


public class LogicIoHandler extends IoHandlerAdapter {

	private static final Logger LOG = LoggerFactory
			.getLogger(LogicIoHandler.class);

	private NioSocketConnector connector;
	
	public NioSocketConnector getConnector() {
		return connector;
	}



	public void setConnector(NioSocketConnector connector) {
		this.connector = connector;
	}



	public void sessionCreated(IoSession session) throws Exception { 
		LOG.info("-IoSession :" + session.toString()); 
		UUIDGenerator gen = new UUIDGenerator();
		String udid = gen.generate().toString();
		WriteFuture w = session.write("{'type':'REG','udid':'"+udid+"','userId':'123456'}");
		w.addListener(new IoFutureListener<IoFuture>() {
			@Override
			public void operationComplete(IoFuture f) {
				// TODO Auto-generated method stub
				//异步返回操作结果
				if(f.isDone()) {
					System.out.println("#### 消息发送成功");
				}
			}
		});
		}
	
	
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		LOG.info("捕获异常");
		
		//这里需要实现重连机制 异常发生的时候
		
		session.close(false);
		cause.printStackTrace();
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		super.messageReceived(session, message);
		System.out.println("ddddd："+message);
		LOG.info("recieved msg:"+message);
		//这里接受服务端发送的消息
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		LOG.info("session被关闭");
		session.close(false);
		this.reConnect(session);
	}
	
	public void reConnect(IoSession session)
	 {
		System.out.println("开始重连");
		try {
			Thread.sleep(65000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ClientStart.reconnect(session);
	 }
	
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		LOG.info("session正空闲" + status.toString());
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		LOG.info("session连接被打开");
		//LOG.info("leaf:  " + START);
		//session.write(START);
	}

}
