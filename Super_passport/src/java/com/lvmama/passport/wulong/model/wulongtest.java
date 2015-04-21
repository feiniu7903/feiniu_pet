package com.lvmama.passport.wulong.model;
import java.util.ArrayList;
import java.util.List;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.passport.utils.WebServiceConstant;

public class wulongtest {
	private static String baseTemplateDir = "/com/lvmama/passport/wulong/template";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			OrderInfo orderInfo=new OrderInfo();
			orderInfo.setType("1");
			orderInfo.setOrderId("201310251110");
			orderInfo.setRealPrice("80");//实际成交价格
			orderInfo.setContactName("汤静测试单");
			orderInfo.setContactPhone("15026847838");
			orderInfo.setIdcardCode("420621198810141243");
			orderInfo.setArriveDate("2013-10-30");
			List<Product> productLists=new ArrayList<Product>();
			Product product=new Product();
			product.setProCode("wlpwxt_543");
			product.setBuyPrice("80");//销售价
			product.setBuyNum("1");
			product.setBuyTotalPrice("80");
			productLists.add(product);
			List<Tourist> touristLists=new ArrayList<Tourist>();
			Tourist tour=new Tourist();
			tour.setFullName("汤静测试单");
			tour.setAddress("xiangyang");
			tour.setIdcardCode("420621198810141243");
			touristLists.add(tour);
			orderInfo.setProductLists(productLists);
			orderInfo.setTouristLists(touristLists);
			String reqXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "orderInfo.xml", orderInfo);
			System.out.println(reqXml);
			String pass=WebServiceConstant.getProperties("wulong.pass");
			String dis_code=WebServiceConstant.getProperties("wulong.dis_code");
			String checkcode=WebServiceConstant.getProperties("wulong.checkcode");
			String dispass=WebServiceConstant.getProperties("wulong.dispass");
			OrderPortType port=new OrderLocator().getorderHttpSoap11Endpoint();
			String resXml=port.orderSubmit(pass, dis_code,checkcode, dispass, reqXml);
			System.out.println("=====resXml===="+resXml);
			String rspCode = TemplateUtils.getElementValue(resXml, "//result/code");
			String rspDesc = TemplateUtils.getElementValue(resXml, "//result/msg");
			System.out.println(rspCode+"=========="+rspDesc);
			if ("OS09999".equalsIgnoreCase(rspCode)) {
				System.out.println("test");
			}else{
				System.out.println("else");
			}
			if("OS05000".equalsIgnoreCase(rspCode)){
				System.out.println("订单实际成交价格[1.0]小于成本价格[80.0]");
			}
			//订单提交成功，订单编号[PF1310290663141243]
			//PF1310290663141243
			String cancelResult=port.orderCancel(pass, dis_code, checkcode, dispass, "201310251110","废单测试");
			System.out.println(cancelResult);
			String queryResult=port.orderQuery(pass, dis_code, checkcode, dispass, "201310251110");
			System.out.println(queryResult);
			String des="订单提交成功，订单编号[PF1310290663141243]";
			if(des.contains("[") && des.contains("]")){
			int index=des.indexOf("[")+1;
			int end=des.indexOf("]");
			if(index < end){
			System.out.println(des.substring(index,end));
			}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
