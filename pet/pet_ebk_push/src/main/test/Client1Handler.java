
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Client1Handler implements IoHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Client1Handler.class);
	 
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		LOGGER.info("捕获异常");
		cause.printStackTrace();
		session.close(false);
		System.out.println("#############");

	}

	@Override
	public void messageReceived(IoSession arg0, Object message) throws Exception {
		// TODO Auto-generated method stub
		 String baowenSrc = message.toString();// 原始报文
         
         LOGGER.info("recieved msg:"+baowenSrc);
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.info("-IoSession实例:" + session.toString());  
		 // 设置IoSession闲置时间，参数单位是秒  
		
	
		//session 创建成功后 发送注册json
		WriteFuture w = session.write("{'type':'reg','udid':'111111','userId':'123456'}");
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
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// TODO Auto-generated method stub
//		//空闲时候  发布心跳数据
//		if (status == IdleStatus.BOTH_IDLE)  
//		 {  
//			session.write("{}");  
//		 }  
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
	
	}

}
