package com.lvmama.pet.web.sms;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;

import com.lvmama.comm.pet.po.sms.SmsContentLog;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.pet.utils.ZkMessage;
import com.lvmama.pet.utils.ZkMsgCallBack;
public class SmsAction extends com.lvmama.pet.web.BaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 7740684279256111332L;
	/**
	 * 日志输出器
	 */
	private static final Logger LOG = Logger.getLogger(SmsAction.class);
	/**
	 * 变量分割符
	 */
	private static final String SPLIT_MSG_SIGN = "##";
	/**
	 * 手机变量名
	 */
	private static final String MOBILE_KEY = "mobile";
	private SmsRemoteService smsRemoteService;
	private List<SmsContentLog> smsContentLoglist;
	private String mobile;
	private String status;
	private Date startDate;
	private Date endDate;
	private boolean searchAllFlag;
	private String content;
	private Date sendDate;
	private String policySend;
	private String furl;
	private String userId;
	private String sendChannel = "QUNFA";
	
	/**
	 * 关键字
	 */
	private String keyword;
	
	/**
	 * 是否是有变量的文件
	 */
	private boolean hasVar;
	
	/**
	 * 短信变量链表
	 */
	private List<Map<String,Object>> msgVarList;
	
	/**
	 * 初始化
	 */
	@Override
	public void doBefore() {
		if (!StringUtils.isEmpty(userId)) {
			Session s = Sessions.getCurrent();
			s.setAttribute("userId", userId);
		}
	}	
	
	/**
	 * 短信查询
	 */
	public void forwardSearch() {
		Map<String,Object> map = new HashMap<String,Object>();
		if (!StringUtils.isEmpty(status)) {
			map.put("status", status);
		}
		if (!StringUtils.isEmpty(mobile)) {
			map.put("mobile", mobile);
		}
		if (null != startDate) {
			map.put("startDate", formateDate(startDate));
		}
		if (null != endDate) {
			map.put("endDate", formateDate(endDate));
		}
		if (map.keySet().size() == 0) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -1);
			map.put("startDate", formateDate(c.getTime()));
		}
		if(!StringUtils.isEmpty(keyword)){
			map.put("keyword", keyword);
		}
		if(searchAllFlag){
			map.put("searchAll", searchAllFlag);
		}
		Page<SmsContentLog> page = smsRemoteService.queryPaginationSms(map, _paging.getActivePage(),
				_paging.getPageSize());
		
		
		_totalRowCountLabel.setValue("" + page.getTotalResultSize());
		_paging.setTotalSize((int) page.getTotalResultSize());
		smsContentLoglist = (List<SmsContentLog>) page.getItems();
	}

	/**
	 * 短信重发
	 */
	public void resend(Map param) {
		final Long id = (Long) param.get("smsId");
		ZkMessage.showQuestion("您确认短信重发？", new ZkMsgCallBack() {
			public void execute() {
				smsRemoteService.reSendSms(id);
				ZkMessage.showInfo("短信重发成功！");
			}
		}, new ZkMsgCallBack() {
			public void execute() {

			}
		});
	}
	/**
	 * 手工发送短信
	 * @param param
	 */
	public void sendByhand(){	
		if (null != getComponent().getFellowIfAny("furl")) {
			furl = ((Textbox) getComponent().getFellow("furl")).getValue();
		}
		if (null != furl && StringUtils.isNotBlank(furl)) {
			hasVar = Boolean.FALSE;
			try {
				List<String> strArray = new ArrayList<String>();
				String keyStr =null; 
				BufferedReader reader = new BufferedReader(new FileReader(furl));
				String temp = null;
				StringBuilder sb = new StringBuilder();
				int i=0;
				while ((temp = reader.readLine()) != null) {
					strArray.add(i++, temp.trim());
				}
				reader.close();
				//查找文件第一行
				if(strArray.size() >= 1){
					keyStr = strArray.get(0);
				}
				if(null == keyStr){
					ZkMessage.showError("上传文件内容有误");
					return;
				}
				hasVar =hasVariable(keyStr);
				//如果有变量，则查找变量对应的值放入变量链表中
				if(hasVar){
					//如果有则取得变量名集合
					List<String> keyArray = StringUtil.split(keyStr, SPLIT_MSG_SIGN);
					msgVarList = new ArrayList<Map<String,Object>>();
					Set<String> mobileSet = new TreeSet<String>();
					for(i=1;strArray.size()>1 && i<strArray.size();i++){
						msgVarList.add(getMsgVar(strArray.get(i),keyArray));
					}
				//没有则添加入手机串
				}else{
					Set<String> mobileSet = new TreeSet<String>();
					for(i=0;i<strArray.size();i++){
						if (!mobileSet.contains(strArray.get(i))) {
							sb.append(strArray.get(i));
							mobileSet.add(strArray.get(i));
						}		
					}
				}
				if (sb.length() != 0) {
					mobile = sb.toString();
				}
			} catch (FileNotFoundException fnfe) {
				LOG.error(fnfe);
				ZkMessage.showError("读取上传的文件出错，无法找到上传的文件");
				return;
			} catch (IOException ioe) {
				LOG.error(ioe);
				ZkMessage.showError("读取上传的文件出错");
				return;
			}
		}
		if (null == mobile && !hasVar) {
			ZkMessage.showInfo("手机号码不能为空！");
			return;
		}

		if (null == content) {
			ZkMessage.showInfo("短信内容不能为空！");
			return;
		}
		if (((Datebox) getComponent().getFellow("sendDate")).isDisabled() || null == sendDate) {
			sendDate = new Date();
		}
		if (null == this.policySend) {
			policySend = "slow_send";
		}
		ZkMessage.showQuestion("您确认短信发送？", new ZkMsgCallBack() {
			public void execute() {
				try {
					//如果有变量，则循环发送手机短信
					if(hasVar && null != msgVarList){
						for(int i=0;i<msgVarList.size();i++){
							try{
								//取得变量替换后的短信
								String curContent = StringUtil.composeMessage(content,msgVarList.get(i));
								//取得手机号
								mobile  = (String)msgVarList.get(i).get(MOBILE_KEY);
								//发送短信
								if(StringUtils.isNotEmpty(mobile)){
									smsRemoteService.sendSms(curContent, mobile, 5, sendChannel, sendDate, getSessionUserName());
								}
							}catch(Exception e){
								LOG.error("短信发送失败：\r\n 手机号："+mobile+" >>"+e);
							}
						}
						content=null;
						mobile=null;
					}else{
						if (mobile.length() > 11) {
							//群发
							if (policySend.equalsIgnoreCase("quick_send") && mobile.length() < 1200) {
								smsRemoteService.sendSms(content, mobile, 9, sendChannel, sendDate, getSessionUserName());
							} else {
								smsRemoteService.sendSms(content, mobile.split(",|\n"), 9, sendChannel, sendDate, getSessionUserName());
							}
						} else {
							smsRemoteService.sendSms(content, mobile, 5, null, sendDate, getSessionUserName());
						}
					}
					ZkMessage.showInfo("短信发送成功！");
				} catch (Exception e) {
					LOG.error("短信发送失败：\r\n"+e);
					ZkMessage.showInfo("短信发送失败！");
				}
			}
		}, new ZkMsgCallBack() {
			public void execute() {

			}
		});

		
		
	}
	
	
	public void changePolicy(String policy) {
		this.policySend = policy;
	}
	
	public void changeSendChannel(String channel) {
		sendChannel = null;
		if ("EMAR".equals(channel)) {
			sendChannel = "QUNFA";
		}
		if ("Montnets".equals(channel)) {
			sendChannel = "MONTNETS";
		}
	}
	
	
	private String formateDate(Date date){
		if(null==date){
			return null;
		}
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	/**
	 * 判断文件第一行是否为变量名称
	 * @param firstStr
	 * @return
	 */
	private Boolean hasVariable(final String firstStr){
		if(null != firstStr && firstStr.matches("^.*("+SPLIT_MSG_SIGN+").*$")){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	/**
	 * 获得变量对应值
	 * @param content
	 * @param keyList
	 * @return
	 */
	private Map<String,Object> getMsgVar(final String content,final List<String> keyList){
		if(!StringUtil.isEmptyString(content) && null != keyList && keyList.size() > 0){
			Map<String,Object> result = new HashMap<String,Object>();
			String[] valueArray = content.split(SPLIT_MSG_SIGN);
			for(int i=0;(i<valueArray.length)&&(keyList.size()>i);i++){
				result.put(keyList.get(i), valueArray[i]);
			}
			return result;
		}
		return null;
	}
	public void changeStatus(String coStatus) {
		if ("".equalsIgnoreCase(coStatus)) {
			status = null;
		}else{
			status=coStatus;
		}
	}
	public List<SmsContentLog> getSmsContentLoglist() {
		return smsContentLoglist;
	}

	public void setSmsContentLoglist(List<SmsContentLog> smsContentLoglist) {
		this.smsContentLoglist = smsContentLoglist;
	}

	public final void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getPolicySend() {
		return policySend;
	}

	public void setPolicySend(String policySend) {
		this.policySend = policySend;
	}

	public String getFurl() {
		return furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public boolean isHasVar() {
		return hasVar;
	}

	public void setHasVar(boolean hasVar) {
		this.hasVar = hasVar;
	}

	public List<Map<String, Object>> getMsgVarList() {
		return msgVarList;
	}

	public void setMsgVarList(List<Map<String, Object>> msgVarList) {
		this.msgVarList = msgVarList;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean getSearchAllFlag() {
		return searchAllFlag;
	}

	public void setSearchAllFlag(boolean searchAllFlag) {
		this.searchAllFlag = searchAllFlag;
	}

}
