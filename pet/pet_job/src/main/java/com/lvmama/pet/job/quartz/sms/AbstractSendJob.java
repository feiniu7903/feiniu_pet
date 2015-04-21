package com.lvmama.pet.job.quartz.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.sms.SmsContent;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.SmsStatusReport;
import com.lvmama.comm.vo.Constant.SMS_ALL_STATUS;


/**
 * 短信发送的抽象类
 *      所有的短信发送类都应该基于此类实现
 * @author Brian
 *
 */
public abstract class AbstractSendJob {
	
	public static final String QIANMING = "【驴妈妈】";
	/**
	 * 手机号的分隔符
	 */
	public static final String SEPARATOR = ",";
	/**
	 * 默认短信的优先级
	 */
	public static final int DEFAULT_PRIORITY = 5;
	/**
	 * 默认每次短信发送数
	 */
	public static final int DEFAULT_LIMIT = 500;
	/**
	 * 不支持长短信的短信的每条默认字符数
	 */
	public static final int NUMBER_OF_CHARS_PER_SHORT_MESSAGE = 70;
	/**
	 * 短信标识的随机数
	 */
	public static final int NUMBER_OF_OFFSET_RONDOM = 1000;
	/**
	 * 日志输出器
	 */
	public final Log LOG = LogFactory.getLog(this.getClass());
	/**
	 * 短信发送成功的返回值
	 */
	protected static final int SUCCESS = 0;
	/**
	 * 短信发送失败的返回值
	 */
	protected static final int ERROR = 1;
	/**
	 * 忽略短信发送结果
	 */
	protected static final int IGNORE = -1;

	/**
	 * 是否实际发送开关
	 */
	private boolean actualSend = false;
    /**
     * 发送短信的条件
     */
	protected String condition;
	/**
     * 发送短信的附加条件
     */
	protected String additionalCondition;
	/**
	 * 失败的次数
	 */
	private int failure;
   /**
    * 发送短信的数目
    */
	private int limit = DEFAULT_LIMIT;
	
	protected String tableSource;
	/**
	 * 是否支持自动拆分短信内容
	 */
	private boolean autoSplitContent = true;
	/**
	 * 是否支持批量发送
	 */
	private boolean supportBatchSend = true;
	/**
	 * 拆分短信内容的字数
	 */
	private int size = NUMBER_OF_CHARS_PER_SHORT_MESSAGE;
	/**
	 * 短信远程服务
	 */
	protected SmsRemoteService smsRemoteService;
	
	/**
	 * 短信的唯一标识
	 */
	protected String serialId = null;
	
	/**
	 * 发送短信
	 */
	public void run() {
		if (Constant.getInstance().isJobRunnable()/*&&false*/) {
			try{
				onStart();
				List<SmsContent> list = queryWaittingSendContent();
				disposeWaitSms(list);
				if (null != list && list.size() > 0) {
					LOG.info("SMS send job running, sms list = " + list.size());
					for (SmsContent sms : list) {
						switch (actualSend(sms)) {
							case SUCCESS:
								onSuccess(sms, autoSplitContent);
								break;
							case IGNORE:
								break;
							case ERROR:
								onError(sms);
								break;
							default:
								onError(sms);
						}
					}
				}
				onEnd();
			}catch(Exception e) {
				LOG.error("发送短信异常:"+e.getMessage(),e);
			}
		}
	}
	/**
	 * 不同配置添加查询参数
	 * @param parameters
	 */
	protected void addQueryParams(Map<String , Object> parameters){};
	/**
	 * 获取需要发送的短信列表
	 * @return 等待发送短信列表
	 */
	protected List<SmsContent> queryWaittingSendContent() {
		Map<String , Object> parameters = new HashMap<String , Object>();
		if (null != condition && condition.trim().length() > 0) {
			parameters.put("condition", condition);
		}
		if (null != additionalCondition && additionalCondition.trim().length() > 0) {
			parameters.put("additionalCondition", additionalCondition);
		}
		if (null != tableSource && tableSource.trim().length() > 0) {
			parameters.put("tableSource", tableSource);
		}else{
			parameters.put("tableSource", "sms_content a");
		}
		
		if (failure > 0) {
			parameters.put("failure", failure);
		}
		if (limit != 0) {
			parameters.put("limit", limit);
		}
		LOG.info("短信查询参数:"+parameters);
		return smsRemoteService.queryWaittingSendSms(parameters);
	}

	/**
	 * 每次执行时预先处理的过程
	 */
	protected void onStart() {	}
	
	/**
	 * 设置待发送短信的渠道
	 * @param list
	 */
	protected void disposeWaitSms(List<SmsContent> list){
		if(list!=null){
			for (SmsContent smsContent : list) {
				smsContent.setChannel(this.getChannel());
				this.smsRemoteService.update(smsContent);
			}
		}
	}
	
	/**
	 * 获取短信通道名称
	 * @return
	 */
	protected String getChannel(){return null;};

	/**
	 * 模板模式的发送逻辑
	 * @param smsContent 需要发送的短信
	 * @return 发送结果
	 */
	protected int actualSend(final SmsContent smsContent) {
		if (null == smsContent || null == smsContent.getMobile()) {
			return ERROR;
		}
		LOG.info("try to send " + smsContent.getId());
		
		//短信设置唯一的序列号
		smsContent.setSerialId(generateSerialNumber());
		//支持批量发送及内容分隔的供应商直接发送
		if (supportBatchSend && autoSplitContent) {
			return send(smsContent);
		}
		//支持内容分隔且不需要批量发送的短信直接发送
		if (autoSplitContent && smsContent.getMobile().indexOf(SmsContent.SEPARATOR) == -1) {
			return send(smsContent);
		}
		//支持批量且内容无需风格的短信直接发送
		if (supportBatchSend && null != smsContent.getContent() && smsContent.getContent().length() <= size) {
			return send(smsContent);
		}
		//切分批量手机
		String[] mobiles = null;
		if (supportBatchSend) {
			mobiles = new String[]{smsContent.getMobile()};
		} else {
			mobiles = smsContent.getMobile().split(SmsContent.SEPARATOR);
		}
		for (String mobile : mobiles) {
			SmsContent content = null;
			if (autoSplitContent) {
				try {
					content = new SmsContent(smsContent.getContent(), mobile, smsContent.getPriority(),
							smsContent.getType(), smsContent.getSendDate());
					content.setId(smsContent.getId());
					content.setFailure(smsContent.getFailure());
					content.setSerialId(generateSerialNumber());
				} catch (Exception exception) {
					LOG.error("Split sms error!" + exception.getMessage());
					continue;
				}
				return send(content);
			} else {
				StringBuilder sb = new StringBuilder(smsContent.getContent());
				int i = 0;
				while (i <= sb.length()) {
					try {
						content = new SmsContent(sb.substring(i, i + size > sb.length() ? sb.length() : i + size),
								mobile, smsContent.getPriority(), smsContent.getType(), smsContent.getSendDate());
						content.setId(smsContent.getId());
						content.setFailure(smsContent.getFailure());
						content.setSerialId(generateSerialNumber());
					} catch (Exception exception) {
						LOG.error("Split sms error!" + exception.getMessage());
						i = i + size + 1;
						continue;
					}
					return send(content);
				}
			}
		}
		return IGNORE;
	}

	/**
	 * 短信发送成功后的处理
	 * @param smsContent 短信
	 * @param autoSplit 是否自动切分
	 */
	protected void onSuccess(final SmsContent smsContent, final boolean autoSplit) {
		try {
			//短信提供商自动提供拆分服务，只需将短信内容保存到日志表即可
			if (autoSplit) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Needn't split, save directly");
				}
				smsContent.setStatus(SMS_ALL_STATUS.WAITREPORT.getStatus());
				smsRemoteService.moveSmsFromWaittingToSended(smsContent);
			} 
//			else {
//				if (LOG.isDebugEnabled()) {
//					LOG.debug("短信需分拆，进行分拆保存处理");
//				}
//				Map<String, Object> parameters = new HashMap<String, Object>();
//				parameters.put("condition", "sms_id = " + smsContent.getId());
//				List contentLogs = smsRemoteService.querySendedSms(parameters);
//				if (null != contentLogs && !contentLogs.isEmpty()) {
//					if (LOG.isDebugEnabled()) {
//						LOG.debug("分拆的短信原始内容已经保存在日志表中，无需添加新的日志记录，只需更新发送条数和新增日志子记录。");
//					}
//					Long id = ((SmsContentLog) contentLogs.get(0)).getId();
//					smsContentLogDao.increaseNumbers(id);
//					SmsContentSubLog subLog = new SmsContentSubLog(id, smsContent.getContent(), smsContent.getMobile());
//					smsContentSubLogDao.save(subLog);
//				} else {
//					if (LOG.isDebugEnabled()) {
//						LOG.debug("分拆的短信原始内容尚未保存在日志表中，需要添加日志记录，并添加日志子记录");
//					}
//					SmsContent origContent = smsContentDao.getByPrimaryKey(smsContent.getId());
//					if (null == origContent) {
//						LOG.warn("无法找到原始的短信内容,id = " + smsContent.getId());
//					}
//					SmsContentLog log = SmsContentLog.convertToSmsContentLog(origContent);
//					smsContentLogDao.save(log);
//
//					SmsContentSubLog subLog = new SmsContentSubLog(log.getId(),
//							smsContent.getContent(), smsContent.getMobile());
//					smsContentSubLogDao.save(subLog);
//
//					smsContentDao.deleteByPrimaryKey(log.getId());
//				}
//			}
		} catch (Exception e) {
			LOG.error(smsContent + "encounter exception!" + e.getMessage());
		}
	}

	/**
	 * 短信发送失败后的处理
	 * @param smsContent 短信
	 */
	protected void onError(final SmsContent smsContent) {
		//更新状态为推送失败
		smsContent.setStatus(SMS_ALL_STATUS.PUSHFAIL.getStatus());
		this.smsRemoteService.update(smsContent);
		//更新失败次数信息
		smsRemoteService.updateFailureCount(smsContent.getId());
	}

	/**
	 * 每次执行后处理的过程
	 */
	protected void onEnd() {
		if (failure > 0) {
			Map<String , Object> parameters = new HashMap<String , Object>();
			if (null != condition && condition.trim().length() > 0) {
				parameters.put("condition", condition);
			}
			parameters.put("failure", failure);
			smsRemoteService.batchMoveSmsFromWaittingToSended(parameters);
		}
	}

	/**
	 * 获取发送报告后的处理
	 * @param serialId 流水号
	 * @param result 发送报告的结果，值为SmsContentLog.REPORT_FOR_SUCCESS或者SmsContentLog.REPORT_FOR_ERROR
	 * @param date 接收到的日期
	 * @param memo 备忘
	 */
	protected void onReport(final List<SmsStatusReport> StatusReportList) {
		for (SmsStatusReport smsStatusReport : StatusReportList) {
			LOG.info("serialId: " + smsStatusReport.getSerialId() + " result: " + smsStatusReport.getResult());
		}
		smsRemoteService.updateReport(StatusReportList);
	}

	/**
	 * 系统自动重发（慎用）
	 * @param serialId 流水号
	 * 此功能只用于当某种原因下，在一定时间内未收到状态的短信的重发。系统会根据流水号的短信的内容和目标手机
	 * 重新发送一条全新的短信，且Failure会在原有的failure数值上加1。但全新的短信是否发送还是受限于短信平台
	 * 本身所配置的条件。
	 */
//	protected void onAutoSend(final Long serialId) {
//		LOG.info("重发流水号为" + serialId + "的短信");
//		smsRemoteService.updateReport(serialId, SmsContentLog.REPORT_FOR_TIMEOUT, new Date(), null);
//	}

	/**
	 * 具体发送短信的实现
	 * @param smsContent 需要发送的短信
	 * @return 发送结果
	 */
	protected abstract int send(final SmsContent smsContent);

	/**
	 * 生成唯一的标识符
	 * @return 随机标识符
	 */
	private long generateSerialNumber() {
		return System.currentTimeMillis() * NUMBER_OF_OFFSET_RONDOM
				+ (int) Math.ceil((Math.random() * NUMBER_OF_OFFSET_RONDOM));
	}

	//setter and getter
	public String getCondition() {
		return condition;
	}

	public void setCondition(final String condition) {
		this.condition = condition;
	}

	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(final int limit) {
		this.limit = limit;
	}

	public boolean isAutoSplitContent() {
		return autoSplitContent;
	}

	public void setAutoSplitContent(final boolean autoSplitContent) {
		this.autoSplitContent = autoSplitContent;
	}

	public int getSize() {
		return size;
	}

	public void setSize(final int size) {
		this.size = size;
	}

	public int getFailure() {
		return failure;
	}

	public void setFailure(final int failure) {
		this.failure = failure;
	}

	public boolean isSupportBatchSend() {
		return supportBatchSend;
	}

	public void setSupportBatchSend(final boolean supportBatchSend) {
		this.supportBatchSend = supportBatchSend;
	}

	public boolean isActualSend() {
		return actualSend;
	}

	public void setActualSend(final boolean actualSend) {
		this.actualSend = actualSend;
	}
	public String getTableSource() {
		return tableSource;
	}
	public void setTableSource(String tableSource) {
		this.tableSource = tableSource;
	}
	public String getAdditionalCondition() {
		return additionalCondition;
	}
	public void setAdditionalCondition(String additionalCondition) {
		this.additionalCondition = additionalCondition;
	}


}
