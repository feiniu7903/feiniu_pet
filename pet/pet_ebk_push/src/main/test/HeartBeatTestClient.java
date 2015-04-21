/**   
 * @Title: HeartBeatTestClient.java 
 * @Package com.underdark.March 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author Minsc Wang ys2b7_hotmail_com   
 * @date 2010-3-14 下午03:17:27 
 * @version V0.9.0 
 */


import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lvmama.push.codec.PushCodecFactory;

/**
 * @ClassName: HeartBeatTestClient
 * @Description: MINA心跳测试客户端,仅供测试 client空闲时每隔N秒向服务器发送心跳包，如返回超时，发出提示
 * @author Minsc Wang ys2b7_hotmail_com
 * @date 2011-3-7 下午02:49:14
 * 
 */
public class HeartBeatTestClient {

	private static final Logger LOG = LoggerFactory
			.getLogger(HeartBeatTestClient.class);

	private static final int PORT = 1225;
	/** 30秒后超时 */
	private static final int IDELTIMEOUT = 60;
	/** 15秒发送一次心跳包 */
	private static final int HEARTBEATRATE = 30;
	/** 心跳包内容 */
	private static final String HEARTBEATREQUEST = "HEARTBEATREQUEST";
	private static final String HEARTBEATRESPONSE = "HEARTBEATRESPONSE";

	private static final String IPADDRESS = "127.0.0.1";

	private static NioSocketConnector connector;
	private static IoHandler handler = new HeartBeatClientHandler();

	public static void main(String[] args) {
		connector = new NioSocketConnector();
		connector.getFilterChain().addLast("log", new LoggingFilter());
		connector.getFilterChain().addLast("code",
				new ProtocolCodecFilter(new TextLineCodecFactory()));
		connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,
				IDELTIMEOUT);
		/** 主角登场 */

		KeepAliveMessageFactory heartBeatFactory = new KeepAliveMessageFactoryImpl();
		KeepAliveRequestTimeoutHandler heartBeatHandler = new KeepAliveRequestTimeoutHandlerImpl();
		KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory,
				IdleStatus.BOTH_IDLE, heartBeatHandler);
		/** 是否回发 */
		heartBeat.setForwardEvent(true);
		/** 发送频率 */
		heartBeat.setRequestInterval(HEARTBEATRATE);
		connector.getSessionConfig().setKeepAlive(true);
		connector.getFilterChain().addLast("heartbeat", heartBeat);

		/** *********** */
		connector.setHandler(handler);
		connector.connect(new InetSocketAddress(IPADDRESS, PORT));
		
		
//		//Create TCP/IP connection     
//         connector = new NioSocketConnector();     
//         connector.getFilterChain().addLast("code",
//				new ProtocolCodecFilter(new TextLineCodecFactory()));
// 		connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,
// 				IDELTIMEOUT);
// 		
// 		
//		KeepAliveMessageFactory heartBeatFactory = new KeepAliveMessageFactoryImpl();
//		KeepAliveRequestTimeoutHandler heartBeatHandler = new KeepAliveRequestTimeoutHandlerImpl();
//		KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory,
//				IdleStatus.BOTH_IDLE, heartBeatHandler);
//		/** 是否回发 */
//		heartBeat.setForwardEvent(true);
//		/** 发送频率 */
//		heartBeat.setRequestInterval(HEARTBEATRATE);
//		connector.getSessionConfig().setKeepAlive(true);
//		connector.getFilterChain().addLast("heartbeat", heartBeat);
//		
// 		
//        //创建接受数据的过滤器     
//        DefaultIoFilterChainBuilder chain = connector.getFilterChain();     
//             
//        //设定这个过滤器将一行一行(/r/n)的读取数据     
//        chain.addLast("myChin", new ProtocolCodecFilter(new TextLineCodecFactory()));     
//             
//        //客户端的消息处理器：一个SamplMinaServerHander对象     
//        connector.setHandler(new Client1Handler());
//      
//        //set connect timeout     
//         
//             
//        //连接到服务器：     
//        ConnectFuture cf = connector.connect(new InetSocketAddress("localhost",1225));     
//             
//        //Wait for the connection attempt to be finished.     
//        cf.awaitUninterruptibly();      
//             
//        cf.getSession().getCloseFuture().awaitUninterruptibly();     
//             
//        connector.dispose();   
		System.out.println("客户端已连接上！");
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
			System.out.println("返回预设语句" + HEARTBEATREQUEST);
			/** 返回预设语句 */
			return HEARTBEATREQUEST;
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
			System.out.println("得到返回");
			return null;
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
			System.out.println("是否是心跳包: " + message);
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
			System.out.println("是否是心跳包: " + message);
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
			System.out.println("心跳超时！");
		}

	}
}
