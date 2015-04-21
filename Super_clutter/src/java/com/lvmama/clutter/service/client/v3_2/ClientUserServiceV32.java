package com.lvmama.clutter.service.client.v3_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.lvmama.clutter.model.MobileOrder;
import com.lvmama.clutter.model.MobileOrderCmt;
import com.lvmama.clutter.model.MobilePayment;
import com.lvmama.clutter.model.MobileReceiver;
import com.lvmama.clutter.model.MobileUser;
import com.lvmama.clutter.model.MobileUserCoupon;
import com.lvmama.clutter.service.client.v3_1.ClientUserServiceV31;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.comment.DicCommentLatitude;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PageElementModel;

public class ClientUserServiceV32 extends ClientUserServiceV31{

	@Override
	public Map<String, Object> getBonusInfo(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getBonusInfo(params);
	}

	@Override
	public Map<String, Object> getBonusIncome(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getBonusIncome(params);
	}

	@Override
	public Map<String, Object> getBonusPayment(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getBonusPayment(params);
	}

	@Override
	public Map<String, Object> getBonusRefund(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getBonusRefund(params);
	}

	@Override
	public String getZhComeFrom(String comeFrom) {
		// TODO Auto-generated method stub
		return super.getZhComeFrom(comeFrom);
	}

	@Override
	public Map<String, Object> reSendSmsCert(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.reSendSmsCert(params);
	}

	@Override
	public Map<String, Object> bonusPay(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.bonusPay(params);
	}

	@Override
	public Map<String, Object> bindingCouponToUser(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.bindingCouponToUser(params);
	}

	@Override
	public Map<String, Object> queryCmtWaitForOrder(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.queryCmtWaitForOrder(param);
	}

	@Override
	public Map<String, Object> commitOrderComment(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		return super.commitOrderComment(param);
	}

	@Override
	public void setOrderMessageProducer(
			TopicMessageProducer orderMessageProducer) {
		// TODO Auto-generated method stub
		super.setOrderMessageProducer(orderMessageProducer);
	}

	@Override
	public MobileUser getUser(Map<String, String> param) {
		// TODO Auto-generated method stub
		return super.getUser(param);
	}

	@Override
	public List<MobileUserCoupon> getCoupon(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getCoupon(param);
	}

	@Override
	public MobileOrder getOrder(Map<String, Object> param) {
		// TODO Auto-generated method stub
		ArgCheckUtils.validataRequiredArgs("orderId", param);
		String subProductType = String.valueOf(param.get("subProductType"));
		Long lvversion = 0L;
		if(param.get("lvversion")!=null){
			lvversion = Long.valueOf(param.get("lvversion").toString());
		}
		Map<String,Object> map = super.getOrderViewList(Long.valueOf(param.get("orderId").toString()), String.valueOf(param.get("userNo")), 1L,subProductType,lvversion);
		List<MobileOrder> orderList = (List<MobileOrder>)map.get("datas");
		if(orderList!=null &&!orderList.isEmpty()){
			// 初始化订单奖金支付信息 . 
			MobileOrder mo =	super.initBonus(orderList.get(0));

			List<OrdOrder> listOrder = (List<OrdOrder>) map.get("ordOrderList");
			if(listOrder!=null && !listOrder.isEmpty()){
				OrdOrder oo = listOrder.get(0);
				if(oo.getOughtPay()!=0){
					mo.setCanToPay(oo.isCanToPay());
				}
			}
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("userId", param.get("userId"));
			List<UserCooperationUser> list = userCooperationUserService.getCooperationUsers(parameters);
			String channel = "";
			if(list!=null&&!list.isEmpty()){
				UserCooperationUser cu = list.get(0);
				channel = cu.getCooperation();
			}
			MobilePayment mpalipay = new MobilePayment(Constant.PAYMENT_GATEWAY.ALIPAY_APP.name(), Constant.getInstance().getAliPayAppUrl());
			MobilePayment mpwap = new MobilePayment(Constant.PAYMENT_GATEWAY.ALIPAY_WAP.name(), Constant.getInstance().getAliPayWapUrl());
			MobilePayment mpupomp = new MobilePayment(Constant.PAYMENT_GATEWAY.UPOMP.name(), Constant.getInstance().getUpompPayUrl());
			MobilePayment tenpayWap = new MobilePayment("TENPAY_WAP", Constant.getInstance().getTenpayWapUrl());
			mo.getPaymentChannels().add(mpalipay);
			mo.getPaymentChannels().add(mpwap);
			mo.getPaymentChannels().add(tenpayWap);
			mo.getPaymentChannels().add(mpupomp);

			if("ALIPAY".equals(channel)){
					mo.getPaymentChannels().remove(mpupomp);
					mo.getPaymentChannels().remove(tenpayWap);
			}

			return mo;

		}  
		return null;	
	}
	
	


	@Override
	public Map<String, Object> getOrderList(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("page",param);
		String subProductType = null;
		if(param.get("subProductType")!=null){
		   subProductType = String.valueOf(param.get("subProductType"));
		}
		Map<String,Object> map = this.getOrderViewList(null,String.valueOf(param.get("userNo")), Long.valueOf(param.get("page").toString()),subProductType);
		map.remove("ordOrderList");
		return map;
	}
	
	
	/**
	 * 获取订单列表.
	 * @param orderId  订单id
	 * @param userId   用户id
	 * @param page
	 * @return
	 */
	protected Map<String, Object> getOrderViewList(Long orderId, String userId,Long page,final String subProductType) {
		// 综合查询 
		CompositeQuery compositeQuery = new CompositeQuery();
		OrderIdentity orderIdentity = new OrderIdentity();
		orderIdentity.setUserId(userId);
		if (orderId != null) {
			orderIdentity.setOrderId(orderId);
		}
		
		OrderContent content = compositeQuery.getContent();
		if(StringUtil.isNotEmptyString(subProductType) && !"null".equals(subProductType)){
			
			content.setSubProductType(subProductType);
		} else {
			List<PageElementModel>  list = Constant.SUB_PRODUCT_TYPE.getList();
			//Iterable<PageElementModel> it =
			String subProductTypes = "";
			Iterator<PageElementModel> it =  list.iterator();
			while(it.hasNext()){
				PageElementModel pm = it.next();
				if(pm.getElementCode().equals(Constant.SUB_PRODUCT_TYPE.TRAIN.name())||pm.getElementCode().equals(Constant.SUB_PRODUCT_TYPE.OWNEXPENSE.name())
						||pm.getElementCode().equals(Constant.SUB_PRODUCT_TYPE.INSURANCE.name())){
					continue;
				}
				subProductTypes+=pm.getElementCode()+",";
			}
			content.setSubProductType(subProductTypes);
		}
		
		compositeQuery.setOrderIdentity(orderIdentity);
       
		// 查询记录总数
		Long totalRecords = orderServiceProxy.compositeQueryOrdOrderCount(compositeQuery);

		// 分页相关 
		Page<MobileOrder> pageConfig = Page.page(totalRecords, 5L, page);
		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(new Integer("" + pageConfig.getStartRows()));
		pageIndex.setEndIndex(new Integer("" + pageConfig.getEndRows()));
		compositeQuery.setPageIndex(pageIndex);
		
		// 订单列表
		List<OrdOrder> ordersList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
		List<MobileOrder> vcoList = new ArrayList<MobileOrder>();
		if (ordersList != null && !ordersList.isEmpty()) {
			for (OrdOrder ordOrder : ordersList) {
			if (ordOrder == null || null == ordOrder.getMainProduct()) {
					continue;
			} 
				// 初始化订单
				// 初始化订单
			MobileOrder mo  = getMobileOrder(ordOrder);
			mo.setCanToPay(ordOrder.isCanToPay());
			vcoList.add(mo);
			}
		}
		pageConfig.setItems(vcoList);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("datas", vcoList);
		resultMap.put("ordOrderList", ordersList);
		resultMap.put("isLastPage", this.isLastPage(pageConfig));
		return resultMap;
	}

	/**
	 * 批量添加或者修改联系人
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@Override
	public String addContacts(Map<String, String> param) throws Exception {
		return super.addContacts(param);
	}
	
	@Override
	public String addFavorite(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.addFavorite(params);
	}

	@Override
	public boolean cancelFavorite(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.cancelFavorite(param);
	}

	@Override
	public Map<String, Object> getFavoriteList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getFavoriteList(param);
	}

	@Override
	public String addContact(Map<String, String> param) {
		// TODO Auto-generated method stub
		return super.addContact(param);
	}

	/**
	 * 获取联系人列表 - 去掉OTHER类型
	 */
	@Override
	public List<MobileReceiver> getContact(Map<String, Object> param) {
		List<UsrReceivers>   list = receiverUserService.loadReceiversByPageConfig(0L, 100, param.get("userNo").toString(),Constant.RECEIVERS_TYPE.CONTACT.name());
		List<MobileReceiver> receiverList = new ArrayList<MobileReceiver>();
		for (UsrReceivers usrReceivers : list) {
			MobileReceiver mr = new MobileReceiver();
			// 过滤掉儿童
			if(Constant.CERT_TYPE.ERTONG.name().equals(usrReceivers.getCardType()) || Constant.CERT_TYPE.OTHER.name().equals(usrReceivers.getCardType())) {
				continue;
			}
			//
			mr.setReceiverName(usrReceivers.getReceiverName());
			mr.setGender(usrReceivers.getGender());
			mr.setMobileNumber(usrReceivers.getMobileNumber());
			mr.setReceiverId(usrReceivers.getReceiverId());
			mr.setCertNo(usrReceivers.getCardNum());
			mr.setCertType(usrReceivers.getCardType());
			mr.setBirthday(usrReceivers.getZhBrithday());
			/*if(Constant.CERT_TYPE.ERTONG.name().equals(usrReceivers.getCardType())){
				mr.setCertNo(DateUtil.formatDate(usrReceivers.getBrithday(), "yyyy-MM-dd"));
			}*/
			receiverList.add(mr);
		}
		return receiverList;
	}

	@Override
	public List<MobileOrderCmt> queryCommentWaitForOrder(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.queryCommentWaitForOrder(param);
	}

	@Override
	public Map<String, Object> queryCommentForOrder(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.queryCommentForOrder(param);
	}

	@Override
	public List<DicCommentLatitude> getCommentLatitudeInfos(
			Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return super.getCommentLatitudeInfos(param);
	}

	@Override
	public String commitComment(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return super.commitComment(param);
	}

	@Override
	public Float getSumYouHuiAmount(List<OrdOrderAmountItem> listAmountItem,
			float earlyCouponAmount, float moreCouponAmount) {
		// TODO Auto-generated method stub
		return super.getSumYouHuiAmount(listAmountItem, earlyCouponAmount,
				moreCouponAmount);
	}
  
}
