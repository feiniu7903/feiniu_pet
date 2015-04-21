package com.lvmama.front.web.buy;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.service.businessCoupon.ProdSeckillRuleService;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.utils.ord.ProductBlackValidateUtil;
import com.lvmama.comm.vo.Constant;


/**
 * 
 * 前台(网站)订单创建实现类。
 * 此类将前台(网站)用户所填写的订购相关数据进行整理，并提交给订单服务进行下单。并在下单成功后转向
 * 订单展示页。
 * 
 * @author Brian
 *
 */
@ParentPackage("frontCreateOrderInterceptorPackage")
@ResultPath("/frontCreateOrderInterceptor")
@Results( {
		@Result(name = "view", location = "/view/view.do?orderId=${orderId}&id=${buyInfo.productId}&days=${buyInfo.days}&leaveTime=${buyInfo.leaveTime}", type = "redirect"),
		@Result(name = "viewMergePay", location = "/view/viewMergePay.do?orderIds=${orderIds}", type = "redirect"),
		@Result(name = "failConfirm", location = "/WEB-INF/pages/buy/ticket/fail_order.ftl", type = "freemarker")
})
public class FrontCreateOrderAction extends CreateOrderAction {
	private static final long serialVersionUID = 7759541215656060198L;
	private static final Log LOG = LogFactory.getLog(FrontCreateOrderAction.class);

	private ProdSeckillRuleService prodSeckillRuleService;
	
	@Actions({
		@Action(
				value="/buy/update"		
				,interceptorRefs={
						@InterceptorRef("frontCreateOrderInterceptor"),
						@InterceptorRef("defaultStack")		
				}
		)
	})
	@Override
	public String execute() throws Exception {
		if (isLogin()) {
			if(!checkGugongProductCardType()){
				return "failConfirm";
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("用户:" + getUserId() + "正在创建订单。");
			}
		} else {
			LOG.error("用户在未登录状态下或Session过期状态下创建订单失败!");
			return ERROR;
		}
		try {			
			//校验是否非法提交秒杀订单
			if(buyInfo.getProductId() == null || buyInfo.getProductId().toString().equals("")){
				return ERROR;
			}
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("productId", buyInfo.getProductId());
			param.put("nowDate", new Date());
			List<ProdSeckillRule> prodSeckillRuleList =prodSeckillRuleService.queryValidSeckillRule(param);
			if(prodSeckillRuleList != null && prodSeckillRuleList.size() > 0){
				Object uuid = ServletUtil.getSession(getRequest(), getResponse(), Constant.SECKILL.SECKILL_SUBMIT_ORDER_UUID.getCode());
				if(uuid == null || buyInfo.getSeckillToken() == null){
					LOG.info("============order seckill 用户非法提交秒杀订单================"+getUserId());
					return ERROR;
				}else if(!uuid.toString().equals(buyInfo.getSeckillToken())){
					LOG.info("============order seckill 用户非法提交秒杀订单================"+getUserId());
					return ERROR;
				}
			}
			//校验是否黑名单
			try{
				boolean flag1 = ProductBlackValidateUtil.getProductBlackValidateUtil().validateBlackByUserAndProductForProds(buyInfo.getProductId(), this.getUser().getId());
				if(!flag1){
					LOG.info("============非法提交订单validate blackListProds validateBlackByUserAndProductForProds ==============="+flag1);
					return ERROR;
				}
				boolean flag2 = ProductBlackValidateUtil.getProductBlackValidateUtil().validateBlackByMoblieAndProductForProds(buyInfo.getProductId(), this.getContact().getMobileNumber());
				if(!flag2){
					LOG.info("============非法提交订单validate blackListProds validateBlackByMoblieAndProductForProds ==============="+flag2);
					return ERROR;
				}
			}catch(Exception e){
				StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
			}
			return super.createOrder();
		} catch(Exception e) {
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
			return ERROR;
		}
	}
	
	/**
	 * 创建订单时校验如果是故宫产品证件号类型必须为身份证号
	 * @return
	 */
	private boolean checkGugongProductCardType(){
		//故宫的产品证件号必须为身份证号
		if(prodProductRoyaltyService.getRoyaltyProductIds().contains(buyInfo.getProductId())){
			if(contact!=null){
				log.info("cardType:"+contact.getCardType());
				if(!StringUtils.equals(contact.getCardType(),Constant.CERT_TYPE.ID_CARD.getCnName())){
					return false;
				}
			}
		}
		return true;
	}

	public void setProdSeckillRuleService(
			ProdSeckillRuleService prodSeckillRuleService) {
		this.prodSeckillRuleService = prodSeckillRuleService;
	}
}
