package com.lvmama.pet.sweb.gateway;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pay.PayPaymentGateway;
import com.lvmama.comm.pet.po.pay.PayPaymentGatewayElement;
import com.lvmama.comm.pet.service.pay.PayPaymentGatewayElementService;
import com.lvmama.comm.pet.service.pay.PayPaymentGatewayService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.PAYMENT_GATEWAY_IS_ALLOW_REFUND;
import com.lvmama.comm.vo.Constant.PAYMENT_GATEWAY_STATUS;
import com.lvmama.comm.vo.Constant.PAYMENT_GATEWAY_TYPE;

public class PaymentGatewayAction extends BackBaseAction {
	
	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = -3374979970113988388L;

	/**
	 * 网关管理服务
	 */
	private PayPaymentGatewayService payPaymentGatewayService;
	
	/**
	 * 网关-线下支付输入项控制
	 */
	private PayPaymentGatewayElementService payPaymentGatewayElementService;
	
	/**
	 * 网关管理页面参数载体
	 */
	private PaymentGatewayModel paymentGatewayModel = new PaymentGatewayModel();
	
	/**
	 * 载入页面 
	 * @author ZHANG Nan
	 * @return
	 */
	public String load(){
		initComBoxDate(paymentGatewayModel);
		if(StringUtils.isNotBlank(paymentGatewayModel.getTarget())){
			return paymentGatewayModel.getTarget();
		}
		else{
			return SUCCESS;	
		}
	}
	/**
	 * 分页查询
	 * @author ZHANG Nan
	 * @return
	 */
	public String query() {
		initComBoxDate(paymentGatewayModel);
		Map<String,String> paramMap=paymentGatewayModel.getParamMap();
		pagination=initPage();
		paramMap.put("start", String.valueOf(pagination.getStartRows()));
		paramMap.put("end", String.valueOf(pagination.getEndRows()));
		Long payPaymentGatewayListCount=payPaymentGatewayService.selectPayPaymentGatewayByParamMapCount(paramMap);
		if(payPaymentGatewayListCount!=null && payPaymentGatewayListCount>0){
			List<PayPaymentGateway> payPaymentGatewayList= payPaymentGatewayService.selectPayPaymentGatewayByParamMap(paramMap);
			paymentGatewayModel.setPayPaymentGatewayList(payPaymentGatewayList);
		}
		pagination.setTotalResultSize(payPaymentGatewayListCount);
		pagination.buildUrl(getRequest());
		return SUCCESS;
	}
	/**
	 * 新增网关
	 * @author ZHANG Nan
	 * @return
	 * @throws IOException 
	 */
	public void save() throws IOException{
		try {
			PayPaymentGateway oldPayPaymentGateway=payPaymentGatewayService.selectPaymentGatewayByGateway(paymentGatewayModel.getGateway());
			if(oldPayPaymentGateway!=null){
				this.getResponse().getWriter().write("{result:'网关CODE已存在,请重新输入!'}");
				return ;
			}
			PayPaymentGateway payPaymentGateway=new PayPaymentGateway();
			BeanUtils.copyProperties(paymentGatewayModel, payPaymentGateway);
			payPaymentGateway.setCreateTime(new Date());
			
			PayPaymentGatewayElement payPaymentGatewayElement=null;
			//目前只对 线下 其它支付控制 线下支付输入项
			if(Constant.PAYMENT_GATEWAY_TYPE.OTHER.name().equals(payPaymentGateway.getGatewayType())){
				payPaymentGatewayElement=paymentGatewayModel.getPayPaymentGatewayElement();
				payPaymentGatewayElement.setGateway(payPaymentGateway.getGateway());		
			}
			payPaymentGatewayService.savePayPaymentGatewayAndElement(payPaymentGateway,payPaymentGatewayElement);
				
			this.getResponse().getWriter().write("{result:'true'}");
		} catch (Exception e) {
			e.printStackTrace();
			this.getResponse().getWriter().write("{result:'操作失败!'}");
		}
	}
	/**
	 * open网关修改页面
	 * @author ZHANG Nan
	 * @return
	 */
	public String openModify(){
		initComBoxDate(paymentGatewayModel);
		Long paymentGatewayId=paymentGatewayModel.getPaymentGatewayId();
		PayPaymentGateway payPaymentGateway=payPaymentGatewayService.selectPaymentGatewayByPK(paymentGatewayId);
		if(payPaymentGateway!=null){
			PayPaymentGatewayElement payPaymentGatewayElement=payPaymentGatewayElementService.selectPaymentGatewayElementByGateway(payPaymentGateway.getGateway());
			BeanUtils.copyProperties(payPaymentGateway, paymentGatewayModel);
			paymentGatewayModel.setPayPaymentGatewayElement(payPaymentGatewayElement);
		}
		return "modify";
	}
	/**
	 * 修改网关
	 * @author ZHANG Nan
	 * @return
	 * @throws IOException 
	 */
	public void modify() throws IOException{
		try {
			PayPaymentGateway payPaymentGateway=new PayPaymentGateway();
			BeanUtils.copyProperties(paymentGatewayModel, payPaymentGateway);
			payPaymentGatewayService.updatePayPaymentGatewayAndElement(payPaymentGateway,paymentGatewayModel.getPayPaymentGatewayElement());
			log.info("gateway modify, userName="+getSessionUserName()+", modify object="+StringUtil.printParam(payPaymentGateway));
			this.getResponse().getWriter().write("{result:'修改成功!'}");
		} catch (Exception e) {
			e.printStackTrace();
			this.getResponse().getWriter().write("{result:'修改失败,请联系管理员!'}");
		}
	}
	/**
	 * 初始化PaymentGatewayModel ComBox数据
	 * @author ZHANG Nan
	 */
	private void initComBoxDate(PaymentGatewayModel paymentGatewayModel){
		//支付网关-是否允许退款
		paymentGatewayModel.setPaymentGatewayIsAllowRefund(PAYMENT_GATEWAY_IS_ALLOW_REFUND.values());
		//支付网关-网关状态
		paymentGatewayModel.setPaymentGatewayStatus(PAYMENT_GATEWAY_STATUS.values());
		//支付网关-网关类型
		paymentGatewayModel.setPaymentGatewayType(PAYMENT_GATEWAY_TYPE.values());
	}
	
	
	
	
	
	public PaymentGatewayModel getPaymentGatewayModel() {
		return paymentGatewayModel;
	}
	public void setPaymentGatewayModel(PaymentGatewayModel paymentGatewayModel) {
		this.paymentGatewayModel = paymentGatewayModel;
	}
	public void setPayPaymentGatewayService(PayPaymentGatewayService payPaymentGatewayService) {
		this.payPaymentGatewayService = payPaymentGatewayService;
	}
	public void setPayPaymentGatewayElementService(PayPaymentGatewayElementService payPaymentGatewayElementService) {
		this.payPaymentGatewayElementService = payPaymentGatewayElementService;
	}
}
