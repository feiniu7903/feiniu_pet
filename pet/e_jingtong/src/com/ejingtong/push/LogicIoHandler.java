package com.ejingtong.push;

import java.lang.reflect.Type;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ejingtong.activity.servise.MainServise;
import com.ejingtong.common.Constans;
import com.ejingtong.log.LogManager;
import com.ejingtong.model.PushResponseData;
import com.ejingtong.uitls.CommandUtils;
import com.ejingtong.uitls.ConnectThread;
import com.ejingtong.uitls.MD5;
import com.ejingtong.uitls.MyThreadPoolExecutor;
import com.ejingtong.uitls.SessionManager;
import com.ejingtong.uitls.ZipUtil2;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class LogicIoHandler extends IoHandlerAdapter {

	private Context mContext;
	private static final Logger LOG = LoggerFactory
			.getLogger(LogicIoHandler.class);	
	
	public LogicIoHandler(Context context){
		super();
		mContext = context;
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		System.out.println("服务器异常");
		LOG.info("捕获异常");
		session.close(false);
		cause.printStackTrace();

		
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		super.messageReceived(session, message);
		if(message == null){
			return;
		}
		
		try{
			Gson gson = new Gson();
			Type type = new TypeToken<PushResponseData>() {
			}.getType();
			String fingerSource = ZipUtil2.uncompress(String.valueOf(message));
			System.out.println(fingerSource);
			String fingerPrinter = MD5.encode(fingerSource);
			
			PushResponseData responsseData = gson.fromJson(fingerSource, type);
			LogManager.log("接收推送消息：" + fingerSource);
			
			if(responsseData.isSyncDatas()){
				LogManager.log("指纹信息:"+fingerPrinter);
			}

			Intent intent = new Intent(mContext, MainServise.class);
			intent.putExtra("push_data", responsseData);
			mContext.startService(intent);

		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
		System.out.println("消息已发送:" + message);
		
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("sessionClosed 关闭");
		session.close(false);
		SessionManager.getInstance().removeSession();
		ConnectivityManager  connectivityManager = (ConnectivityManager) 
				mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo(); 
		
		if(info!=null && info.isAvailable()){
			/**
			 * 线程等待一分钟 重连
			 * 可能是服务端重启
			 */
			Thread.sleep(60000);
			MyThreadPoolExecutor.getInstance().submit(new ConnectThread());
		}
		
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
	
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		LOG.info("session连接被打开");
	}

	
	/**
	 * 设备注册
	 */
	public void sessionCreated(IoSession session) throws Exception { 
		SessionManager.getInstance().putSession(session);
		CommandUtils.sendCommand(Constans.CLIENT_COMMAND_TYPE.REG.name(), mContext);
	}
}
