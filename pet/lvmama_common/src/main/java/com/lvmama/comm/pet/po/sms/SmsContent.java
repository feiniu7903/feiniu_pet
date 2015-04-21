package com.lvmama.comm.pet.po.sms;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.StringUtil;

public class SmsContent implements Serializable {

	private static final long serialVersionUID = 7295931447365545570L;

	private static final Log LOG = LogFactory.getLog(SmsContent.class);
	
	//高优先级
	public static final Integer HIGH_PRIORITY = 1;
	//普通优先级
	public static final Integer NORMAL_PRIORITY = 5;
	//低优先级
	public static final Integer LOW_PRIORITY = 10;
	
	//分隔符
	public static final String SEPARATOR = ",";
	
	protected Long id;  //标示
	protected Long serialId;  //短信序列号
	protected String content; //内容
	protected String mobile;  //目标手机号
	protected Integer priority;  //优先级
	protected String type;  //短信类型
	protected Date sendDate;  //日期
	protected int failure = 0;  //错误失败次数
	protected byte[] data;  //彩信内容
	protected String userId = "System"; //发送人
	protected String opeUserName;//操作人
	protected String tableName;//数据来源表
	protected String memo; //发送备忘
	protected String channel; //发送通道
	/**
	 * 2:待发送,3:推送失败,4:已发送等待状态报告,默认待发送状态
	 */
	protected Integer status = 2;
	/**
	 * 被重发的短信的id，对应于sms_content表的id
	 */
	protected Long resendSmsId;
	public String getFormateDate(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(sendDate);
	}
	/**
	 * Constructor
	 */
	public SmsContent() {
		
	}

	/**
	 * 	
	 * Constructor
	 * @param content 内容
	 * @param mobile 目标手机号，手机号可以多个，以逗号（,）分割，最多支持100个手机号。单个手机号必须11位，并符合手机号码的规范
	 * @throws InvalidSmsException  所创建的短信不符合规范，具体可看错误的信息
	 */
	public SmsContent(final String content, final String mobile) throws Exception {
		this(content, mobile, NORMAL_PRIORITY, null, new Date());
	}
	
	/**
	 * Constructor
	 * @param content 内容
	 * @param mobile 目标手机号，手机号可以多个，以逗号（,）分割，最多支持100个手机号。单个手机号必须11位，并符合手机号码的规范
	 * @param date 希望发送时间
	 * throws InvalidSmsException 所创建的短信不符合规范，具体可看错误的信息
	 */
	public SmsContent(String content, String mobile, Date date) throws Exception {
		this(content, mobile, NORMAL_PRIORITY, null, date);
	}
	
	/**
	 * Constructor
	 * @param content 内容
	 * @param mobile 目标手机号，手机号可以多个，以逗号（,）分割，最多支持100个手机号。单个手机号必须11位，并符合手机号码的规范
	 * @param priority 优先级，值为1到10之间的整数
	 * throws InvalidSmsException 所创建的短信不符合规范，具体可看错误的信息
	 */
	public SmsContent(String content, String mobile, Integer priority) throws Exception {
		this(content, mobile, priority, null, new Date());
	}
	
	/**
	 * Constructor
	 * @param content 内容
	 * @param mobile 目标手机号，手机号可以多个，以逗号（,）分割，最多支持100个手机号。单个手机号必须11位，并符合手机号码的规范
	 * @param type 短信类型
	 * throws InvalidSmsException 所创建的短信不符合规范，具体可看错误的信息
	 */
	public SmsContent(String content, String mobile, String type) throws Exception {
		this(content, mobile, NORMAL_PRIORITY, type, new Date());
	} 
	
	
	/**
	 * Constructor
	 * @param content 内容
	 * @param mobile 目标手机号，手机号可以多个，以逗号,分割（,）最多支持100个手机号。单个手机号必须11位，并符合手机号码的规范
	 * @param priority 优先级 ，值为1到10之间的整数
	 * @param date 希望发送时间
	 * throws InvalidSmsException 所创建的短信不符合规范，具体可看错误的信息
	 */
	public SmsContent(String content, String mobile, int priority,String type, Date date) throws Exception {
		if (null == content || content.equalsIgnoreCase("")) {
			LOG.error("你要发送空短信？");
			//throw new Exception("Null Sms Content.");
		}
		if (null == mobile || mobile.length() < 11 || mobile.length() > 1200) {
			LOG.error("手机号"+mobile+"非法的手机长度，手机号码长度必须介于11位到1200位之间");
			//throw new Exception("手机号"+mobile+"手机号码长度必须介于11位到1200位之间");
		}
		if (mobile.length() != 11 && mobile.indexOf(SEPARATOR) == -1) {
			LOG.error("手机号"+mobile+"错误的手机号分割符，应该使用逗号分隔");
			//throw new Exception("手机号"+mobile+"批量手机号请使用逗号分隔");
			//mobile = mobile.substring(0,11);
		}
		this.mobile = mobile;
		
		this.content = replaceKeyWord(content);
		
		if (priority < 1 || priority > 10) {
			LOG.error("错误的优先级别，优先级别必须为1到10的整数！");
			//throw new Exception("优先级别必须为1到10的整数");
		}
		this.priority = priority;
		
		this.type = type;
		this.sendDate = date;
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("创建SmsContent:" + toString());
		}
	}
	
	public String replaceKeyWord(String content){
		content = content.replaceAll("成人", "成 人");
		content = content.replaceAll("西藏", "西 藏");
		return content;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}
	
	//setter and getter
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSerialId() {
		return serialId;
	}
	public void setSerialId(Long serialId) {
		this.serialId = serialId;
	}
	
	/**
     * 把密码替换为******
     * @return
     */
    public String getContentRlppa(){
    	return content.replaceAll("密码.*? ", "密码***** ").replaceAll("密码.*?。", "密码*****。")
    		.replaceAll("密码.*，", "密码******，").replaceAll("验证码是.*?，", "验证码是******，")
    		.replaceAll("校验码是.*?，", "验证码是******，");
    }
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date date) {
		this.sendDate = date;
	}

	public int getFailure() {
		return failure;
	}

	public void setFailure(int failure) {
		this.failure = failure;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getOpeUserName() {
		return StringUtil.isNotEmptyString(opeUserName)?this.opeUserName:this.userId;
	}
	public void setOpeUserName(String opeUserName) {
		this.opeUserName = opeUserName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getResendSmsId() {
		return resendSmsId;
	}
	public void setResendSmsId(Long resendSmsId) {
		this.resendSmsId = resendSmsId;
	}
	
}
