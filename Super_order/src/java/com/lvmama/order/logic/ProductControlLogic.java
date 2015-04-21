package com.lvmama.order.logic;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.meta.MetaProductControl;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaTime;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.vo.Constant;
import com.lvmama.prd.dao.MetaProductControlDAO;
import com.lvmama.prd.dao.MetaProductDAO;

public class ProductControlLogic {
	
	static Log LOG = LogFactory.getLog(ProductControlLogic.class);

	private MetaProductControlDAO metaProductControlDAO;
	
	private MetaProductDAO metaProductDAO;
	
	public void minusStock(MetaProductBranch metaProductBranch,
			OrdOrderItemProd itemProd, OrdOrderItemMeta ordOrderItemMeta) {
		LOG.info("minus control Stock");
		ordOrderItemMeta.setMetaProduct(metaProductDAO.getMetaProductByPk(ordOrderItemMeta.getMetaProductId()));
		if (metaProductBranch.isTotalDecrease()) {
			LOG.info("minus control Stock TotalDecrease true");
			minusTotalStock(metaProductBranch, itemProd, ordOrderItemMeta);
		} else {
			LOG.info("minus control Stock TotalDecrease false");
			minusSpecDateStock(metaProductBranch, itemProd, ordOrderItemMeta);
		}
	}

	public void restoreStock(MetaProductBranch metaProductBranch, OrdOrderItemProd itemProd,
			OrdOrderItemMeta itemMeta, TimePrice timePrice) {
		itemMeta.setMetaProduct(metaProductDAO.getMetaProductByPk(itemMeta.getMetaProductId()));
		if (metaProductBranch.isTotalDecrease()) {
			LOG.info("restore control Stock TotalDecrease true");
			restoreTotalStock(itemProd, itemMeta);
		} else {
			LOG.info("restore control Stock TotalDecrease false");
			restoreSpecDateStock(itemProd, itemMeta, timePrice);
		}
	}

	private void restoreSpecDateStock(OrdOrderItemProd itemProd,
			OrdOrderItemMeta itemMeta, TimePrice timePrice) {
		if (Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(itemProd.getSubProductType())) { //对酒店单房型的总量递减计算消耗的库存
			List<OrdOrderItemMetaTime> list = itemMeta.getAllOrdOrderItemMetaTime();
			LOG.info("restore control Stock SINGLE_ROOM true");
			for (OrdOrderItemMetaTime ordOrderItemMetaTime : list) {
				LOG.info("restore control Stock SINGLE_ROOM true ordOrderItemMetaTime btQuantity = " + ordOrderItemMetaTime.getBuyOutQuantity());
				updateRestoreControl(ordOrderItemMetaTime.getVisitTime(),
						itemMeta.getMetaProductId(), itemMeta.getMetaBranchId(),
						itemMeta.getOrderId(), ordOrderItemMetaTime.getBuyOutQuantity(),
						itemMeta.getMetaProduct().getControlType());
				updateOrderItemMetaTime(0L, ordOrderItemMetaTime.getItemMetaTimeId());
							
			}
		} else {
			LOG.info("restore control Stock TotalDecrease true SINGLE_ROOM false");
			restoreTotalStock(itemProd, itemMeta);
		}
	}

	private void restoreTotalStock(OrdOrderItemProd itemProd,
			OrdOrderItemMeta itemMeta) {
		Long buyOutQuantity = 0L;
		
		if (Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(itemProd.getSubProductType())) { //对酒店单房型的总量递减计算消耗的库存
			LOG.info("restore control stock totalstock true SINGLE_ROOM true");
			List<OrdOrderItemMetaTime> list = itemMeta.getAllOrdOrderItemMetaTime();
			for (OrdOrderItemMetaTime ordOrderItemMetaTime : list) {
				updateOrderItemMetaTime(0L, ordOrderItemMetaTime.getItemMetaTimeId());
				buyOutQuantity = ordOrderItemMetaTime.getBuyOutQuantity();
				LOG.info("restore control Stock buyOutQuantity = " + buyOutQuantity);
				if (buyOutQuantity > 0) {
					updateRestoreControl(ordOrderItemMetaTime.getVisitTime(), itemMeta.getMetaProductId(),
							itemMeta.getMetaBranchId(), itemMeta.getOrderId(), buyOutQuantity,
							itemMeta.getMetaProduct().getControlType());
				}
			}
		} else {
			buyOutQuantity = itemMeta.getBuyOutQuantity();
			updateOrderItemMeta(0L, itemMeta.getOrderItemMetaId());
			LOG.info("restore control Stock buyOutQuantity = " + buyOutQuantity);
			if (buyOutQuantity > 0) {
				updateRestoreControl(itemProd.getVisitTime(), itemMeta.getMetaProductId(),
						itemMeta.getMetaBranchId(), itemMeta.getOrderId(), buyOutQuantity,
						itemMeta.getMetaProduct().getControlType());
			}
		}
		
	}

	private void minusTotalStock(MetaProductBranch metaProductBranch,
			OrdOrderItemProd itemProd, OrdOrderItemMeta itemMeta) {
		if (itemMeta.getMetaProduct().getControlType() == null) {//无预控
			return;
		}
		Long decreaseStock = 0L;
		Long buyOutQuantity = 0L;
		if (StringUtils.equals(itemProd.getSubProductType(),
				Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name())) { // 对酒店单房型的总量递减计算消耗的库存
			LOG.info("minus control Stock TotalDecrease true SINGLE_ROOM true");
			List<OrdOrderItemMetaTime> list = itemMeta.getAllOrdOrderItemMetaTime();
			for (OrdOrderItemMetaTime ordOrderItemMetaTime : list) {
				decreaseStock = ordOrderItemMetaTime.getQuatity() * itemMeta.getProductQuantity();
				buyOutQuantity = updateControl(ordOrderItemMetaTime.getVisitTime(),
						itemMeta.getMetaProductId(),
						metaProductBranch.getMetaBranchId(),
						itemMeta.getMetaProduct().getControlType(), decreaseStock);
				updateOrderItemMetaTime(buyOutQuantity, ordOrderItemMetaTime.getItemMetaTimeId());
			}
			
		} else {
			LOG.info("minus control Stock TotalDecrease true SINGLE_ROOM false");
			decreaseStock = itemProd.getQuantity()
					* itemMeta.getProductQuantity();
			if (decreaseStock > 0) {
				buyOutQuantity = updateControl(itemProd.getVisitTime(),
						itemMeta.getMetaProductId(),
						metaProductBranch.getMetaBranchId(),
						itemMeta.getMetaProduct().getControlType(), decreaseStock);
				if (buyOutQuantity > 0) {
					updateOrderItemMeta(buyOutQuantity, itemMeta.getOrderItemMetaId());
				}
			}
		}
	}

	private void minusSpecDateStock(MetaProductBranch metaProductBranch,
			OrdOrderItemProd itemProd, OrdOrderItemMeta itemMeta) {
		Long decreaseStock = 0L;
		Long buyOutQuantity = 0L;
		//库存 -1代表不限库存，0代表库存已卖完
		if (StringUtils.equalsIgnoreCase(itemProd.getSubProductType(),Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name())) { 
			//酒店单房型,
			LOG.info("minus control Stock TotalDecrease false SINGLE_ROOM true");
			List<OrdOrderItemMetaTime> list = itemMeta.getAllOrdOrderItemMetaTime();
			for (OrdOrderItemMetaTime ordOrderItemMetaTime : list) {
				decreaseStock = ordOrderItemMetaTime.getQuatity() * itemMeta.getProductQuantity();
				buyOutQuantity = updateControl(ordOrderItemMetaTime.getVisitTime(),
						itemMeta.getMetaProductId(),
						metaProductBranch.getMetaBranchId(),
						itemMeta.getMetaProduct().getControlType(), decreaseStock);
				updateOrderItemMetaTime(buyOutQuantity, ordOrderItemMetaTime.getItemMetaTimeId());
			}
		} else {
			LOG.info("minus control Stock TotalDecrease false SINGLE_ROOM false");
			decreaseStock = itemProd.getQuantity() * itemMeta.getProductQuantity();
			buyOutQuantity = updateControl(itemProd.getVisitTime(),
					itemMeta.getMetaProductId(),
					metaProductBranch.getMetaBranchId(),
					itemMeta.getMetaProduct().getControlType(), decreaseStock);
			if (buyOutQuantity > 0) {
				updateOrderItemMeta(buyOutQuantity, itemMeta.getOrderItemMetaId());
			}
		}
	}
	
	private Long updateControl(Date visiteTime, Long productId, Long branchId, String controlType, Long decreaseStock) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("visiteTime", visiteTime);
		condition.put("productId", productId);
		if (Constant.PRODUCT_CONTROL_TYPE.BRANCH_LEVEL.name().equals(controlType)) {
			condition.put("branchId", branchId);
		}
		MetaProductControl control = this.metaProductControlDAO.findByVisitTimeCondition(condition);
		if (control == null) {
			return 0L;
		}
		Long buyOutQuantity = decreaseStock;
		if (control.getControlQuantity() - control.getSaleQuantity() - decreaseStock >= 0) {
			control.setSaleQuantity(control.getSaleQuantity() + decreaseStock);
		} else {
			buyOutQuantity = control.getControlQuantity() - control.getSaleQuantity();
			control.setSaleQuantity(control.getControlQuantity());
		}
		if (Constant.PRODUCT_CONTROL_TYPE.PRODUCT_LEVEL.name().equals(controlType)) {
			String ext = control.getExt();
			if (ext == null) {
				Map<String, Long> map = new HashMap<String, Long>();
				map.put(branchId + "", buyOutQuantity);
				ext = JSONObject.fromObject(map).toString();
			} else {
				Map<String, Long> map = getExtMap(ext);
				Long qt = map.get(branchId + "");
				if (qt == null) {
					map.put(branchId + "", buyOutQuantity);
				} else {
					map.put(branchId + "", buyOutQuantity + qt);
				}
				ext = JSONObject.fromObject(map).toString();
			}
			control.setExt(ext);
		}
		this.metaProductControlDAO.updateSaleQuantity(control);
		return buyOutQuantity;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Long> getExtMap(String ext) {
		JSONObject json = JSONObject.fromObject(ext);
		Iterator<String> iterator = json.keys(); 
		Map<String, Long> map = new HashMap<String, Long>();
		while (iterator.hasNext()) { 
            String key = iterator.next(); 
            String value = json.getString(key); 
            map.put(key, Long.parseLong(value)); 
        }
		return map;
	}
	
	private void updateRestoreControl(Date visitTime, Long productId,
								Long branchId, Long orderId, Long buyOutQuantity, String controlType) {
		LOG.info("updateRestoreControl buyOutQuantity = " + buyOutQuantity);
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("visiteTime", visitTime);
		condition.put("productId", productId);
		if (Constant.PRODUCT_CONTROL_TYPE.BRANCH_LEVEL.name().equals(controlType)) {
			condition.put("branchId", branchId);
		}
		MetaProductControl control = this.metaProductControlDAO.findByVisitTimeCondition(condition);
		if (control == null) {
			LOG.info("Can not find productControl info when restoring order [" +
					orderId + "] and product branch [" + branchId + "]");
			return;
		}
		Long rQt = buyOutQuantity;
		control.setSaleQuantity(control.getSaleQuantity() - buyOutQuantity);
		if (Constant.PRODUCT_CONTROL_TYPE.PRODUCT_LEVEL.name().equals(controlType)) {
			String ext = control.getExt();
			if (ext == null) {
				LOG.info("Can not find productControl ext info when restoring order [" +
						orderId + "] and product branch [" + branchId + "]");
			} else {
				Map<String, Long> map = getExtMap(ext);
				Long qt = map.get(branchId + "");
				if (qt == null) {
					LOG.info("Can not find productControl ext info when restoring order [" +
							orderId + "] and product branch [" + branchId + "]");
				} else {
					if (qt - rQt >= 0) {
						map.put(branchId + "",  qt - rQt);
					} else {
						map.put(branchId + "",  0L);
						LOG.info("EXT ControlQuantity is not enough when restoring order [" +
								orderId + "] and product branch [" + branchId + "]");
					}
				}
				ext = JSONObject.fromObject(map).toString();
			}
			control.setExt(ext);
		}
		LOG.info("Update product control saleQuantity = " + control.getSaleQuantity());
		this.metaProductControlDAO.updateSaleQuantity(control);
	}
	
	private void updateOrderItemMeta(Long buyOutQuantity, Long orderItemMetaId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("buyOutQuantity", buyOutQuantity);
		param.put("orderItemMetaId", orderItemMetaId);
		this.metaProductControlDAO.updateOrderItemMetaBtQuantity(param);
	}
	
	private void updateOrderItemMetaTime(Long buyOutQuantity, BigDecimal orderItemMetaTimeId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("buyOutQuantity", buyOutQuantity);
		param.put("orderItemMetaTimeId", orderItemMetaTimeId);
		this.metaProductControlDAO.updateOrderItemMetaTimeBtQuantity(param);
	}
	
	public void setMetaProductControlDAO(MetaProductControlDAO metaProductControlDAO) {
		this.metaProductControlDAO = metaProductControlDAO;
	}

	public void setMetaProductDAO(MetaProductDAO metaProductDAO) {
		this.metaProductDAO = metaProductDAO;
	}
}
