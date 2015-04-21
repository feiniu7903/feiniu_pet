package com.lvmama.comm.pet.service.sms;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.sms.SmsContent;
import com.lvmama.comm.pet.po.sms.SmsContentLog;
import com.lvmama.comm.pet.po.sms.SmsMMS;
import com.lvmama.comm.pet.po.sms.SmsReceive;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.pet.vo.SmsLogStat;
import com.lvmama.comm.vo.Constant.SMS_TEMPLATE;
import com.lvmama.comm.vo.SmsStatusReport;
/**
 * 连接短信网关service,对外。
 * @author yuzhibing
 *
 */
public interface SmsRemoteService {
	public static final int ALL_SMS = 0;
	public static final int WAITTING_SEND_SMS = 1;
	public static final int SENDED_SMS = 2;
	public static final int FAILURE_SMS = 3;
	public static final int HISTROY_SMS = 5;
	/**
	 * 发送短信
	 * @param smsContent
	 * @return
	 */
	void insert(SmsContent smsContent);
	/**
	 * 依据短信模版发送短信验证码
	 * @param sst 短信模版
	 * @param mobile 目标手机号，手机号可以多个，以逗号（,）分割，最多支持100个手机号。单个手机号必须11位，并符合手机号码的规范
	 * @param parameters 模版变量值
	 */
	void sendSms(SMS_TEMPLATE st,String mobile,Map<String, Object> parameters);
	/**
	 * 发送短信
	 * @param content 内容
	 * @param mobile 目标手机号，手机号可以多个，以逗号（,）分割，最多支持100个手机号。单个手机号必须11位，并符合手机号码的规范
	 * throws Exception 
	 * 
	 * 平台会将内容以普通的优先级尽快地发送给指定手机
	 */
	@Deprecated
	void sendSms(String content, String mobile) throws Exception;
	
	/**
	 * 使用群发通道发送短信
	 * @param content 内容
	 * @param mobile 目标手机号，手机号可以多个，以逗号（,）分割，最多支持100个手机号。单个手机号必须11位，并符合手机号码的规范
	 * throws Exception 
	 * 
	 * 平台会将内容以普通的优先级使用群发通道尽快地发送给指定手机
	 */	
	void sendQunFaSms(String content, String mobile) throws Exception;
	
	/**
	 * 发送短信
	 * @param content 内容
	 * @param mobile 目标手机号，手机号可以多个，以逗号（,）分割，最多支持100个手机号。单个手机号必须11位，并符合手机号码的规范
	 * @param date 发送日期
	 * throws Exception
	 * 
	 * 平台会将内容以普通的优先级在指定的日期之后尽快地发送给指定手机
	 */
	void sendSms(String content, String mobile, Date date) throws Exception;
	
	/**
	 * 发送短信
	 * @param content 内容
	 * @param mobile 目标手机号，手机号可以多个，以逗号（,）分割，最多支持100个手机号。单个手机号必须11位，并符合手机号码的规范
	 * @param priority 优先级，值为1到10之间的整数
	 * throws Exception
	 * 
	 * 平台会将内容以指定的优先级尽快地发送给指定手机
	 */
	void sendSms(String content, String mobile, Integer priority) throws Exception;
	
	/**
	 * 发送短信
	 * @param content 内容
	 * @param mobile 目标手机号，手机号可以多个，以逗号（,）分割，最多支持100个手机号。单个手机号必须11位，并符合手机号码的规范
	 * @param type 短信类型
	 * throws Exception
	 * 
	 * 平台会将内容以普通的优先级尽快地发送给指定手机，短信类型与发送短信的通道选择有关，
	 * 这取决于短信发送平台的配置
	 */
	void sendSmsWithType(String content, String mobile, String type) throws Exception;
	
	/**
	 * 发送短信
	 * @param content 内容
	 * @param mobile 目标手机号，手机号可以多个，以逗号（,）分割，最多支持100个手机号。单个手机号必须11位，并符合手机号码的规范
	 * @param priority 优先级，值为1到10之间的整数
	 * @param type 短信类型
	 * @param date 发送日期
	 * throws Exception
	 * 
	 * 平台会将内容以普通的优先级在指定的日期之后尽快地发送给指定手机，短信类型与发送短信的通
	 * 道选择有关，这取决于短信发送平台的配置
	 */
	void sendSms(String content, String mobile, int priority, String type, Date date) throws Exception;

	/**
	 * 发送短信
	 * @param content 内容
	 * @param mobile 目标手机号，手机号可以多个，以逗号（,）分割，最多支持100个手机号。单个手机号必须11位，并符合手机号码的规范
	 * @param priority 优先级，值为1到10之间的整数
	 * @param type 短信类型
	 * @param date 发送日期
	 * @param userId  请求发送的用户
	 * throws Exception
	 * 
	 * 平台会将内容以普通的优先级在指定的日期之后尽快地发送给指定手机，短信类型与发送短信的通
	 * 道选择有关，这取决于短信发送平台的配置
	 */	
	void sendSms(String content, String mobile, int priority, String type, Date date, String userId) throws Exception;
	
	SmsContent sendSmsContent(String content, String mobile, int priority, String type, Date date, String userId) throws Exception;
	
	/**
	 * 发送短信
	 * @param content 内容
	 * @param mobile 目标手机号数组
	 * throws Exception 
	 * 
	 * 平台会将内容以普通的优先级尽快地发送给指定手机
	 */	
	void sendSms(String content, String[] mobile) throws Exception;
	
	/**
	 * 发送短信
	 * @param content 内容
	 * @param mobile 目标手机号数组
	 * @param date 发送日期
	 * throws Exception
	 * 
	 * 平台会将内容以普通的优先级在指定的日期之后尽快地发送给指定手机
	 */	
	void sendSms(String content, String[] mobile, Date date) throws Exception;
	
	/**
	 * 发送短信
	 * @param content 内容
	 * @param mobile 目标手机号数组
	 * @param priority 优先级，值为1到10之间的整数
	 * throws Exception
	 * 
	 * 平台会将内容以指定的优先级尽快地发送给指定手机
	 */	
	void sendSms(String content, String[] mobile, Integer priority) throws Exception;
	
	/**
	 * 发送短信
	 * @param content 内容
	 * @param mobile 目标手机号数组
	 * @param type 短信类型
	 * throws Exception
	 * 
	 * 平台会将内容以普通的优先级尽快地发送给指定手机，短信类型与发送短信的通道选择有关，
	 * 这取决于短信发送平台的配置
	 */	
	void sendSms(String content, String[] mobile, String type) throws Exception;
	
	/**
	 * 发送短信
	 * @param content 内容
	 * @param mobile 目标手机号数组
	 * @param priority 优先级，值为1到10之间的整数
	 * @param type 短信类型
	 * @param date 发送日期
	 * throws Exception
	 * 
	 * 平台会将内容以普通的优先级在指定的日期之后尽快地发送给指定手机，短信类型与发送短信的通
	 * 道选择有关，这取决于短信发送平台的配置
	 */	
	void sendSms(String content, String[] mobile, int priority, String type, Date date) throws Exception;

	/**
	 * 发送短信
	 * @param content 内容
	 * @param mobile 目标手机号数组
	 * @param priority 优先级，值为1到10之间的整数
	 * @param type 短信类型
	 * @param date 发送日期
	 * @param userId 请求发送的用户
	 * throws Exception
	 * 
	 * 平台会将内容以普通的优先级在指定的日期之后尽快地发送给指定手机，短信类型与发送短信的通
	 * 道选择有关，这取决于短信发送平台的配置
	 */		
	void sendSms(String content, String[] mobile, int priority, String type, Date date, String userId) throws Exception;
	
	/**
	 * 在工作时间内发送短信
	 * @param content 短信内容
	 * @param mobile 目标手机号
	 * @throws Exception
	 * 
	 * 根据平台的工作时间定义，在平台工作时间内发送短信。其是指定时间发送短信的另一种形式。
	 */
	void sendSmsInWorking(String content, String mobile) throws Exception;
	
	/**
	 * 发送彩信
	 * @param texts  文本内容
	 * @param pictures  图片内容，目前图片仅支持jpg格式
	 * @param mids 音频内容，目前仅支持mid格式
	 * @param mobile 目标手机
	 * @param type 短信类型
	 * @throws Exception
	 */
	void sendMMSms(String[] texts, byte[][] pictures, byte[][] mids, String mobile, String type) throws Exception;

	/**
	 * 
	 * @param mms 彩信元素
	 * @param mobile 目标手机
	 * @param type 短信类型
	 * @throws Exception
	 */
	void sendMMSms(SmsMMS mms, String mobile, String type) throws Exception;
	/**
	 * 根据查询条件，查找需要发送的短信
	 * @param parameters 查询条件
	 * @return
	 */
	List<SmsContent> queryWaittingSendSms(Map<String, Object> parameters);
	
	/**
	 * 根据关联查询短信信息
	 * @param param
	 * @return
	 */
	List<SmsContent> queryByParam(Map<String, Object> param);
	
	/**
	 * 根据查询条件，查找发送记录
	 * @param parameters 查询条件
	 * @return
	 */
	List<SmsContentLog> querySendedSms(Map<String, Object> parameters);
	
	/**
	 * 根据短信标识，增加短信发送失败次数
	 * @param id
	 */
	void updateFailureCount(Serializable id);
	
	/**
	 * 移动短信从等待发送到发送记录中
	 * @param parameters
	 */
	void moveSmsFromWaittingToSended(SmsContent smsContent);
	
	/**
	 * 移动复制短信从等待发送到发送记录中
	 * @param parameters
	 */
	void batchMoveSmsFromWaittingToSended(Map<String, Object> parameters);
	
	/**
	 * 更新发送记录的发送结果
	 * @param serialId 流水号
	 * @param result 发送报告的结果，值为SmsContentLog.REPORT_FOR_SUCCESS或者SmsContentLog.REPORT_FOR_ERROR
	 * @param date 接收到的日期
	 * @param memo 备忘
	 */
	void updateReport(List<SmsStatusReport> reportStatusList);
	
	/**
	 * 保存短信上行记录
	 * @param smsReceive
	 */
	void saveSmsReceive(SmsReceive smsReceive);
	
	/**
	 * 重发短信
	 * @param id 发送记录的标识
	 */
	void reSendSms(Serializable id);
	
	/**
	 * 短信统计
	 * @param parm
	 * @return
	 */
	List<SmsLogStat> queryStat(final Map<String, Object> parm);
	
	/**
	 * 彩信数量统计
	 * @param parm
	 * @return
	 */
	long queryMMSStat(final Map<String, Object> parm);
	
	/**
	 * 分页查询短信发送记录
	 * @param parameterObject
	 * @param start
	 * @param limit
	 * @return
	 */
	Page<SmsContentLog> queryPaginationSms(Map<String, Object> parameterObject, int start, int limit);	
	
	/**
	 * 移动某一时间段前的发送记录到历史表
	 * @param parm
	 */
	void moveContentLogToHis(final Map<String, Object> parm);
	
	long count(final Map<String, Object> parameters);
	
	void sendMMSms(byte[][] is) throws Exception;
	
	
	/**
	 * 查询已发送短信列表
	 * @param params
	 * @return
	 */
	public List<SmsContentLog> getSmsLogListByParams(Map<String, Object> params);
	
	
	/**
	 * 查询已发送短信列表数量
	 * @param params
	 * @return
	 */
	public Long getSmsLogListCountByParams(Map<String, Object> params);
	
	/**
	 * 重发短信,指定需要重发的短信的id，短信通道，原短信所在表
	 * @param id 需要重发的短信的id
	 * @param channel 短信发送通道
	 * @param channel 原短信所在表的名称
	 * @param extendParam 扩展参数
	 */
	public void reSendSms(Long id,String channel,String tableName,Map<Object, Object> extendParam);
	
	/**
	 * 修改短信内容
	 * @param smsContent
	 * @return
	 */
	public int update(SmsContent smsContent);
	/**
	 * 更新短信日志信息
	 * @param smsContentLog
	 * @return
	 */
	public int updateSmsLog(SmsContentLog smsContentLog);
	
	/**
	 * 查询短信日志信息
	 * @param paramMap
	 * @return
	 */
	public List<SmsContentLog> getSmsLogList(Map<String, Object> paramMap);
	
	/**
	 * 获取推送到第三方失败以及发送失败的短信列表，这些短信需要自动重发
	 * @return
	 */
	public List<SmsContentLog> getResendSmsLogList();
}
