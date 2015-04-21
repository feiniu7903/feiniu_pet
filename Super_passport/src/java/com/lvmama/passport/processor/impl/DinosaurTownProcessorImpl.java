package com.lvmama.passport.processor.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassProduct;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.impl.client.dinosaurtown.ArrayOfOrderProduct;
import com.lvmama.passport.processor.impl.client.dinosaurtown.CustomerInfo;
import com.lvmama.passport.processor.impl.client.dinosaurtown.KLCServiceClient;
import com.lvmama.passport.processor.impl.client.dinosaurtown.OrderProduct;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.WebServiceConstant;
/**
 * 常州恐龙园
 * @author chenlinjun
 *
 */
public class DinosaurTownProcessorImpl implements  ApplyCodeProcessor, DestroyCodeProcessor  {
	private static final Log log = LogFactory.getLog(DinosaurTownProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private PassCodeService passCodeService = (PassCodeService) SpringBeanProxy.getBean("passCodeService");
	private static Map<String,String> errorCodeMap=new HashMap<String,String>();
	
	public  DinosaurTownProcessorImpl(){
		if (errorCodeMap.isEmpty()) {
			errorCodeMap.put("101", "合作伙伴编号不存在 ");
			errorCodeMap.put("102", "账户余额不足或订票总量已超出可售票总量 ");
			errorCodeMap.put("103", "此票不存在 ");
			errorCodeMap.put("104", "此票已停止销售");
			errorCodeMap.put("105", "订单人联系人资料不符合规范或部分资料缺失 ");
			errorCodeMap.put("106", "非法操作");
			errorCodeMap.put("107", "没有提交消息签名");
			errorCodeMap.put("108", "提交的消息签名不匹配");
			errorCodeMap.put("109", "订单产品列表写入数据库失败");
			errorCodeMap.put("110", "订单人信息写入数据库失败");
			errorCodeMap.put("111", "次订单以及订单人信息已经存在 ");
			errorCodeMap.put("112", "此订单不存在");
			errorCodeMap.put("113", "订单详情不吻合 ");
			errorCodeMap.put("114", "门票数量不能为0");
			errorCodeMap.put("115", "更新订单表失败 ");
			errorCodeMap.put("116", "不能修改订单的使用日期 ");
			errorCodeMap.put("117", "修改后的订单日期必须在20天里面 ");
			errorCodeMap.put("120", "游玩日期非法（需在下单次开始的 20 天之内）");
			errorCodeMap.put("201", "没有这时间段内的产品信息");
			errorCodeMap.put("202", "贵方下单日期此产品的允许销售日期不符（如周末下 非周末 票）");
			errorCodeMap.put("301", "大剧场没有“入园日期”以及第二天的场次");
			errorCodeMap.put("302", "大剧场没有“入园日期”以及第二天的座位");
			errorCodeMap.put("303", "没有剩余座位、或者锁定座位失败");
			errorCodeMap.put("403", "客户信息不合法（不全、不符合规则）");
			errorCodeMap.put("404", "订购单产品信息不合法");
		}
	}
	@Override
	public Passport apply(PassCode passCode) {
		log.info("DinosaurTown Apply Code Request :"+passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSendOrderid(false);
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport=this.applyCode(passport, passCode);
		log.info("passCode :"+passCode.getSerialNo() +" Client Result："+passport.getCode());
		return passport;
	}
	
	
	/**
	 * 获得响应XML
	 * 
	 * @param list
	 * @return
	 */
	public Passport applyCode(Passport passport, PassCode passCode) {
		// String testurl="http://webservice.cnkly.com/klcws.asmx ";
		// String url="http://webservice.konglongcheng.com/klcws.asmx ";
		// String MerchantCode="LXS001214";
		// String MerchantKey="A1B9c58Z";
		// 基本参数.
		OrdOrder order = this.orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson orderPerson = null;
		if (!order.getTravellerList().isEmpty() && StringUtils.isNotBlank(order.getTravellerList().get(0).getMobile())) {
			orderPerson = order.getTravellerList().get(0);
		} else {
			orderPerson = order.getContact();
		}
		String merchantCode = WebServiceConstant.getProperties("dinosaurtown_MerchantCode");
		String merchantKey = WebServiceConstant.getProperties("dinosaurtown_MerchantKey");
		String RefNo = String.valueOf(passCode.getSerialNo());
		// 业务-顾客详细信息.
		CustomerInfo customer = new CustomerInfo();
		customer.setLinkAddr(orderPerson.getAddress() == null ? "" : orderPerson.getAddress());
		customer.setLinkCard(orderPerson.getCertNo() == null ? "" : orderPerson.getCertNo());
		customer.setLinkMan(orderPerson.getName());
		customer.setLinkMobile(orderPerson.getMobile());
		customer.setLinkSex("");
		// 查询订单详情.
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getPageIndex().setBeginIndex(0);
		compositeQuery.getPageIndex().setEndIndex(1000000000);
		compositeQuery.getMetaPerformRelate().setTargetId(String.valueOf(passCode.getPassPortList().get(0).getTargetId()));
		compositeQuery.getMetaPerformRelate().setOrderId(passCode.getOrderId());
		List<OrdOrderItemMeta> orderitemMeta = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);

		List<OrderProduct> orderProductsList = new ArrayList<OrderProduct>();
		Long startTime = 0L;
		try {
			// 订单子子项
			for (OrdOrderItemMeta itemMeta : orderitemMeta) {
				if (itemMeta.getOrderItemMetaId().longValue() == (passCode.getObjectId().longValue())) {
					Map<String, Object> params = new HashMap<String, Object>();
					String date = DateFormatUtils.format(itemMeta.getVisitTime(), "yyyy-MM-dd");
					params.put("visitDate", date);
					params.put("objectId", itemMeta.getMetaProductId());
					params.put("provider", PassportConstant.PASS_PROVIDER_TYPE.DINOSAURTOWN.name());
					// params.put("merchantType", merchantType);
					PassProduct passProduct = passCodeService.selectPassProductByParams(params);
					String no = "";
					String name = "";
					if (passProduct != null) {
						merchantCode = passProduct.getMerchantType();
						if (StringUtils.isBlank(merchantCode)) {
							throw new IllegalArgumentException("商户号不能空");
						}
						merchantCode = merchantCode.trim();
						
						no = passProduct.getProductIdSupplier();
						if (StringUtils.isBlank(no)) {
							throw new IllegalArgumentException("代理产品编号不能空");
						}
						no = no.trim();
						
						name = passProduct.getProductTypeSupplier();
						if (StringUtils.isBlank(name)) {
							throw new IllegalArgumentException("代理产品类型不能空");
						}
						name = name.trim();
					} else{
						log.info("PassProduct is null. visistDate=" + date + ", objectId=" + itemMeta.getMetaProductId());
						throw new IllegalArgumentException("没有绑定通关产品或绑定有误");
					}
					String visitTime = DateFormatUtils.format(itemMeta.getVisitTime(), "yyyy-MM-dd HH:mm:ss");
					String createTime = DateFormatUtils.format(order.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
					Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
					float price = this.getPrice(passCode.getOrderId(), itemMeta.getOrderItemMetaId());
					OrderProduct orderProduct = new OrderProduct();
					orderProduct.setCreateDate(createTime);
					orderProduct.setInTime(visitTime);
					orderProduct.setPrice(price);
					orderProduct.setProductName(name);
					orderProduct.setProductNo(no);
					orderProduct.setQuantity(count.intValue());
					orderProduct.setSceneDate(visitTime);
					orderProductsList.add(orderProduct);
				}
			}

			StringBuilder buf = new StringBuilder("DinosaurTown Apply INFO: merchantCode=" + merchantCode + "RefNo=" + RefNo + ",{LinkMan=" + customer.getLinkMan() + ",LinkMobile="
					+ customer.getLinkMobile() + ",LinkAddr=" + customer.getLinkAddr() + ",LinkCard=" + customer.getLinkCard() + "}");
			for (OrderProduct orderProduct : orderProductsList) {
				buf.append("{CreateDate=" + orderProduct.getCreateDate() + ",InTime=" + orderProduct.getInTime() + ",Price=" + orderProduct.getPrice() + ",ProductName="
						+ orderProduct.getProductName() + ",ProductNo=" + orderProduct.getProductNo() + ",Quantity=" + orderProduct.getQuantity() + ",SceneDate=" + orderProduct.getSceneDate() + "}");
			}
			log.info(buf.toString());
			ArrayOfOrderProduct orderPrdouct = new ArrayOfOrderProduct();
			orderPrdouct.orderProduct = orderProductsList;
			KLCServiceClient client = new KLCServiceClient();
			startTime = System.currentTimeMillis();
			String Signature = client.getKLCServiceSoap().testSignature(merchantKey, merchantCode, RefNo, customer.getLinkMan(), customer.getLinkSex(), customer.getLinkMobile(),
					customer.getLinkCard(), customer.getLinkAddr(), "", "");
			startTime = System.currentTimeMillis();
			String code = client.getKLCServiceSoap().postOrdersProducts(merchantCode, RefNo, customer, orderPrdouct, Signature);
			log.info("DinosaurTown Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("DinosaurTown Apply code:" + code);
			if (code.contains("W")) {
				passport.setCode(code + ",如果您收到彩信，请凭彩信二维码取票，否则请使用本短信的电子票凭证");
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				passport.setExtId(code);
			} else {
				if(StringUtils.equals(code,"111")){
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				}else{
					String error = "供应商返回异常："+errorCodeMap.get(code.trim());
					if (error == null || "".equals(error)) {
						error = "没有此错误号相对应的错误描述";
					}
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					passport.setComLogContent(error);
					this.reapplySet(passport, passCode.getReapplyCount());
					}

			}
		} catch (Exception e) {
			log.error("DinosaurTown Apply serialNo Error:" + passCode.getSerialNo()+"UseTime:" +(System.currentTimeMillis() - startTime)/1000);
			log.error("DinosaurTown Apply Exception:", e);
			String error = e.getMessage();
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			passport.setComLogContent(error);
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			this.reapplySet(passport, passCode.getReapplyCount());
		}
		return passport;
	}
	/**
	 * 
	 * @param orderId
	 * @param orderItemMetaId
	 * @return
	 */
	public float getPrice(Long orderId, Long orderItemMetaId) {
		float price = 0.0f;
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		boolean exit = false;
		// 订单子指向
		for (OrdOrderItemProd itemProd : ordOrder.getOrdOrderItemProds()) {
			if (exit) {
				break;
			}
			for (OrdOrderItemMeta itemMeta : itemProd.getOrdOrderItemMetas()) {
				if (itemMeta.getOrderItemMetaId().equals(orderItemMetaId)) {
					// 价格
					price = itemProd.getPriceYuan();
					exit = true;
					break;
				}
			}
		}
		return price;
	}
	
	/**
	 * 重新申请码处理
	 * @param passport
	 * @param error
	 */
	public void reapplySet(Passport passport,long times){
		OrderUtil.init().reapplySet(passport, times);
	}
	/**
	 * 废碼
	 */
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("DinosaurTown DestoyCode Request :"+passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		passport.setComLogContent("供应商不提供废码接口");
		passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
		passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
//		this.destroyCode(passport, passCode );
		return passport;
	}
	
	public Passport destroyCode(Passport passport,PassCode passCode)  {
		//基本参数.
		OrdOrder order=this.orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
//		OrdPerson orderPerson=order.getContact();
		OrdPerson orderPerson = null;
		if (!order.getTravellerList().isEmpty() && null != order.getTravellerList().get(0).getMobile()) {
			orderPerson = order.getTravellerList().get(0);
		} else {
			orderPerson = order.getContact();
		}
		String merchantCode=WebServiceConstant.getProperties("dinosaurtown_MerchantCode");
		String merchantKey=WebServiceConstant.getProperties("dinosaurtown_MerchantKey");
		
    	String RefNo=String.valueOf(passCode.getSerialNo());
    	//业务-顾客详细信息.
    	CustomerInfo customer=new CustomerInfo();
		customer.setLinkAddr(orderPerson.getAddress()==null?"":orderPerson.getAddress());
		customer.setLinkCard(orderPerson.getCertNo()==null?"":orderPerson.getCertNo());
		customer.setLinkMan(orderPerson.getName());
		customer.setLinkMobile(orderPerson.getMobile());
		customer.setLinkSex("");
		//查询订单详情.
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getPageIndex().setBeginIndex(0);
		compositeQuery.getPageIndex().setEndIndex(1000000000);
		compositeQuery.getMetaPerformRelate().setTargetId(String.valueOf(passCode.getPassPortList().get(0).getTargetId()));
		compositeQuery.getMetaPerformRelate().setOrderId(passCode.getOrderId());
		List<OrdOrderItemMeta> orderitemMeta = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);

		List<OrderProduct> orderProductsList=new ArrayList<OrderProduct>();
		// 订单子子项
		for (OrdOrderItemMeta itemMeta : orderitemMeta) {
			Map<String,Object> params=new HashMap<String,Object>();
			String date = DateFormatUtils.format(itemMeta.getVisitTime(), "yyyy-MM-dd");
			params.put("visitDate", date);
			params.put("objectId", itemMeta.getMetaProductId());
			params.put("provider", PassportConstant.PASS_PROVIDER_TYPE.DINOSAURTOWN.name());
			//params.put("merchantType", merchantType);
			PassProduct passProduct =passCodeService.selectPassProductByParams(params);
			String no ="";
			String name="";
			if(passProduct!=null){
				merchantCode=passProduct.getMerchantType().trim();
				no = passProduct.getProductIdSupplier();
				name = passProduct.getProductTypeSupplier();
			}else{
				no = itemMeta.getProductIdSupplier();
				name = itemMeta.getProductTypeSupplier();
			}
			String visitTime = DateFormatUtils.format(itemMeta.getVisitTime(), "yyyy-MM-dd HH:mm:ss");
			String createTime = DateFormatUtils.format(order.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
			int count = (int)(itemMeta.getTotalChildQuantity()+ itemMeta.getTotalAdultQuantity());
			float price=this.getPrice(passCode.getOrderId(), itemMeta.getOrderItemMetaId());
			OrderProduct orderProduct=new OrderProduct();
			orderProduct.setCreateDate(createTime);
			orderProduct.setInTime(visitTime);
			orderProduct.setPrice(price);
			orderProduct.setProductName(name);
			orderProduct.setProductNo(no);
			orderProduct.setQuantity(count);
			orderProduct.setSceneDate(visitTime);
			orderProductsList.add(orderProduct);
		}
		try {
			ArrayOfOrderProduct orderPrdouct=new ArrayOfOrderProduct();
			orderPrdouct.orderProduct=orderProductsList;
			 StringBuilder buf=new StringBuilder("DinosaurTown DestoyCode INFO: merchantCode="+merchantCode+"RefNo="+RefNo+",{LinkMan="+customer.getLinkMan()+",LinkMobile="+customer.getLinkMobile()+",LinkAddr="+customer.getLinkAddr()+"}");
			 for(OrderProduct orderProduct :orderProductsList){
				 buf.append("{CreateDate="+orderProduct.getCreateDate()+",InTime="+orderProduct.getInTime()+",Price="+orderProduct.getPrice()+",ProductName="+orderProduct.getProductName()
						 +",ProductNo="+orderProduct.getProductNo()+",Quantity="+orderProduct.getQuantity()+",SceneDate="+orderProduct.getSceneDate()+"}");
			 }
			 log.info(buf.toString());
			 
			KLCServiceClient client=new KLCServiceClient();
			String Signature=client.getKLCServiceSoap().testSignature(merchantKey,merchantCode, RefNo,passCode.getExtId(),"1", customer.getLinkMan(), customer.getLinkSex(),customer.getLinkMobile(),customer.getLinkCard(), customer.getLinkAddr());
			String code=client.getKLCServiceSoap().editOrders(merchantCode, RefNo,passCode.getExtId(), customer,orderPrdouct,1, Signature);
			String error=errorCodeMap.get(code.trim());
			log.info("DinosaurTown DestoyCode code:"+code);
			if(error==null){
				passport.setCode(code);
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			}else{
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setComLogContent("供应商返回异常："+error);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			}
		} catch (Exception e) {
			log.error("DinosaurTown DestoyCode Exception:", e);
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
		}
	return passport;
}
	public static void main(String[] args) {
		//DinosaurTown Apply Code Request :201402271169293
		//DinosaurTown Apply INFO: merchantCode=LXS001214RefNo=201402271169293,{LinkMan=刘伟,LinkMobile=13916518143,LinkAddr=,
		//LinkCard=310108198807042611}{CreateDate=2014-02-27 10:43:06,InTime=2014-02-28 00:00:00,Price=268.0,
		//ProductName=恐龙园+温泉联票（网订）,ProductNo=2060237,Quantity=5,SceneDate=2014-02-28 00:00:00}
		String merchantCode = WebServiceConstant.getProperties("dinosaurtown_MerchantCode");
		String merchantKey = WebServiceConstant.getProperties("dinosaurtown_MerchantKey");
		String RefNo=String.valueOf("201402271169293");
		List<OrderProduct> orderProductsList=new ArrayList<OrderProduct>();
    	//业务-顾客详细信息.
    	CustomerInfo customer=new CustomerInfo();
		customer.setLinkAddr("");
		customer.setLinkCard("");
		customer.setLinkMan("测试重复单");
		customer.setLinkMobile("15026847838");
		customer.setLinkSex("");
		String visitTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		String createTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		OrderProduct orderProduct=new OrderProduct();
		orderProduct.setCreateDate(createTime);
		orderProduct.setInTime(visitTime);
		orderProduct.setPrice(1.0);
		orderProduct.setProductName("恐龙园+温泉联票（网订）");
		orderProduct.setProductNo("2060237");
		orderProduct.setQuantity(1);
		orderProduct.setSceneDate(visitTime);
		orderProductsList.add(orderProduct);
		ArrayOfOrderProduct orderPrdouct = new ArrayOfOrderProduct();
		orderPrdouct.orderProduct = orderProductsList;
		KLCServiceClient client = new KLCServiceClient();
		String Signature = client.getKLCServiceSoap().testSignature(merchantKey, merchantCode, RefNo, customer.getLinkMan(), customer.getLinkSex(), customer.getLinkMobile(),
				customer.getLinkCard(), customer.getLinkAddr(), "", "");
		String code = client.getKLCServiceSoap().postOrdersProducts(merchantCode, RefNo,customer, orderPrdouct, Signature);
		log.info("DinosaurTown Apply code:" + code);
		
	}
}
