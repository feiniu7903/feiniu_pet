package com.lvmama.tnt.front.order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.tnt.cashaccount.po.TntCashAccount;
import com.lvmama.tnt.cashaccount.po.TntCashRefundment;
import com.lvmama.tnt.cashaccount.service.TntCashaccountService;
import com.lvmama.tnt.comm.service.TntLogService;
import com.lvmama.tnt.comm.util.WebFetchPager;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.ResultMessage;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.comm.vo.TntConstant.COM_LOG_OBJECT_TYPE;
import com.lvmama.tnt.comm.vo.TntConstant.COM_LOG_TYPE;
import com.lvmama.tnt.front.BaseController;
import com.lvmama.tnt.front.order.vo.OrderCollecttionVo;
import com.lvmama.tnt.order.po.TntOrder;
import com.lvmama.tnt.order.service.TntOrderService;
import com.lvmama.tnt.user.po.TntUser;

@Controller
@RequestMapping(value = "/mOrder")
public class OrderManageController extends BaseController{

	@Autowired
	private TntOrderService tntOrderService;
	@Autowired
	private TntCashaccountService tntCashaccountService;
	@Autowired
	private OrderService orderServiceProxy;
	
	@Autowired
	private TntLogService tntLogService;
	
	@RequestMapping(value = "/index.do")
	public String index(Model model,HttpServletRequest request){
		model.addAttribute("tntOrder", new TntOrder());
		query("ALL", new TntOrder(), 1, request,model,request.getSession());
		return "/order/myspace/index";
	}
	
	@RequestMapping(value = "/{path}/query.do")
	public String query(@PathVariable String path,TntOrder tntOrder, Integer page, 
			HttpServletRequest request,Model model,HttpSession session){
		int pageNo = page != null ? page : 1;
		TntUser  user = getLoginUser(session);
		tntOrder.setDistributorId(user.getUserId());
		Page<TntOrder> p = Page.page(pageNo, tntOrder);
		p.setModel(Page.MODEL_FRONT);
		List<TntOrder> tntOrderList = null;
		if("ALL".equalsIgnoreCase(path)){
			tntOrderList = query(p, request);
		}
		if("UNPAY".equalsIgnoreCase(path)){
			tntOrderList = queryUnpayOrder(tntOrder,model,p,request);
		}
		if("UNREFUND".equalsIgnoreCase(path)){
			tntOrderList = queryUnrefundOrder(tntOrder,model,p,request);
		}
		model.addAttribute(Page.KEY, p);
		List<OrderCollecttionVo> orderList = buildOrder(tntOrderList);
		model.addAttribute("orderList", orderList);
		return "/order/myspace/list_"+path;
	}
	
	@RequestMapping(value = "/cancelOrder.do")
	public void cancelOrder(HttpServletRequest request,HttpServletResponse response,String orderId,String tntOrderId) throws IOException{
		ResultMessage message = new ResultMessage(true, "取消成功！");
		TntOrder tntOrder = new TntOrder();
		tntOrder.setOrderId(Long.valueOf(orderId));
		tntOrder.setTntOrderId(Long.valueOf(tntOrderId));
		try{
			boolean success = tntOrderService.cancelOrder(tntOrder);
			if(!success){
				message.setSuccess(false);
				message.setErrorText("订单取消失败");
			}
		}catch(Exception e){
			message.setSuccess(false);
			message.setErrorText("订单取消失败");
		}
		super.printRtn(request, response, message);
	}
	
	private List<TntOrder> queryUnrefundOrder(TntOrder tntOrder, Model model,
			Page<TntOrder> p,HttpServletRequest request) {
		tntOrder.setRefundStatus(TntConstant.REFUND_STATUS.WAITING.name());
		List<TntOrder> tntOrderList= query(p, request);
		Long orderRefundCount=0L;
		if(tntOrderList!=null && tntOrderList.size()>0){
			for(TntOrder o : tntOrderList){
				orderRefundCount = orderRefundCount + o.getRefundAmount(); 
			}
		}
		model.addAttribute("num", tntOrderList.size());
		model.addAttribute("orderRefundCount", PriceUtil.convertToYuan(orderRefundCount));
		return  tntOrderList;
	}

	private List<TntOrder> queryUnpayOrder(TntOrder tntOrder, Model model,
			Page<TntOrder> p,HttpServletRequest request) {
		tntOrder.setOrderStatus(Constant.ORDER_STATUS.NORMAL.getCode());
		tntOrder.setPaymentStatus(Constant.PAYMENT_STATUS.UNPAY.getCode());
		tntOrder.setPaymentTarget(Constant.PAYMENT_TARGET.TOLVMAMA.getCode());
		tntOrder.setApproveStatus(Constant.ORDER_APPROVE_STATUS.VERIFIED.getCode());
		List<TntOrder> tntOrderList= query(p, request);
		Long orderAmountCount=0L;
		if(tntOrderList!=null && tntOrderList.size()>0){
			for(TntOrder o : tntOrderList){
				orderAmountCount = orderAmountCount + o.getOrderAmount(); 
			}
		}
		model.addAttribute("num", tntOrderList.size());
		model.addAttribute("orderAmountCount", PriceUtil.convertToYuan(orderAmountCount));
		return  tntOrderList;
	}

	private List<OrderCollecttionVo> buildOrder(List<TntOrder> tntOrderList) {
		List<OrderCollecttionVo> orderList = new ArrayList<OrderCollecttionVo>();
		if(tntOrderList==null|| tntOrderList.size()<1){
			return null;
		}
		for(TntOrder ord : tntOrderList){
			OrdOrder o = orderServiceProxy.queryOrdOrderByOrderId(ord.getOrderId());
			OrderCollecttionVo  vo = new OrderCollecttionVo();
			vo.setOrdOrder(o);
			vo.setTntOrder(ord);
			if(o!=null && !"".equals(o.getZhWaitPayment())){
				vo.setWaitPaymenTime(DateUtil.compareDate(DateUtil.getDateByStr(o.getZhWaitPayment(), "yyyy-MM-dd HH:mm"), new Date()));
			}
			orderList.add(vo);
		}
		return orderList;
	}

	protected List<TntOrder> query(Page<TntOrder> page,
			HttpServletRequest request) {
		WebFetchPager<TntOrder> pager = new WebFetchPager<TntOrder>(
				page, request) {
			@Override
			protected long getTotalCount(TntOrder t) {
				return tntOrderService.count(t);
			}
			@Override
			protected List<TntOrder> fetchDetail(
					Page<TntOrder> page) {
					page = page.desc("TNT_ORDER_ID");
				return tntOrderService.findPage(page);
			}

		};
		return pager.fetch();
	}
	
	
	@RequestMapping(value = "/detail.do")
	public String detail(Model model,Long orderId){
		OrdOrder o = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		TntOrder tOrder = tntOrderService.getDetailByOrderId(orderId);
		OrderCollecttionVo  vo = new OrderCollecttionVo();
		vo.setOrdOrder(o);
		vo.setTntOrder(tOrder);
		if(o!=null){
			vo.setTravellerList(o.getTravellerList());
			vo.setPersonList(o.getPersonList());
		}
		model.addAttribute("order", vo);
		return "/order/myspace/detail";
	}
	
	@RequestMapping(value = "/refund.do")
	public void refund(Model model,Long orderId,HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		ResultMessage message = new ResultMessage(true, "退款成功！");
		TntOrder tOrder = tntOrderService.getByOrderId(orderId);
		if(tOrder==null){
			message.setSuccess(false);
			message.setErrorText("订单不存在");
			super.printRtn(request, response, message);
			return ;
		}
		if(!TntConstant.REFUND_STATUS.WAITING.name().equals(tOrder.getRefundStatus())){
			message.setSuccess(false);
			message.setErrorText("订单当前状态不可以退款");
			super.printRtn(request, response, message);
			return ;
		}
		TntCashAccount cashAccount = tntCashaccountService.getAccountByUserId(tOrder.getDistributorId());
		TntCashRefundment tntCashRefundment = new TntCashRefundment();
		tntCashRefundment.setAmount(tOrder.getRefundAmount());
		tntCashRefundment.setCashAccountId(cashAccount.getCashAccountId());
		tntCashRefundment.setProductName(tOrder.getProductName());
		tntCashRefundment.setTntOrderId(tOrder.getTntOrderId());
		Long refundmentId = tntCashaccountService.addRefundment(tntCashRefundment);
		TntUser tntUser = getLoginUser(request.getSession());
		tntLogService.save(COM_LOG_OBJECT_TYPE.TNT_ACCOUNT.getCode(), null, refundmentId, tntUser.getUserName(), COM_LOG_TYPE.ACCOUNT_REFUNDMENT.name(), "退款", "分销商确认退款", "");
		super.printRtn(request, response, message);
	}
}
