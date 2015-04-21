package com.lvmama.back.sweb.tmall;

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
import com.lvmama.comm.bee.po.tmall.TaobaoProductSyncPojo;
import com.lvmama.comm.bee.service.tmall.TaobaoProductSyncService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * 淘宝门票
 * 
 * @author linkai
 *
 */
@Results({
	@Result(name = "toTickePage", location = "/WEB-INF/pages/back/tmall/sync/ticket_list.jsp")
})
public class TaobaoTicketSyncAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(TaobaoTicketSyncAction.class);
	private TaobaoProductSyncService taobaoProductSyncService;
	
	private Long ticketSkuId;
	private String isSync;
	private Long itemId;
	private Long prodBranchId;
	private Long productId;
	
	/**
	 * 获取淘宝门票同步数据
	 * @return  字符串
	 */
	@Action(value = "/tmall/toTickeSyncList")
	public String toTickeSyncList() {
		pagination = super.initPagination();
		Map<String, Object> pageMap = request2Map(getRequest());
		
		Integer totalRowCount = taobaoProductSyncService.getTaobaoTicketSyncCount(pageMap);
		pagination.setTotalRecords(totalRowCount);
		pageMap.put("_startRow", pagination.getFirstRow());
		pageMap.put("_endRow", pagination.getLastRow());
		
		List<TaobaoProductSyncPojo> productList = taobaoProductSyncService.getTaobaoTicketSyncList(pageMap);
		
		initListInfo(productList);
		
		pagination.setRecords(productList);
		pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		return "toTickePage";
	}
	
	/**
	 * 同步淘宝产品
	 */
	@Action(value = "/tmall/syncTaobaoTicketProduct")
	public void syncTaobaoTicketProduct() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "false");
		try {
            // 同步淘宝门票产品
            String meg = taobaoProductSyncService.syncTaobaoTicketProduct();
            map.put("meg", meg);
            map.put("success", "true");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Sync ticket info error!", e);
		}
		JavaBeanUtil.response2Json(getResponse(), map);
	}
	
	/**
	 * 更新淘宝门票消息
	 */
	@Action(value = "/tmall/updateTaobaoTicketInfo")
	public void updateTaobaoTicketInfo() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "false");
		try {
            String userName = getSessionUser().getUserName();
			boolean isSuccess = taobaoProductSyncService.updateTaobaoTicketInfo(itemId, userName);
			map.put("success", String.valueOf(isSuccess));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Sync ticket info error!", e);
//			map.put("errInfo", "");
		}
		JavaBeanUtil.response2Json(getResponse(), map);
	}
	
	/**
	 * 更新门票 是否同步
	 */
	@Action(value = "/tmall/updateTicketIsSync")
	public void updateTicketIsSync() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "false");
		if (ticketSkuId != null && isSync != null) {
			try {
				String userName = getSessionUser().getUserName();
				taobaoProductSyncService.updateTicketIsSync(ticketSkuId, isSync, itemId, userName);
				map.put("success", "true");
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Update ticket sync error! ticketSkuId=" + ticketSkuId, e);
			}
		}
		JavaBeanUtil.response2Json(getResponse(), map);
	}
	
	/**
	 * 更新门票套餐
	 */
	@Action(value = "/tmall/updateTicketSku")
	public void updateTicketSku() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "false");
		try {
            String userName = getSessionUser().getUserName();
			boolean b = taobaoProductSyncService.updateTaobaoTicketSkuEffDates(ticketSkuId, userName);
			map.put("success", String.valueOf(b));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Update ticket sku error! ticketSkuId=" + ticketSkuId, e);
		}
		JavaBeanUtil.response2Json(getResponse(), map);
	}
	
	@SuppressWarnings("unchecked")
	private static Map<String, Object> request2Map(HttpServletRequest request){
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
	 * @return  CodeItem
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

	public Long getTicketSkuId() {
		return ticketSkuId;
	}

	public void setTicketSkuId(Long ticketSkuId) {
		this.ticketSkuId = ticketSkuId;
	}

	public String getIsSync() {
		return isSync;
	}

	public void setIsSync(String isSync) {
		this.isSync = isSync;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getProdBranchId() {
		return prodBranchId;
	}

	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
}
