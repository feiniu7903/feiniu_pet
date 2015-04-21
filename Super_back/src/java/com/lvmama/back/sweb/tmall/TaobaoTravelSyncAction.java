package com.lvmama.back.sweb.tmall;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.JavaBeanUtil;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.tmall.TaobaoProductSyncPojo;
import com.lvmama.comm.bee.service.distribution.DistributionProductService;
import com.lvmama.comm.bee.service.tmall.TaobaoProductSyncService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * 淘宝线路
 * 
 * @author linkai
 *
 */
@Results({
	@Result(name = "toTravelPage", location = "/WEB-INF/pages/back/tmall/sync/travel_list.jsp")
})
public class TaobaoTravelSyncAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(TaobaoTravelSyncAction.class);

	private Long productId;
	
	private Long itemId;
	
	private Long prodBranchId;
	
	private String prodBranchIds;
	
	private Long travelComboId;
	
	private String isSync;
	
	private TaobaoProductSyncService taobaoProductSyncService;
	
	private DistributionProductService distributionProductService;
	
	/**
	 * 获取淘宝线路同步数据
	 * 
	 * @return
	 */
	@Action(value = "/tmall/toTravelSyncList")
	public String travelSync() {
		pagination = super.initPagination();
		Map<String, Object> pageMap = request2Map(getRequest());
		
		Integer totalRowCount = taobaoProductSyncService.getTaobaoTravelSyncCount(pageMap);
		pagination.setTotalRecords(totalRowCount);
		pageMap.put("_startRow", pagination.getFirstRow());
		pageMap.put("_endRow", pagination.getLastRow());
		
		List<TaobaoProductSyncPojo> productList = taobaoProductSyncService.getTaobaoTravelSyncList(pageMap);
		
		initListInfo(productList);
		
		pagination.setRecords(productList);
		pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		
		return "toTravelPage";
	}
	
	@Action(value = "/tmall/querySelectTravelCombo")
	public void querySelectTravelCombo() {
		List<ProdProductBranch> ppbList = distributionProductService.getProductBranchByProductId(productId);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (ProdProductBranch prodProductBranch : ppbList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("prodBranchId", prodProductBranch.getProdBranchId());
			map.put("branchName", prodProductBranch.getBranchName());
			list.add(map);
		}
		JavaBeanUtil.response2Json(getResponse(), list);
	}
	
	/**
	 * 更新淘宝线路信息
	 */
	@Action(value = "/tmall/updateTaobaoTravelInfo")
	public void updateTaobaoTravelInfo() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "false");
		try {
            String userName = getSessionUser().getUserName();
			boolean isSuccess = taobaoProductSyncService.updateTaobaoTravelInfo(itemId, userName);
			map.put("success", String.valueOf(isSuccess));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Sync ticket info error!", e);
//			map.put("errInfo", "");
		}
		JavaBeanUtil.response2Json(getResponse(), map);
	}
	
	/**
	 * 更新套餐类别信息
	 */
	@Action(value = "/tmall/saveTravelComboType")
	public void saveTravelComboType() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "false");
		try {
			int num = taobaoProductSyncService.updateTravelComboType(travelComboId, prodBranchIds);
			if (num < 1) {
				map.put("error", "产品类别ID有重复！");
			} else {
				map.put("success", "true");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Select TravelCombo error! travelComboId=" + travelComboId, e);
			map.put("error", "系统异常！");
		}
		JavaBeanUtil.response2Json(getResponse(), map);
	}
	
	/**
	 * 更新线路的同步状态
	 */
	@Action(value = "/tmall/updateTravelIsSync")
	public void updateTravelIsSync() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "false");
		if (travelComboId != null && isSync != null) {
			try {
				String userName = getSessionUser().getUserName();
				taobaoProductSyncService.updateTravelIsSync(travelComboId, isSync, itemId, userName);
				map.put("success", "true");
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Update travel sync error! travelComboId=" + travelComboId, e);
			}
		}
		JavaBeanUtil.response2Json(getResponse(), map);
	}
	
	/**
	 * 更新线路套餐时间价格表
	 */
	@Action(value = "/tmall/updateTravelCombo")
	public void updateTravelCombo() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "false");
		try {
            String userName = getSessionUser().getUserName();
			boolean b = taobaoProductSyncService.updateTaobaoTravelComboCalendar(travelComboId, userName);
			map.put("success", String.valueOf(b));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Update travel sync error! travelComboId=" + travelComboId, e);
		}
		JavaBeanUtil.response2Json(getResponse(), map);
	}
	
	/**
	 * 同步淘宝产品信息
	 */
	@Action(value = "/tmall/syncTaobaoTravelProduct")
	public void syncTaobaoTravelProduct() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "false");
		try {
            // 同步淘宝线路产品
            String meg = taobaoProductSyncService.syncTaobaoTravelProduct();
            map.put("meg", meg);
			map.put("success", "true");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Sync ticket info error!", e);
		}
		JavaBeanUtil.response2Json(getResponse(), map);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> request2Map(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> names = request.getParameterNames();
		while(names.hasMoreElements()){
		    String key = names.nextElement();
		    String value = request.getParameter(key);
		    if(value == null || value.trim().equals("")){
		        continue;
		    }
		    map.put(key, value);
		}
		return map;
	}
	
	private void initListInfo(List<TaobaoProductSyncPojo> productList) {
		// 所属公司
		for (TaobaoProductSyncPojo taobaoProductSyncPojo : productList) {
			String filialeName = taobaoProductSyncPojo.getFilialeName();
			if (StringUtils.isNotEmpty(filialeName)) {
				String codeName = CodeSet.getInstance().getCodeName(Constant.CODE_TYPE.FILIALE_NAME.name(), filialeName);
				taobaoProductSyncPojo.setFilialeName(codeName);
			}
		}
	}
	
	/**
	 * 获取所属公司信息
	 * @return
	 */
	public List<CodeItem> getFilialeNameList() {
		return CodeSet.getInstance().getCodeListAndBlank(Constant.CODE_TYPE.FILIALE_NAME.name());
	}
	
	public TaobaoProductSyncService getTaobaoProductSyncService() {
		return taobaoProductSyncService;
	}
	public void setTaobaoProductSyncService(
			TaobaoProductSyncService taobaoProductSyncService) {
		this.taobaoProductSyncService = taobaoProductSyncService;
	}

	public DistributionProductService getDistributionProductService() {
		return distributionProductService;
	}

	public void setDistributionProductService(
			DistributionProductService distributionProductService) {
		this.distributionProductService = distributionProductService;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getProdBranchId() {
		return prodBranchId;
	}

	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}

	public Long getTravelComboId() {
		return travelComboId;
	}

	public void setTravelComboId(Long travelComboId) {
		this.travelComboId = travelComboId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getProdBranchIds() {
		return prodBranchIds;
	}

	public void setProdBranchIds(String prodBranchIds) {
		this.prodBranchIds = prodBranchIds;
	}

	public String getIsSync() {
		return isSync;
	}

	public void setIsSync(String isSync) {
		this.isSync = isSync;
	}
}
