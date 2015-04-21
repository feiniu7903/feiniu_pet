package com.ejingtong.uitls;

import org.apache.mina.core.session.IoSession;

import android.content.Context;

import com.ejingtong.common.Constans;
import com.ejingtong.model.ClientCommand;
import com.ejingtong.push.ClientStart;
import com.google.gson.Gson;

public class CommandUtils {
	public static void sendCommand(String command,Context mContext) {
		if(Constans.userInfo!=null){
			IoSession session = SessionManager.getInstance().getCurrentSession();
			if(session!=null && session.isConnected()){
				ClientCommand cc = new ClientCommand();
				cc.setCommand(command);
				cc.setUdid(Constans.IMEI);
				cc.setUserId(Constans.userInfo.getUserId());
				cc.setWifi(NetWorkUtisl.getInstance(mContext).isWifi());
				cc.setNetWorkType(NetWorkUtisl.getInstance(mContext).getNetWorkType());
				Gson gson = new Gson();
				session.write(gson.toJson(cc));
			}
			
			}
	}
}
