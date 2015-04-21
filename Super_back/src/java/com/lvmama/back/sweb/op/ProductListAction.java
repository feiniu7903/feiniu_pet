/**
 * 
 */
package com.lvmama.back.sweb.op;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.op.OpTravelGroup;
import com.lvmama.comm.bee.service.op.IOpTravelGroupService;
import com.lvmama.comm.utils.DateUtil;

/**
 * 团的产品列表.
 * @author yangbin
 *
 */
@SuppressWarnings("serial")
@Results({
	@Result(name="success",location="/WEB-INF/pages/back/op/product_list.jsp")
})
public class ProductListAction extends BaseAction {

	private String productId;
	private String productName;
	private String travelGroupCode;
	private Date startDate;
	private Date startVisit;
	private Date endVisit;
	
	public ProductListAction(){
		super();
		startDate=DateUtil.getDayStart(new Date());//开始时间
	}
	
	/**
	 * 团服务接口
	 */
	private IOpTravelGroupService opTravelGroupService;
	
	@Action("/op/productList")
	public String execute(){
		Map<String,String> map=new HashMap<String, String>();
		if(startVisit==null){
			map.put("startVisit", DateUtil.getFormatDate(startDate, "yyyy-MM-dd"));
		}
		initPagination();
		pagination.setActionUrl(WebUtils.getUrl(getRequest(),map));
		
		
		Map<String,Object> param=buildParameter();
		long total=opTravelGroupService.selectProductCountByParam(param);
		pagination.setTotalRecords(total);
		if(total>0){
			param.put("skipResult", pagination.getFirstRow());
			param.put("maxResult", pagination.getLastRow());
			
			List<OpTravelGroup> list=opTravelGroupService.selectProductListByParam(param);
			if(CollectionUtils.isNotEmpty(list)){
				for(OpTravelGroup group:list){
					group.setSameProductGroup(opTravelGroupService.selectListByProductDate(group.getProductId(),startDate));
				}
			}
			
			pagination.setRecords(list);			
		}
		
		
		return SUCCESS;
	}
	
	private Map<String,Object> buildParameter(){
		Map<String,Object> map=new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(productId)){
			map.put("productId", productId.trim());
		}
		
		if(StringUtils.isNotEmpty(productName)){
			map.put("productName", productName.trim());
		}
		
		if(StringUtils.isNotEmpty(travelGroupCode)){
			map.put("travelGroupCode", travelGroupCode.trim());
		}
		
		if(startVisit==null){
			startVisit=startDate;
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
		
		if(!getSessionUser().isAdministrator()){
			map.put("orgId", getSessionUser().getDepartmentId());
		}
		return map;
	}

	/**
	 * @param opTravelGroupService the opTravelGroupService to set
	 */
	public void setOpTravelGroupService(IOpTravelGroupService opTravelGroupService) {
		this.opTravelGroupService = opTravelGroupService;
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
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the startVisit
	 */
	public Date getStartVisit() {
		return startVisit;
	}

	/**
	 * @param startVisit the startVisit to set
	 */
	public void setStartVisit(Date startVisit) {
		this.startVisit = startVisit;
	}

	/**
	 * @return the endVisit
	 */
	public Date getEndVisit() {
		return endVisit;
	}

	/**
	 * @param endVisit the endVisit to set
	 */
	public void setEndVisit(Date endVisit) {
		this.endVisit = endVisit;
	}

	public String getTravelGroupCode() {
		return travelGroupCode;
	}

	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	}
}
