package com.lvmama.jinjiang.mock;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.jinjiang.JinjiangClient;
import com.lvmama.jinjiang.model.request.LineCodesRequest;
import com.lvmama.jinjiang.model.response.LineCodesResponse;
import com.lvmama.passport.utils.Md5;

public class LineCodes {
	/**
	 * 
	 * @return Response
	 */
	public String Response()  {
		
		
		/*LineCodesRequest request = new LineCodesRequest();
		request.setUpdateTimeEnd(new Date());
		request.setUpdateTimeStart(DateUtil.DsDay_Hour(new Date(), -10));

		JinjiangClient client = new JinjiangClient();
		LineCodesResponse codesResponse=  client.execute(request);*/
		
		LineCodesResponse object = new LineCodesResponse();
		object.setErrorcode("0000");
		object.setErrormessage("成功");
		List<String> lineCodes = new ArrayList<String>();
		for (int i = 1; i < 10; i++) {
			lineCodes.add("000"+i);
		}
		object.setLineCodes(lineCodes);		
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{"transactionName","transactionMethod","responseClazz","requestURI","success"});
		JSONObject json = JSONObject.fromObject(object,config);		
		return json.toString();
	}
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		LineCodes tes =new LineCodes();
		System.out.println(tes.Response());
	}
}
