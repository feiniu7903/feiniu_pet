package test;

import com.lvmama.passport.utils.WebServiceClient;

public class LingshanTest {
	public static void main(String[] args) throws Exception {
		
		String saveOrderDetail="<?xml version=\"1.0\" encoding=\"utf-8\"?><request><header><accountID>1</accountID><serviceName>SaveOrderDetail</serviceName><digitalSign>4aae76e91294985ec845f88c90033e12</digitalSign><reqTime>2013-01-18 13:40:56</reqTime></header><body><order><serialId>20122345</serialId><productId>1</productId><productName>单门票</productName><settlementType>1</settlementType><payType>1</payType><playDate>2013-01-19 00:00:00</playDate><linkMan>guobin</linkMan><identityCard>310107198304114912</identityCard><linkPhone></linkPhone><linkMobile>13916122915</linkMobile><verifyCode>123</verifyCode><TouristsCount>1</TouristsCount><webPrice>2</webPrice><totalAmount>2</totalAmount><operationMode>1</operationMode></order></body></request>";
		
		String cancelOrder = "<request><header><accountID>1</accountID><serviceName>CancelOrder</serviceName>" +
				"<digitalSign>4aae76e91294985ec845f88c90033e12</digitalSign>" +
				"<reqTime>2013-01-18 13:40:56</reqTime></header><body><serialId>20122345</serialId></body></request>";
		
		String getSaleList = "<request><header><accountID>1</accountID><serviceName>GetSaleList</serviceName><digitalSign>fca35d4759cd7a62d1acb203f67775b1</digitalSign><reqTime>2013-01-18 18:20:15</reqTime></header><body><serialId>20130118260453</serialId><playDate></playDate><page>1</page><pageSize>1</pageSize></body></request>";
		
		String resXml = WebServiceClient.call("http://112.65.136.242:3030/TicketInfoService.asmx?wsdl", new Object[] {saveOrderDetail}, "SaveOrderDetail");
		System.out.println(resXml);
	}
}
