package com.lvmama.pet.sweb.shop.shopOrder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.shop.ShopOrder;
import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.shop.ShopOrderService;
import com.lvmama.comm.pet.service.shop.ShopProductService;
import com.lvmama.comm.pet.service.shop.ShopUserService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.pet.vo.ShopOrderVO;
import com.lvmama.comm.utils.CommentUtil;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;

public class ShopOrderManagerAction extends BackBaseAction {
 
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2372114718439796356L;
	/**
	 * 日志记录器
	 */
	private static final Log LOG = LogFactory.getLog(ShopOrderManagerAction.class);
	 
	private ShopOrderService shopOrderService;
	private ShopUserService shopUserService;
	private SmsRemoteService smsRemoteService;
	private ShopProductService shopProductService;
	
	/**
	 * 查询参数
	 */
	private Map<String, Object> parameters = new HashMap<String, Object>();
	/**
	 * 订单列表
	 */
	private List<ShopOrder> shopOrderList = new ArrayList<ShopOrder>();
	/**
	 * 订单明细
	 */
	private ShopOrder shopOrder;
	/**
	 * 组件有效性.true为无效
	 */
	private boolean isDisabled = true;
	
	private String orderId;
	private String orderIds;
	private String startDate;
	private String endDate;
	private String productType;
	private String productName;
	private String userName;
	private String orderStatus;
	private String operationType;
	
	/**
	 * 导出excel的文件名
	 */
	private String excelFileName;
	/**
	 * 导出excel的文件流
	 */
	private InputStream excelStream;
	private UserUserProxy userUserProxy;
	
	private void initParameters(){
		
		if (StringUtils.isNotBlank(startDate)) {
			parameters.put("startDate", DateUtil.toDate(startDate, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(endDate)) {
			parameters.put("endDate", DateUtil.toDate(endDate, "yyyy-MM-dd"));
		}
		if(StringUtils.isNotBlank(orderId))  parameters.put("orderId", orderId);
		if(StringUtils.isNotBlank(productType))  parameters.put("productType", productType);
		if(StringUtils.isNotBlank(productName))  parameters.put("productName", productName);
		if(StringUtils.isNotBlank(userName))  parameters.put("userName", userName);
		if(StringUtils.isNotBlank(orderStatus))  parameters.put("orderStatus", orderStatus);
	}
	
	/**
	 * 查询订单
	 */
	@Action(value="/shop/shopOrder/queryShopOrderList",results={@Result(location = "/WEB-INF/pages/back/shop/shopOrder/index.jsp")})
	public String search() {
		
		initParameters();
		Long totalRowCount = shopOrderService.orderCount(parameters);
		pagination = Page.page(10, page);
		pagination.setTotalResultSize(totalRowCount);
		pagination.buildUrl(getRequest());
		
		parameters.put("_startRow", pagination.getStartRows());
		parameters.put("_endRow", pagination.getEndRows());
		shopOrderList = shopOrderService.queryShopOrder(parameters);
		
		if (shopOrderList.size() > 0) {
			isDisabled = false;
		} else {
			isDisabled = true;
		}
		
		return SUCCESS;
	}
 
	/**
	 * 操作订单
	 * @param parames 参数
	 */
	@Action(value="/shop/shopOrder/operationOrder")
	public void operationOrder() {
		
		Map<String, Object> param = new HashMap<String, Object>();
		String currentUser = getSessionUserName();
		if(StringUtils.isEmpty(currentUser)){
			param.put("success", false);
			param.put("errorMessage", "请先登陆");
			return;
		}else{
			if ("ORDER_DELIVERY".equals(operationType)) {
				param = doDelivery(Long.parseLong(orderId), param);
			} else if ("ORDER_CANCEL".equals(operationType)) {
				param = doCancel(Long.parseLong(orderId), param);
			} 
		}
		
		try {
			getResponse().getWriter().print(JSONObject.fromObject(param));
		} catch (IOException ioe) {
			
		}		
	}
	
	/**
	 * 订单发货
	 * @param orderId 订单ID
	 */
	private Map<String, Object> doDelivery(final Long orderId, Map<String, Object> param) {

		ShopOrder shopOrder1 = getShopOrderById(orderId);
		if (shopOrder1 != null) {
			shopOrder1.setOrderStatus(Constant.ORDER_STATUS.FINISHED.name());
			int count = shopOrderService.updata(shopOrder1);
			if (count == 0) {
				param.put("success", false);
				param.put("errorMessage", "发货失败");			
				LOG.info("订单号 " + orderId + "发货失败");
			}else{
				param.put("success", true);
				param.put("errorMessage", "发货成功");				
				LOG.info("订单号 " + orderId + "发货成功");
				//站内消息
				if(shopOrder1.getUserId()!=null){
					UserUser user=userUserProxy.getUserUserByPk(shopOrder1.getUserId());
					shopLetter("Y","N",shopOrder1,user);
				}
			}
		}else{
			param.put("success", false);
			param.put("errorMessage", "取消失败,没有该订单号" + orderId);
			LOG.info("订单号 " + orderId + "不存在");
		}
		if (Constant.SHOP_PRODUCT_TYPE.PRODUCT.name().equals(shopOrder1.getProductType())) {
			sendSmsOnDelivery(shopOrder1);
		}
		return param;

	}

	/**
	 * 订单取消
	 * @param orderId 订单ID
	 */
	private Map<String, Object> doCancel(final Long orderId, Map<String, Object> param) {

		ShopOrder shopOrder1 = getShopOrderById(orderId);
		if (shopOrder1 != null) {
			shopOrder1.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
			int count = shopOrderService.updata(shopOrder1);
			if (count == 0) {
				LOG.info("订单号 " + orderId + "取消失败");
				param.put("success", false);
				param.put("errorMessage", "取消失败");
			} else {
				param.put("success", true);
				param.put("errorMessage", "取消成功");
				LOG.info("订单号 " + orderId + "取消成功");
				returnProductStock(shopOrder1);
				//站内消息
				if(shopOrder1.getUserId()!=null){
					UserUser user=userUserProxy.getUserUserByPk(shopOrder1.getUserId());
					shopLetter("N","Y",shopOrder1,user);
				}
			}
		}else{
			param.put("success", false);
			param.put("errorMessage", "取消失败,没有该订单号" + orderId);
			LOG.info("订单号 " + orderId + "不存在");
		}
		return param;

	}
	
	/**
	 * 订单批量发货
	 * @param set 复选集合
	 *  */
	@Action(value="/shop/shopOrder/batchDelivery")
	public void batchDelivery() {
		 
		Map<String, Object> param = new HashMap<String, Object>();
		String currentUser = getSessionUserName();
		if(StringUtils.isEmpty(currentUser)){
			param.put("success", false);
			param.put("errorMessage", "请先登陆");	
			return;
		}

		StringBuffer errorStr = new StringBuffer();
		for (ShopOrder shopOrder1 : getSelectItemList(orderIds)) {
			shopOrder1.setOrderStatus(Constant.ORDER_STATUS.FINISHED.name());
			int count = shopOrderService.updata(shopOrder1);
			if (count == 0) {
				errorStr.append(shopOrder.getOrderId() + "，");
				LOG.info("订单号为 " + shopOrder1.getOrderId() + "发货失败");
				continue;
			}else{
				//站内消息
				if(shopOrder1.getUserId()!=null){
					UserUser user=userUserProxy.getUserUserByPk(shopOrder1.getUserId());
					shopLetter("Y","N",shopOrder1,user);
				}
			}
			LOG.info("订单号为 " + shopOrder1.getOrderId() + "发货成功");
			
			if (Constant.SHOP_PRODUCT_TYPE.PRODUCT.name().equals(shopOrder1.getProductType())) {
				sendSmsOnDelivery(shopOrder1);
			} 
		}
		
		if (errorStr.length() > 0) {
			param.put("success", false);
			param.put("errorMessage", "订单号为" + errorStr + "发货失败");	
		}else{
			param.put("success", true);
			param.put("errorMessage", "批量发货成功");
		}
		
		try {
			getResponse().getWriter().print(JSONObject.fromObject(param));
		} catch (IOException ioe) {
			
		}
	}

	/**
	 * 订单批量取消
	 * @param set 复选集合
	 *  */
	@Action(value="/shop/shopOrder/batchCancel")
	public void batchCancel() {
		
		Map<String, Object> param = new HashMap<String, Object>();
		String currentUser = getSessionUserName();
		if(StringUtils.isEmpty(currentUser)){
			param.put("success", false);
			param.put("errorMessage", "请先登陆");	
			return;
		}
		
		StringBuffer errorStr = new StringBuffer();
		for (ShopOrder shopOrder1 : getSelectItemList(orderIds)) {
			shopOrder1.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
			int count = shopOrderService.updata(shopOrder1);
			if (count == 0) {
				errorStr.append(shopOrder1.getOrderId() + "，");
				LOG.info("订单号为 " + shopOrder1.getOrderId() + "取消失败");
				continue;
			}else{
				//站内消息
				if(shopOrder1.getUserId()!=null){
					UserUser user=userUserProxy.getUserUserByPk(shopOrder1.getUserId());
					shopLetter("N","Y",shopOrder1,user);
				}
			}
			LOG.info("订单号 " + shopOrder1.getOrderId() + "取消成功");
			returnProductStock(shopOrder1);
		} 
		
		if (errorStr.length() > 0) {
			param.put("success", false);
			param.put("errorMessage", "订单号为" + errorStr + "取消失败");	
		}else{
			param.put("success", true);
			param.put("errorMessage", "批量取消订单成功");
		}
		
		try {
			getResponse().getWriter().print(JSONObject.fromObject(param));
		} catch (IOException ioe) {
			
		}
	}

	/**
	 * 数据导出
	 * @throws Exception Exception
	 *  */ 
	 @Action(value="/shop/shopOrder/doExport",results={@Result(type="stream",name="success",params={"contentType","application/vnd.ms-excel","inputName","excelStream","contentDisposition","attachment;filename=${excelFileName}","bufferSize","1024"})})
	 public String doExport() throws Exception {
			initParameters();
			if(StringUtils.isNotBlank(userName))  parameters.put("userName", new String(userName.getBytes("ISO8859-1"),"UTF-8"));
			if(StringUtils.isNotBlank(productName))  parameters.put("productName", new String(productName.getBytes("ISO8859-1"),"UTF-8"));
			
			parameters.put("_startRow", 0);
			parameters.put("_endRow", 900);
			List<ShopOrderVO> orderList = shopOrderService.queryShopOrderVO(parameters);
			return doExcel(orderList);
	 }
	 
	public String doExcel(List excelList) throws Exception {
		//Map<String, Object> param = new HashMap<String, Object>();
			
		try {
			File templateResource = ResourceUtil.getResourceFile("/WEB-INF/resources/template/ShopOrderTemplate.xls");
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName = Constant.getTempDir() + "/excel.xls";

			Map<String,Object> beans = new HashMap<String,Object>();
			beans.put("excelList", excelList);
			XLSTransformer transformer = new XLSTransformer();
			try {
				//new XLSTransformer().transformXLS(templateFileName, beans, destFileName);
				transformer.transformXLS(templateFileName, beans, destFileName);
			} catch (ParsePropertyException e) {
				e.printStackTrace();
				return INPUT;
			} catch (InvalidFormatException e) {
				e.printStackTrace();
				return INPUT;
			}
			File file = new File(destFileName);
			
			if (file != null && file.exists()) {				
				 this.excelFileName = file.getName();
				 this.excelStream = new FileInputStream(file);
				 return SUCCESS;
			} else {
				return INPUT;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	 /*
	@Action(value="/mark/coupon/exportExcel",results={@Result(type="stream",name="success",params={"contentType","application/vnd.ms-excel","inputName","excelStream","contentDisposition","attachment;filename=${excelFileName}","bufferSize","1024"})})
	public String exportExcel() throws IOException {
			File templateResource = ResourceUtil.getResourceFile("/WEB-INF/resources/template/couponCodeTemplate.xls");
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName = Constant.getTempDir() + "/couponCodeTemplate_" + System.currentTimeMillis() +".xls";
			Map<String,Object> beans = new HashMap<String,Object>();
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("couponId", this.couponId);
			markCouponCodeList = new ArrayList<MarkCouponCode>();
			Integer allCodeNum = this.markCouponService.selectMarkCouponCodeRowCount(parameters);
			for(int j = 0; j < allCodeNum; j=j+900){//避免一次返回的数据超过1000，被截取
				parameters.put("_startRow", j+1);
				parameters.put("_endRow", j+900);
				List<MarkCouponCode> returnMarkCouponCodeList = this.markCouponService.selectMarkCouponCodeByParam(parameters);
				markCouponCodeList.addAll(returnMarkCouponCodeList);
			}
			MarkCoupon markCoupon = markCouponService.selectMarkCouponByPk(this.couponId);
			for(int i = 0; i < markCouponCodeList.size(); i++){
				MarkCouponCode markCouponCode = markCouponCodeList.get(i);
				markCouponCode.setValidTimeByCouponDefination(markCoupon);
			}
			beans.put("couponCode", markCouponCodeList);
			
			try {
				new XLSTransformer().transformXLS(templateFileName, beans, destFileName);
			} catch (ParsePropertyException e) {
				e.printStackTrace();
				return INPUT;
			} catch (InvalidFormatException e) {
				e.printStackTrace();
				return INPUT;
			}
			 
			File file = new File(destFileName);
			
			if (file != null && file.exists()) {				
				 this.excelFileName = file.getName();
				 this.excelStream = new FileInputStream(file);
				 return SUCCESS;
			} else {
				return INPUT;
			}
	}	*/
	
	
	/**
	 * 获取复选集合订单ID
	 * @param set 复选集合
	 * @return 复选的订单集合
	 */
	private List<ShopOrder> getSelectItemList(String orderIds) {
		List<ShopOrder> list = new ArrayList<ShopOrder>();
		if (StringUtils.isNotBlank(orderIds)) {
			for (String orderId : orderIds.split(",")) {
				ShopOrder order = getShopOrderById(Long.parseLong(orderId));
				String orderStatus = order.getOrderStatus();
				if (Constant.ORDER_STATUS.CANCEL.name().equals(orderStatus)) {
					LOG.info("订单号 " + orderId + "已取消，批量操作失败");
					continue;
				}
				if (Constant.ORDER_STATUS.FINISHED.name().equals(orderStatus)) {
					LOG.info("订单号 " + orderId + "已发货，批量操作失败");
					continue;
				}
				list.add(order);
			}
		}
		return list;
	}
 
	/**
	 * 根据订单ID查找订单
	 * @param orderId 订单ID
	 * @return 订单
	 *  */
	private ShopOrder getShopOrderById(final Long orderId) {
		if (orderId == null || orderId == 0L) {
			LOG.info("订单号: " + orderId + " 错误");
			return null;
		}
		ShopOrder shopOrder1 = shopOrderService.queryShopOrderByKey(orderId);
		if (shopOrder1 == null) {
			LOG.info("订单号: " + orderId + " 没有相应订单");
			return null;
		}
		return shopOrder1;
	}
	
	/**
	 * 订单取消，产品库存恢复
	 * @param shopOrder1 订单
	 */
	private void returnProductStock(final ShopOrder shopOrder1) {
		ShopProduct product = shopProductService.queryByPk(shopOrder1.getProductId());
		if (product == null) {
			LOG.info("产品" + shopOrder1.getProductId() + "不存在，库存恢复失败.");
			return;
		}
		product.setStocks(product.getStocks() + shopOrder1.getQuantity());
		shopProductService.save(product, null);
		LOG.info("产品" + product.getProductId() + "库存恢复成功.");

		if (null != shopOrder1.getUserId() && null != shopOrder1.getActualPay()) {
			shopUserService.reducePoint(shopOrder1.getUserId() , "POINT_FOR_CUSTOMIZED_RETRUN",
					shopOrder1.getActualPay(), String.valueOf((shopOrder1.getOrderId())));
		}
	}

	/**
	 * 订单发货，发送短信
	 * @param shopOrder1 订单
	 */
	private void sendSmsOnDelivery(final ShopOrder shopOrder1) {
		if (!StringUtils.isEmpty(shopOrder1.getMobile())) {
			try {
				smsRemoteService.sendQunFaSms("亲爱的驴妈妈会员，您在积分商城兑换的"
						+ shopOrder1.getProductName()
						+ "物品已于今日快递发货，请您注意查收。服务热线1010-6060",
						shopOrder1.getMobile());
			} catch (Exception e) {
				LOG.debug("发送短信失败");
			}
		}
	}
	
	/**
	 * 发送站内信
	 * @param orderDelivery
	 * @param orderCancel
	 * @param shopOrder1
	 * @param user
	 */
	private void shopLetter(String orderDelivery ,String orderCancel,ShopOrder shopOrder1,UserUser user){
		//订单发送
		if("Y".equals(orderDelivery)){
			String remark="";
			if(shopOrder1.getRemark()!=null){
				remark="，备注信息："+shopOrder1.getRemark();
			}
			String subject ="您的积分商城货物已发出";
			String message ="亲爱的 "+user.getUserName()+"：<br/>"+
							"<p>您在驴妈妈积分商城兑换的< "+shopOrder1.getProductName()+" >已通过快递寄出"  +remark+
							"，请注意查收。</p><br/>";
			String type ="SHOP";
			CommentUtil.synchLetter(subject,message,type,user.getUserNo());
		}
		//订单取消
		if("Y".equals(orderCancel)){
			String remark="";
			if(shopOrder1.getRemark()!=null){
				remark="，备注信息："+shopOrder1.getRemark();
			}
			String subject ="您的积分商城订单已被取消";
			String message ="亲爱的 "+user.getUserName()+"：<br/>"+
							"<p>您的驴妈妈积分商城订单号< "+shopOrder1.getOrderId()+" >已被取消" +remark+
							"。</p><br/>";
			String type ="SHOP";
			CommentUtil.synchLetter(subject,message,type,user.getUserNo());
		}
	}
	
	public void setShopOrderService(final ShopOrderService shopOrderService) {
		this.shopOrderService = shopOrderService;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(final Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public List<ShopOrder> getShopOrderList() {
		return shopOrderList;
	}

	public void setShopOrderList(final List<ShopOrder> shopOrderList) {
		this.shopOrderList = shopOrderList;
	}

	public ShopOrder getShopOrder() {
		return shopOrder;
	}

	public void setShopOrder(final ShopOrder shopOrder) {
		this.shopOrder = shopOrder;
	}
	public ShopOrderService getShopOrderService() {
		return shopOrderService;
	}

	public boolean isDisabled() {
		return isDisabled;
	}

	public void setDisabled(final boolean isDisabled1) {
		this.isDisabled = isDisabled1;
	}

	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}

	public void setShopProductService(ShopProductService shopProductService) {
		this.shopProductService = shopProductService;
	}

	public void setShopUserService(final ShopUserService shopUserService) {
		this.shopUserService = shopUserService;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	

}
