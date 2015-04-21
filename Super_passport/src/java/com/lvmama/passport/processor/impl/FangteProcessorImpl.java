package com.lvmama.passport.processor.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import sun.misc.BASE64Decoder;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassProduct;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.impl.client.fangte.FangteUtil;
import com.lvmama.passport.processor.impl.client.fangte.model.Order;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;


/**
 * 华强方特接口实现
 * @author lipengcheng
 *
 */
public class FangteProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor , OrderPerformProcessor{
	
	private static final  Log log = LogFactory.getLog(FangteProcessorImpl.class);
	private static final String STATUS = "success";
	/** 订单查询接口*/
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private PassCodeService passCodeService = (PassCodeService) SpringBeanProxy.getBean("passCodeService");
	

	/**
	 * 申请码
	 */
	public Passport apply(PassCode passCode) {
		log.info("Fangte Apply Code: "+passCode.getSerialNo());
		Passport passport = this.applyCode(passCode);
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		passport.setSendOrderid(true);
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSerialno(passCode.getSerialNo());
		return passport;
	}
	
	/**
	 * 废码
	 */
	public Passport destroy(PassCode passCode) {
		log.info("Fangte Destroy Code :"+passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		passport.setSerialno(passCode.getSerialNo());
		Long startTime = 0L;
		try {
			startTime = System.currentTimeMillis();
			String[] result = FangteUtil.tgCancelOrder(passCode.getExtId());
			log.info("Fangte destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			String status = result[0];
			if(status.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
				passport.setComLogContent(status.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			}else{
				if (STATUS.equals(status)) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				} else {
					String code = result[1];
					passport.setComLogContent("供应商返回异常："+code);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("Fangte Destroy Error message:" + code);
				}
			}
		} catch (Exception e) {
			log.error("Fangte destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setComLogContent(e.toString());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("Fangte Destroy Error message: ", e);
		}
		return passport;
	}
	
	/**
	 * 重新申请码处理
	 * @param passport
	 * @param error
	 */
	public void reapplySet(Passport passport, long times) {
		OrderUtil.init().reapplySet(passport, times);
	}
	
	private Passport applyCode(PassCode passCode){
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdOrderItemMeta item =OrderUtil.init().getItemMeta(ordOrder, passCode);
		if (item != null) {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("objectId", item.getMetaBranchId());
			param.put("provider", PassportConstant.PASS_PROVIDER_TYPE.FANGTE.name());
			PassProduct passProduct = passCodeService.selectPassProductByParams(param);
			Long startTime = 0L;
			try {
				Assert.notNull(passProduct, "passProduct is null");
				String num = String.valueOf(item.getProductQuantity()*item.getQuantity()); //订购张数
				String parkId = passProduct.getProductTypeSupplier();
				String ticketTypeIds = passProduct.getProductIdSupplier();
				String exeTypeId = passProduct.getExtId();
				Assert.notNull(parkId, "passProduct.productTypeSupplier is null");
				Assert.notNull(ticketTypeIds, "passProduct.productIdSupplier is null");
				Assert.notNull(exeTypeId, "passProduct.extId is null");
				String [] ticketTypeIdArray = ticketTypeIds.split(",");
				String ticketTypeId = ticketTypeIdArray[0];
				String namelist="";
				String cardidlist="";
				StringBuilder namestr = new StringBuilder("");
				StringBuilder cardstr = new StringBuilder("");
				List<OrdPerson> personList = ordOrder.getTravellerList();
				int totalNum=Integer.parseInt(num);
				if(!personList.isEmpty() && personList.size()>0){
					for(int i=0;i<totalNum;i++){
						namestr.append(personList.get(i).getName()+"-");
						cardstr.append(personList.get(i).getCertNo()+"-");
					}
					namelist = namestr.toString();
					if (namelist.endsWith("-")) {
						namelist = namelist.substring(0, namelist.length()-1);
					}
					cardidlist = cardstr.toString();
					if (cardidlist.endsWith("-")) {
						cardidlist = cardidlist.substring(0, cardidlist.length()-1);
					}
				}
			
				Order order = new Order();
				order.setParkid(parkId);
				order.setPlandate(DateFormatUtils.format(ordOrder.getVisitTime(), "yyyy-MM-dd"));
				order.setTicketTypeId(ticketTypeId);
				order.setTicketcode(passCode.getSerialNo());
				order.setNum(num);
				order.setPhone(ordOrder.getContact().getMobile());
				order.setPrice("0");
				order.setExetypeid(exeTypeId);
				order.setNameList(namelist);
				order.setCardIdList(cardidlist);
				String[] result = null;
				startTime = System.currentTimeMillis();
				if (ticketTypeIdArray.length == 1) {
					result = FangteUtil.tgOrderTicket(order);
				} else {
					order.setComboTypeId(ticketTypeIdArray[1]);
					result = FangteUtil.tgOrderTicketAndCombo(order);
				}
				log.info("Fangte Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
				String status = result[0];
				String code = result[1];
				if (STATUS.equals(status)) {
					if (result.length > 2) {
						byte[] codeImage = new BASE64Decoder().decodeBuffer(result[3]);
						passport.setCodeImage(codeImage);
					}
					passport.setCode(passCode.getSerialNo());
					passport.setExtId(code);
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				} else {
					if(status.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
						passport.setComLogContent(status.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
					}else{
						passport.setComLogContent("供应商返回异常："+code);
					}
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					log.info("Fangte Apply Error message: " + code);
					this.reapplySet(passport, passCode.getReapplyCount());
				}
			} catch (Exception e) {
				log.error("Fangte Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent(e.toString());
				this.reapplySet(passport, passCode.getReapplyCount());
				log.error("Fangte Apply Error message: ", e);
			}
		}

		return passport;
	}

	/**
	 * 获取订单信息
	 */
	public Passport perform(PassCode passCode) {
		log.info("Fangte getOrder: " + passCode.getSerialNo());
		Passport passport = null;
		String[] result = FangteUtil.tgGetOrder(passCode.getExtId());
		if (result != null && result.length > 1) {
			String status = result[0];
			String code = result[1];
			if (STATUS.equals(status)) {
				JSONObject jsonObject = FangteUtil.getOrderJson(code);
				if(jsonObject!=null){
					String planstate = jsonObject.getString("planstate");
					//update by tangJing 2013-07-18
					if ("2".equals(planstate)) {// 是否已入园 [对方状态说明1=未出票2=已出票3=已退票]
						passport = new Passport();
						passport.setChild("0");
						passport.setAdult("0");
						passport.setUsedDate(new Date());
						passport.setDeviceId("Fangte");
					}
				}
			} else {
				log.info("Fangte getOrder error: " + code);
			}
		}
		return passport;
	}

}
