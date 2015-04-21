package com.test;
import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;






public class ClientStart {
	private static String SERVER_HOST = "192.168.0.251";
	//private static String SERVER_HOST = "localhost";
	private static int SERVER_PORT = 1225;
	/** 60秒后超时 */
	private static final int IDEL_TIME_OUT = 60;
	/** 30秒发送一次心跳包 */
	private static final int HEART_BEAT_RATE = 30;
	
	private static LogicIoHandler handler = new LogicIoHandler();
	
    private static ClientStart instance;
    
 
	
	public ClientStart(){
		NioSocketConnector   connector = new NioSocketConnector();
		     
	      connector.getFilterChain().addLast("log", new LoggingFilter());
	        //字符处理器。将二进制数据转换为字符串 
	      
		connector.getFilterChain().addLast("code",
					new ProtocolCodecFilter(new PushCodecFactory() ));
			//必须代码 读写端空闲时间 空闲60秒回会调用心跳过滤器 发送心跳到服务端 否则会超时session 会关闭
			connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,
					IDEL_TIME_OUT);
	
			KeepAliveMessageFactory heartBeatFactory = new KeepAliveMessageFactoryImpl();
			//当检测不到心跳后调用此方法。在这里处理 重连机制
			KeepAliveRequestTimeoutHandler heartBeatHandler = new KeepAliveRequestTimeoutHandlerImpl();
			KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory,
					IdleStatus.BOTH_IDLE, heartBeatHandler);
			/** 是否回发 */
			heartBeat.setForwardEvent(true);
			/** 发送频率 */
			heartBeat.setRequestInterval(HEART_BEAT_RATE);
			//长连接设置
			connector.getSessionConfig().setKeepAlive(true);
			//添加心跳检测filter
			connector.getFilterChain().addLast("heartbeat", heartBeat);
	             
			//逻辑处理handler
		
			connector.setHandler(handler);
			handler.setConnector(connector);
	        //连接到服务器：    在这里有可能重连失败 需要调用重连机制 你自己实现
			
			
	        ConnectFuture cf = connector.connect(new InetSocketAddress(SERVER_HOST,SERVER_PORT));     
	        cf.awaitUninterruptibly();      
	        cf.getSession().getCloseFuture().awaitUninterruptibly();  
	        if (cf.isDone()) {
				if (!cf.isConnected()) {	//若在指定时间内没连接成功，则抛出异常
					//logger.info("fail to connect " + logInfo);
					connector.dispose();	//不关闭的话会运行一段时间后抛出，too many open files异常，导致无法连接

					//throw new Exception();
				}
			}
			
	}
	
	public static void reconnect(IoSession session){
//		ConnectFuture cf = connector.connect(session.getRemoteAddress());     
//        cf.awaitUninterruptibly();      
//        cf.getSession().getCloseFuture().awaitUninterruptibly();   
	}
	
	public static void main(String[] args) {
			for (int i = 0; i < 500; i++) {
				System.out.println(i);
				new Thread(new Runnable() {
					
					@Override
					public void run() {
				
						// TODO Auto-generated method stub
						ClientStart cs = new ClientStart();
					}
				}).start();
			}
			
	 
	}
	
	
}
