package com.ejingtong.push;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import android.content.Context;

import com.ejingtong.common.Constans;
import com.ejingtong.push.code.PushCodecFactory;


public class ClientStart {
	private static Context mContext;
	private static String SERVER_HOST = Constans.ADDR_PUSH_SERVER;
//	private static String SERVER_HOST = "10.2.1.30";
	private static int SERVER_PORT = Constans.PORT_PUSH_SERVER;
	/** 60秒后超时 */
	private static final int IDEL_TIME_OUT = 60;
	/** 30秒发送一次心跳包 */
	private static final int HEART_BEAT_RATE = 30;
	
	private static NioSocketConnector connector;
	private static IoHandler handler;

	private static ClientStart instance;
	private static InetSocketAddress remoteAddress =  new InetSocketAddress(SERVER_HOST,SERVER_PORT);
	static IoSession session;
	public static  ClientStart getInstance(Context context){
		mContext = context;
		handler = new LogicIoHandler(mContext);
		if (instance==null) {
			synchronized (ClientStart.class) {
				instance = new ClientStart();
			}
		}
		return instance;
	}
	
	public static  ClientStart getInstance(){
		return instance;
	}
	
	public  void pushRegist() {
			java.lang.System.setProperty("java.net.preferIPv4Stack", "true");
			java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");
			connector = new NioSocketConnector();
			connector.getFilterChain().addLast("log", new LoggingFilter());
//	        //字符处理器。将二进制数据转换为字符串 
			connector.getFilterChain().addLast("code",
					new ProtocolCodecFilter(new PushCodecFactory(Charset.forName("UTF-8"))));
			//必须代码 读写端空闲时间 空闲60秒回会调用心跳过滤器 发送心跳到服务端 否则会超时session 会关闭
			connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,
					IDEL_TIME_OUT);
			connector.getSessionConfig().setReaderIdleTime(180);
	
			KeepAliveMessageFactory heartBeatFactory = new KeepAliveMessageFactoryImpl();
			//当检测不到心跳后调用此方法。
			KeepAliveRequestTimeoutHandler heartBeatHandler = new KeepAliveRequestTimeoutHandlerImpl(mContext);
			KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory,
					IdleStatus.BOTH_IDLE, heartBeatHandler);
			/** 是否回发 */
			heartBeat.setForwardEvent(true);
			/**
			 * 3分钟超时
			 */
			heartBeat.setRequestTimeout(120);
			/** 发送频率 */
			heartBeat.setRequestInterval(HEART_BEAT_RATE);
			//长连接设置
			connector.getSessionConfig().setKeepAlive(true);
			//添加心跳检测filter
			connector.getFilterChain().addLast("heartbeat", heartBeat);
	             
			//逻辑处理handler
			connector.setHandler(handler);

			for(;;){
				try {
				ConnectFuture future = connector.connect(ClientStart.getRemoteAddress());
				future.awaitUninterruptibly();
				session = future.getSession();
		        break;
				}catch(Exception ex){
					ex.printStackTrace();
					try {
						/**
						 * 50秒后重连
						 */
						Thread.sleep(50000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			 session.getCloseFuture().awaitUninterruptibly();
		     connector.dispose();


		
	 
	}
	
	public void disConnect(){
		if(session!=null){
			session.close(false);
			//connector.dispose();
		}
	}

	public static InetSocketAddress getRemoteAddress() {
		return remoteAddress;
	}

	public static void setRemoteAddress(InetSocketAddress remoteAddress) {
		ClientStart.remoteAddress = remoteAddress;
	}


}
