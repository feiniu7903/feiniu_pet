package ts;


import org.apache.commons.httpclient.params.HttpClientParams;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;

import com.hanqinet.edm.ws.auth.ClientAuthenticationOutHandler;
import com.hanqinet.edm.ws.prnasia.dto.report.QueryResultSum;
import com.hanqinet.edm.ws.prnasia.dto.upload.Message;
import com.hanqinet.edm.ws.prnasia.dto.upload.Task;
import com.hanqinet.edm.ws.prnasia.dto.upload.Template;
import com.hanqinet.edm.ws.prnasia.webservice.service.EDMService;

import java.util.*; 
import java.text.*;

public class EDMServiceCientTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println(encodeBase64("likun_557@163.com,test测试邮件"));
	}
	public static void main1(String[] args) throws Exception {
		
		Service serviceModel = (new ObjectServiceFactory()).create(EDMService.class);
		XFireProxyFactory serviceFactory = new XFireProxyFactory();
		String serviceUrl = "http://mailer.lvmama.com:8081/smartedm/services/EDMService";
		EDMService service = (EDMService) serviceFactory.create(serviceModel, serviceUrl);
		Client client = Client.getInstance(service);
		HttpClientParams params = new HttpClientParams();
		// 避免'Expect: 100-continue' handshake
		//如果服务不需要传输大量的数据，保持长连接，还是建议关闭掉此功能，设置为false。否则，在业务量很大的情况下，很容易将服务端和自己都搞的很慢甚至拖死。		
		params.setParameter(HttpClientParams.USE_EXPECT_CONTINUE,Boolean.FALSE);
		// 设置ws连接超时时间(单位：毫秒)
		params.setParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT, 5000L);
		client.setProperty(CommonsHttpMessageSender.HTTP_CLIENT_PARAMS,params);
		client.addOutHandler(new ClientAuthenticationOutHandler("likun","likun"));//用户名和密码
		
//		//调用webservice接口。
//		Message msg = service.addTaskAndUploadTemplate(null, null);
//		System.out.println(msg.getStatus() + "|"+ msg.getMessage());
		
//		QueryResultSum o = service.openRateAndClickRate(59);
//		System.out.println(o);
//		System.out.println(o.getDistinctOpenRate());
//		System.out.println(o.getSuccessCount());
		
		//组装任务对象		
		Task task = new Task();
		task.setTaskGroupId("1");
		task.setTaskName("test" + getdate());
		task.setEmailColumnName("email,vary01");
		
		//组装模板对象		
		Template template = new Template();
		template.setTemplateName("template-test" + getdate());
		template.setSenderChinese("demo11测试");//发件人昵称
		template.setSenderEmail("likun@lvmama.com");//发件人email				
		template.setSubject("test" + getdate());
		template.setBodyCharset("GBK");
		template.setBodyEncoding("base64");  //发出邮件使用编码格式
		template.setTemplateContent("<h2 style='color:#FF0000;'>$$data.vary01$$</h2>");
		template.setToChinese("$$fullname$$");
		template.setTemplateType("1");  //html格式模板

		//通过ws创建任务，并得到任务ID
		int taskId = 0;
		Message msg = service.addTaskAndUploadTemplate(task, template);
		System.out.println(msg.getStatus() + "|"+ msg.getMessage());
		if ( msg.getStatus() == 1 ) {
			taskId =  Integer.parseInt(msg.getMessage());
		}
		else {
			//出现异常
			return;
		}
		
		//分批上传地址列表
		final int coutOfPart = 1000;
		int count = getMailCount();
		int uploadCount = 0;
		int i = 1;
		while (uploadCount < count){
			//把email列表分段上传，
			//例如，总数10万条记录，每段上传1万条记录，则循环上传10次。
			//或者每次取一定数量email上传，直到全部上传成功为止。
			String partKey = String.valueOf(i);
			String[] lines = getMailList(coutOfPart);
			service.uploadEmailList(taskId, lines, partKey);
			uploadCount+= lines.length;
			i++;
		}
		msg = service.finish(taskId);
		System.out.println(msg.getStatus() + "|"+ msg.getMessage());
		
		//启动任务发送
		msg = service.start(taskId, 0); //0是正式发送，1是清洗发送，2是测试发送
		System.out.println(msg.getStatus() + "|"+ msg.getMessage());
		
	}
	/**
	 * 得到要上传的email总数
	 * @return
	 */
	private static int getMailCount(){
//		return 100000;
		return 1;
	}
	
	private static String[] getMailList(int count) throws Exception{
		String[] ret = new String[1];
		//注意每行数据都要进行base64编码转换
		ret[0] = encodeBase64("likun_557@163.com,test测试邮件\\,你好啊");
		//...
		return ret;
	}
	
	private static String encodeBase64(String str) throws Exception{
		return (new sun.misc.BASE64Encoder()).encode(str.getBytes("GBK"));//目前只能使用GBK
	}
	
	private static String TemplateHTML(){
		String html="<TABLE border='0' cellpadding='0' cellspacing='0' width='200'>\r\n" + 
					"  <TR>\r\n" + 
					"    <TD style='font-family:'宋体'; font-size:12px; color:#333; line-height:20px;'>如果本邮件内容无法正常显示, 请<A href='http://template.zhujinnet.com/2010.07.13/hanqi_edm_online.html' style=' color:#F00;'>点击这里</A></TD>\r\n" + 
					"  </TR>\r\n" + 
					"  <TR>\r\n" + 
					"    <TD><TABLE border='0' cellpadding='0' cellspacing='0' width='200'>\r\n" + 
					"      <TR>\r\n" + 
					"        <TD><IMG alt='' height='181' src='http://template.zhujinnet.com/2010.07.13/images/hanqi_Edm_02.jpg' width='603' style='display: block; border:none;'></TD>\r\n" + 
					"      </TR>\r\n" + 
					"      <TR>\r\n" + 
					"        <TD><IMG alt='' height='214' src='http://template.zhujinnet.com/2010.07.13/images/hanqi_Edm_03.jpg' width='603' style='display: block; border:none;'></TD>\r\n" + 
					"      </TR>\r\n" + 
					"      <TR>\r\n" + 
					"        <TD><IMG alt='' height='124' src='http://template.zhujinnet.com/2010.07.13/images/hanqi_Edm_04.jpg' width='603' style='display: block; border:none;'></TD>\r\n" + 
					"      </TR>\r\n" + 
					"      <TR>\r\n" + 
					"        <TD><TABLE border='0' cellpadding='0' cellspacing='0' width='200'>\r\n" + 
					"          <TR>\r\n" + 
					"            <TD><A href='http://www.smartedm.com' target='_blank'><IMG alt='' border='0' height='257' src='http://template.zhujinnet.com/2010.07.13/images/hanqi_Edm_05.jpg' width='203' style='display: block; border:none;'></A></TD>\r\n" + 
					"            <TD><A href='http://www.smartedm.com' target='_blank'><IMG alt='' border='0' height='257' src='http://template.zhujinnet.com/2010.07.13/images/hanqi_Edm_06.jpg' width='191' style='display: block; border:none;'></A></TD>\r\n" + 
					"            <TD><A href='http://www.smartedm.com' target='_blank'><IMG alt='' border='0' height='257' src='http://template.zhujinnet.com/2010.07.13/images/hanqi_Edm_07.jpg' width='209' style='display: block; border:none;'></A></TD>\r\n" + 
					"          </TR>\r\n" + 
					"        </TABLE></TD>\r\n" + 
					"      </TR>\r\n" + 
					"      <TR>\r\n" + 
					"        <TD><IMG alt='' height='57' src='http://template.zhujinnet.com/2010.07.13/images/hanqi_Edm_08.jpg' width='602' style='display: block; border:none;'></TD>\r\n" + 
					"      </TR>\r\n" + 
					"      <TR>\r\n" + 
					"        <TD><IMG alt='' height='74' src='http://template.zhujinnet.com/2010.07.13/images/hanqi_Edm_10.jpg' width='603' style='display: block; border:none;'></TD>\r\n" + 
					"      </TR>\r\n" + 
					"    </TABLE></TD>\r\n" + 
					"  </TR>\r\n" + 
					"  <TR>    <TD style='font-family:'宋体'; font-size:12px; color:#333; line-height:20px;'>\r\n" + 
					"这是来自汉启网络科技有限公司的信息，您收到此邮件是因为您以前关注过类似的信息。<BR>\r\n" + 
					"如果您以后不想收到来自汉启的类似邮件，请<A href='http://demo11.zhujinnet.com/unsubscribe.html?email=$$data.email$$' style='color:#000;'>点击这里</A>退订。&nbsp;</TD>\r\n" + 
					"  </TR>\r\n" + 
					"</TABLE>";
			return html;
	}
	
	private static String getdate(){		
		SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyyMMdd");		   
		String mydate = setDateFormat.format(Calendar.getInstance().getTime());
		return mydate;
	}
	
}


