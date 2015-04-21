package com.ejingtong.http;

/**
 * 异步获取数据回调抽象类
 * 
 * @author acer
 * 
 */
public abstract class ServiceSyncListener {

	// 获取数据成功
	public void onSuccess(ActionResponse returnObject) {
	};

	// 获取数据失败
	public void onError(ActionResponse returnObject) {
		
	};

	// 设置进度信息
	public void setProgressMessage(String message) {
	};
	
	//下载进度 数值范围0-100
	public void onProgress(int progress){};
	
}
