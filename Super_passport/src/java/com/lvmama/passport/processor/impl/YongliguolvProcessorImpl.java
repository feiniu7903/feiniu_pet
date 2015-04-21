package com.lvmama.passport.processor.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
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
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.ResendCodeProcessor;
import com.lvmama.passport.processor.impl.client.ylgl.YongliguolvUtil;
import com.lvmama.passport.processor.impl.client.ylgl.model.Product;
import com.lvmama.passport.processor.impl.client.ylgl.model.Request;
import com.lvmama.passport.processor.impl.client.ylgl.model.ResponseApplyCode;
import com.lvmama.passport.processor.impl.client.ylgl.model.ResponseDestoryCode;
import com.lvmama.passport.processor.impl.client.ylgl.model.ResponseProductInfo;
import com.lvmama.passport.processor.impl.client.ylgl.model.ResponseReplayCode;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 永利国旅服务商
 * @author lipengcheng
 * @date 2011-10-31
 */
public class YongliguolvProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor, ResendCodeProcessor {
	private static final Log log = LogFactory.getLog(YongliguolvProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private PassCodeService passCodeService = (PassCodeService) SpringBeanProxy.getBean("passCodeService");
	private static Map<String,String> errorMap = new HashMap<String,String>(); 
	private static final int CONNECTION_TIMEOUT = 10000;
	private static final int SO_TIMEOUT = 180000;
	private static final String CHARACTER_ENCODING = "UTF-8";
	public YongliguolvProcessorImpl() {
		if(errorMap.isEmpty()) {
			errorMap.put("0000", "交易成功");
			errorMap.put("0001", "交易重复委托,返回原交易信息");
			errorMap.put("-97", "报文解析错误");
			errorMap.put("-101", "无法获取请求报文");
			errorMap.put("-100", "报文设置有误");
			errorMap.put("-105", "发往回执平台失败");
			errorMap.put("-107", "回执平台响应异常");
			errorMap.put("-109", "回执交易失败");
			errorMap.put("-103", "等待回执平台响应超时");
			errorMap.put("-108", "解析回执平台响应失败");
			errorMap.put("503", " 该接口不支持GET 方式，请以POST 方式提交");
			errorMap.put("-200", "生成压缩文件失败");
			errorMap.put("1000", "系统错误");
			errorMap.put("4012", "机构状态不正常");
			errorMap.put("4014", "该机构码不存在");
			errorMap.put("4013", "机构IP 未绑定");
			errorMap.put("4017", "活动状态不正常");
			errorMap.put("2013", "活动已经结束");
			errorMap.put("4016", "活动不存在或者已过期");
			errorMap.put("4055", "彩信标题超长");
			errorMap.put("4056", "短信内容超长");
			errorMap.put("2048", "有效期限不能小于当前时间");
			errorMap.put("4054", "发送类型错误");
			errorMap.put("1002", "请求流水号重复");
			errorMap.put("2007", "商品状态不正常");
			errorMap.put("2009", "该商品属于次数判断,必须送初始次数");
			errorMap.put("2010", "该商品属于金额判断,必须送初始金额");
			errorMap.put("2011", "该商品属于密码判断,必须送密码");
			errorMap.put("2018", "没有找到该商品信息");
			errorMap.put("4023", "交易类型不为生成撤销失败");
			errorMap.put("2001", "该笔委托状态不正常不能撤销");
			errorMap.put("-100", "订单提交异常：商户余额不足,未进行发送.");
			errorMap.put("-300", "短信发送异常：订单撤销.");
			errorMap.put("-301", "短信发送异常：订单冲正异常.");
			errorMap.put("-303", "订单提交异常：未进行发送.");
			errorMap.put("-900", "订单无效,xml解析错误.");
			errorMap.put("-901", "账户信息无效.");
			errorMap.put("-902", "票号无效.");
			errorMap.put("2002", "订单已被撤销");
		}
	}
	@Override
	public Passport apply(PassCode passCode) {
		log.info("Yongliguolv Applay Code :" + passCode.getSerialNo());
		Passport passport=new Passport();
		long startTime = 0L;
		try {
			Map<String,String> params = this.applyCode(passCode);
			Request request = new Request();
			request.setRequestType("generated_commission_request");
			request.setOrganization(WebServiceConstant.getProperties("yongliguolv_organization"));
			request.setUserId("驴妈妈旅游");
			request.setPwd(WebServiceConstant.getProperties("yongliguolv_pwd"));
			request.setDltel(WebServiceConstant.getProperties("yongliguolv_tel"));
			request.setDlurl(WebServiceConstant.getProperties("yongliguolv_fenxiao_url"));
			request.setProductId(params.get("productId"));
			request.setTel(params.get("tel"));
			request.setCount(params.get("count"));
			request.setType("1");//0多次票1多人票
			request.setOrderId(passCode.getSerialNo());
			request.setUseTime(params.get("useTime"));
			request.setUseName(params.get("useName"));
			
			Map<String, String> param=new HashMap<String,String>();
			param.put("xml", request.toApplayCodeRequestXml());
			startTime = System.currentTimeMillis();
			String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("yongliguolv_url"),param,CHARACTER_ENCODING, CHARACTER_ENCODING,CONNECTION_TIMEOUT,SO_TIMEOUT);
			log.info("Yongliguolv Apply Code serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("Yongliguolv Apply Code Result:" + result);
			if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常："+result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				this.reapplySet(passport, passCode.getReapplyCount(), "FAILED");
			}else{
				ResponseApplyCode responseApplyCode = YongliguolvUtil.getApplyCodeReq(result);
	//			passport.setCode(responseApplyCode.getSysSeq());// 平台流水号
				passport.setExtId(responseApplyCode.getReqSeq());// 放置订单号
				passport.setSerialno(passCode.getSerialNo());
				passport.setAddCode(responseApplyCode.getAdditional());
				if("0000".equals(responseApplyCode.getResult().getId())){
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				}else{
					log.info("Yongliguolv Applay Result status: " + errorMap.get(responseApplyCode.getResult().getId()));
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					passport.setComLogContent("供应商返回异常："+responseApplyCode.getResult().getComment());
					this.reapplySet(passport, passCode.getReapplyCount(), "FAILED");
				}
			}
		} catch (Exception e) {
			log.error("yongliguolv Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.error("Yongliguolv ApplyCode Exception:",e);
			String error=e.getMessage();
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			passport.setComLogContent(error);
			this.reapplySet(passport, passCode.getReapplyCount(), error);
		}
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());
		passport.setSendOrderid(true);
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		return passport;
	}

	public void reapplySet(Passport passport,long times,String error ){
		OrderUtil.init().reapplySet(passport, times);
	}
	
	/**
	 * 撤消交易
	 */
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("Yongliguolv destoy Code :" + passCode.getSerialNo());
		Passport passport=new Passport();
		long startTime = 0L;
		try {
			Request request = new Request();
			request.setRequestType("commission_cancel_request");
			request.setOrganization(WebServiceConstant.getProperties("yongliguolv_organization"));
			request.setUserId("驴妈妈旅游");
			request.setPwd(WebServiceConstant.getProperties("yongliguolv_pwd"));
			request.setOriSeq(passCode.getExtId());
			
			Map<String, String> param=new HashMap<String,String>();
			param.put("xml", request.toDestoryCodeRequestXml());
			startTime = System.currentTimeMillis();
			String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("yongliguolv_url"),param,CHARACTER_ENCODING, CHARACTER_ENCODING,CONNECTION_TIMEOUT,SO_TIMEOUT);
			log.info("Yongliguolv Destory serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("Yongliguolv Destory Code Result:" + result);
			if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setComLogContent("供应商返回异常："+result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
			}else{
				ResponseDestoryCode responseApplyCode = YongliguolvUtil.getDestoryCodeReq(result);
	//			responseApplyCode.getTransTime();//时间
				if("0000".equals(responseApplyCode.getResult().getId())){
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				}else{
					log.error("Yongliguolv Destory Result status: " + errorMap.get(responseApplyCode.getResult().getId()));
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					passport.setComLogContent("供应商返回异常："+responseApplyCode.getResult().getId());
				}
			}
		} catch (Exception e) {
			log.error("Yongliguolv Destory serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.error("Yongliguolv Destoy Exception:",e);
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
		}
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		return passport;
	}
	
	
	/**
	 * 得到产品信息
	 * @throws Exception 
	 */
	public static void getProduct() throws Exception {
		Request request = new Request();
		request.setRequestType("product_get_request");
		request.setOrganization(WebServiceConstant.getProperties("yongliguolv_organization"));
		request.setUserId("驴妈妈旅游");
		request.setPwd(WebServiceConstant.getProperties("yongliguolv_pwd"));
		
		Map<String, String> param=new HashMap<String,String>();
		param.put("xml", request.toProductInfoRequestXml());
		String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("yongliguolv_url"),param,CHARACTER_ENCODING, CHARACTER_ENCODING,CONNECTION_TIMEOUT,SO_TIMEOUT);

		ResponseProductInfo responseProductInfo = YongliguolvUtil.getProductReq(result);
		
		
		List<Product> products = responseProductInfo.getProducts();
		
		if(products!=null){
			for(Product product : products){
				if(product.getProductEndTime()!=null && !"".equals(product.getProductEndTime())){
					
					if(product.getProductTickettype().trim().equals("1")){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String endTime = sdf.format(DateUtil.toDate(product.getProductEndTime(), "yyyy-MM-dd HH:mm:ss"));
					StringBuilder sql = new StringBuilder();
					
					sql.append("INSERT INTO PASS_PRODUCT (PASS_PROD_ID,OBJECT_ID,PRODUCT_ID_SUPPLIER,PRODUCT_TYPE_SUPPLIER,END_DATE,PROVIDER) VALUES")
					   .append("(")
					   .append("pass_product_id_seq.nextval"+",")
					   .append("11111111,")//采购产品ID还不知道
					   .append("\'"+product.getProductId()+"\',")
					   .append("\'"+product.getProductName()+"\',")
					   .append("to_date(\'"+endTime+"\',\'yyyy-MM-dd\'),")
					   .append("\'"+"YONGLIGUOLV"+"\'")
					   .append(");");
					System.out.println(sql.toString());
					}
				}
			}
		}
	}

	/**
	 * 重发
	 */
	@Override
	public Passport resend(PassCode passCode) {
		log.info("Yongliguolv Resend SMS ：" + passCode.getSerialNo());
		Passport passport = new Passport();
		try {
			Request request = new Request();
			request.setRequestType("er_resend_request");
			request.setOrganization(WebServiceConstant.getProperties("yongliguolv_organization"));
			request.setUserId("驴妈妈旅游");
			request.setPwd(WebServiceConstant.getProperties("yongliguolv_pwd"));
			request.setOriSeq(passCode.getExtId());//申请重发的订单号
			
			Map<String, String> param=new HashMap<String,String>();
			param.put("xml", request.toRePlayApplayCodeRequestXml());
			String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("yongliguolv_url"),param,CHARACTER_ENCODING, CHARACTER_ENCODING,CONNECTION_TIMEOUT,SO_TIMEOUT);
			if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setComLogContent("供应商返回异常："+result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
			}else{
				ResponseReplayCode responseReplayCode = YongliguolvUtil.getReplayCodeReq(result);
				if("0000".equals(responseReplayCode.getResult().getId())){
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				}else{
					log.info("Yongliguolv Resend Result status: " + errorMap.get(responseReplayCode.getResult().getId()));
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					passport.setComLogContent("供应商返回异常："+errorMap.get(responseReplayCode.getResult().getId()));
				}
			}
		} catch (Exception e) {
			log.error("Yongliguolv Resend Exception:",e);
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
		}
		
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.RESEND.name());
		return passport;
	}
	
	
	public Map<String,String> applyCode(PassCode passCode){
		 Map<String,String> data=new HashMap<String,String>();
			//基本参数.
			OrdOrder order=this.orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			OrdPerson orderPerson =OrderUtil.init().getContract(order);
			data.put("useName", orderPerson.getName());
			data.put("tel", orderPerson.getMobile());
			 boolean exit=false;
				// 订单子指向
			 for (OrdOrderItemProd itemProd : order.getOrdOrderItemProds()) {
					if(exit){
						break;
					}
					for (OrdOrderItemMeta itemMeta : itemProd.getOrdOrderItemMetas()) {
						if(itemMeta.getOrderItemMetaId().equals(passCode.getObjectId())){
							Map<String,Object> params=new HashMap<String,Object>();
							Date tempDate=DateUtils.addDays(itemMeta.getVisitTime(), itemMeta.getValidDays().intValue());
							String date = DateFormatUtils.format(tempDate, "yyyy-MM-dd");
							params.put("objectId", itemMeta.getMetaProductId());
							params.put("provider", PassportConstant.PASS_PROVIDER_TYPE.YONGLIGUOLV.name());
							Calendar cal = Calendar.getInstance();
							cal.setTime(itemMeta.getVisitTime());
						    int isWeeked = cal.get(Calendar.DAY_OF_WEEK);
						    PassProduct passProduct =null;
							if(isWeeked==7 || isWeeked ==1){
								params.put("isWeekend", "TRUE");
								passProduct=passCodeService.selectPassProductByParams(params);
							}
							if(passProduct==null) {
								params.put("isWeekend", "FALSE");
								passProduct = passCodeService.selectPassProductByParams(params);
							}
							String no ="";
							if(passProduct!=null){
								no = passProduct.getProductIdSupplier();
								date=DateFormatUtils.format(passProduct.getEndDate(), "yyyy-MM-dd");
							}else{
								no = itemMeta.getProductIdSupplier(); 
							}
							String count=String.valueOf(itemMeta.getProductQuantity()*itemMeta.getQuantity());
							data.put("count", count);
							data.put("productId", no);
							data.put("useTime", date);
							exit=true;
							break;
						}
				}
			 }
		return data;
	}
	
	
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}


	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	
}
