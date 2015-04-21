package com.lvmama.passport.callback.http;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.BackBaseAction;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassDevice;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.UsedCodeProcessor;
import com.lvmama.passport.utils.WebServiceConstant;

public class LvmamaCallBackAction extends BackBaseAction{
	private static final long serialVersionUID = 1L;
	private ComLogService comLogService;
	private PassCodeService passCodeService;
	private UsedCodeProcessor usedCodeProcessor;
	private String orderId;//驴妈妈订单号
	private String parterId;//合作方订单号
	private String date;//刷码时间或退票审核通过时间
	private String orderQuantity;//订单总票数
	private String usedQuantity;//消费张数
	private String sign;//签名
	private String messageType;//消息类型（退票cancel  |  刷码通关used   |过期订单overdue）
	private String refundNum;//退票张数
	private String overdueNum;//过期张数
	
	@Action("/lvmamaCallBack")
	public void LvmamaCallBack() {
		log.info("orderId:"+orderId);
		log.info("parterId:"+parterId);
		log.info("date:"+date);
		log.info("orderQuantity:"+orderQuantity);
		log.info("usedQuantity:"+usedQuantity);
		log.info("messageType:"+messageType);
		log.info("refundNum:"+refundNum);
		log.info("overdueNum:"+overdueNum);
		log.info("sign:"+sign);
		String result= "{\"code\":0,\"msg\":\"调用失败\"}";
		try {
			if (orderId != null && date != null && parterId!=null && orderQuantity!=null && usedQuantity!=null) {
				Date useDate = DateUtil.toDate(date, "yyyyMMddHHmmss");
				String key=WebServiceConstant.getProperties("alading.lvmamaKey");
				String lvmamasign=MD5.encode32(key+parterId);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("serialNo", orderId.trim());
				PassCode passCode = passCodeService.getPassCodeByParams(data);
				if(StringUtils.equals(lvmamasign,sign)){
					if(StringUtils.equals(messageType, "cancel")){
						//退票成功消息
						if(passCode!=null){
							List<PassPortCode> passPortCodeList = this.passCodeService.queryProviderByCode(passCode.getCodeId());
							PassPortCode passPortCode = passPortCodeList.get(0);
							String status=passPortCode.getStatus();
							if(!StringUtils.equals(status,"USED")){
								if(refundNum!=null && Long.valueOf(refundNum)>0){
								passCode.setStatus(PassportConstant.PASSCODE_DESTROY_STATUS.DESTROYED.name());
								passCode.setFailedTime(Calendar.getInstance().getTime());
								passCodeService.successExecute(passCode, null);
								String content="合作方订单号:"+parterId+",订单总数量："+orderQuantity+",已消费票数量："+usedQuantity+",退票数量:"+refundNum;
								addComLog(passCode, content, "退票成功");
								}else{
								String content="合作方订单号:"+parterId+",订单总数量："+orderQuantity+",已消费票数量："+usedQuantity+",退票数量:"+refundNum;
								addComLog(passCode, content, "退票审核被拒绝");	
								}
								result="{\"code\":1,\"msg\":\"调用成功\"}";
							}
						}else{
							result="{\"code\":0,\"msg\":\"订单不存在\"}";
						}
						
					}else if(StringUtils.equals(messageType, "overdue")){
						//过期订单消息
						result="{\"code\":1,\"msg\":\"调用成功\"}";
						String content="合作方订单号:"+parterId+",订单总数量："+orderQuantity+",已消费票数量："+usedQuantity+",过期数量:"+overdueNum;
						addComLog(passCode, content, "过期订单消息推送");
					}else{
						if (passCode != null) {
							List<PassPortCode> passPortCodeList = this.passCodeService.queryProviderByCode(passCode.getCodeId());
							PassPortCode passPortCode = passPortCodeList.get(0);
							if (passPortCode != null) {
								// 履行对象
								Long targetId = passPortCode.getTargetId();
								Map<String, Object> params = new HashMap<String, Object>();
								params.put("targetId", targetId);
								List<PassDevice> passDeviceList = this.passCodeService.searchPassDevice(params);
								Passport passport = new Passport();
								passport.setSerialno(passCode.getSerialNo());
								passport.setPortId(targetId);
								passport.setOutPortId(targetId.toString());
								if (passDeviceList != null && passDeviceList.size() > 0) {
									passport.setDeviceId(passDeviceList.get(0).getDeviceNo().toString());
								}
								passport.setChild("0");
								passport.setAdult("0");
								passport.setUsedDate(useDate);
								// 更新履行状态
								String code = usedCodeProcessor.update(passport);
								if ("SUCCESS".equals(code)) {
									result="{\"code\":1,\"msg\":\"调用成功\"}";
									String content="合作方订单号:"+parterId+",订单总数量："+orderQuantity+",已消费票数量："+usedQuantity;
									addComLog(passCode,content,"消费后回调接口");
								}
							}
						}else{
							result="{\"code\":0,\"msg\":\"订单不存在\"}";
						}
					}
				}else{
					result="{\"code\":0,\"msg\":\"签名错误\"}";
				}
			}else{
				result="{\"code\":0,\"msg\":\"参数不能为空\"}";
			}
		} catch (Exception e) {
			log.error("LvmamaCallBack error: ", e);
			result="{\"code\":0,\"msg\":\"回调请求异常\"}";
		}
		log.info("result: " + result);
		sendAjaxResultByJson(result);
	}
	
	//记录回调日志信息
	private void addComLog(PassCode passCode, String logContent, String logName) {
		ComLog log = new ComLog();
		log.setObjectType("PASS_CODE");
		log.setParentId(passCode.getOrderId());
		log.setObjectId(passCode.getCodeId());
		log.setOperatorName("SYSTEM");
		log.setLogType(Constant.COM_LOG_ORDER_EVENT.systemApprovePass.name());
		log.setLogName(logName);
		log.setContent(logContent);
		comLogService.addComLog(log);
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public void setUsedCodeProcessor(UsedCodeProcessor usedCodeProcessor) {
		this.usedCodeProcessor = usedCodeProcessor;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setParterId(String parterId) {
		this.parterId = parterId;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setOrderQuantity(String orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public void setUsedQuantity(String usedQuantity) {
		this.usedQuantity = usedQuantity;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public void setRefundNum(String refundNum) {
		this.refundNum = refundNum;
	}

	public void setOverdueNum(String overdueNum) {
		this.overdueNum = overdueNum;
	}

}
