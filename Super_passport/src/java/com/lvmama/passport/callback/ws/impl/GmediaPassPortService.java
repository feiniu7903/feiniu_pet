package com.lvmama.passport.callback.ws.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassDevice;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassDeviceService;
import com.lvmama.comm.bee.service.pass.PassPortCodeService;
import com.lvmama.comm.bee.service.pass.PassPortService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.callback.ws.IGmediaPassPortService;
import com.lvmama.passport.processor.UsedCodeProcessor;
import com.lvmama.passport.processor.impl.client.gmedia.Base64;
import com.lvmama.passport.processor.impl.client.gmedia.GmediaUtil;
import com.lvmama.passport.processor.impl.client.gmedia.model.Body;
import com.lvmama.passport.processor.impl.client.gmedia.model.CodeImg;
import com.lvmama.passport.processor.impl.client.gmedia.model.Devices;
import com.lvmama.passport.processor.impl.client.gmedia.model.Fd;
import com.lvmama.passport.processor.impl.client.gmedia.model.Field;
import com.lvmama.passport.processor.impl.client.gmedia.model.Format;
import com.lvmama.passport.processor.impl.client.gmedia.model.Gt;
import com.lvmama.passport.processor.impl.client.gmedia.model.Gts;
import com.lvmama.passport.processor.impl.client.gmedia.model.Head;
import com.lvmama.passport.processor.impl.client.gmedia.model.Memo;
import com.lvmama.passport.processor.impl.client.gmedia.model.Mo;
import com.lvmama.passport.processor.impl.client.gmedia.model.Request;
import com.lvmama.passport.processor.impl.client.gmedia.model.Response;
import com.lvmama.passport.processor.impl.client.gmedia.model.Voucher;
import com.lvmama.passport.processor.impl.client.gmedia.model.Vouchers;

/**
 * 银河回调服务接口实现
 * 
 * @author chenlinjun
 * 
 */
public class GmediaPassPortService implements IGmediaPassPortService {
	private static final Log log = LogFactory.getLog(GmediaPassPortService.class);
	private static final String STATU = "100";
	//private static final String ext = "PEd0cz48R3Q+PENuPuaIkOS6uuaVsDwvQ24+PFRuPjA8L1RuPjwvR3Q+PEd0PjxDbj7lhL/nq6XmlbA8L0NuPjxUbj4wPC9Ubj48L0d0PjwvR3RzPg==";
	public static Object obj = new Object();
	private PassCodeService passCodeService;
	private UsedCodeProcessor usedCodeProcessor;
	private ComLogService comLogService;
	@Override
	public String proxyReq(String reqXml) {
		log.info(reqXml);
		return reqXml;
	}

	public String validCodeReq(String reqXml) {
		log.info("Gmedia valid Code Request Data:" + reqXml);

		// 返回值处理
		Request request = GmediaUtil.getRequest(reqXml);
		Head head = request.getHead();
		String sequenceId = head.getSequenceId();
		Body body = GmediaUtil.getRequestBody(request.getReqBody());
		log.info("Gmedia valid AddCode: "+body.getAssistCode()==null?"":body.getAssistCode());
		String timeStamp = body.getTimeStamp();
		String voucherId = body.getVoucherId();
		String deviceId = body.getDeviceId();

		PassCode passCode = this.passCodeService.getCodeBySerialNo(voucherId);
		// 通关设备编号，找对应的通关的编号
		PassDevice passDevice = this.passCodeService.getPaasDeviceByDeviceNoAndProviderId(deviceId, PassportConstant.getInstance().getGmediaId());
		Response response = new Response();
		body = new Body();
		String status = "0";
		if (passCode != null) {
			Long codeId = passCode.getCodeId();
			PassPortCode passPortCode = this.passCodeService.getPassPortCodeByCodeIdAndPortId(codeId, passDevice.getTargetId());
				if (passPortCode != null) {
					long toDay = DateUtil.toDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd").getTime();
					long validTime = passPortCode.getValidTime().getTime();
					long invalidTime = passPortCode.getInvalidTime().getTime();
					boolean isValid = (toDay >= validTime && toDay <= invalidTime) ? true : false;
					boolean validateInvalidDate = passPortCode.validateInvalidDate();
					status = passPortCode.getStatus().trim();
					head = new Head();
					// 码没有被使用
					if (PassportConstant.PASSCODE_USE_STATUS.UNUSED.name().equals(status) && isValid && validateInvalidDate) {
						status = "1";
						head.setStatusCode(STATU);
						head.setMessage("验证正常通过");
					} else if (PassportConstant.PASSCODE_USE_STATUS.DESTROYED.name().equals(status)) {
						status = "0";
						head.setStatusCode("999");
						head.setMessage("凭证已经作废");
					} else if (!isValid) {
						status = "0";
						head.setStatusCode("999");
						if (toDay < validTime) {
							//head.setMessage("凭证还未到游玩日期");
						} else {
							head.setMessage("凭证已经过期");
						}
					} else if(!validateInvalidDate){//add by shihui
						status = "0";
						head.setStatusCode("999");
						head.setMessage("今日不可游玩");
					} else if (PassportConstant.PASSCODE_USE_STATUS.USED.name().equals(status)) {
							status = "0";
							head.setStatusCode("999");
							head.setMessage("凭证在此景点已经使用过，不能重复使用");
					}
					body.setContent(passPortCode.getTerminalContent());
				} else {
						status = "0";
						head.setStatusCode("999");
						head.setMessage("凭证在不能在此景点使用");
						body.setStatus(status);
						body.setContent("");
				}
		}else{
			status = "0";
			head.setStatusCode("999");
			head.setMessage("凭证不存在");
			body.setStatus(status);
			body.setContent("");
		}
		Gts gts = new Gts();
		Mo mo=new Mo();
		String extContent="";
		if(passDevice.isNewDevice()){
			extContent=gts.makePersonXml()+mo.makePersonXml();
		}else{
			extContent=gts.makePersonXml();
		}
		body.setTimeStamp(timeStamp);
		body.setVoucherId(voucherId);
		body.setStatus(status);
		body.setExtContent(extContent);

		head.setSequenceId(sequenceId);
		head.setSigned(head.makeSignedForValidCode(body));
		response.setRepBody(body);
		response.setHead(head);
		String result = response.toValidCodeResponseXml();

		return result;
	}

	@Override
	public String useCodeReq(String reqXml) {
		log.info("Gmedia Use Code Request Data:" + reqXml);
		// 返回值处理
		Request request = GmediaUtil.getRequest(reqXml);
		Head head = request.getHead();
		String sequenceId = head.getSequenceId();
		Body body = GmediaUtil.getRequestBody(request.getReqBody());
		String timeStamp = body.getTimeStamp();
		String voucherId = body.getVoucherId();
		String deviceId = body.getDeviceId();
		// 请求次数
		int reqCount = Integer.valueOf(body.getReqCount());
		// 扩展内容，实际成人数，和时间儿童数
		String extContent = body.getExtContent();
		// 通关设备编号，找对应的通关的编号
		PassDevice passDevice = this.passCodeService.getPaasDeviceByDeviceNoAndProviderId(deviceId, PassportConstant.getInstance().getGmediaId());
		PassCode passCode = this.passCodeService.getCodeBySerialNo(voucherId);

		String child = "0";
		String adult = "0";
		boolean isOnline=true;
		try{
			Map<String,Object> data=this.parseAdultAndChild(extContent);
			isOnline=(Boolean)data.get("isOnline");
			child=(String)data.get("child");
			adult=(String)data.get("adult");
		}catch (Exception e) {
			log.error("Gmedia use Code Parse MO Exception ", e);
		}
		body = new Body();
		head = new Head();
		
		PassPortCode passPortCode = null;
		if (passCode != null) {
			Long codeId = passCode.getCodeId();
			// 防止当多个线程同时取到同一个状态
			synchronized (obj) {
				passPortCode = this.passCodeService.getPassPortCodeByCodeIdAndPortId(codeId, passDevice.getTargetId());
				Passport passport = new Passport();
				passport.setSerialno(voucherId);
				passport.setPortId(passDevice.getTargetId());
				passport.setOutPortId(passDevice.getTargetId().toString());
				passport.setChild(child);
				passport.setAdult(adult);
				passport.setDeviceId(deviceId);
				if(passPortCode!=null){
						long toDay = DateUtil.toDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd").getTime();
						long validTime = passPortCode.getValidTime().getTime();
						long invalidTime = passPortCode.getInvalidTime().getTime();
						boolean isValid = (toDay >= validTime && toDay <= invalidTime) ? true : false;
						boolean validateInvalidDate = passPortCode.validateInvalidDate();
						// 使用状态
						String status = passPortCode.getStatus().trim();
						boolean isValidate=false;
						if (isOnline) {
						   Map<String,Object> data=this.onlineValidate(head,body ,reqCount, status, isValid, isValidate, validateInvalidDate);
						   isValidate=(Boolean)data.get("isValidate");
						   status=(String)data.get("status");
							String code = "NODATA";
							if (isValidate) {
								code = usedCodeProcessor.update(passport);
								this.usedCodeValidate(head, body, passPortCode.getTerminalContent(), code);
							}
						} else {
							String content = null;
							// 码没有被使用
							if (PassportConstant.PASSCODE_USE_STATUS.DESTROYED.name().equals(status)) {
								content = "凭证已经作废";
							} else if (!isValid) {
								if (toDay < validTime) {
									//content = "凭证还未到游玩日期";
								} else {
									content = "凭证已经过期";
								}
							} else if(!validateInvalidDate) {
								content = "今日不可游玩";
							} else if (PassportConstant.PASSCODE_USE_STATUS.USED.name().equals(status)) {
								content = "凭证在此景点已经使用过，不能重复使用";
							}
							String code = usedCodeProcessor.update(passport);
							// 业务系统更新成功
							if ("NODATA".equalsIgnoreCase(code)) {
								content = "凭证使用不正常";
							}
							if (content != null) {
								this.addComLog(passCode, content);
							}
							head.setStatusCode(STATU);
							head.setMessage("离线使用");
							body.setContent("");
							body.setExtContent("");
						}
				}else{
					//在线模式
					if(isOnline){
							head.setStatusCode("999");
							head.setMessage("凭证不能在此景点使用");
							body.setContent("凭证使用不正常");
							body.setExtContent("扩展信息");
					}else{
						this.addComLog(passCode, "codeId:"+codeId+"targetId:"+passDevice.getTargetId()+"凭证不能在此景点使用");
						head.setStatusCode(STATU);
						head.setMessage("离线使用");
						body.setContent("");
						body.setExtContent("");
					}
				}
			}
		}else{
			this.addComLog(passCode, "流水号："+voucherId+"不存在");
			head.setStatusCode(STATU);
			head.setMessage("离线使用");
			body.setContent("");
			body.setExtContent("");
		}
		body.setTimeStamp(timeStamp);
		body.setVoucherId(voucherId);
		head.setSequenceId(sequenceId);
		head.setSigned(head.makeSignedForUsedCode(body));

		Response response = new Response();
		response.setRepBody(body);
		response.setHead(head);
		String result = response.toUsedCodeResponseXml();
		return result;
	}
	/**
	 * 使用码验证
	 * @param head
	 * @param body
	 * @param content
	 * @param code
	 */
	private void usedCodeValidate(Head head,Body body,String content,String code ){
		// 业务系统更新成功
		if (PassportConstant.PASSCODE_APPLY_STATUS.SUCCESS.name().equalsIgnoreCase(code.trim())) {
			head.setStatusCode(STATU);
			head.setMessage("正常使用");
			body.setContent(content);
			body.setExtContent("扩展信息");
		} else if ("NODATA".equalsIgnoreCase(code)) {
			head.setStatusCode("999");
			head.setMessage("使用不正常");
			body.setContent("凭证使用不正常");
			body.setExtContent("扩展信息");
		} else {
			head.setStatusCode("999");
			head.setMessage("使用不正常");
			body.setContent("凭证使用不正常");
			body.setExtContent("扩展信息");
		}
	}
	/**成人数和儿童数
	 * 解析
	 * @param extContent
	 * @return
	 * @throws Exception
	 */
	private  Map<String,Object> parseAdultAndChild(String extContent) throws Exception{
		Map<String,Object> data=new HashMap<String,Object>();
		String child = "0";
		String adult = "0";
		boolean isOnline=true;
		extContent = new String(Base64.decode(extContent), "utf-8");
		log.info("Gmedia use Code Extends Message ++++++++++++++++++++++" + extContent);
		if (extContent.indexOf("Gts") > -1) {
			int index=extContent.indexOf("</Gts>");
			String temp=extContent.substring(0, index);
			temp=temp+"</Gts>";
			log.info("cut Extends:"+temp);
			// 解析儿童数，成人数
			Gts gts = GmediaUtil.getGts(temp);
			if (gts.getGt() != null) {
				for (Gt gt : gts.getGt()) {
					if (Gts.CHILD.equals(gt.getCn())) {
						if(gt.getTn()!=null&&!"".equalsIgnoreCase(gt.getTn().trim())){
							child = gt.getTn();
						}
					} else if (Gts.ADULT.equals(gt.getCn())) {
						if(gt.getTn()!=null&&!"".equalsIgnoreCase(gt.getTn().trim())){
							adult = gt.getTn();
						}
					}
				}
			}
		} else {
			// 解析儿童数，成人数
			Mo mo = null;
				mo = GmediaUtil.getMo(extContent);
				// 可能存在在线或者离线
				isOnline = mo.isOnline();
				if (mo.getFds() != null) {
					for (Fd fds : mo.getFds()) {
						if (Gts.CHILD.equals(fds.getNe())) {
							if(fds.getVe()!=null&&!"".equalsIgnoreCase(fds.getVe().trim())){
								child = fds.getVe();
							}
						} else if (Gts.ADULT.equals(fds.getNe())) {
							if(fds.getVe()!=null&&!"".equalsIgnoreCase(fds.getVe().trim())){
								adult = fds.getVe();
							}
						}
					}
				}
		}
		data.put("isOnline", isOnline);
		data.put("child", child);
		data.put("adult", adult);
		return data;
	}
	/**
	 * 在线模式使用验证
	 * @param head
	 * @param reqCount
	 * @param status
	 * @param isValid
	 * @param isValidate
	 * @return
	 */
	private Map<String,Object> onlineValidate(Head head,Body body,int reqCount, String status,boolean isValid,boolean flag,boolean validateInvalidDate){
		Map<String,Object> data=new HashMap<String,Object>();
		if (reqCount > 1) {
			// 码没有被使用
			if (PassportConstant.PASSCODE_USE_STATUS.UNUSED.name().equals(status) && isValid && validateInvalidDate) {
				status = "1";
				head.setStatusCode(STATU);
				head.setMessage("验证正常通过");
				flag =true;
			} else if (PassportConstant.PASSCODE_USE_STATUS.DESTROYED.name().equals(status)) {
				status = "0";
				head.setStatusCode("999");
				head.setMessage("凭证已经作废");
				body.setContent("凭证使用不正常");
				body.setExtContent("扩展信息");
			} else if (!isValid) {
				status = "0";
				head.setStatusCode("999");
				head.setMessage("凭证已经过期");
				body.setContent("凭证使用不正常");
				body.setExtContent("扩展信息");
			}else if(!validateInvalidDate){//add by shihui
				status = "0";
				head.setStatusCode("999");
				head.setMessage("今日不可游玩");
				body.setContent("凭证使用不正常");
				body.setExtContent("扩展信息");
			} else if (PassportConstant.PASSCODE_USE_STATUS.USED.name().equals(status)) {
				status = "1";
				head.setStatusCode(STATU);
				head.setMessage("验证正常通过");
				body.setContent("凭证使用不正常");
				body.setExtContent("扩展信息");
			}
		} else {
			// 码没有被使用
			if (PassportConstant.PASSCODE_USE_STATUS.UNUSED.name().equals(status) && isValid && validateInvalidDate) {
				status = "1";
				head.setStatusCode(STATU);
				head.setMessage("验证正常通过");
				flag = true;
			} else if (PassportConstant.PASSCODE_USE_STATUS.DESTROYED.name().equals(status)) {
				status = "0";
				head.setStatusCode("999");
				head.setMessage("凭证已经作废");
				body.setContent("凭证使用不正常");
				body.setExtContent("扩展信息");
			} else if (!isValid) {
				status = "0";
				head.setStatusCode("999");
				head.setMessage("凭证已经过期");
				body.setContent("凭证使用不正常");
				body.setExtContent("扩展信息");
			} else if(!validateInvalidDate){
				status = "0";
				head.setStatusCode("999");
				head.setMessage("今日不可游玩");
				body.setContent("凭证使用不正常");
				body.setExtContent("扩展信息");
			} else if (PassportConstant.PASSCODE_USE_STATUS.USED.name().equals(status)) {
					status = "0";
					head.setStatusCode("999");
					head.setMessage("凭证在此景点已经使用过，不能重复使用");
					body.setContent("凭证使用不正常");
					body.setExtContent("扩展信息");
			}
		}
		data.put("isValidate", flag);
		data.put("status", status);
		return data;
	}
	/**
	 * 添加离线日志
	 * @param passCode
	 * @param content
	 */
	private void addComLog(PassCode passCode,String content) {
		ComLog log = new ComLog();
		log.setObjectType("PASS_CODE");
		if(passCode!=null){
			log.setParentId(passCode.getOrderId());
			log.setObjectId(passCode.getCodeId());
		}
		log.setOperatorName("SYSTEM");
		log.setLogType(Constant.COM_LOG_ORDER_EVENT.systemApprovePass.name());
		log.setLogName("银河离线日志");
		log.setContent(content);
		comLogService.addComLog(log);
	}
	/**
	 * 同步凭证 多条
	 * 二维码平台向合作伙伴发送同步凭证请求
	 * @param arg
	 * @return
	 */
	public String getVouchers(String reqXml){
		if (log.isDebugEnabled()) {
			log.debug("Gmedia Vouchers Request Data:" + reqXml);
		}
		// 返回值处理
		Request request = GmediaUtil.getRequest(reqXml);
		Head head = request.getHead();
		String sequenceId = head.getSequenceId();
		
		Body body = GmediaUtil.getRequestBody(request.getReqBody());
		String timeStamp = body.getTimeStamp();
		String currentPage = body.getCurrentPage();
		String fetchSize = body.getFetchSize();
		String lastDate=body.getLastDate();
		
		int startRow=(Integer.valueOf(currentPage));
		if(startRow!=1){
			startRow=((Integer.valueOf(currentPage)-1)*Integer.valueOf(fetchSize))+1;
		}
		int endRow=Integer.valueOf(currentPage)*Integer.valueOf(fetchSize);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("providerId", 1);
		if(!"19000101000000".equalsIgnoreCase(lastDate.trim())){
			Date temp=DateUtil.toDate(lastDate, "yyyyMMddHHmmss");
			lastDate=DateFormatUtils.format(temp, "yyyy-MM-dd HH:mm:ss");
			log.info("Gmedia lastDate:"+lastDate);
		  data.put("createDate", lastDate);
		}else{
			data.put("status", PassportConstant.PASSCODE_USE_STATUS.UNUSED.name());
		}
		data.put("startRow", startRow);
		data.put("endRow", endRow);
		
		//以履行对象索引通关码(及设备终端号对应通关码)
		List<PassCode> passCodeList=this.passCodeService.selectVouchersByProviderId(data);
		
		String factSize=String.valueOf(passCodeList.size());
		Vouchers vouchers=this.getVouchersBody(passCodeList,currentPage);
		
		body=new Body();
		head = new Head();
		body.setTimeStamp(timeStamp);
		body.setCurrentPage(currentPage);
		body.setFetchSize(fetchSize);
		body.setFactSize(factSize);
		body.setVouchers(vouchers);
		
		head.setSequenceId(sequenceId);
		head.setStatusCode("100");
		head.setMessage("");
		head.setSigned(head.makeSignedForVoucher(body));

		Response response = new Response();
		response.setRepBody(body);
		response.setHead(head);
		String result = response.toVoucherResponseXml();
		return result;
	}


	/**
	 * 同步凭证 单条
	 * 二维码平台向合作伙伴发送同步凭证请求。
	 * 
	 * @param reqXml
	 * @return
	 */
	public String getSingleVoucher(String reqXml) {
		log.info("into getSingleVoucher Request Data is : " + reqXml);

		// 返回值处理
		Request request = GmediaUtil.getRequest(reqXml);
		Head head = request.getHead();
        String sequenceId = head.getSequenceId();
		
		Body body = GmediaUtil.getRequestBody(request.getReqBody());
		String timeStamp = body.getTimeStamp();
		String extContent=body.getExtContent();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("serialNo", body.getVoucherId());
		param.put("deviceNo", body.getDeviceId());
		param.put("providerId", 1);
		param.put("startRow", 0);
		param.put("endRow", 1);//只取一条
		
		//以履行对象索引通关码(及设备终端号对应通关码)
		List<PassCode> passCodeList=this.passCodeService.selectVouchersByProviderId(param);
		Voucher voucher = this.getSingleVoucherBody(passCodeList);
		
		if(voucher != null) {
			voucher.setExtContent(extContent);
		}
		
		body = new Body();
		body.setTimeStamp(timeStamp);
		if (voucher != null) {
			body.setVoucher(voucher);
		} else {
			body.setVoucher(new Voucher());
		}

		head = new Head();
		head.setSequenceId(sequenceId);
		if (voucher != null) {
			head.setStatusCode("100");
			head.setMessage("");
		} else {
			head.setStatusCode("712");
			head.setMessage("此凭证在此设备上不存在");
		}
		head.setSigned(head.makeSingedSingleVoucher(body));

		Response response = new Response();
		response.setRepBody(body);
		response.setHead(head);
		String result = response.getSingleVoucherResponseXml();
		return result;
		
	}
	
	/**
	 * 获得单个Voucher的Body实体
	 * @param passCodeList
	 * @param currentPage
	 * @return
	 */
    private Voucher getSingleVoucherBody(List<PassCode> passCodeList) {
    	log.info("passCodeList size is : " + passCodeList.size());
        Voucher voucher = null;
        if(passCodeList!=null&&passCodeList.size()>0){
        	voucher = new Voucher();
            voucher = this.createVoucher(passCodeList.get(0));
            voucher.setExtContent("扩展内容暂定！还没有用到，不确定！");
        }
        return voucher;
    }
	
	/**
	 * 获得多个Voucher的Body对象
	 * 
	 * @param passCodeList
	 * @param currentPage
	 * @return
	 */
	private Vouchers getVouchersBody(List<PassCode> passCodeList,String currentPage){
		Vouchers vouchers=new Vouchers ();
		List<Voucher> voucherList=new ArrayList<Voucher>();
		for(PassCode passCode:passCodeList){
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("providerId", 1);
			data.put("targetId", passCode.getTargetId());
			
			//设备号
			List<String> deviceId=new ArrayList<String>();
			List<PassDevice> paasDevicList=this.passCodeService.searchPassDevice(data);
			for(PassDevice passDevice:paasDevicList){
				deviceId.add(passDevice.getDeviceNo());
			}
			
			Devices devices=new Devices();
			devices.setDeviceId(deviceId);
			
			Voucher voucher = this.createVoucher(passCode);
			voucher.setDevices(devices);
			voucherList.add(voucher);
		}
		vouchers.setVoucher(voucherList);
		return vouchers;
	}
	
	/**
	 * 拼装Voucher对象数据
	 * 
	 * @param passCode
	 * @return
	 */
	private Voucher createVoucher(PassCode passCode) {
	    Voucher voucher=new Voucher();
        voucher.setTitle("");
        voucher.setVoucherType("1");
        voucher.setRemain("1");
        voucher.setRevalid("0");
        voucher.setCheckType("3");
        voucher.setPrintType("ABC");
        voucher.setPrintMode("2");
        voucher.setPrintCount("1");
        voucher.setPrintContent("");
        voucher.setVoucherId(passCode.getSerialNo());
        voucher.setContent(passCode.getTerminalContent());
        
        PassPortCode passPortCode = this.passCodeService.getPassPortCodeByCodeIdAndPortId(passCode.getCodeId(), passCode.getTargetId());
        log.info(passCode.getCodeId()+"===="
                + passCode.getStatus()+"==="
                + passPortCode.getStatus()+"===="
                + (PassportConstant.PASSCODE_APPLY_STATUS.SUCCESS.name().equalsIgnoreCase(passCode.getStatus().trim()) && 
                		PassportConstant.PASSCODE_USE_STATUS.UNUSED.name().equalsIgnoreCase(passPortCode.getStatus().trim()))
            );
        if(PassportConstant.PASSCODE_APPLY_STATUS.SUCCESS.name().equalsIgnoreCase(passCode.getStatus().trim()) && 
        	PassportConstant.PASSCODE_USE_STATUS.UNUSED.name().equalsIgnoreCase(passPortCode.getStatus().trim())){
            voucher.setStatus("1");
        }else{
            voucher.setStatus("0");
        }
        voucher.setValidDate(DateFormatUtils.format(passPortCode.getInvalidTime(), "yyyyMMdd"));
        voucher.setReserveDate(DateFormatUtils.format(passPortCode.getValidTime(), "yyyyMMdd"));
        
        CodeImg codeImg=new CodeImg();
        codeImg.setType("");
        codeImg.setContent("");
        voucher.setCodeImg(codeImg);

        Field fieldChild=new Field();
        fieldChild.setId("1");
        fieldChild.setName("儿童数");
        fieldChild.setValue("0");
        
        Field fieldAdult=new Field();
        fieldAdult.setId("2");
        fieldAdult.setName("成人数");
        fieldAdult.setValue("0");
        
        List<Field> fields=new ArrayList<Field>();
        fields.add(fieldAdult);
        fields.add(fieldChild);
        
        Memo memo=new Memo();
        memo.setFields(fields);
        voucher.setMemo(memo);
        
        return voucher;
	}
	
	/**
	 * 同步打印内容
	 * 二维码平台向合作伙伴发送同步打印内容请求
	 * @param arg
	 * @return
	 */
	public String getPrintFormat (String reqXml){
		log.info("Gmedia Format Request Data:" + reqXml);
		Request request = GmediaUtil.getRequest(reqXml);
		Head head = request.getHead();
		String sequenceId = head.getSequenceId();
		Body body = GmediaUtil.getRequestBody(request.getReqBody());
		String timeStamp = body.getTimeStamp();
		body=new Body();
		body.setTimeStamp(timeStamp);
		
		Format format=new Format();
		format.setType("ABC");
		format.setContent("$$VC$$");
		format.setLogoUrl("");
		List<Format> formates=new ArrayList<Format>();
		formates.add(format);
		body.setFormat(formates);
		
		head.setSequenceId(sequenceId);
		head.setStatusCode("100");
		head.setMessage("");
		head.setSigned(head.makeSignedForFormat(body));
		
		Response response = new Response();
		response.setRepBody(body);
		response.setHead(head);
		String result = response.toFormatResponseXml();
		return result;
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
	 
}
