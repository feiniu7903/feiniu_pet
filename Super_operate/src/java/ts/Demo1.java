package ts;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.hanqinet.edm.ws.prnasia.dto.upload.Task;
import com.hanqinet.edm.ws.prnasia.dto.upload.Template;
import com.lvmama.operate.mail.HqEMailSenderService;
import com.lvmama.operate.mail.model.ResultMessage;

public class Demo1 {

	public static void main(String[] args) throws Exception {
//		sendEmail();
		test1();
	}
	public static void test1() throws MalformedURLException
	{
		long startTime = System.currentTimeMillis();
		List<Integer> taskIdList = new ArrayList<Integer>();
		for (int i = 107; i <=110; i++) {
			taskIdList.add(i);
		}
		String result = JSON.toJSONString(HqEMailSenderService.getInstance().getEdmService().getTaskInfo(taskIdList),true);
		System.out.println(result);
		System.out.println(System.currentTimeMillis()-startTime);
	}
	public static void sendEmail() throws IOException
	{
			//组装任务对象		
			Task task = new Task();
			task.setTaskGroupId("1");
			task.setTaskName("test" + UUID.randomUUID().toString());
			task.setEmailColumnName("email,vary01");
			
			//组装模板对象		
			Template template = new Template();
			template.setTemplateName("template-test" + UUID.randomUUID().toString());
			template.setSenderChinese("demo11测试");//发件人昵称
			template.setSenderEmail("likun@lvmama.com");//发件人email				
			template.setSubject("test" + UUID.randomUUID().toString());
			template.setBodyCharset("GBK");
			template.setBodyEncoding("base64");  //发出邮件使用编码格式
			template.setTemplateContent("<h2 style='color:#FF0000;'>$$data.vary01$$</h2>");
			template.setToChinese("$$fullname$$");
			template.setTemplateType("1");  //html格式模板

			String [] emaiList = new String[]{"likun@lvmama.com,你好啊","itsoku@163.com,你好啊"};
			ResultMessage message =HqEMailSenderService.getInstance().sendEmail(task, template, emaiList, null,true);
			System.out.println(JSON.toJSONString(message,true));
		}
}
