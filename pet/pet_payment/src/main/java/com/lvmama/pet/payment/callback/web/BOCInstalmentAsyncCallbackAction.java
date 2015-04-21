package com.lvmama.pet.payment.callback.web;

import java.io.IOException;
import java.util.Scanner;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.pet.payment.callback.data.BOCInstalmentCallbackData;
import com.lvmama.pet.payment.callback.data.CallbackData;

/**
 * 中行分期支付回调Action.
 * @author sunruyi
 */
@Results( {
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/boc_callback_async.ftl", type = "freemarker")
	})
public class BOCInstalmentAsyncCallbackAction extends CallbackBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5435974452506582142L;
	/**
	 * 中行网上支付服务器后台回调.
	 * @return /WEB-INF/pages/pay/boc_callback_async.ftl
	 */
	@Action("/pay/bocInstalmentCallback/async")
	public String async(){
		return callback(false);
	}
	/**
	 * 获取回调数据.
	 * @return 回调数据
	 */
	@Override
	CallbackData getCallbackData() {
		String xmlResponse = "";
		try {
			Scanner sc = new Scanner(super.getRequest().getInputStream(),"ISO-8859-1");
			StringBuilder sb = new StringBuilder();
			while (sc.hasNextLine()) {
				sb.append(sc.nextLine());
			}
			xmlResponse = new String(sb.toString().getBytes("ISO-8859-1"),"GBK");
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BOCInstalmentCallbackData callbackData = BOCInstalmentCallbackData.initBOCInstalmentCallbackData(xmlResponse);
		return callbackData;
	}
}
