package ts;
import java.io.IOException;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.lvmama.operate.mail.HqEMailSenderService;
import com.lvmama.operate.mail.model.ResultMessage;
import com.lvmama.operate.mail.util.HanQiUtil;

public class Demo2 {

	public static void main(String[] args) throws IOException {
		String[] strings = new String[] { "likun@163.com,你好啊",
				"itsoku@163.com,你好啊" };
		ResultMessage message = HqEMailSenderService.getInstance().sendEmail(
				HanQiUtil.createTask("test1[0be4ca9c78e04150b7f872dbefdb2138]",
						"email","1"),
				HanQiUtil.createTemplate("test1", "itsoku@126.com", "test1",
						"test1[0be4ca9c78e04150b7f872dbefdb2138]",
						"$$fullname$$"), strings, null, true);
		System.out.println(JSON.toJSONString(message, true));
	}

	public static void send() throws IOException {
		String[] strings = new String[] { "likun@163.com,你好啊",
				"itsoku@163.com,你好啊" };
		String flag = "[" + UUID.randomUUID().toString().replaceAll("-", "")
				+ "]";
		flag = "";
		ResultMessage message = HqEMailSenderService.getInstance().sendEmail(
				HanQiUtil.createTask("EDM手动邮件任务" + flag, "email,vary01","1"),
				HanQiUtil.createTemplate("驴妈妈", "likun@lvmama.com", "汉启测试邮件嘿嘿"
						+ flag, "EDM手动邮件模板",
						"<h1>$$fullname$$$$data.vary01$$</h1>"), strings, null,
				true);
		System.out.println(JSON.toJSONString(message, true));
	}
}
