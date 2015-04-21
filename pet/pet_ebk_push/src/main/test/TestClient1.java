import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;



public class TestClient1 {
	/** 
     * 缺省连接超时时间 
     */  
  
	 public static void main(String []args)throws Exception{     
		// for (int i=0;i<1000;i++) {

		        //Create TCP/IP connection     
		        NioSocketConnector connector = new NioSocketConnector();
		        connector.setConnectTimeoutMillis(60000L);  
		        connector.setConnectTimeoutCheckInterval(10000);  
		        //必须代码 读写端空闲时候 调用filter 并分发到当前session ioHandler  的 sessionIdle  这个方法 
		       //connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30); 
		        //创建接受数据的过滤器     
		        DefaultIoFilterChainBuilder chain = connector.getFilterChain();     
		             
		        //设定这个过滤器将一行一行(/r/n)的读取数据     
		        chain.addLast("myChin", new ProtocolCodecFilter(new TextLineCodecFactory()));     
		        //chain.addLast("keep-alive", new MyKeepAliveFilter());
		        chain.addLast("logger", new LoggingFilter());  
		        //客户端的消息处理器：一个SamplMinaServerHander对象     
		        connector.setHandler(new Client1Handler());
		
		             
		        //连接到服务器：     
		
		        ConnectFuture future = connector.connect(new InetSocketAddress(
		        		"localhost",1225));
		        		future.addListener(new IoFutureListener<ConnectFuture>() {
		        		@Override
		        		public void operationComplete(ConnectFuture future) {
		        		//获得session
		        			IoSession session = future.getSession();
		        		}
		        		});
		        		System.out.println("*************"); 
		 //}
	    
	    }  
}
