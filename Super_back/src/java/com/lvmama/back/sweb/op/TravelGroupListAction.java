/**
 * 
 */
package com.lvmama.back.sweb.op;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.op.OpTravelGroup;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.op.IOpTravelGroupService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 计调操作团的页面
 * @author yangbin
 *
 */
@SuppressWarnings("serial")
@Results({
	@Result(name="success",location="/WEB-INF/pages/back/op/travel_group.jsp")
})
public class TravelGroupListAction extends BaseAction {

	
	private IOpTravelGroupService opTravelGroupService;
	private Date startVisit;
	private Date endVisit;
	private String travelGroupCode;
	private String productName;
	private String travelGroupStatus=Constant.TRAVEL_GROUP_STATUS.NORMAL.name();
	private String sort="1";//默认是降序排序
	private String productId;
	private String subProducts[];
	private String nofirst;//是否是第一次到该页面来
	
	/**
	 * 是否要存在订单
	 */
	private String existsOrder;
	
	/**
	 * 产品接口
	 */
	private ProdProductService prodProductService;
	/**
	 * 订单团列表
	 * @return
	 */
	@Action("/op/travelGroupList")
	public String execute()
	{
		initPagination();
		Map<String,Object> parameter=buildParameter();
		long count=opTravelGroupService.selectCountByParam(parameter);
		pagination.setTotalRecords(count);
		
		
		
		if(count>0L){
			parameter.put("skipResult", pagination.getFirstRow());
			parameter.put("maxResult", pagination.getLastRow());
			if(StringUtils.equals("1",sort)){				
				parameter.put("sort","1");//默认是降序排序
			}				
			List<OpTravelGroup> list=opTravelGroupService.selectListByParam(parameter);
			if(CollectionUtils.isNotEmpty(list)){
				for(OpTravelGroup op:list){
					TimePrice tp=prodProductService.getProductPrice(op.getProductId(), op.getVisitTime());
					if(tp!=null){
						op.setSellPrice(tp.getPrice());
						op.setSettlementPrice(tp.getSettlementPrice());
					}
				}
			}
			pagination.setRecords(list);
		}
		Map<String,String> map=new HashMap<String, String>();
		if(StringUtils.isNotEmpty(nofirst)){
			map.put("nofirst", "true");
			if(startVisit!=null){
				map.put("startVisit", DateUtil.getFormatDate(startVisit, "yyyy-MM-dd"));
			}
			if(endVisit!=null){
				map.put("endVisit", DateUtil.getFormatDate(endVisit, "yyyy-MM-dd"));
			}
		}
		pagination.setActionUrl(WebUtils.getUrl(getRequest(),map));
		
		
		Map<String,Object> productParameter=new HashMap<String, Object>();
		if(parameter.containsKey("orgId")){
			productParameter.put("orgId", parameter.get("orgId"));
		}
		productParameter.put("maxResult", 500);
		productList=opTravelGroupService.selectProductListByParam(productParameter);
		
		return SUCCESS;
	}
	
	
	
	private Map<String,Object> buildParameter()
	{
		Map<String,Object> map=new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(travelGroupCode)){
			map.put("travelGroupCode", travelGroupCode);
		}
		if(StringUtils.isNotEmpty(productId)){		
			productId=productId.trim();
//			if(NumberUtils.toLong(productId)>0){
				map.put("productId", productId);
//			}
		}
		if(StringUtils.isNotEmpty(productName)){
			map.put("productName", productName);			
		}
		if(StringUtils.isNotEmpty(travelGroupStatus)){
			map.put("travelGroupStatus", travelGroupStatus);
		}
		if(StringUtils.isEmpty(nofirst)&&startVisit==null&&endVisit==null){//做为第一次进入的该请求的操作
			startVisit=new Date();
			endVisit= DateUtils.addWeeks(startVisit, 1);
		}
		
		if(startVisit!=null||endVisit!=null){
			if(startVisit!=null&&endVisit!=null){
				if(endVisit.after(startVisit)||endVisit.equals(startVisit)){
					map.put("startVisit", DateUtil.getDayStart(startVisit));
					map.put("endVisit", DateUtil.getDayEnd(endVisit));
				}
			}else if(startVisit!=null){
				map.put("startVisit", DateUtil.getDayStart(startVisit));
			}else if(endVisit!=null){
				map.put("endVisit", DateUtil.getDayEnd(endVisit));
			}
		}
			
		if(!ArrayUtils.isEmpty(subProducts)){
			map.put("subProducts", subProducts);
		}
		if(!this.getSessionUser().isAdministrator()) {
			map.put("orgId", this.getSessionUser().getDepartmentId());
		}
		if(StringUtils.isNotEmpty(existsOrder)){
			map.put("existsOrder", true);
		}
		return map;
	}
	
	
	

	public void setOpTravelGroupService(IOpTravelGroupService opTravelGroupService) {
		this.opTravelGroupService = opTravelGroupService;
	}

	public Date getStartVisit() {
		return startVisit;
	}

	public void setStartVisit(Date startVisit) {
		this.startVisit = startVisit;
	}

	public Date getEndVisit() {
		return endVisit;
	}

	public void setEndVisit(Date endVisit) {
		this.endVisit = endVisit;
	}

	public String getTravelGroupCode() {
		return travelGroupCode;
	}

	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	}

	public String getProductName() {
		return productName;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTravelGroupStatus() {
		return travelGroupStatus;
	}

	public void setTravelGroupStatus(String travelGroupStatus) {
		this.travelGroupStatus = travelGroupStatus;
	}

	public String[] getSubProducts() {
		return subProducts;
	}

	public void setSubProducts(String[] subProducts) {
		this.subProducts = subProducts;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}



	/**
	 * @return the nofirst
	 */
	public String getNofirst() {
		return nofirst;
	}



	/**
	 * @param nofirst the nofirst to set
	 */
	public void setNofirst(String nofirst) {
		this.nofirst = nofirst;
	}



	/**
	 * @return the existsOrder
	 */
	public String getExistsOrder() {
		return existsOrder;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}



	/**
	 * @param existsOrder the existsOrder to set
	 */
	public void setExistsOrder(String existsOrder) {
		this.existsOrder = existsOrder;
	}

	public List<OpTravelGroup> getProductList(){
		return productList;
	}
	private List<OpTravelGroup> productList;
}
