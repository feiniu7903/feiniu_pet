package com.lvmama.front.web.cps;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.OrderChannelInfo;
import com.lvmama.comm.order.Order;
import com.lvmama.comm.pet.service.ord.OrdOrderChannelService;
import com.lvmama.comm.product.Product;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.BaseAction;

/**
 * 通过渠道下的订单
 * 
 * @author ganyingwen
 * 
 */
@Results({
		@Result(name = "error", location = "/WEB-INF/pages/cps/index.html", type = "freemarker"),
		@Result(name = "yiqifa", location = "/WEB-INF/pages/cps/yiqifa.ftl", type = "freemarker"),
		@Result(name = "duomai", location = "/WEB-INF/pages/cps/duomai.ftl", type = "freemarker"),
		@Result(name = "unicorn", location = "/WEB-INF/pages/cps/unicorn.ftl", type = "freemarker"),
		@Result(name = "chanet", location = "/WEB-INF/pages/cps/chanet.ftl", type = "freemarker"),
		@Result(name = "linktech", location = "/WEB-INF/pages/cps/linktech.ftl", type = "freemarker"),
		@Result(name = "51bi", location = "/WEB-INF/pages/cps/51bi.ftl", type = "freemarker"),
		@Result(name = "letu" ,location = "/WEB-INF/pages/cps/letu.ftl", type = "freemarker"),
		@Result(name = "51fanli", location = "/WEB-INF/pages/cps/51fanli.ftl", type = "freemarker"),
		@Result(name = "51RuiGuangCPS", location = "/WEB-INF/pages/cps/ruiguangcps.ftl", type = "freemarker"),
		@Result(name = "ZUOCHE", location = "/WEB-INF/pages/cps/zuoche.ftl", type = "freemarker"),
		@Result(name = "zhitui", location = "/WEB-INF/pages/cps/zhitui.ftl", type = "freemarker"),
		@Result(name = "query" , location = "/WEB-INF/pages/cps/query.ftl",type="freemarker"),
		@Result(name = "weiyi" , location = "/WEB-INF/pages/cps/weiyi.ftl",type="freemarker"),
		@Result(name = "yododo" , location = "/WEB-INF/pages/cps/yododo.ftl",type="freemarker"),
		@Result(name = "JXROCK" , location = "/WEB-INF/pages/cps/JXROCK.ftl",type="freemarker"),
		@Result(name = "fanhuan", location = "/WEB-INF/pages/cps/51fanli.ftl", type = "freemarker") })
public class CpsOrderChannelAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6334804963636076169L;
	private OrdOrderChannelService ordOrderChannelService;
	private OrderService orderServiceProxy;

	private List<OrderChannelInfo> orderChannelInfoList;

	/**
	 * 亿起发CPS的查询接口
	 * 
	 * @return
	 */
	@Action("/cps/queryyiqifa")
	public String queryYiqifa() {
		String src = getRequest().getParameter("src");
		String d = getRequest().getParameter("d");
		if (!"emar".equalsIgnoreCase(src)) {
			return "error";
		}
		Date date = null;
		if (!org.apache.commons.lang3.StringUtils.isEmpty(d)) {
			if (d.indexOf("-") != -1) {
				date = DateUtil.toDate(d, "yyyy-MM-dd");
			} else {
                date = DateUtil.toDate(d, "yyyyMMdd");
			}
			if (date == null)
				return "error";
		}
		getResponse().setContentType("text/plain");
		orderChannelInfoList = ordOrderChannelService.queryOrder("EMAR", date,
				null);
		for (OrderChannelInfo info : orderChannelInfoList) {
			info.setOrdOrder(orderServiceProxy.queryOrdOrderByOrderId(info
					.getOrderId()));
		}
		return "yiqifa";
	}
	
	/**  
	 * 多麦CPS的查询接口
	 * 
	 * @return
	 */
	@Action("/cps/queryDuoMai") 
	public String queryDuoMai() {
		Date start = null; 
		Date end = null;
		if (!"DUOMAI".equalsIgnoreCase(this.getRequest().getParameter("channel"))) {  
			return "error";
		}
		if (null != this.getRequest().getParameter("startDate")) {
			start = DateUtil.toDate(getRequest().getParameter("startDate"),
					"yyyyMMddHHmmSS");
		}
		if (null != this.getRequest().getParameter("endDate")) {
			end = DateUtil.toDate(getRequest().getParameter("endDate"),
					"yyyyMMddHHmmSS");
		}
		getResponse().setContentType("text/plain");
		orderChannelInfoList = ordOrderChannelService.queryOrder("DUOMAI", start , end );
		for (OrderChannelInfo info : orderChannelInfoList) {
			info.setOrdOrder(orderServiceProxy.queryOrdOrderByOrderId(info
					.getOrderId()));
		}
		return "duomai"; 
	}
	
	/**  
     * 北京唯一CPS的查询接口
     * http://www.lvmama.com/cps/queryWeiYi.do?unionId=WEIYI&pwd=LVMAMA&starttime=20131231&endtime=20140101
     * @return
     */
    @Action("/cps/queryWeiYi") 
    public String queryWeiYi() {
        Date start = null; 
        Date end = null;
        if (!"WEIYI".equalsIgnoreCase(this.getRequest().getParameter("unionId"))) {  
            return "error";
        }
        if (!"LVMAMA".equalsIgnoreCase(this.getRequest().getParameter("pwd"))) {  
            return "error";
        }
        if (null != this.getRequest().getParameter("starttime")) {
            start = DateUtil.toDate(getRequest().getParameter("starttime")
                    + "000000", "yyyyMMddHHmmSS");
        }
        if (null != this.getRequest().getParameter("endtime")) {
            end = DateUtil.toDate(getRequest().getParameter("endtime")
                    + "235959", "yyyyMMddHHmmSS");
        }
        getResponse().setContentType("text/plain");
        orderChannelInfoList = ordOrderChannelService.queryOrder("WEIYI", start , end );
        StringBuffer sb = new StringBuffer();
        for (OrderChannelInfo info : orderChannelInfoList) {
            info.setOrdOrder(orderServiceProxy.queryOrdOrderByOrderId(info
                    .getOrderId()));
            sb.append(info.getOrdOrder().getZhCreateTime()+"\t");
            sb.append(info.getArg1()+"\t");
            sb.append(info.getArg2()+"\t");
            sb.append(info.getOrderId()+"\t");
            sb.append(info.getOrdOrder().getMainProduct().getProductId()+"\t\t");
            sb.append(info.getOrdOrder().getMainProduct().getQuantity()+"\t");
            sb.append(info.getOrdOrder().getOrderPayFloat()+"\t");
            sb.append(info.getOrdOrder().getPaymentStatus()+"\t\n");
        }
        this.getRequest().setAttribute("sb", sb);
        return "weiyi"; 
    }
	
    /**
     * 游多多CPS的查询接口
     * http://www.lvmama.com/cps/queryYododo.do?fromId=yododo&startDate=20131231&endDate=20140101
     */
    @Action("/cps/queryYododo") 
    public String queryYododo() {
        Date start = null; 
        Date end = null;
        if (!"yododo".equalsIgnoreCase(this.getRequest().getParameter("fromId"))) {  
            return "error";
        }
        if (null != this.getRequest().getParameter("startDate")) {
            start = DateUtil.toDate(getRequest().getParameter("startDate")
                    + "000000", "yyyyMMddHHmmSS");
        }
        if (null != this.getRequest().getParameter("endDate")) {
            end = DateUtil.toDate(getRequest().getParameter("endDate")
                    + "235959", "yyyyMMddHHmmSS");
        }
        getResponse().setContentType("text/plain");
        orderChannelInfoList = ordOrderChannelService.queryOrder("yododo", start , end );
        for (OrderChannelInfo info : orderChannelInfoList) {
            info.setOrdOrder(orderServiceProxy.queryOrdOrderByOrderId(info.getOrderId()));
        }
        return "yododo"; 
    }
    
	/**
	 * 订单查询前台显示
	 * @return
	 */
	@Action("/cps/queryOrderChannelByOrderId") 
    public String queryOrderChannelByOrderId() {
        Long orderId = 0L;
        if (null != this.getRequest().getParameter("orderId")) {
            orderId = Long.valueOf(getRequest().getParameter("orderId"));   
            
        }
        if(null != orderId){
            orderChannelInfoList = ordOrderChannelService.queryOrderByOrderId(orderId);
            for (OrderChannelInfo info : orderChannelInfoList) {
                info.setOrdOrder(orderServiceProxy.queryOrdOrderByOrderId(info.getOrderId()));
            }
        }
        getResponse().setContentType("text/plain");
        return "query"; 
    }
	
	/**  
	 * 乐途CPS的查询接口
	 * @return
	 */
	@Action("/cps/queryLoTour") 
	public String queryLoTour() {
		Date start = null; 
		Date end = null;
		if (!"lotour".equalsIgnoreCase(this.getRequest().getParameter("channel"))) {  
			return "error";
		}
		if (null != this.getRequest().getParameter("startDate")) {
			start = DateUtil.toDate(getRequest().getParameter("startDate"),
					"yyyyMMddHHmmSS");
		}
		if (null != this.getRequest().getParameter("endDate")) {
			end = DateUtil.toDate(getRequest().getParameter("endDate"),
					"yyyyMMddHHmmSS");
		}
		getResponse().setContentType("application/xml");
		orderChannelInfoList = ordOrderChannelService.queryOrder("lotour", start , end );
		
		StringBuffer sb = new StringBuffer();
		sb.append("<orderList>");
		for (OrderChannelInfo info : orderChannelInfoList) {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(info.getOrderId());
			info.setOrdOrder(order);
				sb.append("<order>");
				sb.append("<Id>").append(order.getOrderId()).append("</Id>");
				sb.append("<Destinetime>").append(order.getZhCreateTime()).append("</Destinetime>");
				
			float Subtotal = 0; // 小计
			String productName="";
			String NewPrice="";
			for (OrdOrderItemProd prod : order.getOrdOrderItemProds()) {
				productName = productName+prod.getProductName()+"|";
				Subtotal=Subtotal+prod.getPriceYuan()*prod.getQuantity();
				NewPrice = NewPrice+prod.getPriceYuan()+"|";
			}
			productName = productName.substring(0,productName.length()-1);
			sb.append("<productName>"+productName+"</productName>");
			sb.append("<PlayDate>"+order.getZhVisitTime()+"</PlayDate>");
			NewPrice = NewPrice.substring(0,NewPrice.length()-1);
			sb.append("<NewPrice>"+NewPrice+"</NewPrice>");
			sb.append("<Subtotal>"+Subtotal+"</Subtotal>");
			
			sb.append("<PayMent>在线支付</PayMent>");
			String payStatus = "";
			if("UNPAY".equals(order.getPaymentStatus())){
				payStatus = "待支付";
			}else if("PAYED".equals(order.getPaymentStatus())){
				payStatus = "已支付";
			}else if("PARTPAY".equals(order.getPaymentStatus())){
				payStatus = "部分支付";
			}else if("TRANSFERRED".equals(order.getPaymentStatus())){
				payStatus = "资金转移";
			}else{
				payStatus = "预支付";
			}
			sb.append("<PayStatus>").append(payStatus).append("</PayStatus>");
			sb.append("<DestineStatus>").append(order.getOrderStatus()).append("</DestineStatus>");
			sb.append("</order>");
		}
		sb.append("</orderList>");
		this.getRequest().setAttribute("sb", sb);
		return "letu"; 
	}
	
	
	/**  
     * 坐车网CPS的查询接口
     * @return
     */
    @Action("/cps/queryZUOCHE") 
    public String queryZUOCHE() {
        Date start = null; 
        Date end = null;
        if (!"ZUOCHE".equalsIgnoreCase(this.getRequest().getParameter("channel"))) {  
            return "error";
        }
        if (null != this.getRequest().getParameter("startDate")) {
            start = DateUtil.toDate(getRequest().getParameter("startDate"),
                    "yyyyMMddHHmmSS");
        }
        if (null != this.getRequest().getParameter("endDate")) {
            end = DateUtil.toDate(getRequest().getParameter("endDate"),
                    "yyyyMMddHHmmSS");
        }
        getResponse().setContentType("application/xml");
        orderChannelInfoList = ordOrderChannelService.queryOrder("ZUOCHE", start , end );
        
        StringBuffer sb = new StringBuffer();
        sb.append("<orders>");
        for (OrderChannelInfo info : orderChannelInfoList) {
            OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(info.getOrderId());
            info.setOrdOrder(order);
                sb.append("<order>");
                sb.append("<Id>").append(order.getOrderId()).append("</Id>");
                sb.append("<DestineTime>").append(order.getZhCreateTime()).append("</DestineTime>");
                
            String ProductName="";
            String OrderType="";
            String NewPrice="";
            String Count="";
            for (OrdOrderItemProd prod : order.getOrdOrderItemProds()) {
                ProductName = ProductName+prod.getProductName()+"|";
                OrderType = OrderType + prod.getProductType()+"|";
                Count=Count+prod.getQuantity()+"|";
                NewPrice = NewPrice+prod.getPriceYuan()+"|";
            }
            ProductName = ProductName.substring(0,ProductName.length()-1);
            sb.append("<ProductName>"+ProductName+"</ProductName>");
            OrderType = OrderType.substring(0,OrderType.length()-1);
            sb.append("<OrderType>"+OrderType+"</OrderType>");
            NewPrice = NewPrice.substring(0,NewPrice.length()-1);
            sb.append("<NewPrice>"+NewPrice+"</NewPrice>");
            Count = Count.substring(0,Count.length()-1);
            sb.append("<Count>"+Count+"</Count>");
            sb.append("<DestineStatus>").append(order.getOrderStatus()).append("</DestineStatus>");
            sb.append("</order>");
        }
        sb.append("</orders>");
        this.getRequest().setAttribute("sb", sb);
        return "ZUOCHE"; 
    }
    
	/**
	 * 麒麟cps的查询接口
	 * @return
	 */
	@Action("/cps/queryUnicorn") 
	public String queryUnicorn() {
		String signs ="";
		Date start = null; 
		Date end = null;
		System.out.println();
		if (!"UNICORN".equalsIgnoreCase(this.getRequest().getParameter("channel"))) {  
			return "error";
		}
		if (null != this.getRequest().getParameter("startDate")) {
			start = DateUtil.toDate(getRequest().getParameter("startDate"),
					"yyyyMMddHHmmSS");
		}
		if (null != this.getRequest().getParameter("endDate")) {
			end = DateUtil.toDate(getRequest().getParameter("endDate"),
					"yyyyMMddHHmmSS");
		}
		getResponse().setContentType("application/xml");
		orderChannelInfoList = ordOrderChannelService.queryOrder("UNICORN", start , end );
		StringBuffer sb = new StringBuffer();
		sb.append("<orders>");
		for (OrderChannelInfo info : orderChannelInfoList) {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(info
					.getOrderId());
			info.setOrdOrder(order);
			sb.append("<order>")
					.append("<act_name>"+"</act_name>")
					.append("<feedback><![CDATA[")
					.append(info.getArg1())
					.append("]]></feedback>" +
							"<order_sn><![CDATA[")
					.append(order.getOrderId())
					.append("]]></order_sn>" +
							"<order_commission_type>0</order_commission_type>")
					.append("<goods>");
					
			int sun = 0; // 计算订单子项的价格总和
			for (OrdOrderItemProd prod : order.getOrdOrderItemProds()) {
				sb.append("<good>");
				sb.append("<good_cate><![CDATA[" + prod.getProductType()
						+ "]]></good_cate>");
				sb.append("<good_id><![CDATA[" + prod.getProductId()
						+ "]]></good_id>");
				sb.append("<good_name><![CDATA[" + prod.getProductName()
						+ "]]></good_name>");
				sb.append("<good_price>" + prod.getPriceYuan()
						+ "</good_price>");
				sun += prod.getPriceYuan();
				sb.append("<good_quantity>" +prod.getQuantity()
						+"</good_quantity>");
				sb.append("<good_commission_rate>0.2</good_commission_rate>");
				sb.append("</good>");
			}
			sb.append("</goods>");

			sb.append("<order_price>"+sun+"</order_price>");
			sb.append("<order_coupon>"+(sun-order.getOughtPayYuan())+"</order_coupon>");
			sb.append("<order_time><![CDATA[").append(order.getZhCreateTime()).append("]]></order_time>");
			sb.append("<order_status>0</order_status>");
			signs = ""+info.getArg1()+order.getOrderId()+0+order.getMainProduct().getProductType()+order.getMainProduct().getProductId()+order.getMainProduct().getProductName()+order.getMainProduct().getPriceYuan()+order.getMainProduct().getQuantity()+0.2+sun+(sun-order.getOughtPayYuan())+order.getCreateTime()+order.getOrderStatus();
			try {
				sb.append("<sign>").append((new MD5().code(new MD5().code(signs)+"3ede9464621115570565636b76ebfab3")).toLowerCase());
				sb.append("</sign>");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			sb.append("</order>");
		}
		sb.append("</orders>");
		this.getRequest().setAttribute("sb", sb);
		return "unicorn";
	}

	/**
     * 智推CPS的查询接口
     * TODO
     * @return
     */
    @Action("/cps/queryZhiTui") 
    public String queryZhiTui() {
        Date start = null; 
        Date end = null;
        if (!"zhitui".equalsIgnoreCase(this.getRequest().getParameter("channel"))) {  
            return "error";
        }
        if (null != this.getRequest().getParameter("starttime")) {
            start = DateUtil.toDate(getRequest().getParameter("starttime"),
                    "yyyyMMddHHmmSS");
        }
        if (null != this.getRequest().getParameter("endtime")) {
            end = DateUtil.toDate(getRequest().getParameter("endtime"),
                    "yyyyMMddHHmmSS");
        }
        getResponse().setContentType("application/xml");
        orderChannelInfoList = ordOrderChannelService.queryOrder("zhitui", start , end );
        StringBuffer sb = new StringBuffer();
        sb.append("<orders>");
        for (OrderChannelInfo info : orderChannelInfoList) {
            OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(info
                    .getOrderId());
            info.setOrdOrder(order);
            sb.append("<order>")
                    .append("<a_id>"+info.getArg1()+"</a_id>")
                    .append("<subid>"+info.getArg2()+"</subid>")
                    .append("<o_cd>"+order.getOrderId()+"</o_cd>")
                    .append("<status>"+order.getOrderStatus()+"</status>")
                    .append("<o_date>"+order.getZhCreateTime()+"</o_date>")
                    .append("<price>"+order.getOughtPayYuan()+"</price>")
                    .append("<rate>0.03</rate>");
            sb.append("</order>");
        }
        sb.append("</orders>");
        this.getRequest().setAttribute("sb", sb);
        return "zhitui";        
    }
	
	
	/**
	 * 成果网CPS的查询接口
	 * 
	 * @return
	 */
	@Action("/cps/queryChanet")
	public String queryChanet() {
		Date start = null;
		Date end = null;
		if (!"Chanet".equalsIgnoreCase(this.getRequest().getParameter("user"))) {
			return "error";
		}
		if (null != this.getRequest().getParameter("start")) {
			start = DateUtil.toDate(getRequest().getParameter("start"),
					"yyyyMMddHHmmSS");
		}
		if (null != this.getRequest().getParameter("end")) {
			end = DateUtil.toDate(getRequest().getParameter("end"),
					"yyyyMMddHHmmSS");
		}
		getResponse().setContentType("text/plain");
		orderChannelInfoList = ordOrderChannelService.queryOrder("CHANET",
				start, end);
		for (OrderChannelInfo info : orderChannelInfoList) {
			info.setOrdOrder(orderServiceProxy.queryOrdOrderByOrderId(info
					.getOrderId()));
		}
		return "chanet";
	}

	/**
	 * 领克特CPS的查询接口
	 * 
	 * @return
	 */
	@Action("/cps/queryLinkTech")
	public String queryLinkTech() {
		Date start = null;
		Date end = null;
		if (null != this.getRequest().getParameter("yyyymmdd")) {
			start = DateUtil.toDate(getRequest().getParameter("yyyymmdd")
					+ "000000", "yyyyMMddHHmmSS");
		}
		if (null != this.getRequest().getParameter("yyyymmdd")) {
			end = DateUtil.toDate(getRequest().getParameter("yyyymmdd")
					+ "235959", "yyyyMMddHHmmSS");
		}
		getResponse().setContentType("text/plain");
		orderChannelInfoList = ordOrderChannelService.queryOrder("LINKTECH",
				start, end);
		for (OrderChannelInfo info : orderChannelInfoList) {
			info.setOrdOrder(orderServiceProxy.queryOrdOrderByOrderId(info
					.getOrderId()));
		}
		return "linktech";
	}

	/**
	 * 51比购网的查询订单
	 * 
	 * @return
	 */
	@Action("/cps/queryBiGou")
	public String queryBiGou() {
		Date start = null;
		Date end = null;
		if (null != this.getRequest().getParameter("sdate")) {
			start = DateUtil.toDate(getRequest().getParameter("sdate")
					+ " 00:00:00", "yyyy-MM-dd HH:mm:SS");
		}
		if (null != this.getRequest().getParameter("edate")) {
			end = DateUtil.toDate(getRequest().getParameter("edate")
					+ " 23:59:59", "yyyy-MM-dd HH:mm:SS");
		}
		getResponse().setContentType("text/plain");
		orderChannelInfoList = ordOrderChannelService.queryOrder("51bi", start,
				end);
		for (OrderChannelInfo info : orderChannelInfoList) {
			info.setOrdOrder(orderServiceProxy.queryOrdOrderByOrderId(info
					.getOrderId()));
		}
		return "51bi";
	}

	/**
	 * 51返利网的查询订单
	 * 
	 * @return
	 */
	@Action("/cps/queryFanLi")
	public String queryFanLi() throws Exception {
		Date start = null;
		Date end = null;
		if (null != this.getRequest().getParameter("begin_date")) {
			start = DateUtil.toDate(getRequest().getParameter("begin_date")
					+ " 00:00:00", "yyyy-MM-dd HH:mm:SS");
		}
		if (null != this.getRequest().getParameter("end_date")) {
			end = DateUtil.toDate(getRequest().getParameter("end_date")
					+ " 23:59:59", "yyyy-MM-dd HH:mm:SS");
		}
		getResponse().setContentType("application/xml");
		orderChannelInfoList = ordOrderChannelService.queryOrder("51fanli",
				start, end);
		StringBuffer sb = new StringBuffer();
		sb.append("<fanli_data version=\"3.0\">");
		for (OrderChannelInfo info : orderChannelInfoList) {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(info
					.getOrderId());
			info.setOrdOrder(order);
			if (null != this.getRequest().getParameter("order_status")) {
				if ("5".equals(this.getRequest().getParameter("order_status")) && !Constant.ORDER_STATUS.FINISHED.name().equals(
						order.getOrderStatus())) {
					continue;
				}
				if ("-1".equals(this.getRequest().getParameter("order_status")) && Constant.ORDER_STATUS.CANCEL.name().equals(
						order.getOrderStatus())) {
					continue;
				}
				if ("2".equals(this.getRequest().getParameter("order_status")) && Constant.ORDER_STATUS.NORMAL.name().equals(
						order.getOrderStatus())) {
					continue;
				}
			}
			sb.append("<order order_time=\"")
					.append(order.getZhCreateTime())
					.append("\" order_no=\"")
					.append(order.getOrderId())
					.append("\"  shop_no=\"lvmama\" total_price=\"")
					.append(order.getOughtPayYuan())
					.append("\"  total_qty=\"")
					.append(order.getOrdOrderItemProds().size())
					.append("\"  shop_key=\"6ee00b667bbf4eb3\" u_id=\"")
					.append(info.getArg1())
					.append("\"  username=\"")
					.append(info.getArg1())
					.append("@51fanli.com\"  is_pay =\""
							+ (Constant.PAYMENT_STATUS.PAYED.name().equals(
									order.getPaymentStatus()) ? "1" : "0")
							+ "\" pay_type=\"1\"")
					.append(" order_status=\"")
					.append(Constant.ORDER_STATUS.CANCEL.name().equals(
							order.getOrderStatus()) ? -1
							: (Constant.ORDER_STATUS.FINISHED.name().equals(
									order.getOrderStatus()) ? 5 : 2))
					.append("\" deli_name=\"\"    deli_no  =\"\"  tracking_code=\"")
					.append(info.getArg2())
					.append("\"  pass_code= \""
							+ new MD5().code((order.getOrderId()
									+ "lvmama" + info.getArg1() + "6ee00b667bbf4eb3")
									.toLowerCase()) + "\">");
			sb.append("<products_all>");
			int total = 0; // 计算订单子项的价格总和
			for (OrdOrderItemProd prod : order.getOrdOrderItemProds()) {
				sb.append("<product>");
				sb.append("<product_id>" + prod.getProductId()
						+ "</product_id>");
				sb.append("<product_url>http://www.lvmama.com/product/"
						+ prod.getProductId() + "</product_url>");
				sb.append("<product_qty>" + prod.getQuantity()
						+ "</product_qty>");
				sb.append("<product_price>" + prod.getPriceYuan()
						+ "</product_price>");
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
				sb.append("<coupon_price>")
						.append(order.getOughtPayYuan() - total)
						.append("</coupon_price>");
				sb.append("<comm_no></comm_no>");
				sb.append("</coupon>");
			}
			sb.append("</coupons_all>");
			sb.append("</order>");
		}
		sb.append("</fanli_data>");
		this.getRequest().setAttribute("sb", sb);
		return "51fanli";
	}

	/**
	 * 睿广CPS的查询订单
	 * 
	 * @return
	 */
	@Action("/cps/queryRuiGuangCPS")
	public String queryRuiGuangCPS() {
		Date start = null;
		Date end = null;
		if (null != this.getRequest().getParameter("date")) {
			start = DateUtil.toDate(getRequest().getParameter("date")
					+ " 00:00:00", "yyyy-MM-dd HH:mm:SS");
		}
		if (null != this.getRequest().getParameter("date")) {
			end = DateUtil.toDate(getRequest().getParameter("date")
					+ " 23:59:59", "yyyy-MM-dd HH:mm:SS");
		}
		getResponse().setContentType("text/plain");
		orderChannelInfoList = ordOrderChannelService.queryOrder("RUIGUANGCPS",
				start, end);

		String site_id = this.getRequest().getParameter("site_id");
		StringBuffer sb = new StringBuffer();
		for (OrderChannelInfo info : orderChannelInfoList) {
			if (null != site_id) {
				if (site_id.equals(info.getArg1())) {
					OrdOrder order = orderServiceProxy
							.queryOrdOrderByOrderId(info.getOrderId());
					sb.append(info.getArg1() + "|" + order.getZhCreateTime()
							+ "|" + order.getOrderId() + "|"
							+ order.getActualPayYuan() + "\n");
				}
			} else {
				OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(info
						.getOrderId());
				sb.append(info.getArg1() + "|" + order.getZhCreateTime() + "|"
						+ order.getOrderId() + "|" + order.getActualPayYuan()
						+ "\n");
			}
		}
		this.getRequest().setAttribute("sb", sb);
		return "51RuiGuangCPS";
	}

	@Action("/cps/queryFanHuanCPS")
	public String queryFanHuanCPS() {
		Date start = null;
		Date end = null;
		StringBuilder sb = new StringBuilder();
		String sign = null;
		if (null != this.getRequest().getParameter("begin_date")) {
			start = DateUtil.toDate(getRequest().getParameter("begin_date"),
					"yyyy-MM-dd HH:mm:SS");
		}
		if (null != this.getRequest().getParameter("end_date")) {
			end = DateUtil.toDate(getRequest().getParameter("end_date"),
					"yyyy-MM-dd HH:mm:SS");
		}

		try {
			sign = new MD5().code("fanhuan"
					+ getRequest().getParameter("begin_date")
					+ getRequest().getParameter("end_date")
					+ "eef30cbd7f9c5a4e");

			if (StringUtils.isBlank(sign)
					|| !sign.equalsIgnoreCase(this.getRequest().getParameter("sign"))) {
				return ERROR;
			}
			orderChannelInfoList = ordOrderChannelService.queryOrder("fanhuan",
					start, end);

			getResponse().setContentType("application/xml");
			sb.append("<fanhuan_data version=\"1.0\">");

			for (OrderChannelInfo info : orderChannelInfoList) {
				OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(info
						.getOrderId());
				if (null != order) {
					sb.append("<order>");
					sb.append("<order_time>" + order.getZhCreateTime()
							+ "</order_time>");
					sb.append("<order_no>" + order.getOrderId() + "</order_no>");
					sb.append("<shop_no>lvmama</shop_no>");
					sb.append("<total_price>" + order.getOughtPayYuan()
							+ "</total_price>");
					sb.append("<total_qty>"
							+ order.getOrdOrderItemProds().size()
							+ "</total_qty>");
					sb.append("<u_id>" + info.getArg1() + "</u_id>");
					sb.append("<username>" + "</username>");
					if (order.isPaymentSucc()) {
						sb.append("<is_pay>1</is_pay>");
					} else {
						sb.append("<is_pay>0</is_pay>");
					}
					sb.append("<pay_type>1</pay_type>");
					sb.append("<order_status>0</order_status>");
					sb.append("<deli_name></deli_name>");
					sb.append("<deli_no></deli_no>");
					sb.append("<tracking_code>" + info.getArg2()
							+ "</tracking_code>");
					sb.append("<pass_code>"
							+ new MD5().code(order.getOrderId() + "lvmama"
									+ info.getArg1() + "eef30cbd7f9c5a4e")
							+ "</pass_code>");
					sb.append("<commission>0</commission>");
					sb.append("<products_all>");
					float total = 0F;
					for (OrdOrderItemProd prod : order.getOrdOrderItemProds()) {
						sb.append("<product>");
						sb.append("<product_id>" + prod.getProductId()
								+ "</product_id>");
						sb.append("<product_name>" + prod.getProductName()
								+ "</product_name>");
						sb.append("<product_url>http://www.lvmama.com/product/"
								+ prod.getProductId() + "</product_url>");
						sb.append("<product_qty>" + prod.getQuantity()
								+ "</product_qty>");
						sb.append("<product_price>" + prod.getPriceYuan()
								+ "</product_price>");
						sb.append("<product_comm>0</product_comm>");
						sb.append("<comm_no>A</comm_no>");
						sb.append("</product>");
						total += prod.getPriceYuan() * prod.getQuantity();
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
					sb.append("<coupon_price>"
							+ (order.getOughtPayYuan() - total)
							+ "</coupon_price>");
					sb.append("<comm_no>A</comm_no>");
					sb.append("</coupon>");
					sb.append("</coupons_all>");
					sb.append("</order>");
				}
			}
			sb.append("</fanhuan_data>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.getRequest().setAttribute("sb", sb);
		return "fanhuan";
	}

	/**  
     * 极效CPS的查询接口
     * @return
     */
    @Action("/cps/queryJXROCK") 
    public String queryJXROCK() {
        Date start = null; 
        Date end = null;
        if (!"JXROCK".equalsIgnoreCase(this.getRequest().getParameter("channel"))) {  
            return "error";
        }
        if (null != this.getRequest().getParameter("starttime")) {
            start = DateUtil.toDate(getRequest().getParameter("starttime"),
                    "yyyyMMddHHmmSS");
        }
        if (null != this.getRequest().getParameter("endtime")) {
            end = DateUtil.toDate(getRequest().getParameter("endtime"),
                    "yyyyMMddHHmmSS");
        }
        getResponse().setContentType("application/xml");
        orderChannelInfoList = ordOrderChannelService.queryOrder("JXROCK", start , end );
        
        StringBuffer sb = new StringBuffer();
        sb.append("<orders>");
        for (OrderChannelInfo info : orderChannelInfoList) {
            OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(info.getOrderId());
            if(null != order){
            info.setOrdOrder(order);
                sb.append("<order>");
                sb.append("<c_type>2</c_type>");
                sb.append("<aelinfo>"+info.getArg1()+"</aelinfo>");
                sb.append("<o_cd>"+order.getOrderId()+"</o_cd>");
                for (OrdOrderItemProd prod : order.getOrdOrderItemProds()) {
                    sb.append("<product>");
                        sb.append("<p_cd>"+prod.getProductId()+"</p_cd>");
                        sb.append("<price>"+prod.getPriceYuan()+"</price>");
                        sb.append("<it_cnt>"+prod.getQuantity()+"</it_cnt>");
                        sb.append("<c_cd>"+prod.getProductType()+"</c_cd>");
                        sb.append("<amt>"+prod.getPriceYuan()*prod.getQuantity()+"</amt>");
                    sb.append("</product>");
                }
                sb.append("<pm>在线支付</pm>");
                
                sb.append("<yyyymmdd>"+DateUtil.getFormatDate(order.getCreateTime(),"yyyy-MM-dd")+"</yyyymmdd>");
                sb.append("<hhmiss>"+DateUtil.getFormatDate(order.getCreateTime(),"HH:mm:ss")+"</hhmiss>");
              /**
               * stat_code
               * 100：未支付（用户成功提交订单，但未支付）
               * 150：已支付（用户支付成功）
               * 200：交易成功（用户已支付且没有退货，交易成功）
               * 300：交易失败（系统自动取消订单或用户退货，交易失败
               */
                String stat_code = "";
                if(Constant.ORDER_STATUS.FINISHED.getCode().equals(order.getOrderStatus())){
                    stat_code = "200";
                }else if(Constant.ORDER_STATUS.CANCEL.getCode().equals(order.getOrderStatus())){
                    stat_code = "300";
                }else if(Constant.ORDER_STATUS.NORMAL.getCode().equals(order.getOrderStatus())){
                  if(Constant.PAYMENT_STATUS.UNPAY.getCode().equals(order.getPaymentStatus())){
                      stat_code = "100";
                  }else if(Constant.PAYMENT_STATUS.PAYED.getCode().equals(order.getPaymentStatus())){
                      stat_code = "150";
                  } 
               }
            sb.append("<stat_code>"+stat_code+"</stat_code>");
            sb.append("<payPirce>"+order.getOrderPayFloat()+"</payPirce>");
            sb.append("</order>");
            }
        }
        sb.append("</orders>");
        this.getRequest().setAttribute("sb", sb);
        return "JXROCK"; 
    }
	
	public void setOrdOrderChannelService(
			OrdOrderChannelService ordOrderChannelService) {
		this.ordOrderChannelService = ordOrderChannelService;
	}

	public List<OrderChannelInfo> getOrderChannelInfoList() {
		return orderChannelInfoList;
	}

	public void setOrderChannelInfoList(
			List<OrderChannelInfo> orderChannelInfoList) {
		this.orderChannelInfoList = orderChannelInfoList;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	
	
	public static void main(String[] args) throws Exception {
		System.out.println(new MD5().code("fanhuan"
				+ "2012-11-29 09:32:57"
				+ "2012-11-30 10:32:57"
				+ "eef30cbd7f9c5a4e"));
	}
}