/**   
 * @Title: HeartBeatTestServer.java 
 * @Package com.underdark.March 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author Minsc Wang ys2b7_hotmail_com   
 * @date 2010-3-14 下午02:49:14 
 * @version V0.9.0 
 */


import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: HeartBeatTestServer
 * @Description: MINA心跳测试服务器,仅供测试
 * @author Minsc Wang ys2b7_hotmail_com
 * @date 2011-3-7 下午02:49:14
 * 
 */
public class HeartBeatTestServer {

	private static final Logger LOG = LoggerFactory
			.getLogger(HeartBeatTestServer.class);

	private static final int PORT = 1225;
	/** 30秒后超时 */
	private static final int IDELTIMEOUT = 30;
	/** 15秒发送一次心跳包 */
	private static final int HEARTBEATRATE = 15;
	/** 心跳包内容 */
	private static final String HEARTBEATREQUEST = "HEARTBEATREQUEST";
	private static final String HEARTBEATRESPONSE = "HEARTBEATRESPONSE";
	
	private static SocketAcceptor acceptor;
	private static ExecutorService filterExecutor = new OrderedThreadPoolExecutor();
	private static IoHandler handler = new HeartBeatServerHandler();

	public static void main(String[] args) throws IOException {
		acceptor = new NioSocketAcceptor(Runtime.getRuntime()
				.availableProcessors());
		acceptor.setReuseAddress(true);
		acceptor.getSessionConfig().setReadBufferSize(1024);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,
				IDELTIMEOUT);
		// Decrease the default receiver buffer size
		((SocketSessionConfig) acceptor.getSessionConfig())
				.setReceiveBufferSize(1024);

		acceptor.getFilterChain().addLast("threadPool",
				new ExecutorFilter(filterExecutor));
		acceptor.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new TextLineCodecFactory()));

		/** 主角登场 */

		KeepAliveMessageFactory heartBeatFactory = new KeepAliveMessageFactoryImpl();
		KeepAliveRequestTimeoutHandler heartBeatHandler = new KeepAliveRequestTimeoutHandlerImpl();
		KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory,
				IdleStatus.BOTH_IDLE, heartBeatHandler);
		/** 是否回发 */
		heartBeat.setForwardEvent(true);
		/** 发送频率 */
		heartBeat.setRequestInterval(HEARTBEATRATE);
		acceptor.getFilterChain().addLast("heartbeat", heartBeat);

		/** *********************** */

		acceptor.setHandler(handler);
		acceptor.bind(new InetSocketAddress(PORT));

		LOG.info("服务器开启，监听端口：" + PORT);
	}

	/***
	 * @ClassName: KeepAliveMessageFactoryImpl
	 * @Description: 内部类，实现心跳工厂
	 * @author Minsc Wang ys2b7_hotmail_com
	 * @date 2011-3-7 下午04:09:02
	 * 
	 */
	private static class KeepAliveMessageFactoryImpl implements
			KeepAliveMessageFactory {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.apache.mina.filter.keepalive.KeepAliveMessageFactory#getRequest
		 * (org.apache.mina.core.session.IoSession)
		 */
		@Override
		public Object getRequest(IoSession session) {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.apache.mina.filter.keepalive.KeepAliveMessageFactory#getResponse
		 * (org.apache.mina.core.session.IoSession, java.lang.Object)
		 */
		@Override
		public Object getResponse(IoSession session, Object request) {
			LOG.info("返回预设语句" + HEARTBEATRESPONSE);
			/** 返回预设语句 */
			return HEARTBEATRESPONSE;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.apache.mina.filter.keepalive.KeepAliveMessageFactory#isRequest
		 * (org.apache.mina.core.session.IoSession, java.lang.Object)
		 */
		@Override
		public boolean isRequest(IoSession session, Object message) {
			LOG.info("是否是心跳包: " + message);
			if(message.equals(HEARTBEATREQUEST))
				return true;
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.apache.mina.filter.keepalive.KeepAliveMessageFactory#isResponse
		 * (org.apache.mina.core.session.IoSession, java.lang.Object)
		 */
		@Override
		public boolean isResponse(IoSession session, Object message) {
			LOG.info("是否是心跳包: " + message);
			if(message.equals(HEARTBEATRESPONSE))
				return true;
			return false;
		}

	}

	/***
	 * @ClassName: KeepAliveRequestTimeoutHandlerImpl
	 * @Description: 当心跳超时时的处理，也可以用默认处理 这里like
	 *               KeepAliveRequestTimeoutHandler.LOG的处理
	 * @author Minsc Wang ys2b7_hotmail_com
	 * @date 2011-3-7 下午04:15:39
	 * 
	 */
	private static class KeepAliveRequestTimeoutHandlerImpl implements
			KeepAliveRequestTimeoutHandler {

		/*
		 * (non-Javadoc)
		 * 
		 * @seeorg.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler#
		 * keepAliveRequestTimedOut
		 * (org.apache.mina.filter.keepalive.KeepAliveFilter,
		 * org.apache.mina.core.session.IoSession)
		 */
		@Override
		public void keepAliveRequestTimedOut(KeepAliveFilter filter,
				IoSession session) throws Exception {
			((Logger) LOG).info("心跳超时！");
		}

	}
}
