package com.lvmama.pet.sms.service;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.pub.ComSmsTemplate;
import com.lvmama.comm.pet.po.sms.SmsContent;
import com.lvmama.comm.pet.po.sms.SmsContentLog;
import com.lvmama.comm.pet.po.sms.SmsMMS;
import com.lvmama.comm.pet.po.sms.SmsReceive;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.pet.vo.SmsLogStat;
import com.lvmama.comm.pet.vo.SuZhouLeYuanMMSContent;
import com.lvmama.comm.pet.vo.YiMaMMSContent;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SMS_TEMPLATE;
import com.lvmama.comm.vo.SmsStatusReport;
import com.lvmama.pet.pub.dao.ComSmsTemplateDAO;
import com.lvmama.pet.sms.dao.SmsContentDAO;
import com.lvmama.pet.sms.dao.SmsContentLogDAO;
import com.lvmama.pet.sms.dao.SmsReceiveDAO;

public class SmsServiceImpl implements SmsRemoteService {
	private static final Log LOG = LogFactory.getLog(SmsServiceImpl.class);
	private static final Set<String> MMS_TYPE = new TreeSet<String>();

	private int startWorkingtime = 9;
	private int endWorkingtime = 21;
	//中国移动
	private Pattern pattern_cmcc = Pattern.compile("^("+Constant.getInstance().getProperty("CMCC")+")[0-9]{8}$");
	//中国联通
	private Pattern pattern_cuc = Pattern.compile("^("+Constant.getInstance().getProperty("CUC")+")[0-9]{8}$");
	//中国电信
	private Pattern pattern_ct = Pattern.compile("^("+Constant.getInstance().getProperty("CT")+")[0-9]{8}$");
	//短信内容格式 
	private Pattern pattern_content = Pattern.compile("^(.+)(\\#\\{(.*)\\|(.*)\\|(.*)\\|(.*)\\}\\#)$",Pattern.DOTALL); 
		
	static {
		MMS_TYPE.add("SUZHOULEYUAN");
		MMS_TYPE.add("YIMA");
	}

	@Autowired
	private SmsContentDAO smsContentDAO;
	@Autowired
	private SmsContentLogDAO smsContentLogDAO;
	@Autowired
	private SmsReceiveDAO smsReceiveDAO;
	@Autowired
	private ComSmsTemplateDAO comSmsTemplateDAO;
	public void insert(SmsContent smsContent){
		this.smsContentDAO.insert(smsContent);
	}
	
	/**
	 * 依据短信模版发送短信验证码
	 * @param st 短信模版
	 * @param mobile 目标手机号，手机号可以多个，以逗号（,）分割，最多支持100个手机号。单个手机号必须11位，并符合手机号码的规范
	 * @param parameters 模版变量值
	 */
	public void sendSms(SMS_TEMPLATE st,String mobile,Map<String, Object> parameters){
		ComSmsTemplate cst = comSmsTemplateDAO.selectByPrimaryKey(st.name());
		
		String content = null;
		if(cst != null)
		{
		   try {
			   content = StringUtil.composeMessage(cst.getContent(), parameters);
			   SmsContent sms = new SmsContent(content, mobile);
			   //发送通道
			   String channel= generateChannel(mobile,cst.getChannel(),cst.getChannelCMCC(),cst.getChannelCUC(),cst.getChannelCT());
			   sms.setChannel(channel);
			   smsContentDAO.save(sms);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("replace sms template content error!!!!!!!!!!!!!!!!");
			}
		}
		else
		{
			LOG.error("get none template id "+st.name());
		}
	}
	
	private String generateChannel(String mobile,String channelDefault,String channelCMCC,String channelCUC,String channelCT){
		Matcher matcher_cmcc = pattern_cmcc.matcher(mobile);
		Matcher matcher_cuc = pattern_cuc.matcher(mobile);
		Matcher matcher_ct = pattern_ct.matcher(mobile);
		String channel="";
		if(matcher_cmcc.find()	 && StringUtils.isNotEmpty(channelCMCC)){//是移动号码 且 模版对应的短信通道不为空
			channel = channelCMCC;
		}else if(matcher_cuc.find() && StringUtils.isNotEmpty(channelCUC)){//联通号码 且 模版对应的短信通道不为空
			channel = channelCUC;
		}else if(matcher_ct.find() && StringUtils.isNotEmpty(channelCT)){//电信号码 且 模版对应的短信通道不为空
			channel = channelCT;
		}else if(StringUtils.isNotEmpty(channelDefault)){ //使用默认通道
			channel = channelDefault;
		}
		return channel;
	}
	/**
	 * 为了兼容老的接口 需要转换Cotent中包含的通道信息 
	 * 格式如下: 实际发送短信内容#{默认通道|移动号段通道|联通号段通道|电信号段通道}# 
	 * 如: 您的校验码是1234，请在页面中填写此校验码完成验证。【驴妈妈】#{EMAY|EMAY|EMAY|EMAY}#
	 * 
	 * 所有直接把短信内容传入的方式必须调用此方法才可以发送
	 * 
	 * @param sms
	 */
	private void parseSmsContent(SmsContent sms){
		Matcher matcher_content = pattern_content.matcher(sms.getContent());
		if(matcher_content.find()){
			String content = matcher_content.group(1);
			String channel = generateChannel(sms.getMobile(),matcher_content.group(3),matcher_content.group(4),matcher_content.group(5),matcher_content.group(6));
			sms.setChannel(channel);
			sms.setContent(content);
		}
	}
	
	@Override
	public void sendSms(String content, String mobile) throws Exception {
		SmsContent sms = new SmsContent(content, mobile);
		parseSmsContent(sms);
		smsContentDAO.save(sms);
	}

	public void sendQunFaSms(String content, String mobile) throws Exception {
		SmsContent sms = new SmsContent(content, mobile, "QUNFA");
		parseSmsContent(sms);
		smsContentDAO.save(sms);
	}
	
	public void sendSms(String content, String mobile, Date date)
			throws Exception {
		SmsContent sms = new SmsContent(content, mobile, date);
		parseSmsContent(sms);
		smsContentDAO.save(sms);
	}
	
	public void sendSms(String content, String mobile, Integer priority)
			throws Exception {
		SmsContent sms = new SmsContent(content, mobile, priority);
		parseSmsContent(sms);
		smsContentDAO.save(sms);
	}
	
	public void sendSmsWithType(String content, String mobile, String type)
			throws Exception {
		SmsContent sms = new SmsContent(content, mobile, type);
		parseSmsContent(sms);
		smsContentDAO.save(sms);
	}
	
	public void sendSms(String content, String mobile, int priority,
			String type, Date date) throws Exception {
		SmsContent sms = new SmsContent(content, mobile, priority, type, date);
		parseSmsContent(sms);
		smsContentDAO.save(sms);
	}
	
	@Override
	public void sendSms(String content, String mobile, int priority, String type, Date date, String userId) throws Exception {
		SmsContent sms = new SmsContent(content, mobile, priority, type, date);
		if (StringUtils.isNotEmpty(userId)) {
			sms.setUserId(userId);
		}
		parseSmsContent(sms);
		smsContentDAO.save(sms);	
	}
	
	public SmsContent sendSmsContent(String content, String mobile, int priority, String type, Date date, String userId) throws Exception{
		SmsContent sms = new SmsContent(content, mobile, priority, type, date);
		if (StringUtils.isNotEmpty(userId)) {
			sms.setUserId(userId);
		}
		parseSmsContent(sms);
		return smsContentDAO.save(sms);
	}
	
	public void sendSms(final String content, final String[] mobiles)
			throws Exception {
		Set<String> mobileSet = new TreeSet<String>();
		for (String mobile : mobiles) {
			mobileSet.add(mobile);
		}

		Iterator<String> mobileIterator = mobileSet.iterator();
		while (mobileIterator.hasNext()) {
			SmsContent sms = new SmsContent(content, mobileIterator.next());
			parseSmsContent(sms);
			smsContentDAO.save(sms);
		}
	}
	
	public void sendSms(final String content, final String[] mobiles,
			final Date date) throws Exception {
		Set<String> mobileSet = new TreeSet<String>();
		for (String mobile : mobiles) {
			mobileSet.add(mobile);
		}

		Iterator<String> mobileIterator = mobileSet.iterator();
		while (mobileIterator.hasNext()) {
			SmsContent sms = new SmsContent(content, mobileIterator.next(),
					date);
			parseSmsContent(sms);
			smsContentDAO.save(sms);
		}
	}
	
	public void sendSms(final String content, final String[] mobiles,
			final Integer priority) throws Exception {
		Set<String> mobileSet = new TreeSet<String>();
		for (String mobile : mobiles) {
			mobileSet.add(mobile);
		}

		Iterator<String> mobileIterator = mobileSet.iterator();
		while (mobileIterator.hasNext()) {
			SmsContent sms = new SmsContent(content, mobileIterator.next(),
					priority);
			parseSmsContent(sms);
			smsContentDAO.save(sms);
		}
	}
	
	public void sendSms(final String content, final String[] mobiles,
			final String type) throws Exception {
		Set<String> mobileSet = new TreeSet<String>();
		for (String mobile : mobiles) {
			mobileSet.add(mobile);
		}

		Iterator<String> mobileIterator = mobileSet.iterator();
		while (mobileIterator.hasNext()) {
			SmsContent sms = new SmsContent(content, mobileIterator.next(),
					type);
			parseSmsContent(sms);
			smsContentDAO.save(sms);
		}
	}
	
	public void sendSms(final String content, final String[] mobiles,
			final int priority, String type, Date date) throws Exception {
		Set<String> mobileSet = new TreeSet<String>();
		for (String mobile : mobiles) {
			mobileSet.add(mobile);
		}

		Iterator<String> mobileIterator = mobileSet.iterator();
		while (mobileIterator.hasNext()) {
			SmsContent sms = new SmsContent(content, mobileIterator.next(),
					priority, type, date);
			parseSmsContent(sms);
			smsContentDAO.save(sms);
		}
	}
	
	@Override
	public void sendSms(final String content, final String[] mobiles, final int priority,
			final String type, final Date date, final String userId) throws Exception {
		Set<String> mobileSet = new TreeSet<String>();
		for (String mobile : mobiles) {
			mobileSet.add(mobile);
		}

		Iterator<String> mobileIterator = mobileSet.iterator();
		while (mobileIterator.hasNext()) {
			SmsContent sms = new SmsContent(content, mobileIterator.next(),
					priority, type, date);
			if (!StringUtils.isEmpty(userId)) {
				sms.setUserId(userId);
			}
			parseSmsContent(sms);
			smsContentDAO.save(sms);
		}		
	}	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page queryPaginationSms(String mobile, Date startDate, Date endDate,
			int status, int start, int limit) {
		Map parameters = new HashMap();
		parameters.put("mobile", mobile);
		parameters.put("startDate", startDate);
		parameters.put("endDate", endDate);
		parameters.put("status", status);
		return smsContentLogDAO.pagedQuery(parameters, start * limit, limit);
	}

	@SuppressWarnings({"unchecked" })
	@Override
	public Page<SmsContentLog> queryPaginationSms(Map<String, Object> parameterObject, int start, int limit) {
		return smsContentLogDAO.pagedQuery(parameterObject, start+1,
				limit);
	}

	public void reSend(Long id) {
		Object obj = smsContentLogDAO.querySmsContentLogByPk(id);
		try {
			if (null != obj) {
				SmsContentLog smsContentLog = (SmsContentLog) obj;
				SmsContent smscontent = new SmsContent(
						smsContentLog.getContent(), smsContentLog.getMobile());
				smscontent.setPriority(smsContentLog.getPriority());
				smscontent.setType(smsContentLog.getType());
				smsContentDAO.save(smscontent);
			}
		} catch (Exception e) {
			LOG.warn("无法进行有效的重发操作!" + e.getMessage());
		}
	}

	@Override
	public void sendSmsInWorking(String content, String mobile)
			throws Exception {
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.HOUR_OF_DAY) <= this.endWorkingtime
				&& calendar.get(Calendar.HOUR_OF_DAY) >= this.startWorkingtime) {
			sendSms(content, mobile);
		} else {
			if (calendar.get(Calendar.HOUR_OF_DAY) < 9) {
				calendar.set(Calendar.HOUR_OF_DAY, 9);
				sendSms(content, mobile, calendar.getTime());
			} else {
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				calendar.set(Calendar.HOUR_OF_DAY, 9);
				sendSms(content, mobile, 5, "QUNFA", calendar.getTime());
			}
		}
	}

	@Override
	public void sendMMSms(String[] texts, byte[][] pictures, byte[][] mids,
			String mobile, String type) throws Exception {
		if (null == type || !MMS_TYPE.contains(type)) {
			throw new Exception("类型只能设置成" + MMS_TYPE);
		}
		if ("SUZHOULEYUAN".equals(type)) {
			smsContentDAO.save(new SuZhouLeYuanMMSContent(texts, pictures,
					mids, mobile));
		}
		if ("YIMA".equals(type)) {
			smsContentDAO
					.save(new YiMaMMSContent(texts, pictures, mids, mobile));
		}
	}
	
	
	@Override
	public void sendMMSms(SmsMMS mms, String mobile, String type) throws Exception {
		if (null != mms) {
			sendMMSms(new String[]{mms.getText()}, new byte[][]{mms.getPics()}, new byte[][]{mms.getMids()}, mobile, type);
		}
	}
	
	@Override
	public List<SmsContent> queryWaittingSendSms(Map<String, Object> parameters) {
		return smsContentDAO.query(parameters);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SmsContentLog> querySendedSms(Map<String, Object> parameters) {
		return smsContentLogDAO.query(parameters);
	}

	@Override
	public void updateFailureCount(Serializable id) {
		smsContentDAO.updateFailureCount(id);	
	}

	@Override
	public void moveSmsFromWaittingToSended(SmsContent smsContent) {
		if (null != smsContent) {
			try {
				smsContentLogDAO.save(SmsContentLog.convertToSmsContentLog(smsContent));
				smsContentDAO.deleteByPrimaryKey(smsContent.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void batchMoveSmsFromWaittingToSended(Map<String, Object> parameters) {
		smsContentLogDAO.saveBatch(parameters);
		smsContentDAO.delete(parameters);
	}

	@Override
	public void updateReport(List<SmsStatusReport> reportStatusList) {
		if (null == reportStatusList || reportStatusList.isEmpty()) {
			return;
		}
		for (SmsStatusReport status : reportStatusList) {
			if (null != status.getSerialId()) {
				smsContentLogDAO.updateReport(status.getSerialId(), status.getResult(), status.getDate(), status.getMemo());
			} else {
				if (null != status.getBeforeMemo()) {
					smsContentLogDAO.updateReport(status.getBeforeMemo(), status.getResult(), status.getDate(), status.getMemo());
				}
			}
		}
	}
	
	@Override
	public long count(final Map<String, Object> parameters){
		return smsContentLogDAO.count(parameters);
	}
	
	public void moveContentLogToHis(Map<String, Object> parm) {
		smsContentLogDAO.moveSmsContentLogToHis(parm);
	}
	
	@Override
	public void saveSmsReceive(SmsReceive smsReceive) {
		smsReceiveDAO.save(smsReceive);
	}
	
	@Override
	public void reSendSms(Serializable id) {
		smsContentDAO.retrySend(id);
	}

	@Override
	public List<SmsLogStat> queryStat(final Map<String, Object> parm) {
		return smsContentLogDAO.queryStat(parm);
	}
	
	@Override
	public long queryMMSStat(final Map<String, Object> parm) {
		return smsContentLogDAO.queryMMSStat(parm);
	}
	
	@Override
	public List<SmsContent> queryByParam(Map<String, Object> param) {
		return smsContentDAO.queryByParam(param);
	}


	// setter and getter
	public int getStartWorkingtime() {
		return startWorkingtime;
	}

	public void setStartWorkingtime(final int startWorkingtime) {
		this.startWorkingtime = startWorkingtime;
	}

	public int getEndWorkingtime() {
		return endWorkingtime;
	}

	public void setEndWorkingtime(final int endWorkingtime) {
		this.endWorkingtime = endWorkingtime;
	}
	
	public void sendMMSms(byte[][] is) throws Exception {
		for (byte[] i : is) {
		System.out.print(new String(i));
		}
	}
	
	/**
	 * 查询已发送短信列表
	 * @param params
	 * @return
	 */
	public List<SmsContentLog> getSmsLogListByParams(Map<String, Object> params)
	{
		return this.smsContentLogDAO.getListByParams(params);
	}
	
	/**
	 * 查询已发送短信列表数量
	 * @param params
	 * @return
	 */
	public Long getSmsLogListCountByParams(Map<String, Object> params){
		return this.smsContentLogDAO.getListCountByParams(params);
	}
	
	/**
	 * 重发短信,指定需要重发的短信的id，短信通道，原短信所在表
	 * @param id 需要重发的短信的id
	 * @param channel 短信发送通道
	 * @param channel 原短信所在表的名称
	 * @param extendParam 扩展参数
	 */
	public void reSendSms(Long id,String channel,String tableName,Map<Object, Object> extendParam){
		if(extendParam==null){
			extendParam = new HashMap<Object, Object>();
		}
		extendParam.put("id", id);
		extendParam.put("channel", channel);
		extendParam.put("tableName", tableName);
		this.smsContentDAO.reSendSmsByMap(extendParam);
	}
	
	/**
	 * 更新短信内容信息
	 */
	public int update(SmsContent smsContent){
		if(smsContent.getId()!=null){
			return this.smsContentDAO.update(smsContent);
		}else{
			return -1;
		}
	}
	
	/**
	 * 更新短信日志信息
	 * @param smsContentLog
	 * @return
	 */
	public int updateSmsLog(SmsContentLog smsContentLog){
		return this.smsContentLogDAO.updateSmsLog(smsContentLog);
	}
	
	/**
	 * 查询短信日志信息
	 * @param paramMap
	 * @return
	 */
	public List<SmsContentLog> getSmsLogList(Map<String, Object> paramMap){
		return this.smsContentLogDAO.getSmsLogListByParams(paramMap);
	}
	/**
	 * 获取推送到第三方失败以及发送失败的短信列表，这些短信需要自动重发
	 * @return
	 */
	public List<SmsContentLog> getResendSmsLogList(){
		return this.smsContentLogDAO.getResendSmsLogList();
	}
}
