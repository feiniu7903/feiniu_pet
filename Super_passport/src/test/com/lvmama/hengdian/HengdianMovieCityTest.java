package com.lvmama.hengdian;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.atwuxi.AtWuXiTest;
import com.lvmama.passport.hengdianmc.client.AgentInterfaceLocator;
import com.lvmama.passport.hengdianmc.client.AgentInterfaceSoap;
import com.lvmama.passport.hengdianmc.client.OrderInfo;
import com.lvmama.passport.hengdianmc.client.OrderRep;

public class HengdianMovieCityTest {

	private static final Log log = LogFactory.getLog(AtWuXiTest.class);


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
		AgentInterfaceSoap s=new AgentInterfaceLocator().getAgentInterfaceSoap();
		OrderInfo submitOrderBean=new OrderInfo();
		submitOrderBean.setTimeStamp("20140610150901");
		submitOrderBean.setCompanyCode("LMM8217211V04");//商家编码，由横店影视城分配
		submitOrderBean.setCompanyName("驴妈妈");//商家名称
		submitOrderBean.setCompanyOrderID("20140610151001");//商家自己的订单流水号
		submitOrderBean.setArrivalDate("2014-06-12");//游玩时间 
		submitOrderBean.setOrderTime("2014-06-10");//订单时间
		submitOrderBean.setVisitorName("郝菲菲");//游客姓名 
		submitOrderBean.setVisitorMobile("15221026734");//游客手机号 
		submitOrderBean.setIdCardNeed(1);//是否需要身份证号
		submitOrderBean.setIdCard("411381199411076126");//身份证号码 
		submitOrderBean.setPayType(String.valueOf(1));
		submitOrderBean.setProducts("<product><viewid>01</viewid><viewname>秦王宫</viewname><Type>Adult</Type><number>1</number></product>");
		OrderRep result=s.orderReq(submitOrderBean);
		System.out.println(result.isResult());
		System.out.println(result.getDealTime());
		System.out.println(result.getErrorMsg());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


}
