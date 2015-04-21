package com.lvmama.eplace.web.supplier;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.zkoss.zul.Window;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.po.pass.PassPortInfo;
import com.lvmama.comm.bee.po.pass.UserRelateSupplierProduct;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.passport.utils.ZKUtils;
import com.lvmama.passport.utils.ZkMessage;

/**
 * 通关信息查询
 * 
 * @author chenlinjun
 * 
 */
public class CheckPassPortAction extends ZkBaseAction {
	private static final long serialVersionUID = 4589584124336568092L;
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private String codeNo;
	private String addCode;
	private List<UserRelateSupplierProduct> userRelateSupplierProducts;
	private EPlaceService eplaceService;
	private boolean isDimension=false;
	private boolean isShow=true;
	private PassCodeService  passCodeService;
	@Override
	protected void doBefore() throws Exception {
		long userId = this.getSessionUser().getPassPortUserId();
		userRelateSupplierProducts = eplaceService.getMeatProductBySupplierUserId(userId);
		UserRelateSupplierProduct userRelateSupplierProduct = eplaceService.getSupplierUserForTargetId(userId);
		if (userRelateSupplierProduct!=null&&Constant.CCERT_TYPE.DIMENSION.name().equals(
				userRelateSupplierProduct.getSupPerformTarget().getCertificateType())) {
			isDimension = true;
			isShow=false;
		}
	}

	/**
	 * 通关验证
	 * 
	 * @param win
	 * @throws Exception
	 */
	public void doCheck(Window win) throws Exception {
		boolean flag = false;
		OrdOrder OrdOrder = null;
		Long orderId = 0l;
		PassPortInfo passPortInfo = new PassPortInfo();
		Long userId = this.getSessionUser().getPassPortUserId();
		UserRelateSupplierProduct userRelateSupplierProduct = eplaceService.getSupplierUserForTargetId(userId);
		Long targetId = userRelateSupplierProduct.getSupPerformTarget().getTargetId();
		if (Constant.CCERT_TYPE.DIMENSION.name().equals(
				userRelateSupplierProduct.getSupPerformTarget().getCertificateType())) {
			flag = true;
		}
		if (flag) {
			// 二维码
			addCode = this.codeNo;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("addCode", this.addCode);
			PassCode passCode = this.passCodeService.getPassCodeByParams(params);
			if (passCode != null) {
				Long codeId = passCode.getCodeId();
				String msg =   this.valid(codeId, targetId);
				if (msg == null) {
					String serialNo = passCode.getSerialNo();
					OrdOrder = orderServiceProxy.queryOrdOrderBySerialNo(serialNo);
					if (OrdOrder != null || !OrdOrder.isCanceled()){
						passPortInfo = this.getPassPortInfo(targetId, OrdOrder);
						passPortInfo.setOrderId(OrdOrder.getOrderId());
						passPortInfo.setMobile(OrdOrder.getContact().getMobile());
						passPortInfo.setName(OrdOrder.getContact().getName());
						passPortInfo.setTargetId(targetId);
						passPortInfo.setSerialNo(serialNo);
						passPortInfo.setCodeId(codeId);
						if (OrdOrder.isPayToLvmama()) {
							passPortInfo.setPayChannel("支付给驴妈妈");
							passPortInfo.setPayToSupplier(false);
						} else {
							passPortInfo.setPayToSupplier(true);
							passPortInfo.setPayChannel("支付给供应商");
						}
						this.showPassPort(win, passPortInfo);
					} else {
						ZkMessage.showWarning("订单:" + this.codeNo + "不存在或者已经被取消");
					}
				} else {
					ZkMessage.showWarning(msg);
				}
			} else {
				ZkMessage.showWarning("此凭证不存在");
			}
		} else {
			try{
				orderId = Long.valueOf(this.codeNo);
				CompositeQuery compositeQuery = new CompositeQuery();
				compositeQuery.getMetaPerformRelate().setTargetId(String.valueOf(targetId));
				compositeQuery.getMetaPerformRelate().setOrderId(orderId);
				List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
				int size=orderItemMetas.size();
				Map<String,Object> params=new HashMap<String,Object>();
				if(size>1){
					params.put("objectId", orderId);
					params.put("objectType","ORD_ORDER");
				}else{
					Long orderItemId=orderItemMetas.get(0).getOrderItemMetaId();
					params.put("objectId", orderItemId);
					params.put("objectType","ORD_ORDER_ITEM_META");
				}
				boolean isPerform=passCodeService.hasPassCodePerform(params);
				if(isPerform){
					ZkMessage.showWarning("订单:" + this.codeNo + "已经通关过,不能重复通关");
					return;
				} 
			}catch(Exception e){
				e.printStackTrace();
				ZkMessage.showWarning("订单:" + this.codeNo + "不存在或者已经被取消");
				return;
			}
			OrdOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			if (OrdOrder != null && !OrdOrder.isCanceled()) {
				passPortInfo = this.getPassPortInfo(targetId, OrdOrder);
				passPortInfo.setOrderId(OrdOrder.getOrderId());
				passPortInfo.setMobile(OrdOrder.getContact().getMobile());
				passPortInfo.setName(OrdOrder.getContact().getName());
				passPortInfo.setTargetId(targetId);
				if (OrdOrder.isPayToLvmama()) {
					passPortInfo.setPayChannel("支付给驴妈妈");
					passPortInfo.setPayToSupplier(false);
				} else {
					passPortInfo.setPayChannel("支付给供应商");
					passPortInfo.setPayToSupplier(true);
				}
				this.showPassPort(win, passPortInfo);
			} else {
				ZkMessage.showWarning("订单:" + this.codeNo + "不存在或者已经被取消");
			}
		}
	}

	private void showPassPort(Window win, PassPortInfo passPortInfo) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("passPortInfo", passPortInfo);
		String url = "/eplace/supplier/passport_info.zul";
		ZKUtils.showWindow(win, url, params);
	}

	/**
	 * 二维码验证
	 * 
	 * @param codeId
	 * @param targetId
	 * @return
	 */
	private String valid(Long codeId, Long targetId) {
		PassPortCode passPortCode = passCodeService.getPassPortCodeByCodeIdAndPortId(codeId, targetId);
		String status = "0";
		String msg = null;
		if (passPortCode != null) {
			long toDay = DateUtil.toDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd").getTime();
			long validTime = passPortCode.getValidTime().getTime();
			long invalidTime = passPortCode.getInvalidTime().getTime();
			boolean isValid = (toDay >= validTime && toDay <= invalidTime) ? true : false;
			boolean validateInvalidDate = passPortCode.validateInvalidDate();
			status = passPortCode.getStatus().trim();
			// 码没有被使用
			if (Constant.PASSCODE_USE_STATUS.UNUSED.name().equals(status) && isValid && validateInvalidDate) {
				msg = null;
			} else if (Constant.PASSCODE_USE_STATUS.DESTROYED.name().equals(status)) {
				msg = "凭证已经作废";
			} else if (!isValid) {
				if (toDay < validTime) {
					//msg = "凭证还未到游玩日期";
				} else {
					msg = "凭证已经过期";
				}
			} else if(!validateInvalidDate) {
				msg = "今日不可游玩";
			} else if (Constant.PASSCODE_USE_STATUS.USED.name().equals(status)) {

				msg = "凭证在此景点已经使用过，不能重复使用";
			}
		} else {
			msg = "凭证在不能在此景点使用";
		}
		return msg;
	}

	/**
	 * 组织通关信息
	 * 
	 * @param targetId
	 * @return
	 */
	private PassPortInfo getPassPortInfo(Long targetId, OrdOrder OrdOrder) {
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMetaPerformRelate().setTargetId(String.valueOf(targetId));
		compositeQuery.getMetaPerformRelate().setOrderId(OrdOrder.getOrderId());
		compositeQuery.getPageIndex().setBeginIndex(0);
		compositeQuery.getPageIndex().setEndIndex(1000000000);
		List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy
				.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
		long adult = 0;
		long child = 0;
//		int size = orderItemMetas.size();
//		if (size > 1) {
			for (int j = 0; j < orderItemMetas.size(); j++) {
				OrdOrderItemMeta itemMeta = orderItemMetas.get(j);
				// 成人数
				adult = adult + itemMeta.getTotalAdultQuantity();
				// 儿童数
				child = child + itemMeta.getTotalChildQuantity();
			}
//		} else {
//			OrdOrderItemMeta itemMeta = orderItemMetas.get(0);
//			// 成人数
//			adult = adult + itemMeta.getTotalAdultQuantity();
//			// 儿童数
//			child = child + itemMeta.getTotalChildQuantity();
//		}
		PassPortInfo passPortInfo = new PassPortInfo();
		passPortInfo.setChild(child);
		passPortInfo.setAdult(adult);
		passPortInfo.setTotalMan(child + adult);
		if (OrdOrder.isPayToLvmama()) {
			passPortInfo.setPrice(0);
			passPortInfo.setPriceYuan("-");
		} else {
			passPortInfo.setPrice(OrdOrder.getOughtPayYuan());
			passPortInfo.setPriceYuan(String.valueOf(OrdOrder.getOughtPayYuan()));
		}
		return passPortInfo;
	}

	public String getAddCode() {
		return addCode;
	}

	public void setAddCode(String addCode) {
		this.addCode = addCode;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public List<UserRelateSupplierProduct> getUserRelateSupplierProducts() {
		return userRelateSupplierProducts;
	}

	public void setUserRelateSupplierProducts(List<UserRelateSupplierProduct> userRelateSupplierProducts) {
		this.userRelateSupplierProducts = userRelateSupplierProducts;
	}

 
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setEplaceService(EPlaceService eplaceService) {
		this.eplaceService = eplaceService;
	}

	public boolean isDimension() {
		return isDimension;
	}

	public void setDimension(boolean isDimension) {
		this.isDimension = isDimension;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

}
