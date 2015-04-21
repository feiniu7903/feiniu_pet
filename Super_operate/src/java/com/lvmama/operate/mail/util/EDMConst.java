package com.lvmama.operate.mail.util;

/**
 * 发送邮件使用到的常量
 * 
 * @author likun
 * 
 */
public class EDMConst {
	/**
	 * 邮件任务最大发送失败次数
	 */
	public final static int EDMEMAILTASKMAXFAILCOUNT = Integer
			.parseInt(HanQiResources.get("EDMEmailTaskMaxFailCount"));

	public static void main(String[] args) {
		System.out.println(EDMEMAILTASKMAXFAILCOUNT);
	}
}
