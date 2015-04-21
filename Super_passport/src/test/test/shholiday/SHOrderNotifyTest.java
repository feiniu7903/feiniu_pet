package test.shholiday;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.utils.FreeMarkerConfiguration;
import com.lvmama.shholiday.action.ShholidayNotifyMessageAction;

import freemarker.template.TemplateException;

@ContextConfiguration({ "classpath:applicationContext-passport-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SHOrderNotifyTest {

	@Autowired
	FreeMarkerConfiguration shholidayNotifyFreeMarkerConfiguration;
	
	@Test
	public void orderstatusNotifyTest() throws TemplateException{
		ShholidayNotifyMessageAction noti = new ShholidayNotifyMessageAction();
		noti.setShholidayNotifyFreeMarkerConfiguration(shholidayNotifyFreeMarkerConfiguration);
		noti.setMessageXML(getOrderStatusReq());
		noti.setUnique("FMHOLIDAY");
		noti.setUserId("A_interface_1");
		noti.setPassword("interface");
		noti.setExternalUserID("5082");
		noti.setExternalUserName("接口测试");
		noti.executeNotify();
	}

	@Test
	public void orderModifyNotiTest() throws TemplateException{
		ShholidayNotifyMessageAction noti = new ShholidayNotifyMessageAction();
		noti.setShholidayNotifyFreeMarkerConfiguration(shholidayNotifyFreeMarkerConfiguration);
		noti.setMessageXML(getOrderModifyReq());
		noti.executeNotify();
	}
	private String getOrderStatusReq() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<OTRequest Version=\"\"><!-- Version:1.0 -->\n" +
                "<TransactionName>OTA_TourOrderStatusNotifyRQ</TransactionName>\n" +
                "<Header>\n" +
                "<SessionID><!-- 预留字段，不填 --></SessionID>\n" +
                "<Invoker><!-- 调用者 不填 --></Invoker>\n" +
                "<Encoding><!-- 字符编码 --></Encoding>\n" +
                "<Locale><!-- 区域 不填 --></Locale>\n" +
                "<SerialNo><!-- 流水号 ★，同一通知内容，多次通知时，流水号相同 --></SerialNo>\n" +
                "<TimeStamp><!-- 时间戳 YYYY-mm-DD HH:MM:SS --></TimeStamp>\n" +
                "<Application><!-- 子系统名称 String类型 填写\"travelpkg\" ★ --></Application>\n" +
                "</Header>\n" +
                "<DestinationSystemCodes>\n" +
                "<UniqueID><!-- String 不填 --></UniqueID>\n" +
                "</DestinationSystemCodes>\n" +
                "<IdentityInfo>\n" +
                "<OfficeID><!-- 代理Office号 预留字段不填 --></OfficeID>\n" +
                "<UserID><!-- 接口用户名  预留字段不填--></UserID>\n" +
                "<Password><!-- 接口用户密码  预留字段不填--></Password>\n" +
                "<Role><!-- 角色 预留字段 不填 --></Role>\n" +
                "</IdentityInfo>\n" +
                "<Source>\n" +
                "<OfficeCode><!-- 代理Office号 String类型 预留字段,不填 --></OfficeCode>\n" +
                "<UniqueID><!-- 供应商Code String ★ --></UniqueID>\n" +
                "<BookingChannel><!-- 预定渠道代码(默认填写:HOTELBE) 预留字段,可不填 --></BookingChannel>\n" +
                "</Source>\n" +
                "<OrderNotifyRQ  OrderPackageNo=\"PU465654\" ExternalOrderNo=\"535655\"><!-- OrderPackageNo ★:订单号，ExternalOrderNo:外部订单号；2个只要传递其中任何一个,都有值以OrderPackageNo为准 -->\n" +
                "<ExternalUserInfo>\n" +
                "<ExternalUserID><!-- 外部登录人员用户名 String 预留字段,可不填 --></ExternalUserID>\n" +
                "<ExternalUserName><!-- 外部登录人员用户真实姓名 String 预留字段,不填 --></ExternalUserName>\n" +
                "</ExternalUserInfo>\n" +
                "<OrderStatus>\n" +
                "<Status CurrentStatusCode=\"XE\" OriginalStatusCode=\"BRR\"/><!-- CurrentStatusCode ★:当前状态代码,OriginalStatusCode★:原状态代码 -->\n" +
                "<NotifyStyle>XE</NotifyStyle>\n" +
                "</OrderStatus>\n" +
                "</OrderNotifyRQ>\n" +
                "</OTRequest>\n";
	}
	
	private String getOrderModifyReq() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<OTRequest Version=\"\"><!-- Version:1.0 -->\n" +
                "<TransactionName>OTA_TourOrderModifyNotifyRQ</TransactionName>\n" +
                "<Header>\n" +
                "<SessionID><!-- 预留字段，不填 --></SessionID>\n" +
                "<Invoker><!-- 调用者 不填 --></Invoker>\n" +
                "<Encoding><!-- 字符编码 --></Encoding>\n" +
                "<Locale><!-- 区域 不填 --></Locale>\n" +
                "<SerialNo><!-- 流水号 ★，同一通知内容，多次通知时，流水号相同 --></SerialNo>\n" +
                "<TimeStamp><!-- 时间戳 YYYY-mm-DD HH:MM:SS --></TimeStamp>\n" +
                "<Application><!-- 子系统名称 String类型 填写\"travelpkg\" ★ --></Application>\n" +
                "</Header>\n" +
                "<DestinationSystemCodes>\n" +
                "<UniqueID><!-- String 不填 --></UniqueID>\n" +
                "</DestinationSystemCodes>\n" +
                "<IdentityInfo>\n" +
                "<OfficeID><!-- 代理Office号 预留字段不填 --></OfficeID>\n" +
                "<UserID><!-- 接口用户名  预留字段不填--></UserID>\n" +
                "<Password><!-- 接口用户密码  预留字段不填--></Password>\n" +
                "<Role><!-- 角色 预留字段 不填 --></Role>\n" +
                "</IdentityInfo>\n" +
                "<Source>\n" +
                "<OfficeCode><!-- 代理Office号 String类型 预留字段,不填 --></OfficeCode>\n" +
                "<UniqueID><!-- 供应商Code String ★ --></UniqueID>\n" +
                "<BookingChannel><!-- 预定渠道代码(默认填写:HOTELBE) 预留字段,可不填 --></BookingChannel>\n" +
                "</Source>\n" +
                "<OrderNotifyRQ BookModifyNo=\"344354\" ExternalModifyOrderNo=\"54354\">"+
                "<ExternalUserInfo>\n" +
                "<ExternalUserID><!-- 外部登录人员用户名 String 预留字段,可不填 --></ExternalUserID>\n" +
                "<ExternalUserName><!-- 外部登录人员用户真实姓名 String 预留字段,不填 --></ExternalUserName>\n" +
                "</ExternalUserInfo>\n" +
                "<OrderPackageNo>245325434<!-- OrderPackageNo:订单号String--></OrderPackageNo>\n" +
                "<NotifyStyle>DEALING<!-- APP：退改签申请通知，DEALING:接受处理(第一次接受处理时才通知),OVER:处理完毕,Refund:财务结算通知,CANCEL：取消处理,OTHER-其它费用,STOP:退改签终止 --></NotifyStyle>\n" +
                "<BookModifyStatus>\n" +
                "<Status CurrentStatusCode=\"\" OriginalStatusCode=\"\"/><!-- CurrentStatusCode ★:当前状态代码,OriginalStatusCode★:原状态代码 -->\n" +
                "</BookModifyStatus>\n" +
                "<BookModifyAmount>\n" +
                "<Replenishment>Y<!-- Y-可以进行补款或退款,N-暂时还不能或不需要进行补款或退款 --></Replenishment>\n" +
                "<ModifyAmountStyle>R<!-- R:表示退客人钱,P:表示客人需要补款 --></ModifyAmountStyle>\n" +
                "<ModifyAmount>234.00<!-- 退款或补款金额(元)：0或空 表示没有金额变动 --></ModifyAmount>\n" +
                "</BookModifyAmount>\n" +
                "</OrderNotifyRQ>" +
                "</OTRequest>\n";
	}
}
