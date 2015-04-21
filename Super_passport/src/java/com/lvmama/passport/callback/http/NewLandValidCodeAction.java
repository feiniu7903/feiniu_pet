package com.lvmama.passport.callback.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.lvmama.BackBaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassDevice;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassDeviceService;
import com.lvmama.comm.bee.service.pass.PassPortCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.UsedCodeProcessor;
import com.lvmama.passport.processor.impl.client.newland.NewlandUtil;
import com.lvmama.passport.processor.impl.client.newland.model.CredentialInfo;
import com.lvmama.passport.processor.impl.client.newland.model.CredentialList;
import com.lvmama.passport.processor.impl.client.newland.model.CredentialSyncReq;
import com.lvmama.passport.processor.impl.client.newland.model.GoodsRecord;
import com.lvmama.passport.processor.impl.client.newland.model.GoodsRecordList;
import com.lvmama.passport.processor.impl.client.newland.model.Order;
import com.lvmama.passport.processor.impl.client.newland.model.OrderResend;
import com.lvmama.passport.processor.impl.client.newland.model.PGoodsList;
import com.lvmama.passport.processor.impl.client.newland.model.PosList;
import com.lvmama.passport.processor.impl.client.newland.model.RollbackReq;
import com.lvmama.passport.processor.impl.client.newland.model.RollbackRes;
import com.lvmama.passport.processor.impl.client.newland.model.Status;
import com.lvmama.passport.processor.impl.client.newland.model.VerifyReq;
import com.lvmama.passport.processor.impl.client.newland.model.VerifyRes;

/**
 * 翼码验证码回调
 * 
 * @author chenlinjun
 * 
 */

@ParentPackage("json-default")
public class NewLandValidCodeAction extends BackBaseAction {

	private static final long serialVersionUID = -7708285526012782447L;
	public static Object obj = new Object();
	private UsedCodeProcessor usedCodeProcessor;
	private OrderService orderServiceProxy;
	private PassCodeService passCodeService;
	private TopicMessageProducer passportMessageProducer;
	@Action("/newland/validCode")
	public void validCode() {
		String data = getRequestData();
		if (data.indexOf("VerifyReq") != -1) {
			// 验证码请求
			this.verifyReq(data);
		} else if (data.indexOf("RollbackReq") != -1) {
			// 回退请求
			this.rollbackReq(data);
		}
	}

	@Action("/newland/resentSms")
	public void resentSms() {
		log.info("newland resentSms");
		String data =getRequestData();
		String xml="<?xml version=\"1.0\" encoding=\"GBK\"?><OrderResendRes><StatusCode>0000</StatusCode></OrderResendRes>";
		try{
		 OrderResend orderResend=NewlandUtil.getResendSmsReq(data);
		 for(Order order:orderResend.getOrders()){
			 PassCode  passCode=passCodeService.getCodeBySerialNo(order.getOrderId());
			 if(passCode!=null){
				 log.info("newland resentSms:"+passCode.getSerialNo());
				 passportMessageProducer.sendMsg(MessageFactory.newPasscodeApplySuccessMessage(passCode.getCodeId(),"newland"));
			 }else{
				 log.info("newland not find :"+order.getOrderId());
			 }
		 }
		 this.sendXmlResult(xml);
		}catch (Exception e){
			log.error("newland resentSms", e);
			 this.sendXmlResult(xml);
		}
	}
	
	/**
	 * 获得请求数据
	 * 
	 * @return
	 */
	private String getRequestData() {
		StringBuilder result = new StringBuilder();
		InputStream input = null;
		BufferedReader in = null;
		try {
			input = this.getRequest().getInputStream();
			in = new BufferedReader(new InputStreamReader(input, "GBK"));
			String line = "";
			while ((line = in.readLine()) != null) {
				result.append(line.trim());
			}
			log.info("翼码验证码回调数据:" + result.toString());
		} catch (Exception ex) {
			log.error("翼码刷码更新订单状态异常", ex);
		} finally {
			try {
				if (input != null)
					input.close();
				if (in != null)
					in.close();
			} catch (Exception ex) {
				log.error("翼码刷码更新订单状态异常", ex);
			}
		}
		return result.toString();
	}

	/**
	 * 验证码请求处理
	 * 
	 * @param data
	 */
	private void verifyReq(String data) {
		try {
			Passport passport = new Passport();
			String usedDate=null;
			VerifyReq verifyReq =null;
			// 返回值处理
			String addCodeMd5="";
			boolean isOnLine=true;
			//判断离线验证
			if(data.indexOf("PosTransTime")!=-1){
				isOnLine=false;
				verifyReq = NewlandUtil.getVerifyReq(data,false);
				usedDate=verifyReq.getPosTransTime();
				Date temp=DateUtil.toDate(usedDate, "yyyyMMddHHmmss");
				passport.setUsedDate(temp);
				addCodeMd5 = verifyReq.getCredential().trim();
				log.info("NewLand not onLine validCode:"+addCodeMd5);
			}else{
				verifyReq = NewlandUtil.getVerifyReq(data,true);
				addCodeMd5 = verifyReq.getCredential().trim();
				log.info("NewLand onLine validCode:"+addCodeMd5);
			}
			GoodsRecord goodsRecord = verifyReq.getGoodsRecord();
			PGoodsList pGoodsList = goodsRecord.getPGoodsList();

			String deviceId = verifyReq.getPos_id().trim();
			passport.setDeviceId(deviceId);
			passport.setProvider("NEWLAND");
			String spareField = "";
			String useMode = "9";
			String applyAmount="";
			PassPortCode passPortCode = null;
			Status status = new Status();
			PassCode passCode = passCodeService.getCodeByAddCodeMd5(addCodeMd5);
			if (passCode != null) {
				Long orderId = passCode.getOrderId();
				Long codeId = passCode.getCodeId();

				OrdOrder ordOrder = this.orderServiceProxy.queryOrdOrderByOrderId(orderId);
				boolean isChangePerson =hasChangePerson(ordOrder);
				// 通关设备编号，找对应的通关的编号
				PassDevice passDevice = passCodeService.getPaasDeviceByDeviceNoAndProviderId(deviceId, PassportConstant.getInstance().getNewlandId());
				// 防止当多个线程同时取到同一个状态
				synchronized (obj) {
					log.info("validCode Info: TargetId ="+passDevice.getTargetId()+",deviceId="+deviceId+",CodeId= "+passCode.getCodeId());
					passPortCode = passCodeService.getPassPortCodeByCodeIdAndPortId(codeId, passDevice.getTargetId());
					String usedStatus = passPortCode.getStatus().trim();

					boolean isValidatePass = false;// 是否验证通过
					boolean validateInvalidDate = passPortCode.validateInvalidDate();
					// 码没有被使用
					if (PassportConstant.PASSCODE_USE_STATUS.UNUSED.name().equals(usedStatus) && ifTerm(passPortCode.getValidTime(), passPortCode.getInvalidTime()) && validateInvalidDate) {
						status.setStatus_code("0");
						status.setStatus_text("验证正常通过");
						isValidatePass = true;
					} else if (PassportConstant.PASSCODE_USE_STATUS.DESTROYED.name().equals(usedStatus)) {
						status.setStatus_code("3");
						status.setStatus_text("凭证已经作废");
					} else if (PassportConstant.PASSCODE_USE_STATUS.USED.name().equals(usedStatus)) {
						status.setStatus_code("3");
						status.setStatus_text("凭证在此景点已经使用过，不能重复使用");
					} else if (!ifTerm(passPortCode.getValidTime(), passPortCode.getInvalidTime())) {
						status.setStatus_code("3");
						if (ifMaturate(passPortCode.getValidTime())) {
							status.setStatus_text("凭证还未到游玩时间");
						} else {
							status.setStatus_text("凭证已经过期");
						}
					} else if(!validateInvalidDate) {//add by shihui
						status.setStatus_code("3");
						status.setStatus_text("今日不可游玩");
					}
 					if(!isOnLine){
						isValidatePass = true;	
					}
					passport.setSerialno(passCode.getSerialNo());
					passport.setPortId(passDevice.getTargetId());
					passport.setOutPortId(passDevice.getTargetId().toString());
					passport.setChild("0");
					passport.setAdult("0");
					if (!isChangePerson) {
						useMode = "0";
						if (isValidatePass) {
							this.usedCode(passport, status);
						}
					} else {
						String[] childAndAdult = this.getChildAndAdult(passDevice.getTargetId().toString(), passCode.getOrderId());
						// 修改人数
						String temp = verifyReq.getCustomization();
						if ("".equalsIgnoreCase(temp)) {
							status.setStatus_code("3035");
							spareField="成人票=" + childAndAdult[0] + "|儿童票=" + childAndAdult[1] + "|";
							goodsRecord.setSpareField(spareField);
						} else {
							String[] num = temp.split("\\|");
							//判断是否修改了人数
							if(Integer.valueOf(childAndAdult[0])!=Integer.valueOf(num[0])||
							   Integer.valueOf(childAndAdult[1])!=Integer.valueOf(num[1])){
								String adult=num[0];
								String child=num[1];
								passport.setAdult(adult);
								passport.setChild(child);
								float price=Integer.valueOf(adult)*Float.valueOf(childAndAdult[2])*100+
								Integer.valueOf(child)*Float.valueOf(childAndAdult[3])*100;
								applyAmount=String.valueOf(price);
							}
							if (isValidatePass) {
								this.usedCode(passport, status);
							}
						}
					}
				}
			} else {
				status.setStatus_code("3");
				status.setStatus_text("凭证码不存在");
			}

			goodsRecord = new GoodsRecord();
			goodsRecord.setPgoods_id(pGoodsList.getPgoods_id());
			goodsRecord.setPgoods_name("");
			goodsRecord.setApply_amount(applyAmount);//修改人数后的实际订单金额
			goodsRecord.setUsed_amount("");
			goodsRecord.setRemain_amount("");
			goodsRecord.setRemain_times("");
			goodsRecord.setUse_mode(useMode);// 9：表示终端设备上面可以修改人数；0：表示终端设备上面不可以修改人数；
			goodsRecord.setSpareField(spareField);

			List<GoodsRecord> goodsRecords = new ArrayList<GoodsRecord>();
			goodsRecords.add(goodsRecord);

			GoodsRecordList goodsRecordList = new GoodsRecordList();
			goodsRecordList.setGoodsRecords(goodsRecords);

			VerifyRes verifyRes = new VerifyRes();
			verifyRes.setGoodsRecordList(goodsRecordList);
			verifyRes.setPos_id(verifyReq.getPos_id());
			verifyRes.setCustomization(verifyReq.getCustomization());
			verifyRes.setReq_sequence(verifyReq.getReq_sequence());
			verifyRes.setCredential_class(verifyReq.getCredential_class());
			verifyRes.setCredential(addCodeMd5);
			verifyRes.setAssist_number(passCode == null ? "" : passCode.getAddCode());
			verifyRes.setPrint_text(passPortCode.getTerminalContent());
			verifyRes.setPrint_links("");
			verifyRes.setActivity_id("");
			verifyRes.setActivity_description("驴妈妈旅游网");
			verifyRes.setStatus(status);
			this.sendXmlResult(verifyRes.toVerifyResXml());

		} catch (Exception e) {
			log.error("翼码验证码请求处理", e);
		}

	}
/**
 * 凭证数据同步
 */
	@Action("/newland/credentialSyncRes")
	public  void credentialSyncRes(){
		//String data="<?xml version=\"1.0\" encoding=\"gbk\"?><CredentialSyncReq><ReqSequence>12345678</ReqSequence><CurrentPage>1</CurrentPage><PageSize>10</PageSize><LastSyncTime>19000101000000</LastSyncTime></CredentialSyncReq>";
		String data=this.getRequestData();
		CredentialSyncReq credentialSync=new CredentialSyncReq();
			try{
				CredentialSyncReq credentialSyncReq = NewlandUtil.getCredentialSyncReq(data);
				String currentPage = credentialSyncReq.getCurrentPage();
				String pageSize = credentialSyncReq.getPageSize();
				String lastDate=credentialSyncReq.getLastSyncTime();
				
				int startRow=(Integer.valueOf(currentPage));
				if(startRow!=1){
					startRow=((Integer.valueOf(currentPage)-1)*Integer.valueOf(pageSize))+1;
				}
				int endRow=Integer.valueOf(currentPage)*Integer.valueOf(pageSize);
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("providerId", 3);
				param.put("code", "BASE64");
				if(!"19000101000000".equalsIgnoreCase(lastDate.trim())){
					Date temp=DateUtil.toDate(lastDate, "yyyyMMddHHmmss");
					String date=DateFormatUtils.format(temp, "yyyy-MM-dd HH:mm:ss");
					log.info("newland lastDate:"+date);
					param.put("createDate", date);
					credentialSync.setSyncTime(lastDate);
				}else{
					param.put("status", PassportConstant.PASSCODE_USE_STATUS.UNUSED.name());
					credentialSync.setSyncTime(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
				}
				param.put("startRow", startRow);
				param.put("endRow", endRow);
				//以履行对象索引通关码(及设备终端号对应通关码)
				List<PassCode> passCodeList=passCodeService.selectVouchersByProviderId(param);
				String factSize=String.valueOf(passCodeList.size());
				credentialSync.setReqSequence(credentialSyncReq.getReqSequence());
				credentialSync.setCurrentPage(currentPage);
				credentialSync.setPageSize(pageSize);
				credentialSync.setFactSize(factSize);
				credentialSync.setSyncTime(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
				CredentialList credentialList=this.getCredentialList(passCodeList);
				credentialSync.setCredentialList(credentialList);
				Status status=new Status();
				status.setStatus_code("0000");
				status.setStatus_text("交易成功");
				credentialSync.setStatus(status);
				this.sendXmlResult(credentialSync.toCredentialSyncReqXml());
			}catch (Exception e){
				log.error("NewLand credentialSyncRes ",e);
			}
	}
	
	private CredentialList getCredentialList(List<PassCode> passCodeList){

		List<CredentialInfo> credentialInfos= new ArrayList<CredentialInfo>();
		for(PassCode passCode:passCodeList){
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("providerId", 3);
			data.put("targetId", passCode.getTargetId());
			List<PassDevice> paasDevicList=passCodeService.searchPassDevice(data);
			
			CredentialInfo info=new CredentialInfo();
			info.setTransactionId(passCode.getSerialNo());
			info.setContentMd5(passCode.getAddCodeMd5());
			info.setAssistNumberMd5(passCode.getAddCodeMd5());
			info.setAssistNumber(passCode.getAddCode().substring(12, 16));
			info.setCredentialAmount("0");
			info.setPrintText(passCode.getTerminalContent());
			info.setPrintLinks("2");
			info.setActivityDescription("驴妈妈旅游网");
			PassPortCode passPortCode = passCodeService.getPassPortCodeByCodeIdAndPortId(passCode.getCodeId(), passCode.getTargetId());
			info.setBeginUseTime(DateFormatUtils.format(passPortCode.getValidTime(), "yyyyMMddHHmmss"));
			info.setExpiryTime(DateFormatUtils.format(passPortCode.getInvalidTime(), "yyyyMMddHHmmss"));

			if(PassportConstant.PASSCODE_APPLY_STATUS.SUCCESS.name().equalsIgnoreCase(passCode.getStatus().trim())&&
					PassportConstant.PASSCODE_USE_STATUS.UNUSED.name().equalsIgnoreCase(passPortCode.getStatus().trim())){
				info.setCredentialStatus("0");
			}else{
				info.setCredentialStatus("1");
			}
			OrdOrder ordOrder = this.orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			boolean isChangePerson =hasChangePerson(ordOrder);
			if(!isChangePerson){
				info.setUseMode("0");
			}else{
				info.setUseMode("9");
			}
			String[] childAndAdult = this.getChildAndAdult(passCode.getTargetId().toString(), passCode.getOrderId());
			String spareField="成人票=" + childAndAdult[0] + "|儿童票=" + childAndAdult[1] + "|";
			String credentialPrice=childAndAdult[2] + "|" + childAndAdult[3] + "|";
			info.setSpareField(spareField);
			info.setCredentialPrice(credentialPrice);
			
			List<PosList> deviceId=new ArrayList<PosList>();
			for(PassDevice passDevice:paasDevicList){
				PosList posList=new PosList();
				posList.setPosId(passDevice.getDeviceNo());
				deviceId.add(posList);
			}
			info.setPosLists(deviceId);
			credentialInfos.add(info);
		}
		CredentialList credentialList=new CredentialList();
		credentialList.setCredentialInfos(credentialInfos);
		return credentialList;
	}
	
	/**
	 * 使用码
	 * 
	 * @param passport
	 * @param status
	 */
	public void usedCode(Passport passport, Status status) {
		String code = "NODATA";
		code = usedCodeProcessor.update(passport);
		// 业务系统更新成功

		if (PassportConstant.PASSCODE_APPLY_STATUS.SUCCESS.name().equalsIgnoreCase(code.trim())) {
			status.setStatus_code("0");
			status.setStatus_text("正常使用");
		} else if ("NODATA".equalsIgnoreCase(code)) {
			status.setStatus_code("3");
			status.setStatus_text("凭证使用不正常");
		} else {
			status.setStatus_code("3");
			status.setStatus_text("凭证使用不正常");
		}
	}

	/**
	 * 订单指定产品的成人数和儿童数
	 * 
	 * @param targetId
	 * @param orderId
	 */
	public String [] getChildAndAdult(String targetId, Long orderId) {
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMetaPerformRelate().setTargetId(targetId);
		compositeQuery.getMetaPerformRelate().setOrderId(orderId);
		compositeQuery.getPageIndex().setBeginIndex(0);
		compositeQuery.getPageIndex().setEndIndex(1000000000);

		List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);

		long adult = 0;
		long child = 0;
		float childPrice=0;
		float adultPrice=0;
		if(orderItemMetas!=null&&orderItemMetas.size()>0){
			int size = orderItemMetas.size();
			if (size > 1) {
				for (int j = 0; j < size; j++) {
					OrdOrderItemMeta itemMeta = orderItemMetas.get(j);
					// 成人数
					adult = adult + itemMeta.getTotalAdultQuantity();
					// 儿童数
					child = child + itemMeta.getTotalChildQuantity();
				}
			} else {
				OrdOrderItemMeta itemMeta = orderItemMetas.get(0);
				// 成人数
				adult = adult + itemMeta.getTotalAdultQuantity();
				// 儿童数
				child = child + itemMeta.getTotalChildQuantity();
				float price=itemMeta.getSellPriceToYuan();
				if(adult>0){
					adultPrice=price;
				}else{
					childPrice=price;
				}
			}
		}
		log.info("NewLand getChildAndAdult adult="+adult+":child="+child+",adultPrice="+adultPrice+",childPrice="+childPrice);
		return new String[]{String.valueOf(adult),String.valueOf(child),String.valueOf(adultPrice),String.valueOf(childPrice)};
	}

	public void setUsedCodeProcessor(UsedCodeProcessor usedCodeProcessor) {
		this.usedCodeProcessor = usedCodeProcessor;
	}

	/**
	 * 回退请求处理
	 * 
	 * @param data
	 */
	private void rollbackReq(String data) {
		try {
			RollbackReq rollbackReq = NewlandUtil.getRollbackReq(data);
			String assistNoMD5 = rollbackReq.getCredential().trim();
			log.info("回退请求处理,辅助码MD5:" + assistNoMD5);
			String deviceId = rollbackReq.getPos_id().trim();
			PassCode passCode = this.passCodeService.getCodeByAddCodeMd5(assistNoMD5);
			if (passCode != null) {
				// 通关设备编号，找对应的通关的编号
				PassDevice passDevice = passCodeService.getPaasDeviceByDeviceNoAndProviderId(deviceId, PassportConstant.getInstance().getNewlandId());
				log.info("rollbackReq Info: TargetId ="+passDevice.getTargetId()+",deviceId="+deviceId+",CodeId="+passCode.getCodeId());
				PassPortCode passPortCode = passCodeService.getPassPortCodeByCodeIdAndPortId(passCode.getCodeId(), passDevice.getTargetId());
				if(passPortCode!=null){
					passPortCode.setStatus(PassportConstant.PASSCODE_USE_STATUS.UNUSED.name());
					// 更新通关点信息
					passCodeService.updatePassPortCode(passPortCode);
				}else{
				  log.error("通关点信息查找不到");
				}
			} else {
				log.error("辅助码不存在");
			}

			RollbackRes rollbackRes = new RollbackRes();
			rollbackRes.setPos_id(deviceId);
			rollbackRes.setCustomization(rollbackReq.getCustomization());
			rollbackRes.setReq_sequence(rollbackReq.getReq_sequence());
			// 0:条码 1:辅助码
			rollbackRes.setCredential_class(rollbackReq.getCredential_class());
			rollbackRes.setCredential(rollbackReq.getCredential());
			rollbackRes.setOrg_sequence(rollbackReq.getOrg_sequence());
			GoodsRecord goodsRecord = rollbackReq.getGoodsRecord();
			Status status = new Status();
			status.setStatus_code("0");
			status.setStatus_text("正确");
			rollbackRes.setGoodsRecord(goodsRecord);
			rollbackRes.setStatus(status);
			this.sendXmlResult(rollbackRes.toRollbackResXml());
		} catch (Exception e) {
			log.error("翼码回退请求处理", e);
		}
	}

	/**
	 * 判断凭证是否在游玩游玩日期之内
	 * 
	 * @param validTime
	 * @param invalidTime
	 * @return
	 */
	private boolean ifTerm(Date validTime, Date invalidTime) {
		long toDay = new Date().getTime();
		return (toDay >= validTime.getTime() && toDay <= invalidTime.getTime()) ? true : false;
	}
	
/**
 * 判断是否可以让终端修改人数
 * @param ordOrder
 * @return
 */
	private boolean hasChangePerson(OrdOrder ordOrder){
		boolean flag=false;
		if (ordOrder.getOrdOrderItemProds()!=null
				&& ordOrder.getOrdOrderItemProds().size() == 1
				&& ordOrder.getAllOrdOrderItemMetas()!=null
				&& ordOrder.getAllOrdOrderItemMetas().size() == 1) {
			// 采购产品
			OrdOrderItemMeta orderItemMeta = ordOrder.getAllOrdOrderItemMetas().get(0);
			int adult=orderItemMeta.getAdultQuantity().intValue();
			int child=orderItemMeta.getChildQuantity().intValue();
			int productQuantity=orderItemMeta.getProductQuantity().intValue();
			log.info("hasChangePerson:productQuantity="+productQuantity+",adult="+adult+",child="+child);
			if((adult==1&&child==0&&productQuantity==1)||(adult==0&&child==1&&productQuantity==1)){
				List<OrdOrderAmountItem> couponList=this.orderServiceProxy.queryOrdOrderAmountItem(ordOrder.getOrderId(),"ORDER_COUPON_AMOUNT");
				if(couponList==null||(couponList!=null&&couponList.isEmpty())){
					flag=true;
					log.info("hasChangePerson:"+flag);
					flag=ordOrder.isChangeAmount();
				}else{
					flag=false;
				}
			}
		}
		log.info("hasChangePerson:"+flag);
		return flag;
	}
	/**
	 * 判断凭证是否到游玩日期
	 * 
	 * @param validTime
	 * @return
	 */
	private boolean ifMaturate(Date validTime) {
		return new Date().getTime() < validTime.getTime();
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public PassCodeService getPassCodeService() {
		return passCodeService;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}
 
	public void setPassportMessageProducer(
			TopicMessageProducer passportMessageProducer) {
		this.passportMessageProducer = passportMessageProducer;
	}
}
