package com.lvmama.pet.web.shop.shopOrder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listitem;

import com.lvmama.comm.pet.po.shop.ShopOrder;
import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.service.shop.ShopOrderService;
import com.lvmama.comm.pet.service.shop.ShopProductService;
import com.lvmama.comm.pet.service.shop.ShopUserService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.utils.ZkMessage;
import com.lvmama.pet.utils.ZkMsgCallBack;

/**
 * 积分商城订单的管理
 * @author ganyingwen
 *
 */
public class ShopOrderManagerAction extends com.lvmama.pet.web.BaseAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 12316354651L;
	/**
	 * 日志记录器
	 */
	private static final Log LOG = LogFactory.getLog(ShopOrderManagerAction.class);
	/**
	 * 订单服务
	 */
	private ShopOrderService shopOrderService;
	/**
	 * 用户逻辑接口
	 */
	private ShopUserService shopUserService;
	/**
	 * 短信服务
	 */
	private SmsRemoteService smsRemoteService;
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
	/**
	 * 产品管理的逻辑层
	 */
	private ShopProductService shopProductService;

	/**
	 * 查询订单
	 */
	public void search() {
		shopOrderList.clear();
		Long totalRowCount = shopOrderService.orderCount(parameters);
		initialPageInfoByMap(totalRowCount, parameters);
		shopOrderList = shopOrderService.queryShopOrder(parameters);
		if (shopOrderList.size() > 0) {
			isDisabled = false;
		} else {
			isDisabled = true;
		}
	}

	/**
	 * 操作订单
	 * @param parames 参数
	 */
	public void operationOrder(final Map<String, Object> parames) {
		String operationType = parames.get("operationType").toString();
		if ("ORDER_DELIVERY".equals(operationType)) {
			Long orderId = (Long) parames.get("orderId");
			doDelivery(orderId);
		} else if ("ORDER_CANCEL".equals(operationType)) {
			Long orderId = (Long) parames.get("orderId");
			doCancel(orderId);
		}
	}

	/**
	 * 订单发货
	 * @param orderId 订单ID
	 */
	private void doDelivery(final Long orderId) {
		String currentUser = getSessionUserName();
		if(StringUtils.isEmpty(currentUser)){
			ZkMessage.showInfo("请先登陆.");
			return;
		}
		
		ZkMessage.showQuestion("确定发货吗?", new ZkMsgCallBack() {
			public void execute() {
				ShopOrder shopOrder1 = getShopOrderById(orderId);
				if (shopOrder1 != null) {
					shopOrder1.setOrderStatus(Constant.ORDER_STATUS.FINISHED.name());
					int count = shopOrderService.updata(shopOrder1);
					if (count == 0) {
						alert("发货失败");
						LOG.info("订单号 " + orderId + "发货失败");
					}
					LOG.info("订单号 " + orderId + "发货成功");
				}
				if (Constant.SHOP_PRODUCT_TYPE.PRODUCT.name().equals(shopOrder1.getProductType())) {
					sendSmsOnDelivery(shopOrder1);
				} 
				refreshComponent("search");
			}
		}, new ZkMsgCallBack() {
			public void execute() {
			}
		});
	}

	/**
	 * 订单取消
	 * @param orderId 订单ID
	 */
	private void doCancel(final Long orderId) {
		String currentUser = getSessionUserName();
		if(StringUtils.isEmpty(currentUser)){
			ZkMessage.showInfo("请先登陆.");
			return;
		}
		
		ZkMessage.showQuestion("确定取消吗?", new ZkMsgCallBack() {
			public void execute() {
				ShopOrder shopOrder1 = getShopOrderById(orderId);
				if (shopOrder1 != null) {
					shopOrder1.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
					int count = shopOrderService.updata(shopOrder1);
					if (count == 0) {
						LOG.info("订单号 " + orderId + "取消失败");
						alert("取消失败");
						return;
					}
					LOG.info("订单号 " + orderId + "取消成功");
					returnProductStock(shopOrder1);
					refreshComponent("search");
				}
			}
		}, new ZkMsgCallBack() {
			public void execute() {
			}
		});
	}

	/**
	 * 订单批量发货
	 * @param set 复选集合
	 */
	public void batchDelivery(final Set<Listitem> set) {
		if (set.size() == 0) {
			alert("请至少选择一个订单项！");
			return;
		}

		String currentUser = getSessionUserName();
		if(StringUtils.isEmpty(currentUser)){
			ZkMessage.showInfo("请先登陆.");
			return;
		}
		
		StringBuffer errorStr = new StringBuffer();
		for (ShopOrder shopOrder1 : getSelectItemList(set)) {
			shopOrder1.setOrderStatus(Constant.ORDER_STATUS.FINISHED.name());
			int count = shopOrderService.updata(shopOrder1);
			if (count == 0) {
				errorStr.append(shopOrder.getOrderId() + "，");
				LOG.info("订单号为 " + shopOrder1.getOrderId() + "发货失败");
				continue;
			}
			LOG.info("订单号为 " + shopOrder1.getOrderId() + "发货成功");
			
			if (Constant.SHOP_PRODUCT_TYPE.PRODUCT.name().equals(shopOrder1.getProductType())) {
				sendSmsOnDelivery(shopOrder1);
			} 
		}
		
		if (errorStr.length() > 0) {
			alert("订单号为" + errorStr + "发货失败");
		}
	}

	/**
	 * 订单批量取消
	 * @param set 复选集合
	 */
	public void batchCancel(final Set<Listitem> set) {
		if (set.size() == 0) {
			alert("请至少选择一个订单项！");
			return;
		}

		String currentUser = getSessionUserName();
		if(StringUtils.isEmpty(currentUser)){
			ZkMessage.showInfo("请先登陆.");
			return;
		}
		
		StringBuffer errorStr = new StringBuffer();
		for (ShopOrder shopOrder1 : getSelectItemList(set)) {
			shopOrder1.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
			int count = shopOrderService.updata(shopOrder1);
			if (count == 0) {
				errorStr.append(shopOrder1.getOrderId() + "，");
				LOG.info("订单号为 " + shopOrder1.getOrderId() + "取消失败");
				continue;
			}
			LOG.info("订单号 " + shopOrder1.getOrderId() + "取消成功");
			returnProductStock(shopOrder1);
		}
		if (errorStr.length() > 0) {
			alert("订单号" + errorStr + "取消失败");
		}
	}

	/**
	 * 数据导出
	 * @throws Exception Exception
	 */
	 public void doExport() throws Exception {
		Map<String, Object> parameTemp = parameters;
		parameTemp.remove("_startRow");
		parameTemp.remove("_endRow");
		List<ShopOrder> orderList = shopOrderService.queryShopOrder(parameTemp);
		doExcel(orderList);
	}
	 
	public void doExcel(List excelList) throws Exception {
		try {
			File templateResource = ResourceUtil.getResourceFile("/WEB-INF/resources/template/ShopOrderTemplate.xls");
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName = Constant.getTempDir() + "/excel.xls";

			Map beans = new HashMap();
			beans.put("excelList", excelList);
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(templateFileName, beans, destFileName);

			File file = new File(destFileName);
			if (file != null && file.exists()) {
				Filedownload.save(file, "application/vnd.ms-excel");
			} else {
				alert("下载失败");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 /**
	  * 增加根据产品类型查询订单
	  * @param productType 产品类型
	  */
	 public void addSearchByProductType(final String productType) {
		 parameters.put("productType", productType);
		 search();
	 }

	 /**
	  * 增加根据订单状态查询订单
	  * @param orderStatus 订单状态
	  */
	 public void addSearchByOrderStatus(final String orderStatus) {
		 parameters.put("orderStatus", orderStatus);
		 search();
	 }

	@Override
	@SuppressWarnings("rawtypes")
	public void showWindow(final String uri, final Map params) throws Exception {
		shopOrder = (ShopOrder) params.get("shopOrder");
		super.showWindow(uri, params);
	}

	/**
	 * 根据订单ID查找订单
	 * @param orderId 订单ID
	 * @return 订单
	 */
	private ShopOrder getShopOrderById(final Long orderId) {
		if (orderId == null || orderId == 0L) {
			alert("订单号错误");
			return null;
		}
		ShopOrder shopOrder1 = shopOrderService.queryShopOrderByKey(orderId);
		if (shopOrder1 == null) {
			alert("没有相应订单");
			return null;
		}
		return shopOrder1;
	}

	/**
	 * 获取复选集合订单ID
	 * @param set 复选集合
	 * @return 复选的订单集合
	 */
	private List<ShopOrder> getSelectItemList(final Set<Listitem> set) {
		List<ShopOrder> list = new ArrayList<ShopOrder>();
		if (set != null && set.size() > 0) {
			for (Iterator<Listitem> iter = set.iterator(); iter.hasNext();) {
				Listitem listitem = (Listitem) iter.next();
				ShopOrder order = (ShopOrder) listitem.getValue();
				String orderStatus = order.getOrderStatus();
				if (Constant.ORDER_STATUS.CANCEL.name().equals(orderStatus)) {
					alert("订单号" + order.getOrderId() + "已取消，批量操作失败");
					return new ArrayList<ShopOrder>();
				}
				if (Constant.ORDER_STATUS.FINISHED.name().equals(orderStatus)) {
					alert("订单号" + order.getOrderId() + "已发货，批量操作失败");
					return new ArrayList<ShopOrder>();
				}
				list.add(order);
			}
		}
		return list;
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



}