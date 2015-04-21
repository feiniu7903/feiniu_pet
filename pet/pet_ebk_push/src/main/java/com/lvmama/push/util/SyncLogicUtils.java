package com.lvmama.push.util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;

import com.lvmama.comm.utils.MD5;
import org.slf4j.LoggerFactory;

public class SyncLogicUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(SyncLogicUtils.class);
	public static void sync(IoSession session,Map<String,Object> result,List<Long> pushIds,String udid){
		JSONObject json = JSONObject.fromObject(result);
		String fingerSource = json.toString();
		String callBackMD5ID = "";
		LOGGER.info(json.toString());
		try {
			callBackMD5ID = MD5.encode(json.toString());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.setAttribute(callBackMD5ID, pushIds);
		if(session!=null&&session.isConnected()){
			LOGGER.info("fingerPrinter:"+callBackMD5ID);
				WriteFuture wf = null;
				try {
					wf = session.write(ZipUtil2.compress(fingerSource));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				wf.addListener(new IoFutureListener<WriteFuture>() {
					@Override
					public void operationComplete(WriteFuture future) {
						// TODO Auto-generated method stub
						if(future.isWritten()){
							LOGGER.info("destination device  written message  success" );
						} else {
							LOGGER.info("destination device  written message failure" );
	
						}
					}
				});
	
			}else {
				LOGGER.info("device is not online device is "+udid);
			}
	}
}
