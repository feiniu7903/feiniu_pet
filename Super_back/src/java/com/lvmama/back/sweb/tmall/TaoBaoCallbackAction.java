package com.lvmama.back.sweb.tmall;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.python.modules.synchronize;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.StringUtil;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.tmall.OrdTmallMap;
import com.lvmama.comm.bee.service.DownTmallOrderInterface;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.tmall.OrdTmallMapService;
import com.lvmama.comm.bee.service.tmall.TOPInterface;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.utils.json.JSONResult;
import com.taobao.api.ApiException;
import com.taobao.api.response.TradeFullinfoGetResponse;

/***
 * 接收淘宝电子凭证通知回调  action
 * @author dingming
 *
 */
public class TaoBaoCallbackAction extends BaseAction {
	private static final Log log = LogFactory.getLog(TaoBaoCallbackAction.class);
	private static final long serialVersionUID = 1L;
	//验签密钥
	private static final String SIGN_SECRET="65f39986167809ae78a1fd3beb84565a";
	private OrdTmallMapService ordTmallMapService;
	private DownTmallOrderInterface downTmallOrderInterface;
	private PassCodeService passCodeService;
	private TopicMessageProducer passportMessageProducer;
	private TopicMessageProducer orderMessageProducer;
	private ProdProductService prodProductService;
	private OrderService orderServiceProxy;
	
    /**接口调用时的时间：yyyy-MM-dd HH:mm:ss*/
	private String timestamp;
	/**签名*/
	private String sign; 
	/** 淘宝订单交易号*/
	private String order_id;
	/**买家的手机号码*/
	private String mobile;
	/**购买的商品数量*/
	private String num;
	/** 重新发码和修改手机号码通知特有参数 ,剩余的可核销数 **/
	private String left_num; 
	/**通知类型(send,resend,cancle...)*/
	private String method;
	/** 淘宝卖家seller_id*/
	private String taobao_sid;
	/**淘宝卖家用户名（旺旺号）*/
	private String seller_nick;
	/**商品标题*/
	private String item_title;
	/**发送类型：0：二维码(发短信、彩信，短信只有验证码) 1：矩阵码(只发短信，短信包含矩阵码和验证码) 2：验证码(只发短信，短信只有验证码)，目前默认值为2*/
	private String send_type;
	/** 核销类型:0：不限制 1:一码一刷 2:一码多刷 默认值为0*/
	private String consume_type;
	/**短信、彩信文字模板*/
	private String sms_template;
	/**有效期开始时间:yyyy-MM-dd HH:mm:ss*/
	private String valid_start;
	/**有效期截止时间：yyyy-MM-dd HH:mm:ss*/
	private String valid_ends;
	/**淘宝商品编号*/
	private String num_iid; 
	/**商家发布商品时有填写的话就传*/
	private String outer_iid;
	/**下单时选择的销售属性对应的商家编码*/
	private String sub_outer_iid;
	/**下单时选择的销售属性对应的文本*/
	private String sku_properties;
	/**token验证串，商家回调时须回传，否则将验证不通过，同一个订单的token是唯一的，不会变*/
	private String token;
	/**订单实付金额*/
	private String total_fee;
	/** 接收订单修改通知（使用有效期修改、维权成功）的通知特有参数   通知类型: 1. 使用有效期修改通知 2. 维权成功通知*/
	private String sub_method;
	/** 接收订单修改通知（使用有效期修改、维权成功）的通知特有参数,修改的数据 注意：处理data之前必须判断sub_method的类型，不同的类型会返回不同的data数据,如果是使用有效期修改，通知的是有效期开始和结束时间{“valid_start”:有效期开始时间,”valid_ends”:有效期结束时间} */
	private String data;
	
	
	/**电子凭证平台回调接口**/
	@Action(value = "/callback/notice")
	public synchronized void  notice() {
		JSONResult result = new JSONResult();
		try {
			log.info("receive taobao eticket "+method+" type notice!!!");
			Map<String,String[]> paramsMap = getRequest().getParameterMap();
			Map<String, String> param = new HashMap<String, String>();
			StringBuffer params_str = new StringBuffer();
			for(Entry<String,String[]> item: paramsMap.entrySet()){
				String value = item.getValue()[0];
				String key = item.getKey();
				params_str.append(key).append("=").append(value).append("&");;
				if(!StringUtil.isEmptyString(value) && !"sign".equalsIgnoreCase(key)){
					param.put(key, value);
				}
			}
			log.info("Tmall callback params:"+params_str);
			String signResult = SignUtil.sign(SIGN_SECRET,param);
				if (signResult.equals(sign)) {
					if (method.equals("send")) {
						result.put("code", 200);
						result.outPutNoMessage(getResponse());
						try {
							TradeFullinfoGetResponse response = addOrder(param);
							if(response!=null){
								downTmallOrderInterface.downEticketOrder(param, response);
							}
						} catch (Exception e) {
							result.put("code", 502);
							result.outPutNoMessage(getResponse());
							log.error(this, e);
						}
					} else if (method.equals("resend")) {
						result.put("code", 200);
						result.outPutNoMessage(getResponse());
						try {
							resendCode(order_id, token);
						} catch (Exception e) {
							log.error(this, e);
						}
	
					} else if (method.equals("cancel")) {
						result.put("code", 200);
						result.outPutNoMessage(getResponse());
						cancelCode(order_id);
					} else if (method.equals("modified")) {
						// 修改手机号码
						result.put("code", 200);
						result.outPutNoMessage(getResponse());
						try {
							modifyMobile(order_id,token);
						}catch (Exception e) {
							log.error(this, e);
						}
					} else if (method.equals("order_modify")) {
						// 修改订单
						result.put("code", 200);
						result.outPutNoMessage(getResponse());
					}
				}else{
					log.error(order_id+"sign not match!!!!!");
					result.put("code", 501);
					result.outPutNoMessage(getResponse());
				}
		} catch (Exception e) {
			log.error(order_id + ":淘宝回调异常"+e.getMessage());
			result.put("code", 500);
			result.outPutNoMessage(getResponse());
		}
	}

	/***
	 * 根据淘宝订单号获取订单详情 将需要搬单的电子凭证订单插入到ord_tmall_map中并保存token值
	 * 
	 * @param tmallId
	 * @throws Exception
	 */
	private TradeFullinfoGetResponse addOrder(Map<String,String> data) throws Exception {
		//淘宝订单交易号
		String tmallId = data.get("order_id");
		String productIdBranchId=data.get("sub_outer_iid");
		if(ordTmallMapService.selectTmallNo(tmallId)==false){
			log.error(tmallId+" already  insert ord_tmall_map!!!!!");
			return null;
		}
		TradeFullinfoGetResponse response = TOPInterface.getFullIfo(tmallId);
		List<com.taobao.api.domain.Order> ords = response.getTrade().getOrders();
		OrdTmallMap order = null;
		for (com.taobao.api.domain.Order ord : ords) {
			order = new OrdTmallMap();
			order.setTmallOrderNo(response.getTrade().getTid() + "");
			order.setBuyerNick(response.getTrade().getBuyerNick());
			order.setBuyerMobile(mobile);
			order.setProductTmallPrice(new BigDecimal(ord.getPrice()));
			Long productId = null;
			Long branchId = null;
			if (!StringUtil.isEmptyString(productIdBranchId)) {
				if (productIdBranchId.indexOf(",") != -1) {
					String arrs[] = productIdBranchId.split(",");
					productId = Long.valueOf(arrs[0]);
					branchId = Long.valueOf(arrs[1]);
				}
			}
			order.setProductId(productId);
			order.setCategoryId(branchId);
			order.setProductName(ord.getSkuPropertiesName());
			order.setSystemOrder("true");
			order.setToken(token);
			order.setBuyNum(ord.getNum().toString());
			order.setOperatorName("system");
			order.setCreateTime(new Date());
			if(prodProductService !=null ){
				ProdProduct product = prodProductService.getProdProductById(productId);
				if(product != null){
					order.setProductType(product.getProductType());
				}
			}
			ordTmallMapService.insert(order);
			log.info("put taobao eticket order insert ord_tmall_map: "+tmallId+", "+productId+", "+branchId+";");
		}
		return response;
	}

	/**
	 * 重新发码通知处理
	 * 
	 * @param tmallId
	 * @throws NumberFormatException
	 * @throws ApiException
	 */
	private void resendCode(String tmallId, String token) throws NumberFormatException, ApiException {
		List<OrdTmallMap> ords = ordTmallMapService.selectByTmallNo(tmallId);
		PassCode passCode = null;
		StringBuffer sb=new StringBuffer();
		for (OrdTmallMap ord : ords) {
			// token决定是电子凭证平台订单 lvorderid决定是super下单成功的订单
			if (!StringUtil.isEmptyString(ord.getToken())&& ord.getLvOrderId() != null) {
				log.info(ord.getLvOrderId()+" apply resend passCode begin!!!");
				passCode = passCodeService.getPassCodeByOrderIdStatus(ord.getLvOrderId());
				if(passCode!=null){
					PassEvent event = passCodeService.resend(passCode.getCodeId());
					passportMessageProducer.sendMsg(MessageFactory.newPasscodeEventMessage(event.getEventId()));
					if (!passCode.isNeedSendSms()) { 
						passportMessageProducer.sendMsg(MessageFactory.newPasscodeEventMessage(event.getEventId())); 
						} else { 
						orderMessageProducer.sendMsg(MessageFactory.newCertSmsSendMessage(ord.getLvOrderId(), passCode.getMobile())); 
						} 
					log.info(ord.getLvOrderId()+" apply resend passCode ok!!!");
					sb.append(passCode.getCode()+":"+ord.getBuyNum());
					sb.append(",");;
				}else{
					log.error(ord.getLvOrderId()+" apply resend passCode failed!!!");
				}
			}
		}
		if(sb.length()>=1){
			sb.deleteCharAt(sb.length()-1);
		}
//		TOPInterface.eticketReSend(Long.valueOf(tmallId), sb.toString(),token);
	}
	/***
	 * 退款通知 处理 将所申码废掉
	 * @param tmallId
	 */
	public  void cancelCode(String tmallId) {
		List<OrdTmallMap> ords = ordTmallMapService.selectByTmallNo(tmallId);
		for (OrdTmallMap ord : ords) {
			if (!StringUtil.isEmptyString(ord.getToken())&&ord.getLvOrderId()!=null) {
				orderServiceProxy.cancelOrder(ord.getLvOrderId(), "电子凭证平台退款通知执行废单操作", "system");
				log.info(ord.getLvOrderId()+"废单!!!");
			}
		}
	}

	/**
	 * 修改手机号码通知处理
	 * @param tmallId
	 * @throws NumberFormatException
	 * @throws ApiException
	 */
	private void modifyMobile(String tmallId, String token) throws NumberFormatException, ApiException {
		List<OrdTmallMap> ords = ordTmallMapService.selectByTmallNo(tmallId);
		PassCode passCode = null;
		StringBuffer sb=new StringBuffer();
		for (OrdTmallMap ord : ords) {
			// token决定是电子凭证平台订单 lvorderid决定是super下单成功的订单
			if (!StringUtil.isEmptyString(ord.getToken())&& ord.getLvOrderId() != null) {
				log.info(ord.getLvOrderId()+" apply resend passCode begin!!!");
				passCode = passCodeService.getPassCodeByOrderIdStatus(ord.getLvOrderId());
				if(passCode!=null){
					sb.append(passCode.getCode()+":"+ord.getBuyNum());
					sb.append(",");;
				}else{
					log.error(ord.getLvOrderId()+" apply resend passCode failed!!!");
				}
			}
		}
		if(sb.length()>=1){
			sb.deleteCharAt(sb.length()-1);
		}
//		TOPInterface.eticketReSend(Long.valueOf(tmallId), sb.toString(),token);
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setLeft_num(String left_num) {
		this.left_num = left_num;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setTaobao_sid(String taobao_sid) {
		this.taobao_sid = taobao_sid;
	}

	public void setSeller_nick(String seller_nick) {
		this.seller_nick = seller_nick;
	}

	public void setItem_title(String item_title) {
		this.item_title = item_title;
	}

	public void setSend_type(String send_type) {
		this.send_type = send_type;
	}

	public void setConsume_type(String consume_type) {
		this.consume_type = consume_type;
	}

	public void setSms_template(String sms_template) {
		this.sms_template = sms_template;
	}

	public void setValid_start(String valid_start) {
		this.valid_start = valid_start;
	}

	public void setValid_ends(String valid_ends) {
		this.valid_ends = valid_ends;
	}

	public void setNum_iid(String num_iid) {
		this.num_iid = num_iid;
	}

	public void setOuter_iid(String outer_iid) {
		this.outer_iid = outer_iid;
	}

	public void setSub_outer_iid(String sub_outer_iid) {
		this.sub_outer_iid = sub_outer_iid;
	}

	public void setSku_properties(String sku_properties) {
		this.sku_properties = sku_properties;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public void setSub_method(String sub_method) {
		this.sub_method = sub_method;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setOrdTmallMapService(OrdTmallMapService ordTmallMapService) {
		this.ordTmallMapService = ordTmallMapService;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}
	
	public void setDownTmallOrderInterface(DownTmallOrderInterface downTmallOrderInterface) {
		this.downTmallOrderInterface = downTmallOrderInterface;
	}

	public void setPassportMessageProducer(TopicMessageProducer passportMessageProducer) {
		this.passportMessageProducer = passportMessageProducer;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
}
