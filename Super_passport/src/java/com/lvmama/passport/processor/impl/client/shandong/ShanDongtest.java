package com.lvmama.passport.processor.impl.client.shandong;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.lvmama.comm.utils.MD5;
import com.lvmama.passport.processor.impl.client.shandong.model.OrderBean;
public class ShanDongtest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
	      System.out.println("Invoking saveOrder...");
	      java.lang.String versionNo = "v1";
	      String kid="3bfef15e-45fb-4d92-ac6e-387a6284051a";
	      String otaName = "tuyou";
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      Date date = sdf.parse("2013-11-28 22:10:12");
	      OrderBean bean =new OrderBean();
	      Calendar calendar = Calendar.getInstance();
	      calendar.setTime(date);
	      bean.setBegin_time(calendar);
	      bean.setEnd_time(calendar);
	      bean.setOrder_source("be462247-55f4-4489-9109-7a20ebf257e2");
	      bean.setStrategy_id("589c46b1-77d9-4d03-9c23-1ea0efd57d9");
	      bean.setProduct_info_id("10d46da8-dd6e-4756-a3cb-2ba29b050fcf");
	      bean.setPurchaser_name("汤静测试单");
	      bean.setCounts(1);
	      bean.setPurchaser_phone("15026847838");
	      String sign=MD5.encode(versionNo+kid+otaName+"2013ota");
	      System.out.println(sign);
	      System.out.println(otaName);
	      WebservicePortType port=new WebserviceLocator().getwebserviceHttpPort();
	      com.lvmama.passport.processor.impl.client.shandong.model.OrderResult _saveOrder__return =port.saveOrder(versionNo, false, kid,otaName, bean, sign);
	      System.out.println("saveOrder.result=" + _saveOrder__return.getCodeStr());
	      System.out.println("saveOrder.result=" + _saveOrder__return.getMessage());
	      System.out.println("saveOrder.result=" + _saveOrder__return.getOrderId());
	      System.out.println("saveOrder.result=" + _saveOrder__return.getStateCode());


//	      saveOrder.result=1383186096574Tq0Q8n
//	      saveOrder.result=成功
//	      saveOrder.result=06988673-036a-483e-8346-e24becd9c361
//	      saveOrder.result=001
	      
//	      String orderId="ddb525fc-3071-41c6-80dc-02644fd5750f";
//	      String sign=MD5.encode(versionNo+kid+otaName+orderId+"2013ota");
//	      CancelResult s= port.cancellOrder(versionNo,kid,"tuyou",orderId,sign);
//	      System.out.println(s.getMessage());
	}
	



}
