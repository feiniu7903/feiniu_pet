package com.lvmama.front.web.interceptor;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderChannel;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.distribution.DistributionService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.ord.OrdOrderChannelService;
import com.lvmama.comm.pet.service.place.PlacePlaceDestService;
import com.lvmama.comm.utils.CpsUtil;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 前台创建订单拦截器
 * 主要在前台创建订单完成后拦截，根据Cookie中的值来判断订单是否有推广来源，如有，则记录。
 * @author Brian
 *
 */

public final class FrontCreateOrderInterceptor extends AbstractInterceptor{
	private static final long serialVersionUID = -5907324346474728872L;
	private static final Log LOG = LogFactory.getLog(FrontCreateOrderInterceptor.class);
	private static final String ORDER_FROM_CHANNEL = "orderFromChannel";  //Cookie中记录订单推广渠道的名字
	private static final String ORDER_OUTTER_CHANNEL = "oUC"; //Cookie中记录订单站外渠道的名字
	private static final String ORDER_OUTTER_CHANNEL_TIME = "oUT"; //Cookie中记录订单站外渠道的时间
	private static final String ORDER_INNER_CHANNEL = "oIC"; //Cookie中记录订单站内渠道的名字
	private static final String ORDER_INNER_CHANNEL_TIME = "oIT"; //Cookie中记录订单站内渠道的时间
	
	private OrdOrderChannelService ordOrderChannelService;
	private OrderService orderServiceProxy;
	private ProdProductService prodProductService;
	private PlacePlaceDestService placePlaceDestService;
	private ProdProductPlaceService prodProductPlaceService;
	private DistributionService distributionService;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		debug("进入前台下单拦截器......");
		String result = invocation.invoke(); 
		if ("view".equals(result)) {
			debug("成功创建订单,进入订单推广来源的判断");
			if (null != getRequest().getAttribute("orderId")) {
				Cookie cookie = getCookie(ORDER_FROM_CHANNEL);
				Long orderId = (Long) getRequest().getAttribute("orderId");
				OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
				if (null == order) {
					return result;
				}
				
				if (null != cookie) {
					String orderFromChannel = cookie.getValue();
					//第三方的CPS渠道
					if (StringUtils.isNotEmpty(orderFromChannel) 
							&& !isQQCbOrder(orderFromChannel, order)
							&& !isEMAROrder(orderFromChannel, order) 
							&& !isChanetOrder(orderFromChannel, order)
							&& !isLinkTechOrder(orderFromChannel, order)
							&& !isBiGouOrder(orderFromChannel, order)
							&& !isFanLiOrder(orderFromChannel, order)
							&& !isRuiGuangCPSOrder(orderFromChannel, order)
							&& !isDuoMaiOrder(orderFromChannel,order)
							&& !isBeiJingWeiYi(orderFromChannel,order)
							&& !isUnicornOrder(orderFromChannel,order)
							&& !isFanHuanCPSOrder(orderFromChannel, order)
							&& !isLeTuOrder(orderFromChannel,order)
							&& !isZuoCheOrder(orderFromChannel,order)
							&& !isNeverBlueOrder(orderFromChannel,order)
							&& !isZhongMinOrder(orderFromChannel,order)
							&& !isZhiTuiOrder(orderFromChannel,order)
							&& !isYoududuOrder(orderFromChannel,order)
							&& !isJiXiaoOrder(orderFromChannel,order)
							&& !isBaiDuTuanGouCPSOrder(orderFromChannel,order)) {
					}
				}
				
				//记录站内的渠道
				saveInnerChannel(order);
			}
			
		}
		return result;
	}
	
    /**
     * 亿起发的来源
     * @param orderFromChannel 订单来源
     * @param order  订单
     * @return 是否订单来自于亿起发
     */
	private boolean isEMAROrder(final String orderFromChannel, final OrdOrder order) {
		if ("EMAR".equals(orderFromChannel)) {
			//亿起发的记录
			String arg1 = null;
			String arg2 = null;
			Cookie cookie = getCookie("wi");
			if (null != cookie) {
				arg1 = cookie.getValue();
			}
			cookie = getCookie("cid");
			if (null != cookie) {
				arg2 = cookie.getValue();
			}
			OrdOrderChannel ordOrderChannel = new OrdOrderChannel(order.getOrderId(), orderFromChannel,arg1, arg2);
			ordOrderChannelService.insert(ordOrderChannel);
			
			try {
				debug("发送订单资料给亿起发");
				StringBuffer sb = new StringBuffer("http://o.yiqifa.com/servlet/handleCpsIn?");
				sb.append("cid=").append(arg2).append("&wi=").append(arg1).append("&on=").append(order.getOrderId()).append("&ta=1&pp=").append(order.getOughtPayYuan()).append("&sd=").append(URLEncoder.encode(order.getZhCreateTime(),"utf-8"));
				debug("提交数据：" + sb.toString());
				HttpClient httpClient = new HttpClient();
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
				GetMethod getMethod = new GetMethod(sb.toString());
				httpClient.executeMethod(getMethod);
			} catch (Exception ioe) {
				StackOverFlowUtil.printErrorStack(getRequest(), null, ioe);
				LOG.error(ioe.getMessage());
			}
			return true;
		}		
		return false;
	}
	
	/**
     * 成果网的来源
     * @param orderFromChannel 订单来源
     * @param order  订单
     * @return 是否订单来自于成果网
     */
	private boolean isChanetOrder(final String orderFromChannel, final OrdOrder order) {
		if ("CHANET".equals(orderFromChannel)) {
			//成果网的记录
			String arg1 = null;
			Cookie cookie = getCookie("chanetId");
			if (null != cookie) {
				arg1 = cookie.getValue();
			}
			OrdOrderChannel ordOrderChannel = new OrdOrderChannel(order.getOrderId(), orderFromChannel,arg1, null);
			ordOrderChannelService.insert(ordOrderChannel);
			
			try {
				debug("发送订单资料给成果网");
				StringBuffer sb = new StringBuffer("http://count.chanet.com.cn/add_action_ec.cgi?");
				sb.append("t=").append("6701");
				sb.append("&id=").append(arg1);
				sb.append("&i=").append(order.getOrderId());
				sb.append("&sign=").append(new MD5().code("user=lvmama&pw=" + new MD5().code("lvmama")));
				sb.append("&o=");
				for (OrdOrderItemProd prod : order.getOrdOrderItemProds()) {
					sb.append("GOODS1/").append(prod.getPriceYuan()).append("/").append(prod.getQuantity()).append("/").append(URLEncoder.encode(prod.getProductName(), "utf-8")).append(":");
				}
				sb.setLength(sb.length() - 1);
				debug("提交数据：" + sb.toString());
				HttpClient httpClient = new HttpClient();
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
				GetMethod getMethod = new GetMethod(sb.toString());
				httpClient.executeMethod(getMethod);
			} catch (Exception ioe) {
				LOG.error(ioe.getMessage());
			}
			return true;
		}		
		return false;		
	}
	
	/**
     * 领克特(LINKTECH)的来源
     * @param orderFromChannel 订单来源
     * @param order  订单
     * @return 是否订单来自于领克特(LINKTECH)
     */
	private boolean isLinkTechOrder(final String orderFromChannel, final OrdOrder order) {
		if ("LINKTECH".equals(orderFromChannel)) {
			//领克特(LINKTECH)的记录
			String arg1 = null;
			Cookie cookie = getCookie("ltInfo");
			if (null != cookie) {
				arg1 = cookie.getValue();
			}
			OrdOrderChannel ordOrderChannel = new OrdOrderChannel(order.getOrderId(), orderFromChannel,arg1, null);
			ordOrderChannelService.insert(ordOrderChannel);
			
			try {
				debug("发送订单资料给领克特");
				StringBuffer sb = new StringBuffer("http://service.linktech.cn/purchase_cps.php?");
				sb.append("a_id=").append(URLEncoder.encode(arg1,"UTF-8"));
				sb.append("&m_id=lvmama");
				sb.append("&mbr_id=").append(order.getUserId());
				sb.append("&o_cd=").append(order.getOrderId());
				
				StringBuffer p_cd = new StringBuffer();
				StringBuffer price = new StringBuffer();
				StringBuffer it_cnt = new StringBuffer();
				StringBuffer c_cd = new StringBuffer();
				
				for (OrdOrderItemProd prod : order.getOrdOrderItemProds()) {
					p_cd.append(prod.getProductId()).append("||");
					price.append(prod.getPriceYuan()).append("||");
					it_cnt.append(prod.getQuantity()).append("||");
					c_cd.append("GOODS").append("||");
			}				
				
				if (p_cd.length() > 2) {
					p_cd.setLength(p_cd.length() - 2);
				}
				if (price.length() > 2) {
					price.setLength(price.length() - 2);
				}
				if (it_cnt.length() > 2) {
					it_cnt.setLength(it_cnt.length() - 2);
				}
				if (c_cd.length() > 2) {
					c_cd.setLength(c_cd.length() - 2);
				}
				sb.append("&p_cd=" + p_cd.toString());
				sb.append("&price=" + price.toString());
				sb.append("&it_cnt=" + it_cnt.toString());
				sb.append("&c_cd=" + c_cd.toString());
				
				debug("提交数据：" + sb.toString());
				HttpClient httpClient = new HttpClient();
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
				GetMethod getMethod = new GetMethod(sb.toString());
				httpClient.executeMethod(getMethod);
			} catch (Exception ioe) {
				LOG.error(ioe.getMessage());
			}
			return true;
		}		
		return false;		
	}
	
	/**
	 * qq彩贝来源
	 * @param orderFromChannel
	 * @param order
	 * @return
	 */
	private boolean isQQCbOrder(final String orderFromChannel, final OrdOrder order){
		if ("qqcb".equals(orderFromChannel) || "TENCENTQQ".equals(orderFromChannel)) {
			String uid = null;
			Cookie cookie = getCookie("cpsuid");
			if (null != cookie) {
				uid = cookie.getValue();
			}
			String trackingCode = null;
			cookie = getCookie("tracking_code");
			if (null != cookie) {
				trackingCode = cookie.getValue();
			}
			
			if (StringUtils.isBlank(uid) || StringUtils.isBlank(trackingCode)) {
				return false;
			}
			LOG.debug("create cps qq cb order :"+order.getOrderId()+","+orderFromChannel+","+uid+","+trackingCode);
			OrdOrderChannel ordOrderChannel = new OrdOrderChannel(order.getOrderId(), orderFromChannel,uid, trackingCode);
			ordOrderChannelService.insert(ordOrderChannel);
			
			CpsUtil.getInstance().sendQQCbCpsRequest(order, CpsUtil.ORDER_CREATE, orderFromChannel, uid, trackingCode);
			return true;
		}
		return false;
	}



	
    /**
     * 比购网的来源
     * @param orderFromChannel 订单来源
     * @param order  订单
     * @return 是否订单来自于亿起发
     */
	private boolean isBiGouOrder(final String orderFromChannel, final OrdOrder order) {
		if ("51bi".equals(orderFromChannel)) {
			//比购网的记录
			String uid = null;
			String clientIP = InternetProtocol.getRemoteAddr(getRequest());
			Cookie cookie = getCookie("cpsuid");
			if (null != cookie) {
				uid = cookie.getValue();
			}
			
			OrdOrderChannel ordOrderChannel = new OrdOrderChannel(order.getOrderId(), orderFromChannel,uid, null);
			ordOrderChannelService.insert(ordOrderChannel);
			
			try {
				debug("发送订单资料给51比购网");
				StringBuffer sb = new StringBuffer("http://www.51bi.com/orderback.jhtml?");
				sb.append("bid=").append("7604565").append("&uid=").append(uid).append("&oid=")
					.append(order.getOrderId()).append("&cost=").append(order.getOughtPayYuan()).append("&cback=").append("")
					.append("&ip=").append(clientIP).append("&mcode=").append(new MD5().code(uid + order.getOrderId()));
				debug("提交数据：" + sb.toString());
				HttpClient httpClient = new HttpClient();
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
				GetMethod getMethod = new GetMethod(sb.toString());
				httpClient.executeMethod(getMethod);
			} catch (Exception ioe) {
				LOG.error(ioe.getMessage());
			}
			return true;
		}		
		return false;
	}
	
	 /**
     * 51返利网的来源
     * @param orderFromChannel 订单来源
     * @param order  订单
     * @return 是否订单来自于亿起发
     */
	public boolean isFanLiOrder(final String orderFromChannel, final OrdOrder order) {
		if ("51fanli".equals(orderFromChannel)) {
			//返利网的记录
			String uid = "uid";
			String trackingCode = "tracking_code";
			Cookie cookie = getCookie("cpsuid");
			if (null != cookie) {
				uid = cookie.getValue();
			}
			cookie = getCookie("tracking_code");
			if (null != cookie) {
				trackingCode = cookie.getValue();
			}
			OrdOrderChannel ordOrderChannel = new OrdOrderChannel(order.getOrderId(), orderFromChannel,uid, trackingCode);
			ordOrderChannelService.insert(ordOrderChannel);
			
			try {
				LOG.info("发送订单资料给51返利网");
				StringBuffer sb = new StringBuffer();
				sb.append("<fanli_data version=\"3.0\">");  
				sb.append("<order order_time=\"").append(order.getZhCreateTime()).append("\" order_no=\"").append(order.getOrderId())
				.append("\"  shop_no=\"lvmama\" total_price=\"").append(order.getOughtPayYuan()).append("\"  total_qty=\"").append(order.getOrdOrderItemProds().size())
				.append("\"  shop_key=\"6ee00b667bbf4eb3\" u_id=\"").append(uid).append("\"  username=\"").append(uid).append("@51fanli.com\"  is_pay =\"0\" pay_type=\"1\" order_status=\"1\" ")     
				.append("deli_name=\"\"    deli_no  =\"\"  tracking_code=\"").append(trackingCode)
				.append("\"  pass_code= \"" + new MD5().code((order.getOrderId() + "lvmama" + uid + "6ee00b667bbf4eb3").toLowerCase()) +"\">");  
				sb.append("<products_all>");
				int total = 0;  //计算订单子项的价格总和
				for (OrdOrderItemProd prod : order.getOrdOrderItemProds()) {
					sb.append("<product>");    
			        sb.append("<product_id>" + prod.getProductId() + "</product_id>");    
			        sb.append("<product_url>http://www.lvmama.com/product/"+ prod.getProductId() + "</product_url>");   
			        sb.append("<product_qty>" + prod.getQuantity() + "</product_qty>");  
			        sb.append("<product_price>" +   prod.getPriceYuan() + "</product_price>"); 
			        total += prod.getAmountYuan();
			        sb.append("<product_comm>0</product_comm>"); 
			        sb.append("<comm_no></comm_no>");  
			        sb.append("</product>");	
				}
				sb.append("</products_all>"); 
				sb.append("<coupons_all>"); 
				if (order.getOughtPayYuan() > total) {
					sb.append("<coupon>");
					sb.append("<coupon_no>youhui</coupon_no>");
					sb.append("<coupon_qty>1</coupon_qty>");
					sb.append("<coupon_price>").append(order.getOughtPayYuan() - total).append("</coupon_price>");
					sb.append("<comm_no></comm_no>");
					sb.append("</coupon>");
				}
				sb.append("</coupons_all>"); 
				sb.append("</order>");  
				sb.append("</fanli_data>");

				LOG.info("提交数据：" + sb.toString());
				HttpClient httpClient = new HttpClient();
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
				PostMethod getMethod = new PostMethod("http://data2.51fanli.com/index.php/DataHandle/handlePostData");
				getMethod.addParameter("content", sb.toString());
				httpClient.executeMethod(getMethod);
			} catch (Exception ioe) {
				LOG.error(ioe.getMessage());
			}
			return true;
		}		
		return false;		
	}
	
	 /**
     * 睿广CPS的来源
     * @param orderFromChannel 订单来源
     * @param order  订单
     * @return 是否订单来自于睿广
     */
	public boolean isRuiGuangCPSOrder(final String orderFromChannel, final OrdOrder order) {
		if ("RUIGUANGCPS".equals(orderFromChannel)) {
			//睿广的记录
			String site_id = "site_id";
			Cookie cookie = getCookie("site_id");
			if (null != cookie) {
				site_id = cookie.getValue();
			}

			OrdOrderChannel ordOrderChannel = new OrdOrderChannel(order.getOrderId(), orderFromChannel,site_id, null);
			ordOrderChannelService.insert(ordOrderChannel);
			
			return true;
		}		
		return false;		
	}
	
	 /**
     * 返还网CPS的来源
     * @param orderFromChannel 订单来源
     * @param order  订单
     * @return 是否订单来自于返还网
     */
	public boolean isFanHuanCPSOrder(final String orderFromChannel, final OrdOrder order) {
		if ("fanhuan".equals(orderFromChannel)) {
			//睿广的记录
			String u_id = null;
			String tracking_code = null;
			
			Cookie cookie = getCookie("cpsuid");
			if (null != cookie) {
				u_id = cookie.getValue();
			}
			cookie = null;
			cookie = getCookie("tracking_code");
			if (null != cookie) {
				tracking_code = cookie.getValue();
			}
			
			if (StringUtils.isBlank(u_id) || StringUtils.isBlank(tracking_code)) {
				return false;
			}

			OrdOrderChannel ordOrderChannel = new OrdOrderChannel(order.getOrderId(), orderFromChannel,u_id, tracking_code);
			ordOrderChannelService.insert(ordOrderChannel);
			
			try {
				StringBuilder sb = new StringBuilder();
				sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
				sb.append("<fanhuan_data version=\"1.0\">");
				sb.append("<order>");
				sb.append("<order_time>" + order.getZhCreateTime() + "</order_time>");
				sb.append("<order_no>" + order.getOrderId() + "</order_no>");
				sb.append("<shop_no>lvmama</shop_no>");
				sb.append("<total_price>" + order.getOughtPayYuan() + "</total_price>");
				sb.append("<total_qty>" + order.getOrdOrderItemProds().size() + "</total_qty>");
				sb.append("<u_id>" + u_id + "</u_id>");
				sb.append("<username>" + "</username>");
				sb.append("<is_pay>0</is_pay>");
				sb.append("<pay_type>1</pay_type>");
				sb.append("<order_status>0</order_status>");
				sb.append("<deli_name></deli_name>");
				sb.append("<deli_no></deli_no>");
				sb.append("<tracking_code>" + tracking_code + "</tracking_code>");	
				String code = new MD5().code(order.getOrderId() + "lvmama" + u_id + "eef30cbd7f9c5a4e");
				sb.append("<pass_code>" + code.toLowerCase()  + "</pass_code>");
				sb.append("<commission>0</commission>");
				sb.append("<products_all>");
				float total = 0F;
				for (OrdOrderItemProd prod : order.getOrdOrderItemProds()) {
					sb.append("<product>");
					sb.append("<product_id>" + prod.getProductId() + "</product_id>");
					sb.append("<product_name>" + prod.getProductName() + "</product_name>");
					sb.append("<product_url>http://www.lvmama.com/product/" + prod.getProductId() + "</product_url>");
					sb.append("<product_qty>" + prod.getQuantity() + "</product_qty>");
					sb.append("<product_price>" + prod.getPriceYuan() + "</product_price>");
					sb.append("<product_comm>0</product_comm>");
					sb.append("<comm_no>A</comm_no>");
					sb.append("</product>");
					total += prod.getPriceYuan() *prod.getQuantity();
				}
				sb.append("</products_all>");
				sb.append("<coupons_all>");
				sb.append("<coupon>");
				sb.append("<coupon_no></coupon_no>");
				if (total > order.getOughtPay()) {
					sb.append("<coupon_qty>1</coupon_qty>");
				} else {
					sb.append("<coupon_qty>0</coupon_qty>");
				}
				sb.append("<coupon_price>" + (order.getOughtPayYuan() - total) +"</coupon_price>");
				sb.append("<comm_no>A</comm_no>");
				sb.append("</coupon>");
				sb.append("</coupons_all>");
				sb.append("</order>");
				sb.append("</fanhuan_data>");
	
				LOG.info("提交数据：" + sb.toString());
				HttpClient httpClient = new HttpClient();
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
				PostMethod getMethod = new PostMethod("http://order.fanhuan.com/fhunion");
				getMethod.addParameter("content", sb.toString());
				httpClient.executeMethod(getMethod);
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
			return true;
		}		
		return false;		
	}	
	
	/**
	 * 多麦的来源
	 * @param orderFromChannel 订单来源
	 * @param order 订单
	 * @return 是否订单来自多麦
	 */
	private boolean isDuoMaiOrder(final String orderFromChannel,final OrdOrder order){
		if("DUOMAI".equalsIgnoreCase(orderFromChannel)){
			String arg1 = null;
			
			Cookie cookie = getCookie("feedback");
			if (null != cookie) {
				arg1 = cookie.getValue();
			}
			OrdOrderChannel ordOrderChannel = new OrdOrderChannel(order.getOrderId(),orderFromChannel,arg1,null);
			ordOrderChannelService.insert(ordOrderChannel);
			try{
				debug("发送订单资料给多麦广告联盟");
				StringBuffer sb = new StringBuffer("http://www.duomai.com/api/order.php?");
				sb.append("hash=8093423f1e9dfffb98c30f958cc9025b").append("&euid=").append(arg1).append("&order_sn=").append(order.getOrderId()).append("&order_time=").append(order.getCreateTime()).append("&orders_price=").append(order.getOughtPayYuan()).append("&encoding=utf-8");
				debug("提交数据："+sb.toString());
				HttpClient httpClient = new HttpClient();
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
				GetMethod getMethod = new GetMethod(sb.toString());
				httpClient.executeMethod(getMethod);
			}catch(Exception ioe){
				StackOverFlowUtil.printErrorStack(getRequest(),null,ioe);
				LOG.error(ioe.getMessage());
			}
			return true;	
		}
		return false;
	}
	
	/**
     * 北京唯一的来源
     * @param orderFromChannel 订单来源
     * @param order 订单
     * @return 是否订单来自北京唯一
     */
    private boolean isBeiJingWeiYi(final String orderFromChannel,final OrdOrder order){
        if("WEIYI".equalsIgnoreCase(orderFromChannel)){
            String cid = null;//唯一联盟下的二级联盟会员ID以及相关信息
            String bid = null;
            Cookie cidC = getCookie("cid");
            Cookie bidC = getCookie("unUserName");
            if (null != cidC) {
                cid = cidC.getValue();//参数一
            }
            if (null != bidC){
                bid = bidC.getValue();//参数二
            }
            OrdOrderChannel ordOrderChannel = new OrdOrderChannel(order.getOrderId(),orderFromChannel,cid,bid);
            ordOrderChannelService.insert(ordOrderChannel);
            try{
                debug("发送订单资料给北京唯一");
                StringBuffer sb = new StringBuffer("http://track.weiyi.com/orderpush.aspx?");
                sb.append("mid=lvmama_wy").append("&odate=").append(DateUtil.getFormatDate(order.getCreateTime(),"yyyyMMddHHmmss")).append("&cid=").append(cid);
                sb.append("&bid=");
                if (null != bid){
                    sb.append(URLEncoder.encode(bid,"utf-8"));
                }else{
                    sb.append("");
                }
                sb.append("&oid=").append(order.getOrderId()).append("&pid=").append(order.getMainProduct().getProductId()).append("&ptype=");
                sb.append("&pnum=").append(order.getMainProduct().getQuantity()).append("&price=").append(order.getOrderPayFloat()).append("&ostat=").append("1");
                debug("提交数据："+sb.toString());
                HttpClient httpClient = new HttpClient();
                httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
                httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
                GetMethod getMethod = new GetMethod(sb.toString());
                httpClient.executeMethod(getMethod);
            }catch(Exception ioe){
                StackOverFlowUtil.printErrorStack(getRequest(),null,ioe);
                LOG.error(ioe.getMessage());
            }
            return true;    
        }
        return false;
    }
	
    /**
     * 游多多的来源
     * @param orderFromChannel
     * @param order
     * @return
     */
    private boolean isYoududuOrder(final String orderFromChannel,final OrdOrder order){
        if("yododo".equalsIgnoreCase(orderFromChannel)){
            OrdOrderChannel ordOrderChannel = new OrdOrderChannel(order.getOrderId(),orderFromChannel,null,null);
            ordOrderChannelService.insert(ordOrderChannel);
            return true;    
        }
        return false;
    }
    
    /**
     * JXROCK--极效联盟
     * @param orderFromChannel
     * @param order
     * @return
     */
    public boolean isJiXiaoOrder(final String orderFromChannel,final OrdOrder order){
        if("JXROCK".equalsIgnoreCase(orderFromChannel)){
            String aelinfo = null;
            Cookie cookie = getCookie("AELINFO");//对应于 JXROCK 生成的 Cookie“AELINFO”中的值
            if (null != cookie) {
                aelinfo = cookie.getValue();
            }
            OrdOrderChannel ordOrderChannel = new OrdOrderChannel(order.getOrderId(),orderFromChannel,aelinfo,null);
            ordOrderChannelService.insert(ordOrderChannel);
            try{
                LOG.info("发送订单资料给JXROCK--极效联盟");
                StringBuffer sb = new StringBuffer("http://api4.17elink.com/GroupPerformanceByRequest/Purchase/lvmama?");
                String amt = ""; String p_cd = ""; String price = ""; String it_cnt= ""; String c_cd = "";
                for (OrdOrderItemProd prod : order.getOrdOrderItemProds()) {
                    p_cd += prod.getProductId() +"_";
                    price += prod.getPriceYuan()+"_";
                    it_cnt+= prod.getQuantity() +"_";
                    c_cd += prod.getProductType()+"_";
                    amt += prod.getPriceYuan()*prod.getQuantity()+"_";
                }
                //MD5 Hash校验签名，详见（Hash校验签名）
                /**
                 * 1) 对参数列表里的每一个参数名从a到z的顺序排序，若遇到相同首字母，则按第二个字母顺序排序，以此类推；
                 * 2) 排序完成之后，再把所有参数以及参数值以“&”字符连接起来：参数名=值&参数名=值&参数名=值&……
                 */
                //aelinfo amt c_cd hhmiss it_cnt o_cd p_cd pm price stat_code  yyyymmdd sign
                String sign="aelinfo="+aelinfo+"&amt="+amt.substring(0,amt.length()-1)+"&c_cd="+c_cd.substring(0,c_cd.length()-1)+"&hhmiss="+DateUtil.getFormatDate(order.getCreateTime(),"HHmmss");
                sign += "&it_cnt="+it_cnt.substring(0,it_cnt.length()-1)+"&o_cd="+order.getOrderId()+"&p_cd="+p_cd.substring(0,p_cd.length()-1);
                sign += "&price="+price.substring(0,price.length()-1)+"&stat_code=100"+"&yyyymmdd="+DateUtil.getFormatDate(order.getCreateTime(),"yyyyMMdd");
                sb.append(sign);
                LOG.info("提交数据："+sb.toString());
                HttpClient httpClient = new HttpClient();
                httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
                httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
                GetMethod getMethod = new GetMethod(sb.toString());
                httpClient.executeMethod(getMethod);
            }catch(Exception ioe){
                StackOverFlowUtil.printErrorStack(getRequest(),null,ioe);
                LOG.error(ioe.getMessage());
            }
            
        return true;
        }
        return false;
    }
    
	/**
	 * 麒麟的来源
	 * @param orderFromChannel 订单来源
	 * @param order 订单
	 * @return 是否订单来自麒麟
	 */
	private boolean isUnicornOrder(final String orderFromChannel,final OrdOrder order){
		if("UNICORN".equalsIgnoreCase(orderFromChannel)){
			String arg1 = null;
			String signs= "";
			String goodsId="";
			String goodsName="";
			String goodsPrice="";
			String goodsQuantity="";
			String goodsType="";
			Cookie cookie = getCookie("feedback");
			if (null != cookie) {
				arg1 = cookie.getValue();
			}
			OrdOrderChannel ordOrderChannel = new OrdOrderChannel(order.getOrderId(),orderFromChannel,arg1,null);
			ordOrderChannelService.insert(ordOrderChannel);
			try{
				debug("发送订单资料给麒麟广告联盟");
				StringBuffer sb = new StringBuffer("http://api.70xg.com/?uid=lvmama&");
							sb.append("act_name=")
							.append("&feedback=").append(ordOrderChannel.getArg1())
							.append("&order_sn=").append(order.getOrderId())
							.append("&order_commission_type=0");
							
					int sun = 0; // 计算订单子项的价格总和
					for (OrdOrderItemProd prod : order.getOrdOrderItemProds()) {
						goodsType+=prod.getProductType()+"|";
						goodsId+= prod.getProductId()+"|";
						goodsName+=URLEncoder.encode(prod.getProductName(),"utf-8")+"|";
						goodsPrice+=prod.getPriceYuan()+"|";
						goodsQuantity+=prod.getQuantity()+"|";
						sun += prod.getPriceYuan();
					}
					sb.append("&goods_cate=" + goodsType.substring(0,goodsType.length()-1));
					sb.append("&goods_id=" + goodsId.substring(0,goodsId.length()-1));
					sb.append("&goods_name=" + goodsName.substring(0,goodsName.length()-1));
					sb.append("&goods_price=" + goodsPrice.substring(0,goodsPrice.length()-1));
					sb.append("&goods_quantity=" +goodsQuantity.substring(0,goodsQuantity.length()-1));
					sb.append("&goods_commission_rate=0.2");
					
					
					sb.append("&order_price="+sun);
					sb.append("&order_coupon="+(sun-order.getOughtPayYuan()));
					signs = ""+ordOrderChannel.getArg1()+order.getOrderId()+0+order.getMainProduct().getProductType()+order.getMainProduct().getProductId()+order.getMainProduct().getProductName()+order.getMainProduct().getPriceYuan()+order.getMainProduct().getQuantity()+0.2+sun+(sun-order.getOughtPayYuan())+order.getCreateTime()+order.getOrderStatus();
					sb.append("&order_time=").append(URLEncoder.encode(order.getZhCreateTime(),"utf-8"));
					sb.append("&order_status=0");
					sb.append("&sign=").append((new MD5().code(new MD5().code(signs)+"3ede9464621115570565636b76ebfab3")).toLowerCase());
					
					debug("提交数据：" + sb.toString());
					HttpClient httpClient = new HttpClient();
					httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
					httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
					GetMethod getMethod = new GetMethod(sb.toString());
					httpClient.executeMethod(getMethod);
			}catch(Exception ioe){
				StackOverFlowUtil.printErrorStack(getRequest(),null,ioe);
				LOG.error(ioe.getMessage());
			}
			return true;	
		}
		return false;
	}
	
	
	/**
	 * 乐途的来源
	 * @param orderFromChannel 订单来源
	 * @param order 订单
	 * @return 是否订单来自乐途
	 */
	private boolean isLeTuOrder(final String orderFromChannel,final OrdOrder order){
		if("lotour".equalsIgnoreCase(orderFromChannel)){
			String arg1 = null;
			
			String PlayDate="";
			String ProductName="";
			String NewPrice="";
			Cookie cookie = getCookie("feedback");
			if (null != cookie) {
				arg1 = cookie.getValue();
			}
			OrdOrderChannel ordOrderChannel = new OrdOrderChannel(order.getOrderId(),orderFromChannel,arg1,null);
			ordOrderChannelService.insert(ordOrderChannel);
			try{
				debug("发送订单资料给乐途广告联盟");
				StringBuffer sb = new StringBuffer("http://xxxxxxxxxxxxxxxxxxxxx");
						  sb.append("&Id=").append(order.getOrderId()).append("&Destinetime=").append(order.getCreateTime());
							
					int Subtotal = 0; // 计算订单子项的价格总和
					for (OrdOrderItemProd prod : order.getOrdOrderItemProds()) {
						ProductName+=URLEncoder.encode(prod.getProductName(),"utf-8")+"|";
						PlayDate+= prod.getVisitTime()+"|";
						NewPrice+=prod.getPriceYuan()+"|";
						Subtotal += prod.getPriceYuan();
					}
					sb.append("&ProductName=" + ProductName.substring(0,ProductName.length()-1));
					sb.append("&PlayDate=" + PlayDate.substring(0,PlayDate.length()-1));
					sb.append("&NewPrice=" + NewPrice.substring(0,NewPrice.length()-1));
					sb.append("&Subtotal="+Subtotal);
					sb.append("&PayMent=在线支付");
					sb.append("&PayStatus=").append(order.getPaymentStatus());
					sb.append("&DestineStatus=").append(order.getOrderStatus());					
					//debug("提交数据：" + sb.toString());
					HttpClient httpClient = new HttpClient();
					httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
					httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
					GetMethod getMethod = new GetMethod(sb.toString());
					httpClient.executeMethod(getMethod);
			}catch(Exception ioe){
				StackOverFlowUtil.printErrorStack(getRequest(),null,ioe);
				LOG.error(ioe.getMessage());
			}
			return true;	
		}
		return false;
	}
	
	
	
	/**
	 * NeverBlue的来源
	 * @param orderFromChannel 订单来源
	 * @param order 订单
	 * @return 是否订单来自NeverBlue
	 */
	private boolean isNeverBlueOrder(final String orderFromChannel,final OrdOrder order){
		if("nvrblu".equalsIgnoreCase(orderFromChannel)){
			String arg1 = null;
			String arg2 = null;
			
			Cookie click = getCookie("click");
			Cookie subid = getCookie("subid");
			if (null != click) {
				arg1 = click.getValue();
			}
			if (null != subid) {
				arg2 = subid.getValue();
			}
			
			OrdOrderChannel ordOrderChannel = new OrdOrderChannel(order.getOrderId(),orderFromChannel,arg1,arg2);
			ordOrderChannelService.insert(ordOrderChannel);
			try{
				debug("发送订单资料给nvrblu联盟");
				StringBuffer sb = new StringBuffer("http://nbjmp.com/postback/?action=23400&o=17066&encoded_click_info={"+click+"}&authkey=89ec1ae0f985e3ed0ab47111deee3c4e20d782b76d5877c639629b9e2b9caf4f");
				debug("提交数据："+sb.toString());
				HttpClient httpClient = new HttpClient();
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
				GetMethod getMethod = new GetMethod(sb.toString());
				httpClient.executeMethod(getMethod);
			}catch(Exception ioe){
				StackOverFlowUtil.printErrorStack(getRequest(),null,ioe);
				LOG.error(ioe.getMessage());
			}
			return true;	
		}
		return false;
	}
	
	/**
	 * 智推cps
	 * @param orderFromChannel
	 * @param order
	 * @return TODO
	 */
	private boolean isZhiTuiOrder(final String orderFromChannel,final OrdOrder order){
        if("zhitui".equalsIgnoreCase(orderFromChannel)){
            String arg1 = null;
            String arg2 = null;
            String p_cd = "";
            String price = "";
            String it_cnt = "";
            
            Cookie a_id = getCookie("a_id");
            Cookie subid = getCookie("subid");
            if (null != a_id) {
                arg1 = a_id.getValue();
            }
            if (null != subid) {
                arg2 = subid.getValue();
            }
            
            OrdOrderChannel ordOrderChannel = new OrdOrderChannel(order.getOrderId(),orderFromChannel,arg1,arg2);
            ordOrderChannelService.insert(ordOrderChannel);
            try{
                LOG.info("发送订单资料给智推cps联盟");
                StringBuffer sb = new StringBuffer("http://api.zhitui.com/recive.php?").append("a_id="+arg1);
                sb.append("&subid=").append(URLEncoder.encode(arg2,"UTF-8")).append("&o_cd="+order.getOrderId());
                for (OrdOrderItemProd prod : order.getOrdOrderItemProds()) {
                    p_cd+=prod.getProductId()+"||";
                    price+=prod.getPriceYuan()+"||";
                    it_cnt+=prod.getQuantity()+"||";
                }
                p_cd = p_cd.substring(0,p_cd.length()-2);
                sb.append("&p_cd=").append(p_cd);
                price = price.substring(0,price.length()-2);
                sb.append("&price=").append(price);
                it_cnt = it_cnt.substring(0,it_cnt.length()-2);
                sb.append("&it_cnt=").append(it_cnt);
                sb.append("&o_date=").append(order.getCreateTime());
                sb.append("&rate=0.03");
                sb.append("&status=1");
                sb.append("&note=");
                LOG.info("提交数据："+sb.toString());
                HttpClient httpClient = new HttpClient();
                httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
                httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
                GetMethod getMethod = new GetMethod(sb.toString());
                httpClient.executeMethod(getMethod);
            }catch(Exception ioe){
                StackOverFlowUtil.printErrorStack(getRequest(),null,ioe);
                LOG.error(ioe.getMessage());
            }
            return true;    
        }
        return false;
    }
	
	/**
	 * 中民保险
	 * @param orderFromChannel
	 * @param order
	 * @return TODO
	 */
	private boolean isZhongMinOrder(final String orderFromChannel,final OrdOrder order){
        if("ZhongMin".equalsIgnoreCase(orderFromChannel)){
            String arg1 = null;
            String arg2 = null;
            String Md5Str = "";
            
            Cookie cps = getCookie("cps");
            Cookie ouid = getCookie("ouid");
            if (null != cps) {
                arg1 = cps.getValue();
            }
            if (null != ouid) {
                arg2 = ouid.getValue();
            }
            
            OrdOrderChannel ordOrderChannel = new OrdOrderChannel(order.getOrderId(),orderFromChannel,arg1,arg2);
            ordOrderChannelService.insert(ordOrderChannel);
            try{
                LOG.info("发送订单资料给中民保险联盟");
                StringBuffer sb = new StringBuffer("http://www.zhongmin.cn/fanli/zmfanliorder.ashx?");
                sb.append("cps="+arg1+"&ouid="+arg2+"&subnumber="+order.getOrderId()+"&price="+order.getOughtPayYuan()+"&orderstate=1");
                Md5Str=arg1+arg2+order.getOrderId()+order.getOughtPayYuan()+1+"zhongminlvmama";
                sb.append("&md5="+new MD5().code(Md5Str));
                LOG.info("提交数据："+sb.toString());
                HttpClient httpClient = new HttpClient();
                httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
                httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
                GetMethod getMethod = new GetMethod(sb.toString());
                httpClient.executeMethod(getMethod);
            }catch(Exception ioe){
                StackOverFlowUtil.printErrorStack(getRequest(),null,ioe);
                LOG.error(ioe.getMessage());
            }
            return true;    
        }
        return false;
    }
	
	/**
	 * 百度团购
	 * @param orderFromChannel
	 * @param order
	 * @return
	 */
	public boolean isBaiDuTuanGouCPSOrder(final String orderFromChannel, final OrdOrder order) {
		if ("BaiDuTuanGou".equals(orderFromChannel)) {
			String session_key = null;
			String access_token = null;
			String uid = null;
			String session_secret = null;
			
			Cookie cookie = getCookie("baidu_session_key");
			if (null != cookie) {
				session_key = cookie.getValue();
			}
			
			cookie = getCookie("baidu_access_token");
			if (null != cookie) {
				access_token = cookie.getValue();
			}
			
			cookie = getCookie("baidu_uid");
			if (null != cookie) {
				uid = cookie.getValue();
			}
			
			cookie = getCookie("baidu_session_secret");
			if (null != cookie) {
				session_secret = cookie.getValue();
			}

			
			if (StringUtils.isEmpty(session_key) || StringUtils.isEmpty(access_token) || StringUtils.isEmpty(uid) || StringUtils.isEmpty(session_secret)) {
				return false;
			}

			OrdOrderChannel ordOrderChannel = new OrdOrderChannel(order.getOrderId(), orderFromChannel,session_key, access_token);
			ordOrderChannelService.insert(ordOrderChannel);
			
			try {
				ProdProduct product = prodProductService.getProdProduct(order.getMainProduct().getProductId());
				if (null == product) {
					return false;
				}
				
				String now = DateUtil.formatDate(new java.util.Date(), "yyyy-MM-dd hh:mm:ss");
				HttpClient httpClient = new HttpClient();
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
				PostMethod getMethod = new PostMethod("http://openapi.baidu.com/rest/2.0/hao123/saveOrder");
				getMethod.addParameter("access_token", access_token);
				getMethod.addParameter("format", "json");
				getMethod.addParameter("session_key", session_key);
				getMethod.addParameter("timestamp", now);
				
				StringBuffer sb = new StringBuffer();
				
				try {
					MessageDigest md5 = MessageDigest.getInstance("MD5");
					byte[] bytes = md5.digest(sb.append("format=json&").append("session_key=" + session_key).append("timestamp=" + now).append("uid=" + uid).append(session_secret).toString().getBytes("UTF-8"));
					StringBuilder sign = new StringBuilder();
					for (int i = 0; i < bytes.length; i++) {
						String hex = Integer.toHexString(bytes[i] & 0xFF);
						if (hex.length() == 1) {
							sign.append("0");
						}
						sign.append(hex);
					}
					getMethod.addParameter("sign",sign.toString());
				} catch (GeneralSecurityException ex) {
					return false;
				}
					
				getMethod.addParameter("order_id","" + order.getOrderId());
				getMethod.addParameter("order_time","" + order.getCreateTime().getTime());
				
				Place fromDest = prodProductPlaceService.getFromDestByProductId(product.getProductId());
				if (null != fromDest) {
					List<Long> parentPlaceList = placePlaceDestService.selectParentPlaceIdList(fromDest.getPlaceId());
					if (!parentPlaceList.isEmpty()) {
						List<String> cityList = distributionService.getBaiduCityNameByPlaceIds(parentPlaceList);
						if (!cityList.isEmpty()) {
							getMethod.addParameter("order_city","" + "" + cityList.get(0));
						} else {
							getMethod.addParameter("order_city","" + "全国");
						}
					} else {
						getMethod.addParameter("order_city","" + "全国");
					}	
				} else {
					getMethod.addParameter("order_city","" + "全国");
				}
				
				
				
				getMethod.addParameter("title","" + URLEncoder.encode(product.getProductName(),"utf-8"));
				getMethod.addParameter("logo", product.getAbsoluteSmallImageUrl());
				getMethod.addParameter("url", "http://www.lvmama.com/product/" + product.getProductId());
				getMethod.addParameter("price", "" + order.getMainProduct().getPrice());
				getMethod.addParameter("goods_num", "" + order.getMainProduct().getQuantity());
				getMethod.addParameter("sum_price", "" + (order.getMainProduct().getPrice() * order.getMainProduct().getQuantity()));
				getMethod.addParameter("summary", "" + URLEncoder.encode(product.getManagerRecommend(),"utf-8"));
				getMethod.addParameter("expire", "" + order.getVisitTime().getTime() + 24*3600);
				getMethod.addParameter("uid", uid);		
				getMethod.addParameter("tn", "");
				cookie = getCookie("baiduid");
				if (null != cookie) {
					getMethod.addParameter("baiduid", "" + cookie.getValue());
				} else {
					getMethod.addParameter("baiduid", "");
				}			
				getMethod.addParameter("bonus", "0" );
				
				int state = httpClient.executeMethod(getMethod);
				if(state == HttpStatus.SC_OK){
					String qqCbStatus = new String(getMethod.getResponseBody(),"UTF-8");
					LOG.info("baidu tuangou cps status "+qqCbStatus);
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage());
			}
			return true;
		}		
		return false;		
	}	
	
	
	/**
     * 坐车网的来源
     * @param orderFromChannel 订单来源
     * @param order 订单
     * @return 是否订单来自坐车网
     */
    private boolean isZuoCheOrder(final String orderFromChannel,final OrdOrder order){
        if("ZUOCHE".equalsIgnoreCase(orderFromChannel)){
            String arg1 = null;
            

            Cookie cookie = getCookie("feedback");
            if (null != cookie) {
                arg1 = cookie.getValue();
            }
            OrdOrderChannel ordOrderChannel = new OrdOrderChannel(order.getOrderId(),orderFromChannel,arg1,null);
            ordOrderChannelService.insert(ordOrderChannel);
            //坐車網暫時不需要提供推送訂單接口
//            try{
//                debug("发送订单资料给坐车网");
//        
//                StringBuffer sb = new StringBuffer("http://xxxxxxxxxxxxxxxxxxxxx");
//                sb.append("&Id=").append(order.getOrderId()).append("&DestineTime=").append(order.getCreateTime());                           
//                
//                String ProductName="";
//                String OrderType="";
//                String NewPrice="";
//                String Count="";
//                    for (OrdOrderItemProd prod : order.getOrdOrderItemProds()) {
//                        try {
//                            ProductName=ProductName+URLEncoder.encode(prod.getProductName(),"utf-8")+"|";
//                        } catch (UnsupportedEncodingException e) {
//                            StackOverFlowUtil.printErrorStack(getRequest(),null,e);
//                            LOG.error(e.getMessage());
//                        }
//                        OrderType = OrderType + prod.getProductType()+"|";
//                        Count=Count+prod.getQuantity()+"|";
//                        NewPrice = NewPrice+prod.getPriceYuan()+"|";
//                    }
//                    ProductName = ProductName.substring(0,ProductName.length()-1);
//                    sb.append("&ProductName="+ProductName);
//                    OrderType = OrderType.substring(0,OrderType.length()-1);
//                    sb.append("&OrderType="+OrderType);
//                    NewPrice = NewPrice.substring(0,NewPrice.length()-1);
//                    sb.append("&NewPrice="+NewPrice);
//                    Count = Count.substring(0,Count.length()-1);
//                    sb.append("&Count="+Count);
//                    sb.append("&DestineStatus=").append(order.getOrderStatus());           
//                    //debug("提交数据：" + sb.toString());
//                    HttpClient httpClient = new HttpClient();
//                    httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
//                    httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
//                    GetMethod getMethod = new GetMethod(sb.toString());
//                    httpClient.executeMethod(getMethod);
//            }catch(Exception ioe){
//                StackOverFlowUtil.printErrorStack(getRequest(),null,ioe);
//                LOG.error(ioe.getMessage());
//            }
            return true;    
        }
        return false;
    }
	
	
	/**
	 * 记录站内渠道
	 * @param order
	 */
	public void saveInnerChannel(final OrdOrder order) {
		Set<LoscString> set = new TreeSet<LoscString>();	
		Cookie cookie_channel = getCookie(ORDER_OUTTER_CHANNEL);
		Cookie cookie_time = getCookie(ORDER_OUTTER_CHANNEL_TIME);

		if (null != cookie_channel 
				&& StringUtils.isNotBlank(cookie_channel.getValue())
				&& null != cookie_time
				&& StringUtils.isNotBlank(cookie_time.getValue())) {
			String value = cookie_channel.getValue();
			String time = cookie_time.getValue();
			int j = 0;
			for (int i = 0 ; i < value.length() ; i = i + 6) {
				set.add(new LoscString(time.substring(j, j + 4), value.substring(i, i + 6)));
				j = j + 4;
			}
		}
		
		cookie_channel = getCookie(ORDER_INNER_CHANNEL);
		cookie_time = getCookie(ORDER_INNER_CHANNEL_TIME);
		if (null != cookie_channel 
				&& StringUtils.isNotBlank(cookie_channel.getValue())
				&& null != cookie_time
				&& StringUtils.isNotBlank(cookie_time.getValue())) {
			String value = cookie_channel.getValue();
			String time = cookie_time.getValue();
			int j = 0;
			for (int i = 0 ; i < value.length() ; i = i + 6) {
				set.add(new LoscString(time.substring(j, j + 4), value.substring(i, i + 6)));
				j = j + 4;
			}
		}		
		
			
		for (LoscString ls : set) {
			ordOrderChannelService.insert(new OrdOrderChannel(order.getOrderId(), ls.getValue()));
		}
		
	}
	
	/**
	 * 获取HttpRequest
	 * @return
	 */
	private HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	
	/**
	 * 获取指定Cookie
	 * @param name Cookie的名字
	 * @return
	 */
	private final Cookie getCookie(final String name) {
		Cookie[] cookies = this.getRequest().getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase(name)) {
				return cookie;
			}
		}
		return null;
	}
	
	/**
	 * 打印调试信息
	 * @param message
	 */
	private final void debug(final String message) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(message);
		}
	}

	public OrdOrderChannelService getOrdOrderChannelService() {
		return ordOrderChannelService;
	}

	public void setOrdOrderChannelService(
			OrdOrderChannelService ordOrderChannelService) {
		this.ordOrderChannelService = ordOrderChannelService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setPlacePlaceDestService(PlacePlaceDestService placePlaceDestService) {
		this.placePlaceDestService = placePlaceDestService;
	}

	public void setProdProductPlaceService(
			ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}

	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}
}

class LoscString implements java.lang.Comparable<Object> {
	private String time;
	private String value;
	
	public LoscString(String time, String value) {
		this.value = value;
		this.time = time;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getTime() {
		return time;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof LoscString) {
			LoscString v = (LoscString) o;
			if (getTime().equals(v.getTime())) {
				return 1;
			} else {
				return getTime().compareTo(v.getTime());
			}
		} else {
			return -1;
		}
	}
}
