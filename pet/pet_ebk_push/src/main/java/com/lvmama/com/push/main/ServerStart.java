package com.lvmama.com.push.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lvmama.push.codec.PushCodecFactory;
import com.lvmama.push.handler.EbkPushIoHandlerAdapter;
import com.lvmama.push.util.KeepAliveMessageFactoryImpl;
import com.lvmama.push.util.KeepAliveRequestTimeoutHandlerImpl;

public class ServerStart {
	
	
	private static final Logger LOG = LoggerFactory
			.getLogger(ServerStart.class);

	private static final int PORT = 1225;
	/** 60秒后超时 */
	private static final int IDELTIMEOUT = 60;
	/** 30秒发送一次心跳包 */
	private static final int HEARTBEATRATE = 30;

	
	private static SocketAcceptor acceptor;
	private static ExecutorService filterExecutor = new OrderedThreadPoolExecutor();
	private static IoHandler handler = new EbkPushIoHandlerAdapter();
	
	
	public ServerStart(){
		start();
	}
	
	public void start() {
	//初始化入口
	acceptor = new NioSocketAcceptor(Runtime.getRuntime()
			.availableProcessors());
	acceptor.setReuseAddress(true);
	
	acceptor.getSessionConfig().setReadBufferSize(1024);
	//设置读写空闲超时时间
	acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,
			IDELTIMEOUT);
	// Decrease the default receiver buffer size
	((SocketSessionConfig) acceptor.getSessionConfig())
			.setReceiveBufferSize(1024);

	//添加各种过滤器 如日志过滤器
	acceptor.getFilterChain().addLast("threadPool",
			new ExecutorFilter(filterExecutor));
	acceptor.getFilterChain().addLast("logging",
			new LoggingFilter());
	/**
	 * 编码处理工厂 
	 */
	acceptor.getFilterChain().addLast("codec",
			new ProtocolCodecFilter(new PushCodecFactory()));

	/** 心跳管理 */
	KeepAliveMessageFactory heartBeatFactory = new KeepAliveMessageFactoryImpl();
	KeepAliveRequestTimeoutHandler heartBeatHandler = new KeepAliveRequestTimeoutHandlerImpl();
	/**
	 * 设置心跳发送时间 空闲后30秒发送心跳包
	 */
	KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory,
			IdleStatus.BOTH_IDLE, heartBeatHandler);
	/** 是否回发 */
	heartBeat.setForwardEvent(true);
	/** 发送频率 */
	heartBeat.setRequestInterval(HEARTBEATRATE);
	acceptor.getFilterChain().addLast("heartbeat", heartBeat);

	/** *********************** */
	//绑定业务逻辑处理handler
	acceptor.setHandler(handler);
	try {
		//绑定端口并启动
		acceptor.bind(new InetSocketAddress(PORT));
		LOG.debug("mina server started");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
